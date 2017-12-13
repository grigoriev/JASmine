#!/usr/bin/env bash

/opt/jboss/.bin/create-datasource.sh &
/opt/jboss/wildfly/bin/standalone.sh -b 0.0.0.0
