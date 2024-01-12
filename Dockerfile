# Use a lightweight Java runtime
FROM openjdk:17-jdk-slim


# Set the working directory inside the container
WORKDIR /app

# Copy the WAR file into the container at /app
COPY target/train-app.war /app/train-app.war

# Expose the port that your Spring Boot application will run on
EXPOSE 8081

# Specify the command to run your application
CMD ["java", "-jar", "train-app.war"]
