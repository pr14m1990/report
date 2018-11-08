#!/bin/sh
DIR=`dirname $0`
cd $DIR
java -server \
    -Xms1G \
    -Xmx1G \
    -XX:MetaspaceSize=96M \
    -XX:MaxMetaspaceSize=256m  \
    -Djava.net.preferIPv4Stack=true  \
    -Djava.awt.headless=true \
    -Dcom.sun.management.jmxremote \
    -cp .:./resources:./lib/* ${start-class} "$@"