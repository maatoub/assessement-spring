package ma.digiup.assignement.dto;

import java.math.BigDecimal;
import java.util.Date;

import lombok.Data;

@Data
public class TransferDto {
  private String nrCompteEmetteur;
  private String nrCompteBeneficiaire;
  private String motif;
  private BigDecimal montant;
  private Date date;

}
