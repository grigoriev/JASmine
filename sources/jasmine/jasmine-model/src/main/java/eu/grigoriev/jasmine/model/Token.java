package eu.grigoriev.jasmine.model;

import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Token {
    @NonNull
    private String id;
    @NonNull
    private String username;
    @NonNull
    private String token;
}
