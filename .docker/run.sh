#! /bin/bash

if [ -e "owish-0.0.1-SNAPSHOT.jar" ]; then
    rm -f owish-0.0.1-SNAPSHOT.jar
fi

cp ../owish/target/owish-0.0.1-SNAPSHOT.jar .

docker-compose up --build