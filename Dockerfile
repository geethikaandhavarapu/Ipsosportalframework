# Use an official OpenJDK 17 runtime as a parent image
FROM openjdk:17-jdk-slim

# Set environment variables to avoid prompts from debconf
ENV DEBIAN_FRONTEND=noninteractive

# Install necessary packages
RUN apt-get update && \
    apt-get install -y wget gnupg unzip curl gnupg2  libnss3 libxss1 libappindicator3-1 libgtk-3-0 xvfb ffmpeg  x11vnc x11-apps fluxbox chromium && \
    apt-get install -y openjdk-17-jdk maven python3 python3-pip   && \
    pip3 install awscli --upgrade && \
    apt-get clean && \
    rm -rf /var/lib/apt/lists/*

ENV DISPLAY=:99

# Set the working directory
WORKDIR /app

# Copy the Maven project files
COPY pom.xml /app/
COPY src /app/src
COPY dashboard /app/dashboard
RUN cd /app
RUN ls
ENV JVM_ARGS="-Xms1048m -XX:NewSize=1024m -XX:MaxNewSize=2048m -Duser.timezone=UTC"

# Package the Maven project without running tests
#assembly:single
RUN mvn -version
RUN mvn clean package dependency:copy-dependencies assembly:single -DskipTests

# Copy the generated JAR file to the app directory
#COPY target/selenium*.jar /app/

RUN wget https://repo.maven.apache.org/maven2/io/qameta/allure/allure-commandline/2.27.0/allure-commandline-2.27.0.tgz && \
    tar -zxvf allure-commandline-2.27.0.tgz -C /opt && \
    ln -s /opt/allure-2.27.0/bin/allure /usr/bin/allure && \
    allure --version && \
    rm allure-commandline-2.27.0.tgz

# Copy the entry point script
COPY entrypoint.sh /app/entrypoint.sh
RUN chmod +x /app/entrypoint.sh
CMD fluxbox & x11vnc -forever -usepw -create -display :99
CMD Xvfb :99 -screen 0 1920x1080x24
# Define the entry point
ENTRYPOINT ["/app/entrypoint.sh"]