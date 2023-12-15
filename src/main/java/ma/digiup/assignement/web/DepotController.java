package ma.digiup.assignement.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ma.digiup.assignement.dto.DepotDto;
import ma.digiup.assignement.service.TransactionService;

@RestController
public class DepotController {
    public static final int MONTANT_MAXIMAL = 10000;
    @Autowired
    private TransactionService comptService;
        @PostMapping("/depot-sold")
        public String depositSolde(@RequestBody DepotDto depotDto) {
           comptService.depotSolde(depotDto);
           return "le compte "+depotDto.getNumCompte() +" a recue le montant de : "+depotDto.getMontant();
        }
}



