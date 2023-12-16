package ma.digiup.assignement.dto;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class DepotDto {

    private BigDecimal montant;
    private String numCompte;

}
