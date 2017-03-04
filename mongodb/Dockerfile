FROM mongo:3
MAINTAINER Vladislav Glinskiy <glinskiy56@gmail.com>

ADD init.sh /init.sh

RUN \
 chmod +x /init.sh

ENTRYPOINT ["/init.sh"]