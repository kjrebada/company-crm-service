#!/usr/bin/env bash

if [ $# -eq 0 ]
then
    profile="default"
else
    profile="$1"
fi

mvn clean package -U && java -jar company-crm-api/target/company-crm-api-1.0.0.jar --spring.profiles.active=${profile}