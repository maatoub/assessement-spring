package ma.digiup.assignement.dto;

import java.math.BigDecimal;

public class DepotDto {
    private BigDecimal montant;
    private String numCompte;
    public BigDecimal getMontant() {
        return montant;
    }
    public void setMontant(BigDecimal montant) {
        this.montant = montant;
    }
 
    public String getNumCompte() {
        return numCompte;
    }
    public void setNumCompte(String numCompte) {
        this.numCompte = numCompte;
    }
}
