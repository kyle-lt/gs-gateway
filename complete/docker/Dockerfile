FROM openjdk:11.0.2-jre-slim 

# AppD
RUN apt-get update
RUN apt-get install -y curl jq unzip
COPY downloadJavaAgentLatest.sh .
RUN ./downloadJavaAgentLatest.sh

RUN mkdir /app
WORKDIR /app

ADD ./gs-gateway-0.1.0.jar .

### OpenShift Considerations...
### Setup user for build execution and application runtime
#ENV APP_ROOT=/app
#ENV PATH=${APP_ROOT}:${PATH} HOME=${APP_ROOT}
#RUN chmod -R u+x ${APP_ROOT} && \
#    chgrp -R 0 ${APP_ROOT} && \
#    chmod -R g=u ${APP_ROOT} /etc/passwd

### Setup user for AppD agent access
#ENV APPD_ROOT=/opt/appdynamics
#RUN chmod -R u+x ${APPD_ROOT} && \
#    chgrp -R 0 ${APPD_ROOT} && \
#    chmod -R g=u ${APPD_ROOT} /etc/passwd

### Containers should NOT run as root as a good practice
#USER 10001
#WORKDIR ${APP_ROOT}

# Add custom AppD logging spec
ADD ./log4j.xml /opt/appdynamics/java/ver21.2.0.31849/conf/logging

ENV JAVA_OPTS $JAVA_OPTS
CMD java $JAVA_OPTS \
-Dcom.sun.management.jmxremote \
-Dcom.sun.management.jmxremote.port=9010 \
-Dcom.sun.management.jmxremote.rmi.port=9010 \
-Dcom.sun.management.jmxremote.local.only=false \
-Dcom.sun.management.jmxremote.authenticate=false \
-Dcom.sun.management.jmxremote.ssl=false \
-Djava.rmi.server.hostname=0.0.0.0 \
-jar gs-gateway-0.1.0.jar