package ma.digiup.assignement.service;
import javax.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ma.digiup.assignement.domain.Compte;
import ma.digiup.assignement.dto.DepotDto;
import ma.digiup.assignement.dto.TransferDto;
import ma.digiup.assignement.exceptions.CompteNonExistantException;
import ma.digiup.assignement.exceptions.SoldeDisponibleInsuffisantException;
import ma.digiup.assignement.exceptions.TransactionException;
import ma.digiup.assignement.repository.CompteRepository;

@Service
@Transactional
public class TransactionService {

    @Autowired
    private CompteRepository compteRepository;
    public static final int MONTANT_MAXIMAL = 10000;
    Logger LOGGER = LoggerFactory.getLogger(TransactionService.class);

    public void depotSolde(DepotDto depotDto){
        Compte compte = compteRepository.findByNrCompte(depotDto.getNumCompte());
        if(compte != null){
            if (depotDto.getMontant().intValue() < MONTANT_MAXIMAL) {
            compte.setSolde(compte.getSolde().add(depotDto.getMontant()));
            compteRepository.save(compte);
            }else{
                throw new RuntimeException("Le montant maximal que vous pouvez déposer par opération doit être inférieur à 10 000 DH");
            }
        }else{
            throw new RuntimeException("Compte non trouvé");
        }
    }

    public void valideComptes(Compte compteEmetteur, Compte compteBeneficiaire) throws CompteNonExistantException {
        if (compteEmetteur == null || compteBeneficiaire == null) {
            LOGGER.error("Compte Non existant");
            throw new CompteNonExistantException("Compte Non existant");
        }
    }

    public void valideMontant(TransferDto transferDto) throws SoldeDisponibleInsuffisantException {
        if (transferDto.getMontant().equals(null) || transferDto.getMontant().intValue() == 0) {
            LOGGER.error("Montant vide");
            throw new SoldeDisponibleInsuffisantException("Montant vide");
        } else if (transferDto.getMontant().intValue() < 10) {
            LOGGER.error("Montant minimal de transfer non atteint");
            throw new SoldeDisponibleInsuffisantException("Montant minimal de transfer non atteint");
        } else if (transferDto.getMontant().intValue() > MONTANT_MAXIMAL) {
            LOGGER.error("Montant maximal de transfer dépassé");
            throw new SoldeDisponibleInsuffisantException("Montant maximal de transfer dépassé");
        }
    }

    public void valideMotif(TransferDto transferDto) throws TransactionException {
        if (transferDto.getMotif().length() < 0) {
            LOGGER.error("Motif vide");
            throw new TransactionException("Motif vide");
        }
    }
    
}
