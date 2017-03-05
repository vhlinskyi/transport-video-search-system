FROM java:8-jre
MAINTAINER Vladislav Glinskiy <glinskiy56@gmail.com>

ADD ./target/account-service.jar /app/
CMD ["java", "-Xmx200m", "-jar", "/app/account-service.jar"]

EXPOSE 5000