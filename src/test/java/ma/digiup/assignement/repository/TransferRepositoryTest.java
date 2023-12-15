package ma.digiup.assignement.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import ma.digiup.assignement.repository.TransferRepository;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import ma.digiup.assignement.domain.Compte;
import ma.digiup.assignement.domain.Transfer;
import ma.digiup.assignement.domain.Utilisateur;

@DataJpaTest
@ExtendWith(SpringExtension.class)
public class TransferRepositoryTest {

  @Autowired
  private TestEntityManager entityManager;

  @Autowired
  private TransferRepository transferRepository;

  Utilisateur user1() {
    Utilisateur user = new Utilisateur();
    user.setBirthdate(new Date());
    user.setFirstname("adil");
    user.setLastname("last1");
    user.setUsername("saad");
  //  user.setId( (long) 2);
    user.setGender("MALE");
    user.setPassword("1234");
    return user;
  }

  Utilisateur user2() {
    Utilisateur user = new Utilisateur();
    user.setBirthdate(new Date());
    user.setFirstname("khalid");
    user.setLastname("last2");
    user.setUsername("ahmed");
    //user.setId( (long) 3);
    user.setGender("male");
    user.setPassword("1234");
    return user;
  }

  Compte compteBeneficiaire() {
    Compte compte = new Compte();
    compte.setNrCompte("196200356");
    compte.setRib("rib4");
    compte.setSolde(new BigDecimal("8925.00"));
    //compte.setId((long) 2);
    compte.setUtilisateur(user1());
    return compte;
  }

  Compte compteEmetteur() {
    Compte compte = new Compte();
    compte.setNrCompte("545415");
    compte.setRib("rib2");
    compte.setSolde(new BigDecimal("1520.50"));
    //compte.setId((long) 3);
    compte.setUtilisateur(user2());
    return compte;
  }

  @Test
  public void findOne() {
    // given
    Utilisateur user1 = user1();
    entityManager.persist(user1);
    entityManager.flush();

    Utilisateur user2 = user2();
    entityManager.merge(user2);
    entityManager.flush();

    Compte compteBeneficiaire = compteBeneficiaire();
    compteBeneficiaire.setUtilisateur(user1);
    entityManager.merge(compteBeneficiaire);
    entityManager.flush();

    Compte compteEmetteur = compteEmetteur();
    compteEmetteur.setUtilisateur(user2);
    entityManager.merge(compteEmetteur);
    entityManager.flush();

    Transfer transfer = new Transfer();
    transfer.setMontantTransfer(new BigDecimal("2005.00"));
    transfer.setCompteBeneficiaire(compteBeneficiaire);
    transfer.setCompteEmetteur(compteEmetteur);
    transfer.setDateExecution(new Date());
    transfer.setMotifTransfer("motif1");
    entityManager.persist(transfer);
    entityManager.flush();

    // when
    Optional<Transfer> found = transferRepository.findById(transfer.getId());

    // then
    assertNotNull(found);
    assertEquals(found.get().getMontantTransfer(), transfer.getMontantTransfer());
  }

  @Test
  public void findAll() {
    // given
    Transfer transfer1 = new Transfer();
    transfer1.setMontantTransfer(new BigDecimal("100.00"));
    entityManager.persist(transfer1);

    Transfer transfer2 = new Transfer();
    transfer2.setMontantTransfer(new BigDecimal("200.00"));
    entityManager.persist(transfer2);
    entityManager.flush();

    // when
    List<Transfer> transfers = transferRepository.findAll();

    // then
    assertNotNull(transfers);
    assertTrue(transfers.size() >= 2);
  }

  @Test
  public void save() {
    // given
    Transfer transfer = new Transfer();
    transfer.setMontantTransfer(new BigDecimal("100.00"));
    transfer.setMotifTransfer("ver");
    // when
    Transfer saved = transferRepository.save(transfer);

    // then
    assertNotNull(saved);
    assertNotNull(saved.getId());
    assertEquals(saved.getMontantTransfer(), transfer.getMontantTransfer());
  }

  @Test
  public void delete() {
    // given
    Transfer transfer = new Transfer();
    transfer.setMontantTransfer(new BigDecimal("100.00"));
    transfer.setMotifTransfer("ver");
    entityManager.persist(transfer);
    entityManager.flush();

    // when
    transferRepository.delete(transfer);

    // then
    Transfer found = transferRepository.findById(transfer.getId()).orElse(null);
    assertNull(found);
  }
}
