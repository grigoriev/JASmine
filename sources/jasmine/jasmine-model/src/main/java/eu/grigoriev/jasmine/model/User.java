package eu.grigoriev.jasmine.model;

import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private String serviceName;
    private String username;
    private Boolean active;
    private List<Role> roles;
}
