#!/bin/sh

set -e

MYSQL_CONNECTOR_JAVA_VERSION=5.1.45
MYSQL_CONNECTOR_JAVA_JAR=mysql-connector-java-${MYSQL_CONNECTOR_JAVA_VERSION}.jar
HOME=/opt/jboss
WILDFLY_HOME=${HOME}/wildfly
JBOSS_CLI=${WILDFLY_HOME}/bin/jboss-cli.sh

if [ ! -f ${WILDFLY_HOME}/$MYSQL_CONNECTOR_JAVA_JAR ]; then
    curl -sfL -o ${WILDFLY_HOME}/$MYSQL_CONNECTOR_JAVA_JAR https://repo1.maven.org/maven2/mysql/mysql-connector-java/${MYSQL_CONNECTOR_JAVA_VERSION}/${MYSQL_CONNECTOR_JAVA_JAR}
fi

MYSQL_MODULE_ADDED=${HOME}/.mysql-module-added
JDBC_DRIVER_REGISTERED=${HOME}/.jdbc-driver-registered
DATASOURCE_ADDED=${HOME}/.datasource-added

if [ -f ${DATASOURCE_ADDED} ]; then
    echo "datasource was already added - nothing to do"
    exit 0
fi

until ${JBOSS_CLI} --connect quit ; do
  echo "wildfly is not available - waiting..."
  sleep 1
done

if [ ! -f ${MYSQL_MODULE_ADDED} ]; then
    ${JBOSS_CLI} -c --command="module add --name=com.mysql --resources=$WILDFLY_HOME/$MYSQL_CONNECTOR_JAVA_JAR --dependencies=javax.api,javax.transaction.api" && touch ${MYSQL_MODULE_ADDED}
fi

if [ ! -f ${JDBC_DRIVER_REGISTERED} ]; then
    ${JBOSS_CLI} -c --command="/subsystem=datasources/jdbc-driver=mysql:add(driver-name=mysql,driver-module-name=com.mysql)" && touch ${JDBC_DRIVER_REGISTERED}
fi

if [ ! -f ${DATASOURCE_ADDED} ]; then
    ${JBOSS_CLI} -c --command="data-source add --jndi-name=java:/JASmineDS --name=JASmineDS --connection-url=jdbc:mysql://$MYSQL_HOST:3306/$MYSQL_DATABASE --driver-name=mysql --user-name=$MYSQL_USER --password=$MYSQL_PASSWORD" && touch ${DATASOURCE_ADDED}
fi

echo "`basename "$0"` executed successfully"