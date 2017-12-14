#!/usr/bin/env bash

/opt/jboss/dist/bin/apply-wildfly-custom-settings.sh &
/opt/jboss/wildfly/bin/standalone.sh --debug 8787 -b 0.0.0.0 -bmanagement 0.0.0.0
