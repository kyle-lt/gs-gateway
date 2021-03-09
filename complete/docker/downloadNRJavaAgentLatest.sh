#!/bin/sh

#######################################################################
# This script will fetch the latest AppDynamics Sun/Oracle Java agent #
# and place it in /opt/appdynamics/java.                              #
# This script requires:                                               #
# curl                                                                #
# unzip       							      #
# jq 								      #
#######################################################################

# Download Root URL
#DOWNLOAD_PATH=https://download-files.appdynamics.com/
DOWNLOAD_PATH=https://download.newrelic.com/newrelic/java-agent/newrelic-agent/current/newrelic-java.zip

# Fetch latest Sun Java Agent download path from AppD
#FILE_PATH=$(curl https://download.appdynamics.com/download/downloadfilelatest/ | jq -r '.[].s3_path' | grep java-jdk8)

# Construct the full URL
#DOWNLOAD_PATH=$DOWNLOAD_PATH$FILE_PATH

# Hard-coding to 20.7.0 for testing
#DOWNLOAD_PATH=https://download-files.appdynamics.com/download-file/sun-jvm/20.7.0.30639/AppServerAgent-20.7.0.30639.zip

# Print URL to stdout
echo "Downloading NR agent from: " $DOWNLOAD_PATH

# Fetch agent and write into working directory
curl -L $DOWNLOAD_PATH -o ./newrelic-java.zip

# Create directory in which to put the agent bits
mkdir -p /opt/newrelic/java

# Unzip the agent
unzip ./newrelic-java.zip -d /opt/newrelic/java

# Remove the zip
rm -f ./newrelic-java.zip
