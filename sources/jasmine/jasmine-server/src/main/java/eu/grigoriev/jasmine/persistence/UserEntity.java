package eu.grigoriev.jasmine.persistence;

import eu.grigoriev.jasmine.model.account.Role;
import eu.grigoriev.jasmine.model.account.User;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "user")
public class UserEntity {
    private String id;
    private String username;
    private String password;
    private Boolean active;
    private Collection<RoleEntity> roleEntities;
    private Collection<UserPropertyEntity> userPropertyEntities;

    @Id
    @Column(name = "id", nullable = false, length = 36)
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Basic
    @Column(name = "username", nullable = false, length = 255)
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Basic
    @Column(name = "password", nullable = false, length = 255)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Basic
    @Column(name = "active", nullable = true)
    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    @ManyToMany
    @JoinTable(
            name = "user_role",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id")
    )
    public Collection<RoleEntity> getRoleEntities() {
        return roleEntities;
    }

    public void setRoleEntities(Collection<RoleEntity> roleEntities) {
        this.roleEntities = roleEntities;
    }

    @OneToMany(mappedBy = "userEntity", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    public Collection<UserPropertyEntity> getUserPropertyEntities() {
        return userPropertyEntities;
    }

    public void setUserPropertyEntities(Collection<UserPropertyEntity> userPropertyEntities) {
        this.userPropertyEntities = userPropertyEntities;
    }

    public User toUser() {
        List<Role> roles = new ArrayList<>();
        for (RoleEntity roleEntity : roleEntities) {
            roles.add(new Role(roleEntity.getRole()));
        }
        return new User(id, username, active, roles);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserEntity that = (UserEntity) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(username, that.username) &&
                Objects.equals(password, that.password) &&
                Objects.equals(active, that.active) &&
                Objects.equals(roleEntities, that.roleEntities) &&
                Objects.equals(userPropertyEntities, that.userPropertyEntities);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, username, password, active, roleEntities, userPropertyEntities);
    }
}
