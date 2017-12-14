package eu.grigoriev.jasmine;

import org.glassfish.jersey.server.ResourceConfig;

import javax.ws.rs.ApplicationPath;

@ApplicationPath("/")
public class JASmineApplication extends ResourceConfig {

    public JASmineApplication() {
        packages(
                "eu.grigoriev.jasmine"
        );
    }
}
