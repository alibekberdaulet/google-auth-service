FROM maven:3.6-jdk-8-openj9
COPY ./ ./
WORKDIR ./
RUN mvn clean package
CMD ["java", "-jar", "target/google-auth-service-0.0.1-SNAPSHOT.jar"]