package ma.digiup.assignement.security;

import java.util.Arrays;
import java.util.Collections;
import javax.crypto.spec.SecretKeySpec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.web.SecurityFilterChain;
import com.nimbusds.jose.jwk.source.ImmutableSecret;

import ma.digiup.assignement.service.AuthService;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class SecurityConfig {

    

    @Autowired
    private AuthService authService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity https) throws Exception {
        return https
                .csrf(arg -> arg.disable())
                .sessionManagement(args -> args.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .requestMatchers()
                .antMatchers("/")
                .and()
                .authorizeHttpRequests(arg -> arg.anyRequest().authenticated())
                .oauth2ResourceServer(oauth2 -> oauth2.jwt()
                        .jwtAuthenticationConverter(jwt -> {
                            String username = jwt.getSubject();
                            return new UsernamePasswordAuthenticationToken(
                                    username,
                                    "n/a",
                                    Collections.emptyList() // Pas d'autorités
                            );
                        }))

                .build();
    }

    @Bean
    public JwtEncoder jwtEncoder() {
        String keySecret = "a3f5b3b7a7c3d4e5f3e6d7c2b3a4c5e6f3a4b5c6d7e3f2a3b4c5d6e7f8a9b2c3";
        return new NimbusJwtEncoder(new ImmutableSecret<>(keySecret.getBytes()));
    }

    @Bean
    public JwtDecoder jwtDecoder() {
        String keySecret = "a3f5b3b7a7c3d4e5f3e6d7c2b3a4c5e6f3a4b5c6d7e3f2a3b4c5d6e7f8a9b2c3";
        SecretKeySpec secretKeySpec = new SecretKeySpec(keySecret.getBytes(), "HmacSHA256");
        return NimbusJwtDecoder.withSecretKey(secretKeySpec).macAlgorithm(MacAlgorithm.HS256).build();
    }

    @Bean
    public AuthenticationManager authenticationManager() {
        DaoAuthenticationProvider dao = new DaoAuthenticationProvider();
        dao.setPasswordEncoder(passwordEncoder());
        dao.setUserDetailsService(authService);
        return new ProviderManager(Arrays.asList(dao));
    }
}

// .jwtAuthenticationConverter(jwt -> {
// String username = jwt.getSubject();
// return new UsernamePasswordAuthenticationToken(
// username,
// "n/a",
// Collections.emptyList() // Pas d'autorités
// );
// })