package eu.grigoriev.jasmine.persistence;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "service")
public class ServiceEntity {
    @Id
    @Column(name = "name", nullable = false, length = 255)
    private String name;

    @Basic
    @Column(name = "description", nullable = true, length = 1024)
    private String description;

    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(mappedBy = "id.serviceEntity", cascade = CascadeType.ALL)
    private Collection<UserEntity> userEntities;

    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(mappedBy = "id.serviceEntity", cascade = CascadeType.ALL)
    private Collection<RoleEntity> roleEntities;
}
