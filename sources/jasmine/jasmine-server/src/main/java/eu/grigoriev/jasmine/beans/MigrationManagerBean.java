package eu.grigoriev.jasmine.beans;

import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.LiquibaseException;
import liquibase.resource.ClassLoaderResourceAccessor;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.*;
import javax.inject.Singleton;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@Slf4j
@TransactionManagement(TransactionManagementType.BEAN)
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
@Singleton
@Startup
public class MigrationManagerBean {

    private static final String DB_CHANGELOG_XML = "db/changelog.xml";

    @Resource(mappedName = "jdbc:/JASmineDS")
    private DataSource dataSource;

    @PostConstruct
    private void updateDatabaseSchema() {
        try (Connection connection = dataSource.getConnection()) {
            liquibaseUpdateWithChangelog(connection, DB_CHANGELOG_XML);
        } catch (SQLException | LiquibaseException e) {
            log.error("", e);
        }
    }

    private void liquibaseUpdateWithChangelog(Connection connection, String changelog) throws LiquibaseException {
        Database database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(new JdbcConnection(connection));
        Liquibase liquibase = new Liquibase(changelog, new ClassLoaderResourceAccessor(), database);
        liquibase.update("");
    }
}
