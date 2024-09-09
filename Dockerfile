# Use an official OpenJDK image as a base image
FROM openjdk:17-jdk-alpine

# Set the working directory inside the container
WORKDIR /app

# Copy the Maven wrapper and pom.xml into the container
COPY .mvn/ .mvn
COPY mvnw pom.xml ./

# Set permissions for Maven wrapper and run Maven to download dependencies
RUN chmod +x ./mvnw
RUN ./mvnw dependency:resolve

# Copy the source code into the container
COPY src ./src

# Build the application using Maven
RUN ./mvnw package -DskipTests

# Expose the port the app runs on
EXPOSE 8080

# Set the entry point to run the JAR file
CMD ["java", "-jar", "target/Daily_Expense_Tracker-0.0.1-SNAPSHOT.jar"]
