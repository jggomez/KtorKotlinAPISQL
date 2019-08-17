# Use the official maven/Java 8 image to create a build artifact.
# https://hub.docker.com/_/maven
FROM gradle:4.10 as builder

# Copy local code to the container image.
#COPY build.gradle .
#COPY src ./src
COPY --chown=gradle . /home/app
WORKDIR /home/app

# Build a release artifact.
#RUN gradle clean build --no-daemon
RUN gradle assemble --no-daemon

# Use AdoptOpenJDK for base image.
# It's important to use OpenJDK 8u191 or above that has container support enabled.
# https://hub.docker.com/r/adoptopenjdk/openjdk8
# https://docs.docker.com/develop/develop-images/multistage-build/#use-multi-stage-builds

FROM openjdk:8-jre-alpine

# Copy the jar to the production image from the builder stage.
#COPY --from=builder build/libs/accounts-1.0.jar /app.jar
COPY --from=builder /home/app/build/libs/*.jar /home/app/server.jar

EXPOSE 3939

# Run the web service on container startup.
CMD [ "java", "-jar", "-Djava.security.egd=file:/dev/./urandom", "/home/app/server.jar" ]