#!/bin/bash

CURRENT_PID=$(ps -ef | grep java | grep moabang)
if [ -z ${CURRENT_PID} ] ;then
echo "> 현재 구동중인 애플리케이션이 없으므로 종료하지 않습니다."
else
echo "> kill -9 $CURRENT_PID"
kill -9 $CURRENT_PID
sleep 10

export JENKINS_NODE_COOKIE=dontKillMe
export BUILD_ID=dontKillMe
nohup java -jar /var/lib/jenkins/workspace/test/BE/build/libs/moabang-0.0.1-SNAPSHOT.jar > /dev/null 2>&1 &
fi
