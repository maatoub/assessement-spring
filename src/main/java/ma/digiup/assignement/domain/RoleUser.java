package ma.digiup.assignement.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import lombok.Data;


// @Data
// @Entity
public class RoleUser implements Serializable {
    @Id
    private String role;
}
