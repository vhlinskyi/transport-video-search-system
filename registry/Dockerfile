FROM java:8-jre
MAINTAINER Vladislav Glinskiy <glinskiy56@gmail.com>

ADD ./target/registry.jar /app/
CMD ["java", "-Xmx200m", "-jar", "/app/registry.jar"]

EXPOSE 8761