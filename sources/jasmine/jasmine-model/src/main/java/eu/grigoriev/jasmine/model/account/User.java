package eu.grigoriev.jasmine.model.account;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Data
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
