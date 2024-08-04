package nl.softcause.onestoplogshop.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;

@Configuration
public class SecurityWithCsrfCookieConfig {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.
            cors(cors -> cors.disable()).
            csrf((csrf) -> csrf
                          .ignoringRequestMatchers(
//                                "/oauth2/**",
                                  "/oauth2/**",
                                  "/oidc-authorize",
                                  "/api/version")
                          .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                          .csrfTokenRequestHandler(new CsrfTokenRequestAttributeHandler())
            ).
            authorizeHttpRequests(authz -> {
                        authz.requestMatchers(
                                "/",
                                "/index.html",
                                "/api/**"
                        ).authenticated();
                        authz.anyRequest().permitAll();
                    }
            );
        http.oauth2Login(o -> o.failureHandler(new DefaultAuthenticationFailureHandler()));
        return http.build();
    }
}