package eu.grigoriev.jasmine.listeners;

import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.LiquibaseException;
import liquibase.resource.ClassLoaderResourceAccessor;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Resource;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@Slf4j
@WebListener
public class DatabaseMigrationInvoker implements ServletContextListener {

    private static final String DB_CHANGELOG_XML = "/db/changelog.xml";

    @Resource(mappedName = "java:/JASmineDS")
    private DataSource dataSource;

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        invokeLiquibaseUpdate();
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
    }

    private void invokeLiquibaseUpdate() {
        try (Connection connection = dataSource.getConnection()) {
            log.info("starting database update...");

            Database database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(new JdbcConnection(connection));
            Liquibase liquibase = new Liquibase(DB_CHANGELOG_XML, new ClassLoaderResourceAccessor(), database);
            liquibase.update("");

            log.info("database update executed");
        } catch (SQLException | LiquibaseException e) {
            log.error("", e);
        }
    }
}
