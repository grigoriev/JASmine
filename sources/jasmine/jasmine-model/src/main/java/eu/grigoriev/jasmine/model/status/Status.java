package eu.grigoriev.jasmine.model.status;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@ToString
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum Status {
    OK("OK"),
    WARNING("WARN"),
    ERROR("ERROR");

    @Getter
    private final String value;
}
