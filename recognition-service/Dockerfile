FROM openjdk:8
MAINTAINER Vladislav Glinskiy <glinskiy56@gmail.com>

# Install prerequisites
RUN apt-get update && apt-get install -y \
    libopencv-dev \
    libtesseract-dev \
    git \
    cmake \
    build-essential \
    libleptonica-dev \
    liblog4cplus-dev \
    libcurl3-dev \
    beanstalkd

# Clone OpenALPR repository
RUN git clone https://github.com/openalpr/openalpr.git

RUN mkdir openalpr/src/build

WORKDIR openalpr/src/build

# Setup the compile environment
RUN cmake -DCMAKE_INSTALL_PREFIX:PATH=/usr -DCMAKE_INSTALL_SYSCONFDIR:PATH=/etc ..

# Compile the library and install the binaries/libraries to system
RUN make && make install

WORKDIR /

ADD ./target/recognition-service.jar /app/
CMD ["java", "-Xmx200m", "-jar", "/app/recognition-service.jar"]
