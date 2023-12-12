package ma.digiup.assignement.service;

import java.math.BigDecimal;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ma.digiup.assignement.domain.Compte;
import ma.digiup.assignement.repository.CompteRepository;

@Service
@Transactional
public class ComptService {

    @Autowired
    private CompteRepository compteRepository;
    
    public void depot(BigDecimal  montant, String numCompte){
        Compte compte = compteRepository.findByNrCompte(numCompte);
        if(compte == null){
            throw new RuntimeException("Compte non trouvé");
        }else{
            compte.setSolde(compte.getSolde().add(montant));
            compteRepository.save(compte);
        }
    }
}
