FROM openjdk:8-jre-alpine
RUN apk update && apk upgrade && apk add bash
COPY ./owish-0.0.1-SNAPSHOT.jar /usr/src/olbati/
COPY ./wait-for-it.sh /usr/src/olbati
WORKDIR /usr/src/olbati
EXPOSE 8080
CMD ["java", "-jar", "owish-0.0.1-SNAPSHOT.jar"]