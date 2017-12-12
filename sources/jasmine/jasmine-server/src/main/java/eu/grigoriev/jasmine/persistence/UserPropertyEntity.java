package eu.grigoriev.jasmine.persistence;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "user_property")
public class UserPropertyEntity {
    private long id;
    private String property;
    private String value;

    @Id
    @Column(name = "id", nullable = false)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "property", nullable = false, length = 255)
    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property;
    }

    @Basic
    @Column(name = "value", nullable = false, length = 255)
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserPropertyEntity that = (UserPropertyEntity) o;
        return id == that.id &&
                Objects.equals(property, that.property) &&
                Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, property, value);
    }
}
