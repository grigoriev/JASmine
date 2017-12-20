package eu.grigoriev.jasmine.persistence;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Collection;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "account")
public class AccountEntity {

    @EmbeddedId
    private AccountPK id;

    @Basic
    @Column(name = "password", nullable = false, length = 255)
    private String password;

    @Basic
    @Column(name = "active", nullable = true)
    private Boolean active;

    @ManyToMany
    @JoinTable(
            name = "account_role",
            joinColumns = {
                    @JoinColumn(name = "account_service_name", referencedColumnName = "service_name"),
                    @JoinColumn(name = "username", referencedColumnName = "username")
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "role_service_name", referencedColumnName = "service_name"),
                    @JoinColumn(name = "role", referencedColumnName = "role")
            }
    )
    private Collection<RoleEntity> roleEntities;

    @OneToMany(mappedBy = "accountEntity", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Collection<AccountPropertyEntity> userPropertyEntities;
}
