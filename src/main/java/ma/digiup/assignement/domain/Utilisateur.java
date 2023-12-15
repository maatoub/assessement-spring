package ma.digiup.assignement.domain;

import javax.persistence.*;
import lombok.Data;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Data
@Table(name = "UTILISATEUR")
public class Utilisateur implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(length = 10, nullable = false, unique = true)
    private String username;

    @Column(length = 60, nullable = false)
    private String password;

    @Column(length = 10, nullable = false)
    private String gender;

    @Column(length = 60, nullable = false)
    private String lastname;

    @Column(length = 60, nullable = false)
    private String firstname;

    @Temporal(TemporalType.DATE)
    private Date birthdate;

    // @ManyToMany(fetch = FetchType.EAGER)
    // private List<RoleUser> roles;

    // public Utilisateur() {
    // this.roles = new ArrayList<>();
    // }
}
