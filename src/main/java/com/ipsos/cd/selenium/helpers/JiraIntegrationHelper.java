package com.ipsos.cd.selenium.helpers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ipsos.cd.selenium.enums.JiraFields;
import com.ipsos.cd.selenium.enums.JiraStatus;
import com.ipsos.cd.selenium.utils.LogUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.ipsos.cd.selenium.constants.FrameworkConstants.*;

public class JiraIntegrationHelper {

    private static final Logger LOGGER = Logger.getLogger(JiraIntegrationHelper.class.getName());

    private final CloseableHttpClient httpClient;
    private final ObjectMapper objectMapper;

    public JiraIntegrationHelper() {
        this.httpClient = HttpClients.createDefault();
        this.objectMapper = new ObjectMapper();
    }


    private String getAuthHeader() {
        String auth = JIRA_USERNAME + ":" + JIRA_API_TOKEN;
        byte[] encodedAuth = Base64.getEncoder().encode(auth.getBytes(StandardCharsets.ISO_8859_1));
        return "Basic " + new String(encodedAuth);
    }

    public void addCommentToIssue(String issueKey, String comment) {
        String url = JIRA_URL + "/rest/api/2/issue/" + issueKey + "/comment";
        try {
            HttpPost request = new HttpPost(url);
            request.setHeader("Authorization", getAuthHeader());
            request.setHeader("Content-Type", "application/json");

            String json = objectMapper.writeValueAsString(new Comment(comment));
            request.setEntity(new StringEntity(json));

            HttpResponse response = httpClient.execute(request);
            if (response.getStatusLine().getStatusCode() != 201) {
                throw new RuntimeException("Failed to add comment to issue: " + response.getStatusLine());
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error adding comment to JIRA issue", e);
        }
    }


    public String createIssue(String summary, String description, String sprintId, String projectKey, String assigneeAccountId) {
        String url = JIRA_URL + "/rest/api/2/issue";
        try {
            HttpPost request = new HttpPost(url);
            request.setHeader("Authorization", getAuthHeader());
            request.setHeader("Content-Type", "application/json");

            Map<String, Object> issue = new HashMap<>();
            Map<String, Object> fields = new HashMap<>();
            fields.put("project", Map.of("key", projectKey));
            fields.put("summary", summary);
            fields.put("description", description);
            fields.put("issuetype", Map.of("name", "Bug"));
            fields.put("assignee", Map.of("accountId", assigneeAccountId));
            if (StringUtils.isNotEmpty(sprintId)) fields.put(JiraFields.SPRINT_ID.getFieldName(), sprintId);
            issue.put("fields", fields);

            String json = objectMapper.writeValueAsString(issue);
            StringEntity entity = new StringEntity(json);
            request.setEntity(entity);

            HttpResponse response = httpClient.execute(request);
            if (response.getStatusLine().getStatusCode() != 201) {
                throw new RuntimeException("Failed to create issue: " + response.getStatusLine());
            }

            return objectMapper.readTree(response.getEntity().getContent()).get("key").asText();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error creating JIRA issue", e);
            return null;
        }
    }


    public Map<String, Object> getIssueDetailObject(String issueKey) {
        String url = JIRA_URL + "/rest/api/2/issue/" + issueKey;
        try {
            HttpGet request = new HttpGet(url);
            request.setHeader("Authorization", getAuthHeader());
            request.setHeader("Content-Type", "application/json");

            HttpResponse response = httpClient.execute(request);
            if (response.getStatusLine().getStatusCode() != 200) {
                throw new RuntimeException("Failed to get issue details: " + response.getStatusLine());
            }
            LOGGER.log(Level.INFO, "getIssueDetails JIRA issue details", response);
            return objectMapper.readValue(response.getEntity().getContent(), HashMap.class);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error getting JIRA issue details", e);
            return null;
        }
    }

    public void logTestReport(String issueKey, String testCaseId, String testCaseStatus) {
        String comment = "test case : " + testCaseId + " has " + testCaseStatus + " ; report and report is available : " + TEST_REPORT_BOARD + "/" + BUILD_NO + "/index.html";
        LOGGER.log(Level.INFO, "report logged for " + issueKey);
        addCommentToIssue(issueKey, comment);
    }

    public void processIssueOpen(String issueKey, String testCaseId) {
        String issueId = updateIssueStatusOrCreateIssueByActive(issueKey, JiraStatus.Reopened.toString());
        String comment = "test case : " + testCaseId + " has failed hence task has reopened/created report and report is available : " + TEST_REPORT_BOARD + "/" + BUILD_NO + "/index.html";
        addCommentToIssue(issueId, comment);
    }

    public void processIssueClose(String issueKey, String testCaseId) {
        String issueId = updateIssueStatusOrCreateIssueByActive(issueKey, JiraStatus.Done.toString());
        String comment = "test case : " + testCaseId + " has passed hence closing and report is available : " + TEST_REPORT_BOARD + "/" + BUILD_NO + "/index.html";
        addCommentToIssue(issueId, comment);
    }

    public String updateIssueStatusOrCreateIssueByActive(String issueKey, String status) {
        if (StringUtils.isNotEmpty(issueKey)) {
            try {
                Map<String, String> issueDetails = getIssueDetails(issueKey);
                String assigneeAccountId = issueDetails.get("assigneeAccountId");
                String issueSprintId = issueDetails.get("sprintId");
                String boardId = issueDetails.get("boardId");
                String issueStatus = issueDetails.get("issueStatus");
                List<Map<String, Object>> activeSprints = getActiveSprintsOrderedByStartDate(boardId);
                if (!StringUtils.equalsIgnoreCase(issueStatus, status)) {
                    for (Map<String, Object> sprint : activeSprints) {
                        if ("active".equalsIgnoreCase((String) sprint.get("state"))) {
                            activeSprints.add(sprint);
                        }
                    }
                    boolean isActiveSprint = activeSprints.stream().anyMatch(stringObjectMap -> StringUtils.equalsIgnoreCase(issueSprintId, (String) stringObjectMap.get("id")));
                    boolean isReopenTask = StringUtils.equalsIgnoreCase(issueStatus, JiraStatus.Done.toString()) && StringUtils.equalsIgnoreCase(JiraStatus.Reopened.toString(), status);
                    boolean isCloseTask = StringUtils.equalsIgnoreCase(JiraStatus.Done.toString(), status);
                    if (isActiveSprint && (isReopenTask || isCloseTask)) {
                        updateIssueStatus(issueKey, status);
                        return issueKey;
                    } else if (!activeSprints.isEmpty()) {
                        Map<String, Object> oldestActiveSprint = activeSprints.get(0);
                        String summary = "Broken old ticket jira :" + issueKey + " ; " + issueDetails.get("summary");
                        String description = issueDetails.get("description");
                        if (!StringUtils.equalsIgnoreCase(issueStatus, status)) {
                            String newIssueKey = createIssue(summary, description, (String) oldestActiveSprint.get("id"), issueDetails.get("projectKey"), assigneeAccountId);
                            LogUtils.info("new jira has created in sprint :" + oldestActiveSprint.get("id") + " ; JiraId: " + newIssueKey);
                            return newIssueKey;
                        }
                    } else {
                        Map<String, Object> oldestActiveSprint = activeSprints.get(0);
                        String summary = "Broken old ticket jira :" + issueKey + " ; " + issueDetails.get("summary");
                        String description = issueDetails.get("description");
                        String newIssueKey = createIssue(summary, description, (String) oldestActiveSprint.get("id"), issueDetails.get("projectKey"), assigneeAccountId);
                        moveIssueToBacklog(boardId, newIssueKey);
                        LogUtils.info("no active sprint found so new jira has created in backlog ; JiraId: " + newIssueKey);
                        return newIssueKey;
                    }
                } else return issueKey;
            } catch (Exception e) {
                LogUtils.error("Failed to transact Jira id : " + issueKey + " error : " + e.getMessage());
                return issueKey;
            }
        } else {
            LogUtils.error("Jira not found hence unable to create task");
            return issueKey;
        }
        return issueKey;
    }


    public Map<String, String> getIssueDetails(String issueKey) throws Exception {
        String url = JIRA_URL + "/rest/api/2/issue/" + issueKey;
        HttpGet request = new HttpGet(url);
        request.setHeader("Authorization", getAuthHeader());
        request.setHeader("Content-Type", "application/json");

        HttpResponse response = httpClient.execute(request);
        if (response.getStatusLine().getStatusCode() != 200) {
            throw new RuntimeException("Failed to get issue details: " + response.getStatusLine());
        }

        Map<String, String> taskDetails = new HashMap<>();

        Map<String, Object> issueDetails = objectMapper.readValue(response.getEntity().getContent(), HashMap.class);
        LOGGER.log(Level.INFO, "issueDetails  JIRA issue details" + issueDetails);
        Map<String, Object> fields = (Map<String, Object>) issueDetails.get("fields");
        Map<String, Object> status = (Map<String, Object>) fields.get("status");
        // Extract issue type
        Map<String, Object> issueType = (Map<String, Object>) fields.get("issuetype");
        String issueTypeName = (String) issueType.get("name");

        // Extract parent ID
        Map<String, Object> parent = (Map<String, Object>) fields.get("parent");
        String parentId = parent != null ? (String) parent.get("key") : null;

        // Extract assignee
        Map<String, Object> assignee = (Map<String, Object>) fields.get("assignee");
        String assigneeName = assignee != null ? (String) assignee.get("displayName") : null;
        String assigneeAccountId = assignee != null ? (String) assignee.get("accountId") : null;

        Map<String, Object> project = (Map<String, Object>) fields.get("project");
        String projectKey = (String) project.get("key");
        List<Map<String, Object>> sprints = (List<Map<String, Object>>) fields.get(JiraFields.SPRINT_ID.getFieldName());

        String summary = (String) fields.get("summary");
        String description = (String) fields.get("description");

        if (sprints != null && !sprints.isEmpty()) {
            Map<String, Object> sprint = sprints.get(0);
            String sprintStatus = (String) sprint.get("state");
            if (StringUtils.equalsIgnoreCase(sprintStatus, "Active")) {
                taskDetails.put("sprintId", (String) sprint.get("id"));
                taskDetails.put("boardId", (String) sprint.get("boardId"));
            }
        }

        taskDetails.put("status", (String) status.get("name"));
        taskDetails.put("issueType", issueTypeName);
        taskDetails.put("parentId", parentId);
        taskDetails.put("issueTypeName", issueTypeName);
        taskDetails.put("assigneeName", assigneeName);
        taskDetails.put("assigneeAccountId", assigneeAccountId);
        taskDetails.put("projectKey", projectKey);
        taskDetails.put("summary", summary);
        taskDetails.put("description", description);
        return taskDetails;
    }

    public List<Map<String, Object>> getActiveSprintsOrderedByStartDate(String boardId) throws Exception {
        String url = JIRA_URL + "/rest/agile/1.0/board/" + boardId + "/sprint?state=active";
        HttpGet request = new HttpGet(url);
        request.setHeader("Authorization", getAuthHeader());
        request.setHeader("Content-Type", "application/json");

        HttpResponse response = httpClient.execute(request);
        if (response.getStatusLine().getStatusCode() != 200) {
            throw new RuntimeException("Failed to get sprints: " + response.getStatusLine());
        }

        Map<String, Object> responseMap = objectMapper.readValue(response.getEntity().getContent(), HashMap.class);
        List<Map<String, Object>> sprints = (List<Map<String, Object>>) responseMap.get("values");

        List<Map<String, Object>> activeSprints = new ArrayList<>();
        for (Map<String, Object> sprint : sprints) {
            if ("active".equalsIgnoreCase((String) sprint.get("state"))) {
                activeSprints.add(sprint);
            }
        }

        // Sort active sprints by start date
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSX");
        Collections.sort(activeSprints, new Comparator<Map<String, Object>>() {
            @Override
            public int compare(Map<String, Object> o1, Map<String, Object> o2) {
                LocalDate startDate1 = LocalDate.parse((String) o1.get("startDate"), formatter);
                LocalDate startDate2 = LocalDate.parse((String) o2.get("startDate"), formatter);
                return startDate1.compareTo(startDate2);
            }
        });

        return activeSprints;
    }

    public void updateIssueStatus(String issueKey, String status) {
        String url = JIRA_URL + "/rest/api/2/issue/" + issueKey + "/transitions";
        try {
            HttpPost request = new HttpPost(url);
            request.setHeader("Authorization", getAuthHeader());
            request.setHeader("Content-Type", "application/json");

            String json = objectMapper.writeValueAsString(new Transition(status));
            request.setEntity(new StringEntity(json));

            HttpResponse response = httpClient.execute(request);
            if (response.getStatusLine().getStatusCode() != 204) {
                throw new RuntimeException("Failed to update issue status: " + response.getStatusLine());
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error updating JIRA issue status", e);
        }
    }

    public void moveIssueToBacklog(String boardId, String issueKey) {
        String url = JIRA_URL + "/rest/agile/1.0/backlog/" + boardId + "/issue";
        try {
            HttpPost request = new HttpPost(url);
            request.setHeader("Authorization", getAuthHeader());
            request.setHeader("Content-Type", "application/json");

            Map<String, Object> payload = new HashMap<>();
            payload.put("issues", Collections.singletonList(issueKey));

            String json = objectMapper.writeValueAsString(payload);
            StringEntity entity = new StringEntity(json);
            request.setEntity(entity);

            HttpResponse response = httpClient.execute(request);
            if (response.getStatusLine().getStatusCode() != 204) {
                throw new RuntimeException("Failed to move issue to backlog: " + response.getStatusLine());
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error updating JIRA issue status", e);
        }
    }


    private static class Comment {
        public String body;

        public Comment(String body) {
            this.body = body;
        }
    }

    private static class Transition {
        public TransitionId transition;

        public Transition(String status) {
            this.transition = new TransitionId(status);
        }
    }

    private static class TransitionId {
        public String id;

        public TransitionId(String status) {
            // Map status to JIRA transition ID
            // This mapping should be defined based on your JIRA workflow
            this.id = status.equals("Done") ? "31" : "51"; // Example mapping
        }
    }
}
