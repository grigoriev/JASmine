package eu.grigoriev.jasmine.persistence;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "token")
public class TokenEntity {

    @Id
    @Column(name = "id", nullable = false, length = 36)
    private String id;

    @Basic
    @Column(name = "jwt", nullable = false, length = 4096)
    private String jwt;

    @OneToOne
    @JoinColumns({
            @JoinColumn(name = "service_name", referencedColumnName = "service_name"),
            @JoinColumn(name = "username", referencedColumnName = "username")
    })
    private UserEntity userEntity;
}
