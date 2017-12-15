package eu.grigoriev.jasmine.model;

import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
public class User {
    @NonNull
    private String id;
    @NonNull
    private String username;
    @NonNull
    private Boolean active;
    private List<Role> roles;
}
