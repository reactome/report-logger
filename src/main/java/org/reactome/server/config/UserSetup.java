package org.reactome.server.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * To have this configuration here to fix BeanCurrentlyInCreationException, details are below
 * <a href="https://stackoverflow.com/questions/70130209/beancurrentlyincreationexception-after-upgrading-boot-to-2-6">...</a>
 */

@Configuration
public class UserSetup {

    private final String user;
    private final String password;

    public UserSetup(@Value("${report.user}") String user,
                     @Value("${report.password}") String password) {
        this.user = user;
        this.password = password;
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth, PasswordEncoder passwordEncoder) throws Exception {
        String passwordEncoded = passwordEncoder.encode(password);
        auth.inMemoryAuthentication()
                .withUser(user).password(passwordEncoded)
                .authorities("ROLE_REPORTER");
    }
}
