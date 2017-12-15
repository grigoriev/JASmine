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
public class Application {
    @NonNull
    private String name;
    private String description;

    public static Application fromString(String jsonRepresentation) {
        try {
            return new ObjectMapper().readValue(jsonRepresentation, Application.class);
        } catch (IOException e) {
            throw new WebApplicationException();
        }
    }
}
