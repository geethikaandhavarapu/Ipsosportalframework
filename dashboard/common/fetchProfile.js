function fetchProfile(profileJsonPath) {
    fetch(profileJsonPath).then(response => response.json()).then(res => {
        const tableInfo = document.getElementById('generalInfos');
        const keys = Object.keys(res);
        let template = '';
        keys.forEach(key => {
            template += `<tr>
                            <td>${key}</td>
                            <td>${res[key]}</td>
                        </tr>`;
        });
        tableInfo.innerHTML += template;
    });
    fetch('https://s3.us-east-2.amazonaws.com/load-test-int-report.ipsos-cd-dev.com/tasks/task_status.json').then(response => response.json()).then(res => {
        console.log(res);
        const taskStatus = document.getElementById('task_status');
        if (task_status) {
            task_status.classList.add(res.task_status);
            taskStatus.innerHTML = res.task_status;
            taskStatus.title = Object.keys(res).map(a => a + ': ' + res[a]).toString();
        }
    });
}
