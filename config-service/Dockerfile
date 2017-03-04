FROM java:8-jre
MAINTAINER Vladislav Glinskiy <glinskiy56@gmail.com>

ADD ./target/config-service.jar /app/
CMD ["java", "-Xmx200m", "-jar", "/app/config-service.jar"]

EXPOSE 8888