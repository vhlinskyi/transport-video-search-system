FROM openjdk:8
MAINTAINER Vladislav Glinskiy <glinskiy56@gmail.com>

ADD ./target/web-ui-service.jar /app/
CMD ["java", "-Xmx200m", "-jar", "/app/web-ui-service.jar"]