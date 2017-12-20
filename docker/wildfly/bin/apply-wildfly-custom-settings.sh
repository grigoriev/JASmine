#!/bin/sh

set -e

POSTGRES_CONNECTOR_VERSION="42.1.4"
POSTGRES_CONNECTOR_JAR="postgresql-$POSTGRES_CONNECTOR_VERSION.jar"

HOME="/opt/jboss"
WILDFLY_HOME="$HOME/wildfly"
WILDFLY_DEPLOYMENTS="$WILDFLY_HOME/standalone/deployments"
JBOSS_CLI="$WILDFLY_HOME/bin/jboss-cli.sh"

DIST="$HOME/dist"
DIST_BIN="$DIST/bin"
DIST_DELIVERY="$DIST/delivery"

DATASOURCE_NAME="JASmineDS"

echo "downloading https://jdbc.postgresql.org/download/$POSTGRES_CONNECTOR_JAR..."
if [ ! -f "$WILDFLY_HOME/$POSTGRES_CONNECTOR_JAR" ]; then
    curl -sfL -o "$WILDFLY_HOME/$POSTGRES_CONNECTOR_JAR" "https://jdbc.postgresql.org/download/$POSTGRES_CONNECTOR_JAR"
fi

until "$JBOSS_CLI" -c --command=":read-attribute(name=server-state)" 2> /dev/null | grep -q "running" ; do
  echo "wildfly is not available - waiting..."
  sleep 1
done

POSTGRES_MODULE_ADDED="$HOME/.postgres-module-added"
JDBC_DRIVER_REGISTERED="$HOME/.jdbc-driver-registered"
DATASOURCE_ADDED="$HOME/.datasource-added"

if [ ! -f "$POSTGRES_MODULE_ADDED" ]; then
    "$JBOSS_CLI" -c --command="module add --name=org.postgres --resources=$WILDFLY_HOME/$POSTGRES_CONNECTOR_JAR --dependencies=javax.api,javax.transaction.api" && touch "$POSTGRES_MODULE_ADDED"
else
    echo "Postgres module has been already added"
fi

if [ ! -f "$JDBC_DRIVER_REGISTERED" ]; then
    "$JBOSS_CLI" -c --command="/subsystem=datasources/jdbc-driver=postgres:add(driver-name=postgres,driver-module-name=org.postgres,driver-class-name=org.postgresql.Driver)" && touch "$JDBC_DRIVER_REGISTERED"
else
    echo "Postgres JDBC driver has been already registered"
fi

if [ ! -f "$DATASOURCE_ADDED" ]; then
    "$JBOSS_CLI" -c --command="data-source add --jndi-name=java:/$DATASOURCE_NAME --name=$DATASOURCE_NAME --connection-url=jdbc:postgresql://$POSTGRES_HOST:5432/$POSTGRES_DB --driver-name=postgres --user-name=$POSTGRES_USER --password=$POSTGRES_PASSWORD" && touch "$DATASOURCE_ADDED"
else
    echo "$DATASOURCE_NAME has been already added"
fi

for WAR in $(find "$DIST_DELIVERY" -maxdepth 1 -name "*.war")
do
    FILENAME=$(basename "$WAR")

    echo "creating symlink from $WAR to $WILDFLY_DEPLOYMENTS and marker file $FILENAME.dodeploy"
    ln -sfn "$WAR" "$WILDFLY_DEPLOYMENTS"
    touch "$WILDFLY_DEPLOYMENTS/$FILENAME.dodeploy"
done

echo "`basename "$0"` executed successfully"