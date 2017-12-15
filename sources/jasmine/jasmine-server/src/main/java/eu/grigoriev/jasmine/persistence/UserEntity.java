package eu.grigoriev.jasmine.persistence;

import eu.grigoriev.jasmine.model.Role;
import eu.grigoriev.jasmine.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Data
@Entity
@Table(name = "user")
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity {

    @Id
    @Column(name = "id", nullable = false, length = 36)
    private String id;

    @Basic
    @Column(name = "username", nullable = false, length = 255)
    private String username;

    @Basic
    @Column(name = "password", nullable = false, length = 255)
    private String password;

    @Basic
    @Column(name = "active", nullable = true)
    private Boolean active;

    @ManyToMany
    @JoinTable(
            name = "user_role",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id")
    )
    private Collection<RoleEntity> roleEntities;

    @OneToMany(mappedBy = "userEntity", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Collection<UserPropertyEntity> userPropertyEntities;

    public User toUser() {
        List<Role> roles = new ArrayList<>();
        for (RoleEntity roleEntity : roleEntities) {
            roles.add(new Role(roleEntity.getRole()));
        }
        return new User(id, username, active, roles);
    }
}
