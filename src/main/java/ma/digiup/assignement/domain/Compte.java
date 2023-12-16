package ma.digiup.assignement.domain;

import javax.persistence.*;

import lombok.Data;

import java.math.BigDecimal;

@Entity
@Data
@Table(name = "COMPTE")
public class Compte {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @Column(length = 16, unique = true, nullable = false)
  private String nrCompte;
  @Column(length = 16, unique = true, nullable = false)
  private String rib;

  @Column(precision = 16, scale = 2, nullable = false)
  private BigDecimal solde;

  @ManyToOne()
  @JoinColumn(name = "utilisateur_id")
  private Utilisateur utilisateur;
}
