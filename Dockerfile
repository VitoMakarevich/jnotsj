FROM openjdk:8-jdk-alpine
VOLUME /tmp
EXPOSE 9090
ENTRYPOINT ["java","-jar", "/tmp/libs/jnotsj-0.0.1.jar"]