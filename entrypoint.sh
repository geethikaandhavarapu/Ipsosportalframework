#!/bin/sh

# Check if the suite XML file is provided
if [ -z "$1" ]; then
  echo "Error: No TestNG suite XML file provided."
  echo "Usage: ./entrypoint.sh <path-to-suite-xml>"
  exit 1
fi

ls
CLOUD_FRONT_DIST_ID=ETV87YV8UPYFP
BUILD_NO=$1
AWS_BUCKET_NAME=selenium-test-report.ipsos-cd-dev.com
aws configure

CURRENT_TIME=$(date "+%Y%m%d_%H%M%S")

UPLOAD_SYNC_DIR=report_uploads
EMPTY_DIR=empty_dir
mkdir -p $EMPTY_DIR

cp -rf dashboard/*.html $EMPTY_DIR/
cp -rf dashboard/js $EMPTY_DIR/

aws s3 ls s3://$AWS_BUCKET_NAME --recursive --human-readable --summarize | awk '{print $5}'| awk -F '/' '/\// {print $1}' | grep -o '[0-9]*' | sort -u > $EMPTY_DIR/folders.txt
echo "[" > $EMPTY_DIR/tprofiles.json
 echo "{\"buildNumber\":$BUILD_NO , \"isReportAvailable\":false}," >> $EMPTY_DIR/tprofiles.json
while IFS= read -r line; do
    echo "{\"buildNumber\":$line , \"isReportAvailable\":true}," >> $EMPTY_DIR/tprofiles.json
done < $EMPTY_DIR/folders.txt
sed '$s/,$//' $EMPTY_DIR/tprofiles.json > $EMPTY_DIR/profiles.json
echo "]" >> $EMPTY_DIR/profiles.json

aws s3 sync $EMPTY_DIR s3://$AWS_BUCKET_NAME --acl public-read

aws cloudfront create-invalidation --distribution-id $CLOUD_FRONT_DIST_ID --paths "/*"

# Run the TestNG tests with the provided suite XML file
#ls
#ls target/
#ls target/test-classes/
#ls target/test-classes/testdataRegion/
#ls target/test-classes/suites/
#ls target/test-classes/config/
#cat src/test/resources/suites/region/LoginTestRegion.xml

xvfb-run java -cp target/selenium-2.4.0.jar:target/selenium-2.4.0-tests.jar:target/selenium-2.4.0-jar-with-dependencies.jar:target/dependency/:target/test-classes/:target/classes org.testng.TestNG $1
#mvn compile package test

# generate allure report
allure generate --single-file allure-results --clean -o allure-report
mkdir -p $UPLOAD_SYNC_DIR
cp -rf allure-report $UPLOAD_SYNC_DIR/
cp -rf ExportData $UPLOAD_SYNC_DIR/
cp -rf ExportData $UPLOAD_SYNC_DIR/
cp -rf reports/ExtentReports/ExtentReports.html $UPLOAD_SYNC_DIR/index.html
cp -rf logs $UPLOAD_SYNC_DIR/

PROFILE_FILE=$UPLOAD_SYNC_DIR/profile.json
CONFIG_ENV_FILE=$UPLOAD_SYNC_DIR/config.env

# create if not there very first time
cp -rf $EMPTY_DIR/profiles.json .
rm -rf $EMPTY_DIR/*
sed -e 's/false/true/g' profiles.json > $EMPTY_DIR/profiles.json

cat $EMPTY_DIR/profiles.json

aws s3 sync $EMPTY_DIR s3://$AWS_BUCKET_NAME/$REMOTE_UPLOAD_SYNC_DIR  --acl public-read

rm -rf $EMPTY_DIR/*
aws s3 sync $EMPTY_DIR s3://$AWS_BUCKET_NAME/latest/$REMOTE_UPLOAD_SYNC_DIR  --acl public-read
aws s3 sync $UPLOAD_SYNC_DIR s3://$AWS_BUCKET_NAME/latest/$REMOTE_UPLOAD_SYNC_DIR  --acl public-read
aws s3 sync $UPLOAD_SYNC_DIR s3://$AWS_BUCKET_NAME/$BUILD_NO/$REMOTE_UPLOAD_SYNC_DIR  --acl public-read

aws cloudfront create-invalidation --distribution-id $CLOUD_FRONT_DIST_ID --paths "/*"
