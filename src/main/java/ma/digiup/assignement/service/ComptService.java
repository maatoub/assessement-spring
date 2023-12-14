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
public class ComptService {

    @Autowired
    private CompteRepository compteRepository;
    public static final int MONTANT_MAXIMAL = 10000;
    Logger LOGGER = LoggerFactory.getLogger(ComptService.class);
    public void depot(DepotDto depotDto){
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

    public void valideMontant(Compte c1,Compte c2 ,TransferDto tDto) throws SoldeDisponibleInsuffisantException, CompteNonExistantException, TransactionException {
        if (c2 == null || c1 == null) {
            System.out.println("Compte Non existant");
            throw new CompteNonExistantException("Compte Non existant");
        }
        if (tDto.getMontant().equals(null)) {
            System.out.println("Montant vide");
            throw new SoldeDisponibleInsuffisantException("Montant vide");
        } else if (tDto.getMontant().intValue() == 0) {
            System.out.println("Montant vide");
            throw new SoldeDisponibleInsuffisantException("Montant vide");
        } else if (tDto.getMontant().intValue() < 10) {
            System.out.println("Montant minimal de transfer non atteint");
            throw new SoldeDisponibleInsuffisantException("Montant minimal de transfer non atteint");
        } else if (tDto.getMontant().intValue() > MONTANT_MAXIMAL) {
            System.out.println("Montant maximal de transfer dépassé");
            throw new SoldeDisponibleInsuffisantException("Montant maximal de transfer dépassé");
        }
        // valid motif
        if (tDto.getMotif().length() < 0) {
            System.out.println("Motif vide");
            throw new TransactionException("Motif vide");
        }
        // valid sold
        if (c1.getSolde().intValue() - tDto.getMontant().intValue() < 0) {
            LOGGER.error("Solde insuffisant pour l'utilisateur");
        }
    }
}
