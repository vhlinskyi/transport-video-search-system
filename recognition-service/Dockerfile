FROM java:8-jre
MAINTAINER Vladislav Glinskiy <glinskiy56@gmail.com>

ADD ./target/recognition-service.jar /app/
CMD ["java", "-Xmx200m", "-jar", "/app/recognition-service.jar"]
