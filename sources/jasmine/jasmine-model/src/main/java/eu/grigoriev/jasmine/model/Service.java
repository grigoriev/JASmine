package eu.grigoriev.jasmine.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.ws.rs.WebApplicationException;
import java.io.IOException;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Service {
    @NonNull
    private String name;
    private String description;

    public static Service fromString(String jsonRepresentation) {
        try {
            return new ObjectMapper().readValue(jsonRepresentation, Service.class);
        } catch (IOException e) {
            throw new WebApplicationException();
        }
    }
}
