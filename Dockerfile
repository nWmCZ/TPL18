FROM tomcat:9-jre8

ARG BUILD_DATE

ARG VCS_REF

LABEL   org.label-schema.build-date=$BUILD_DATE \
        org.label-schema.vcs-url="https://github.com/nWmCZ/TPL18.git" \
        org.label-schema.vcs-ref=$VCS_REF \
        org.label-schema.schema-version="1.0.0-rc.1"

EXPOSE 8080

RUN mkdir -p /Work/tpl/in /Work/tpl/out /Work/tpl/in_processed /Work/tpl/out_ack

COPY ./devops/tomcat/setenv.sh /usr/local/tomcat/bin/setenv.sh

COPY ./src/main/resources/application.properties /Work/tpl/

COPY ./target/tpl*.war /usr/local/tomcat/webapps/tpl.war
