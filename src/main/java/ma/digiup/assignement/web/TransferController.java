package ma.digiup.assignement.web;

import ma.digiup.assignement.domain.Utilisateur;
import ma.digiup.assignement.repository.UtilisateurRepository;
import ma.digiup.assignement.domain.Compte;
import ma.digiup.assignement.domain.Transfer;
import ma.digiup.assignement.dto.TransferDto;
import ma.digiup.assignement.exceptions.CompteNonExistantException;
import ma.digiup.assignement.exceptions.SoldeDisponibleInsuffisantException;
import ma.digiup.assignement.exceptions.TransactionException;
import ma.digiup.assignement.repository.CompteRepository;
import ma.digiup.assignement.repository.TransferRepository;
import ma.digiup.assignement.service.AuditService;
import ma.digiup.assignement.service.TransactionService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import org.springframework.web.bind.annotation.RequestMapping;


@RestController(value = "/transfers")
@RequestMapping("trans/")
@PreAuthorize("isAuthenticated()")
class TransferController {

    @Value("${montant.maximal}")
    private int MONTANT_MAXIMAL;

    Logger LOGGER = LoggerFactory.getLogger(TransferController.class);

    @Autowired
    private CompteRepository compteRepo;
    @Autowired
    private TransferRepository transferRepo;
    @Autowired
    private AuditService auditService;
    @Autowired
    private TransactionService transactionService;
    @Autowired   
    private UtilisateurRepository userRepo;

    @GetMapping("/list-of-transfers")
    List<Transfer> loadAll() {
        LOGGER.info("Lister des transfers");
        List<Transfer> allTransfers = transferRepo.findAll();
        if (CollectionUtils.isEmpty(allTransfers)) {
            return null;
        } else {
            return allTransfers;
        }
    }

    @GetMapping("/list-of-accounts")
    List<Compte> loadAllCompte() {
        List<Compte> allAccounts = compteRepo.findAll();
        if (CollectionUtils.isEmpty(allAccounts)) {
            return null;
        } else {
            return allAccounts;
        }
    }

    @GetMapping("/list-users")
    List<Utilisateur> loadAllUsers() {
        List<Utilisateur> allUsers = userRepo.findAll();

        if (CollectionUtils.isEmpty(allUsers)) {
            return null;
        } else {
            return allUsers;
        }
    }

    @PostMapping("/executer-transfers")
    @ResponseStatus(HttpStatus.CREATED)
    public void createTransaction(@RequestBody TransferDto transferDto)
            throws SoldeDisponibleInsuffisantException, CompteNonExistantException, TransactionException {

        Transfer transfer = new Transfer();
        Compte c1 = compteRepo.findByNrCompte(transferDto.getNrCompteEmetteur());
        Compte f12 = compteRepo.findByNrCompte(transferDto.getNrCompteBeneficiaire());
        BigDecimal solde = new BigDecimal(f12.getSolde().intValue() + transferDto.getMontant().intValue());
        transactionService.valideComptes(c1, f12);
        transactionService.valideMontant(transferDto);
        transactionService.valideMotif(transferDto);
        if (c1.getSolde().intValue() - transferDto.getMontant().intValue() < 0) {
            LOGGER.error("Solde insuffisant pour l'utilisateur");
        }
        c1.setSolde(c1.getSolde().subtract(transferDto.getMontant()));
        compteRepo.save(c1);
        f12.setSolde(solde);
        compteRepo.save(f12);
        transfer.setDateExecution(transferDto.getDate());
        transfer.setCompteBeneficiaire(f12);
        transfer.setCompteEmetteur(c1);
        transfer.setMontantTransfer(transferDto.getMontant());
        transferRepo.save(transfer);
        auditService.auditTransfer("Transfer depuis " + transferDto.getNrCompteEmetteur() + " vers " + transferDto
                .getNrCompteBeneficiaire() + " d'un montant de " + transferDto.getMontant().toString());
    }

    @PostMapping("/save-transaction")
    public Transfer saveTransaction(@RequestBody Transfer Transfer) {
        return transferRepo.save(Transfer);
    }

}
