package eu.grigoriev.jasmine.persistence;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "application")
public class ApplicationEntity {
    private String id;
    private String application;

    @Id
    @Column(name = "id", nullable = false, length = 36)
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Basic
    @Column(name = "application", nullable = false, length = 255)
    public String getApplication() {
        return application;
    }

    public void setApplication(String application) {
        this.application = application;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ApplicationEntity that = (ApplicationEntity) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(application, that.application);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, application);
    }
}
