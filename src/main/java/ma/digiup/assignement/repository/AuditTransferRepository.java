package ma.digiup.assignement.repository;

import ma.digiup.assignement.domain.AuditTransfer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuditTransferRepository extends JpaRepository<AuditTransfer, Long> {
}
