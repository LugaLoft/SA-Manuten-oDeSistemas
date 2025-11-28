package com.tcc2.ellemVeigaOficial.config.security;

import com.tcc2.ellemVeigaOficial.config.authentication.UserAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    private final UserAuthenticationFilter userAuthenticationFilter;

    @Autowired
    public SecurityConfiguration(UserAuthenticationFilter userAuthenticationFilter) {
        this.userAuthenticationFilter = userAuthenticationFilter;
    }

    public static final String [] ENDPOINTS_WITH_AUTHENTICATION_NOT_REQUIRED = {
            "/",
            "/login",
            "/css/**",
            "/js/**",
            "/images/**",
            "/favicon.ico",

            "/paginainicial",
            "/relatorio",
            "/fluxocaixa",
            "/alterarcliente",
            "/alterarpagamento",
            "/alterarpedido",
            "/alterarproduto",
            "/alterarusuario",
            "/alterarvenda",
            "/alterarvendedor",
            "/buscarcliente",
            "/buscarpagamento",
            "/buscarpedido",
            "/buscarproduto",
            "/buscarusuario",
            "/buscarvenda",
            "/buscarvendedor",
            "/cadastrocliente",
            "/cadastropagamento",
            "/cadastropedido",
            "/cadastroproduto",
            "/cadastrousuario",
            "/cadastrovenda",
            "/cadastrovendedor",
            "/cliente/**",
            "/formapagamento/**",
            "/pedido/**",
            "/pedidoproduto/**",
            "/produto/**",
            "/produtovenda/**",
            "/tipoentrega/**",
            "/status/**",
            "/usuario/**", 
            "/vendedor/**",
            "/venda/**"
        };

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(ENDPOINTS_WITH_AUTHENTICATION_NOT_REQUIRED).permitAll()
                        .anyRequest().authenticated())
                .addFilterBefore(userAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        return httpSecurity.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}