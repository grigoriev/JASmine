package eu.grigoriev.jasmine.services;

import lombok.extern.slf4j.Slf4j;
import org.junit.ClassRule;
import org.junit.Test;
import org.testcontainers.containers.Container;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.output.OutputFrame;
import org.testcontainers.containers.output.Slf4jLogConsumer;
import org.testcontainers.containers.output.ToStringConsumer;
import org.testcontainers.containers.wait.HostPortWaitStrategy;

import java.io.IOException;

@Slf4j
public class AccountServiceTest {

    public static final int DB_PORT = 5432;
    public static final int SERVICE_PORT = 8080;

    public static final String JASMINEDB = "jasminedb";
    public static final String JASMINEUSER = "jasmineuser";
    public static final String JASMINEPASS = "jasminepass";

    private static Slf4jLogConsumer logConsumer = new Slf4jLogConsumer(log);
    private static ToStringConsumer toStringConsumer = new ToStringConsumer();

//    @ClassRule
//    public static GenericContainer mysql = new GenericContainer("mysql:5.7")
//            .withEnv("MYSQL_RANDOM_ROOT_PASSWORD", "yes")
//            .withEnv("MYSQL_DATABASE", JASMINEDB)
//            .withEnv("MYSQL_USER", JASMINEUSER)
//            .withEnv("MYSQL_PASSWORD", JASMINEPASS)
//            .waitingFor(new HostPortWaitStrategy())
//            .withLogConsumer(logConsumer)
//            .withExposedPorts(DB_PORT);
//
//    @ClassRule
//    public static GenericContainer wildfly = new GenericContainer("jboss/wildfly:11.0.0.Final")
//            .withEnv("MYSQL_DATABASE", JASMINEDB)
//            .withEnv("MYSQL_USER", JASMINEUSER)
//            .withEnv("MYSQL_PASSWORD", JASMINEPASS)
//            .waitingFor(new HostPortWaitStrategy())
//            .withLogConsumer(logConsumer)
//            .withExposedPorts(SERVICE_PORT);

    @Test
    public void register() {
    }

    @Test
    public void info() throws IOException, InterruptedException {
//        wildfly.followOutput(toStringConsumer, OutputFrame.OutputType.STDOUT);
//
//        wildfly.addEnv("MYSQL_HOST", mysql.getContainerIpAddress());
//        wildfly.addEnv("MYSQL_PORT", String.valueOf(mysql.getMappedPort(DB_PORT)));
//
//        String utf8String = toStringConsumer.toUtf8String();
//
//        Container.ExecResult execResult = wildfly.execInContainer("ls", "-la");
    }

    @Test
    public void update() {
    }

    @Test
    public void delete() {
    }
}