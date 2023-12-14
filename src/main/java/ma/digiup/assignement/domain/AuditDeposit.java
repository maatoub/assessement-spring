package ma.digiup.assignement.domain;

import ma.digiup.assignement.domain.util.EventType;

import javax.persistence.*;

import lombok.Data;

@Entity
@Data
@Table(name = "AUDIT_D")
public class AuditDeposit {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @Column(length = 100)
  private String message;

  @Enumerated(EnumType.STRING)
  private EventType eventType;

}
