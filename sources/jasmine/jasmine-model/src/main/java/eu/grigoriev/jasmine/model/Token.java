package eu.grigoriev.jasmine.model;

import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Token {
    private String id;
    private String jwt;
}
