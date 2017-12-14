package eu.grigoriev.jasmine.model.account;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class Role {
    @NonNull
    private String role;
}
