#!/usr/bin/env bash

pushd "`dirname $0`/../sources/jasmine" > /dev/null
mvn clean package
popd > /dev/null

pushd "`dirname $0`/.." > /dev/null
docker exec jasmine-service-dev bash -c "touch /opt/jboss/wildfly/standalone/deployments/jasmine.war.dodeploy"
popd > /dev/null
