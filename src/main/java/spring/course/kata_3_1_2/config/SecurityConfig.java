package spring.course.kata_3_1_2.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import spring.course.kata_3_1_2.services.PersonDetailsService;

import static org.springframework.security.config.Customizer.withDefaults;

@EnableWebSecurity
@Configuration
public class SecurityConfig {

    private final PersonDetailsService personDetailsService;

    @Autowired
    public SecurityConfig(PersonDetailsService personDetailsService) {
        this.personDetailsService = personDetailsService;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(
            auth -> auth.requestMatchers("/auth/login", "/auth/registration").permitAll()
            .requestMatchers("/admin").hasRole("ADMIN")
            .requestMatchers("/user").hasAnyRole("USER", "ADMIN")
            .anyRequest().authenticated()
            )
        .formLogin(
            formLogin -> formLogin
            .loginPage("/auth/login").successHandler(new SuccessUserHandler())
            )
        .logout(
            logout -> logout
            .logoutUrl("/logout")
            .logoutSuccessUrl("/auth/login")
        );
        return http.build();
    }

//    protected void configure(HttpSecurity http) throws Exception {
//        http.formLogin(
//                formLogin -> formLogin
//                .loginPage("/auth/login")
//                .loginProcessingUrl("process_login")
//                .defaultSuccessUrl("/showUserInfo", true)
//                .failureUrl("/auth/login?error")
//                .permitAll()
//        ).logout(
//                logout -> logout
//                        .logoutUrl("/logout")
//                        .logoutSuccessUrl("/auth/login")
//        );
//    }




    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(personDetailsService);
    }

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }
}
