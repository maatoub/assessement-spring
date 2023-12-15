package ma.digiup.assignement;
import ma.digiup.assignement.domain.Utilisateur;
import ma.digiup.assignement.repository.UtilisateurRepository;
import ma.digiup.assignement.domain.Compte;
import ma.digiup.assignement.domain.RoleUser;
import ma.digiup.assignement.domain.Transfer;
import ma.digiup.assignement.repository.CompteRepository;
import ma.digiup.assignement.repository.TransferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;

import java.math.BigDecimal;
import java.util.Date; 


@SpringBootApplication
public class NiceBankApplication implements CommandLineRunner {
	@Autowired
	private CompteRepository compteRepository;
	@Autowired
	private UtilisateurRepository utilisateurRepository;
	@Autowired
	private TransferRepository transferRepository;

	public static void main(String[] args) {
		SpringApplication.run(NiceBankApplication.class, args);
	}
   
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

	CommandLineRunner commandLineRunner(JdbcUserDetailsManager detailsManager) {
		PasswordEncoder pEncoder = passwordEncoder();
		return args -> {
			detailsManager.createUser(
					User.withUsername("user1")
							.password(pEncoder.encode("1234"))
							.roles("USER").build());
			detailsManager.createUser(
					User.withUsername("nasser")
							.password(pEncoder.encode("1234"))
							.roles("USER", "admin").build());
		};

	}


	@Override
	public void run(String... strings) throws Exception {
	

		PasswordEncoder encoder = passwordEncoder();
		Utilisateur utilisateur1 = new Utilisateur();
		utilisateur1.setUsername("user1");
		utilisateur1.setLastname("last1");
		utilisateur1.setFirstname("first1");
		utilisateur1.setGender("Male");
		utilisateur1.setPassword(encoder.encode("1234"));
		//utilisateur1.setRoles("ADMIN");
		utilisateurRepository.save(utilisateur1);
		
		Utilisateur utilisateur2 = new Utilisateur();
		utilisateur2.setUsername("user2");
		utilisateur2.setLastname("last2");
		utilisateur2.setFirstname("first2");
		utilisateur2.setGender("Female");
		utilisateur2.setPassword(encoder.encode("1234"));
		utilisateurRepository.save(utilisateur2);

		Compte compte1 = new Compte();
		compte1.setNrCompte("010000A000001000");
		compte1.setRib("RIB1");
		compte1.setSolde(BigDecimal.valueOf(200000L));
		compte1.setUtilisateur(utilisateur1);

		compteRepository.save(compte1);

		Compte compte2 = new Compte();
		compte2.setNrCompte("010000B025001000");
		compte2.setRib("RIB2");
		compte2.setSolde(BigDecimal.valueOf(140000L));
		compte2.setUtilisateur(utilisateur2);

		compteRepository.save(compte2);

		Transfer v = new Transfer();
		v.setMontantTransfer(BigDecimal.TEN);
		v.setCompteBeneficiaire(compte2);
		v.setCompteEmetteur(compte1);
		v.setDateExecution(new Date());
		v.setMotifTransfer("Assignment 2021");

		transferRepository.save(v);
	}
}
