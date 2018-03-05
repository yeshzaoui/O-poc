FROM docker.elastic.co/logstash/logstash:6.2.2
RUN rm -f /usr/share/logstash/pipeline/logstash.conf
ADD pipeline/ /usr/share/logstash/pipeline/
ADD ./mysql-connector-java-5.1.45.jar /usr/share/logstash/bin/