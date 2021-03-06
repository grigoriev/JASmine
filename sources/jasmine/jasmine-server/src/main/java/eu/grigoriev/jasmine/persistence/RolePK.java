package eu.grigoriev.jasmine.persistence;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class RolePK implements Serializable {

    @ManyToOne
    @JoinColumn(name = "service_name")
    private ServiceEntity serviceEntity;

    @Basic
    @Column(name = "role", nullable = false, length = 255)
    private String role;
}
