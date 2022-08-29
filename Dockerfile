FROM openjdk:11-jre-slim
ARG JAR_FILE=*.jar
COPY target/${JAR_FILE} application.jar
COPY entrypoint.sh .
ENV DEBUG_PORT 9999
ENV DEBUG_MODE true

RUN chmod +x application.jar
RUN chmod +x entrypoint.sh

EXPOSE 9090 9999

ENTRYPOINT ["/entrypoint.sh"]