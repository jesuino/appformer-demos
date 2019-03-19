FROM java:openjdk-8-jdk
ADD target/standalone-editor-thorntail.jar /opt/standalone-editor-thorntail.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/opt/standalone-editor-thorntail.jar", "-Djava.net.preferIPv4Stack=true"]

