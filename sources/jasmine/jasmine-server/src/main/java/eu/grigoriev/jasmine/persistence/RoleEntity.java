package eu.grigoriev.jasmine.persistence;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "role")
public class RoleEntity {

    @EmbeddedId
    private RolePK id;

    @Basic
    @Column(name = "default_assignment", nullable = true)
    private Boolean defaultAssignment;

    @Basic
    @Column(name = "description", nullable = true, length = 1024)
    private String description;

    @ManyToMany(mappedBy = "roleEntities")
    private Collection<UserEntity> userEntities;
}
