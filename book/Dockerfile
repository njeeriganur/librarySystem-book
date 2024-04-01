# Use the official OpenJDK image as the base image
FROM openjdk:17-oracle

# Set the working directory in the container
WORKDIR /book

# Copy the JAR file from the build stage to the container

COPY /target/book-0.0.1-SNAPSHOT.jar book.jar

# Expose the port the application runs on
EXPOSE 8080

# Command to run the Spring Boot application
CMD ["java", "-jar", "book.jar"]