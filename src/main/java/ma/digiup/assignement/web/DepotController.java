package ma.digiup.assignement.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ma.digiup.assignement.dto.DepotDto;
import ma.digiup.assignement.service.ComptService;

@RestController
public class DepotController {
    public static final int MONTANT_MAXIMAL = 10000;
    @Autowired
    private ComptService comptService;
        @PostMapping("/depot-sold")
        public String deposit(@RequestBody DepotDto depotDto) {
           comptService.depot(depotDto);
           return "le compte "+depotDto.getNumCompte() +" a recue le montant de : "+depotDto.getMontant();
        }
}



