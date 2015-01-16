#!/bin/sh

cd ..

nohup /opt/bea/bea1033/jdk1.6.0_20/bin/java \
-Ddb2process \
-jar target/db2process-1.0-SNAPSHOT.jar &
