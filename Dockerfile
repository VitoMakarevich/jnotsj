FROM gradle:5.4.1-jdk8-alpine
MAINTAINER vitomakarevich
VOLUME /tmp
EXPOSE 9090
ADD /build/libs/jnotsj-0.0.1.jar ./jnotsj.jar
ENTRYPOINT ["java", "-jar", "jnotsj.jar"]