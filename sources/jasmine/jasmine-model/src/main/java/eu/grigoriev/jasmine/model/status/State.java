package eu.grigoriev.jasmine.model.status;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class State {
    @NonNull
    private Status status;
    private String message;
}
