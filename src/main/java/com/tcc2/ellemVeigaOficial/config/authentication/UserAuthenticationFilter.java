package com.tcc2.ellemVeigaOficial.config.authentication;

//import com.tcc2.ellemVeigaOficial.config.security.SecurityConfiguration;
import com.tcc2.ellemVeigaOficial.config.userdetails.UserDetailsImpl;
import com.tcc2.ellemVeigaOficial.models.Usuario;
import com.tcc2.ellemVeigaOficial.repositories.UsuarioRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;

//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
//import java.util.List;
import java.io.IOException;
//import java.util.Arrays;

@Component
@AllArgsConstructor
public class UserAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenService jwtTokenService;
    private final UsuarioRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) 
            throws ServletException, IOException {
        String token = recoveryToken(request);

        if (token != null && jwtTokenService.validarToken(token)) {
            String subject = jwtTokenService.getAssuntoToken(token);
            Usuario user = userRepository.findByUsuario(subject)
                    .orElseThrow(() -> new RuntimeException("Usuário não encontrado."));

            UserDetailsImpl userDetails = new UserDetailsImpl(user);
            Authentication authentication = new UsernamePasswordAuthenticationToken(
                    userDetails, null, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        /*
        if (checkIfEndpointIsNotPublic(request)) {
            String token = recoveryToken(request);

            if (token != null) {
                String subject = jwtTokenService.getAssuntoToken(token);
                Usuario user = userRepository.findByUsuario(subject)
                        .orElseThrow(() -> new RuntimeException("Usuário não encontrado."));

                UserDetailsImpl userDetails = new UserDetailsImpl(user);
                Authentication authentication = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } else {
                // Token ausente, seguir com a requisição sem autenticação
                filterChain.doFilter(request, response);
                return;
            }
        }*/
        filterChain.doFilter(request, response);
    }

    private String recoveryToken(HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            return null;
        }
        return authorizationHeader.substring(7);
    }

    /*Recupera o token do cabeçalho Authorization da requisição
    private String recoveryToken(HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader == null)
            return null;

        return authorizationHeader.replace("Bearer ", "");
    }

    Verifica se o endpoint requer autenticação antes de processar a requisição
    private boolean checkIfEndpointIsNotPublic(HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        List<String> publicEndpoints = Arrays.asList(SecurityConfiguration.ENDPOINTS_WITH_AUTHENTICATION_NOT_REQUIRED);
        return publicEndpoints.stream().noneMatch(requestURI::startsWith);
    }*/
}
