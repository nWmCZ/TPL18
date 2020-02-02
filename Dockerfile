FROM maven:3-jdk-8
RUN git clone https://github.com/nWmCZ/TPL18.git /TPL18.git
WORKDIR /TPL18.git
RUN mvn clean install

FROM openjdk:8-alpine
ARG BUILD_DATE

ARG VCS_REF

LABEL   org.label-schema.build-date=$BUILD_DATE \
        org.label-schema.vcs-url="https://github.com/nWmCZ/TPL18.git" \
        org.label-schema.vcs-ref=$VCS_REF \
        org.label-schema.schema-version="1.0.0-rc.1"

EXPOSE 8080
COPY --from=0 /TPL18.git/target/tpl18-0.0.1-SNAPSHOT.jar /
COPY ./src/main/resources/application.properties /Work/tpl/
RUN mkdir -p /Work/tpl/in /Work/tpl/out /Work/tpl/in_processed /Work/tpl/out_ack

ENTRYPOINT ["/usr/bin/java", "-jar", "/tpl18-0.0.1-SNAPSHOT.jar", "-Dspring.config.location=/Work/tpl/application.properties"]
