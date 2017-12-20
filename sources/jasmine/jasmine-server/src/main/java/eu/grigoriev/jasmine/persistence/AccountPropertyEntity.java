package eu.grigoriev.jasmine.persistence;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "account_property")
public class AccountPropertyEntity {

    @Id
    @Column(name = "id", nullable = false)
    private long id;

    @Basic
    @Column(name = "property", nullable = false, length = 255)
    private String property;

    @Basic
    @Column(name = "value", nullable = false, length = 255)
    private String value;

    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "service_name", referencedColumnName = "service_name"),
            @JoinColumn(name = "username", referencedColumnName = "username")
    })
    private AccountEntity accountEntity;
}
