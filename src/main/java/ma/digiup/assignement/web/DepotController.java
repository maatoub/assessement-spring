package ma.digiup.assignement.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ma.digiup.assignement.dto.DepotDto;
import ma.digiup.assignement.service.TransactionService;

@RestController
public class DepotController {

    @Value("${montant.maximal}")
    private int MONTANT_MAXIMAL;
    @Autowired
    private TransactionService comptService;

        @PostMapping("/deposit-sold")
        public String depositSolde(@RequestBody DepotDto depotDto) {
           comptService.depotSolde(depotDto);
           return "le compte "+depotDto.getNumCompte() +" a recue le montant de : "+depotDto.getMontant();
        }
}



