package ma.digiup.assignement.repository;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.util.List;
import javax.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ma.digiup.assignement.domain.Transfer;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
@DataJpaTest
public class TransferRepositoryTest {

  @Autowired
  private TestEntityManager entityManager;

  @Autowired
  private TransferRepository transferRepository;

  @Test
  public void findOne() {
    // given
    Transfer transfer = new Transfer();
    transfer.setMontantTransfer(new BigDecimal("100.00"));
    entityManager.persist(transfer);
    entityManager.flush();

    // when
    Transfer found = transferRepository.findById(transfer.getId()).orElse(null);

    // then
    assertNotNull(found);
    assertEquals(found.getMontantTransfer(), transfer.getMontantTransfer());
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
    entityManager.persist(transfer);
    entityManager.flush();

    // when
    transferRepository.delete(transfer);

    // then
    Transfer found = transferRepository.findById(transfer.getId()).orElse(null);
    assertNull(found);
  }
}
