# Use an official java runtime as a parent image
FROM openjdk:8

# Set the working directory to /app
WORKDIR /data/app

# Copy the current directory contents into the container at /app
ADD . /data/app
# COPY . /usr/src/myapp

RUN apt-get update && apt-get install -y maven

# Build the application
RUN mvn clean install

# Make port 8181 available to the world outside this container
EXPOSE 8181

# Set proxy server, replace host:port with values for your servers
#ENV http_proxy host:port
#ENV https_proxy host:port

# Declare env variables
ENV MAIN_JAR target/estore-0.0.1-SNAPSHOT.jar
RUN echo "Main jar file is $MAIN_JAR"

# Run app when the container launches
#CMD ["java", "-jar", "target/estore-0.0.1-SNAPSHOT.jar"]

# or using entry point
ENTRYPOINT java -jar $MAIN_JAR


# Run the docker container with command >> docker run -it 1122:8181 estore:v0

# Open URL (http://localhost:1122/estore/test) to test running webservice