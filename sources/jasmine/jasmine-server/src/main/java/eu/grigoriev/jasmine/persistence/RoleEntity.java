package eu.grigoriev.jasmine.persistence;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "role")
public class RoleEntity {
    private String id;
    private String role;
    private Collection<UserEntity> userEntities;

    @Id
    @Column(name = "id", nullable = false, length = 36)
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Basic
    @Column(name = "role", nullable = false, length = 255)
    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @ManyToMany(mappedBy = "roleEntities")
    public Collection<UserEntity> getUserEntities() {
        return userEntities;
    }

    public void setUserEntities(Collection<UserEntity> userEntities) {
        this.userEntities = userEntities;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RoleEntity that = (RoleEntity) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(role, that.role) &&
                Objects.equals(userEntities, that.userEntities);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, role, userEntities);
    }
}
