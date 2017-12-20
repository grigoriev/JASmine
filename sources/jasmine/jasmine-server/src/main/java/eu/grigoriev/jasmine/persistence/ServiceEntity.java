package eu.grigoriev.jasmine.persistence;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.Collection;

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
    private Collection<AccountEntity> accountEntities;

    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(mappedBy = "id.serviceEntity", cascade = CascadeType.ALL)
    private Collection<RoleEntity> roleEntities;
}
