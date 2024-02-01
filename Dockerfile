# Use an official OpenJDK runtime as a base image
FROM openjdk:17-jdk-slim

# Refer to Maven build -> finalName
ARG JAR_FILE=target/movies-api-0.0.1-SNAPSHOT.jar

# Set the working directory in the container
WORKDIR /app

# Copy the JAR file into the container at /app
COPY ${JAR_FILE} app.jar

# Expose the port that your application runs on
EXPOSE 8080

# Specify the command to run your application
ENTRYPOINT ["java","-jar","app.jar"]