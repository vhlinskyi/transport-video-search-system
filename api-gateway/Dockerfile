FROM java:8-jre
MAINTAINER Vladislav Glinskiy <glinskiy56@gmail.com>

ADD ./target/api-gateway.jar /app/
CMD ["java", "-Xmx200m", "-jar", "/app/api-gateway.jar"]

EXPOSE 4000