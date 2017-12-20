#!/bin/sh

set -e

MYSQL_CONNECTOR_JAVA_VERSION="5.1.45"
MYSQL_CONNECTOR_JAVA_JAR="mysql-connector-java-$MYSQL_CONNECTOR_JAVA_VERSION.jar"

HOME="/opt/jboss"
WILDFLY_HOME="$HOME/wildfly"
WILDFLY_DEPLOYMENTS="$WILDFLY_HOME/standalone/deployments"
JBOSS_CLI="$WILDFLY_HOME/bin/jboss-cli.sh"

DIST="$HOME/dist"
DIST_BIN="$DIST/bin"
DIST_DELIVERY="$DIST/delivery"

DATASOURCE_NAME="JASmineDS"

if [ ! -f "$WILDFLY_HOME/$MYSQL_CONNECTOR_JAVA_JAR" ]; then
    curl -sfL -o "$WILDFLY_HOME/$MYSQL_CONNECTOR_JAVA_JAR" "https://repo1.maven.org/maven2/mysql/mysql-connector-java/$MYSQL_CONNECTOR_JAVA_VERSION/$MYSQL_CONNECTOR_JAVA_JAR"
fi

until "$JBOSS_CLI" -c --command=":read-attribute(name=server-state)" 2> /dev/null | grep -q "running" ; do
  echo "wildfly is not available - waiting..."
  sleep 1
done

MYSQL_MODULE_ADDED="$HOME/.mysql-module-added"
JDBC_DRIVER_REGISTERED="$HOME/.jdbc-driver-registered"
DATASOURCE_ADDED="$HOME/.datasource-added"

if [ ! -f "$MYSQL_MODULE_ADDED" ]; then
    "$JBOSS_CLI" -c --command="module add --name=com.mysql --resources=$WILDFLY_HOME/$MYSQL_CONNECTOR_JAVA_JAR --dependencies=javax.api,javax.transaction.api" && touch "$MYSQL_MODULE_ADDED"
else
    echo "MySQL module has been already added"
fi

if [ ! -f "$JDBC_DRIVER_REGISTERED" ]; then
    "$JBOSS_CLI" -c --command="/subsystem=datasources/jdbc-driver=mysql:add(driver-name=mysql,driver-module-name=com.mysql)" && touch "$JDBC_DRIVER_REGISTERED"
else
    echo "MySQL JDBC driver has been already registered"
fi

if [ ! -f "$DATASOURCE_ADDED" ]; then
    "$JBOSS_CLI" -c --command="data-source add --jndi-name=java:/$DATASOURCE_NAME --name=$DATASOURCE_NAME --connection-url=jdbc:mysql://$MYSQL_HOST:3306/$MYSQL_DATABASE --driver-name=mysql --user-name=$MYSQL_USER --password=$MYSQL_PASSWORD" && touch "$DATASOURCE_ADDED"
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