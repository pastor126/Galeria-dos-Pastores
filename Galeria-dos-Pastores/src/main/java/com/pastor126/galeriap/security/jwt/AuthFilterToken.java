package com.pastor126.galeriap.security.jwt;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.pastor126.galeriap.service.UserDetailServiceImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class AuthFilterToken extends OncePerRequestFilter {

    @Autowired
    private JwtUtils jwtUtil;

    @Autowired
    private UserDetailServiceImpl userDetailService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {
            // Obtém o token JWT da requisição
            String jwt = getToken(request);

            // Verifica se o token é válido e o contexto de segurança ainda não tem uma autenticação
            if (jwt != null && jwtUtil.validateJwtToken(jwt) && SecurityContextHolder.getContext().getAuthentication() == null) {
                
                // Obtém o username a partir do token JWT
                String username = jwtUtil.getUsernameFromToken(jwt);
                
                // Carrega os detalhes do usuário usando o UserDetailsService
                UserDetails userDetails = userDetailService.loadUserByUsername(username);
                
                // Cria o token de autenticação com as credenciais do usuário
                UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                
                // Define a autenticação no contexto de segurança
                SecurityContextHolder.getContext().setAuthentication(auth);
            }

        } catch (Exception e) {
            System.out.println("Não foi possível autenticar o usuário: " + e.getMessage());
        }

        // Continua o filtro para a próxima etapa na cadeia
        filterChain.doFilter(request, response);
    }

    private String getToken(HttpServletRequest request) {
        String headerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(headerToken) && headerToken.startsWith("Bearer ")) {
            return headerToken.substring(7);  // Remove o prefixo "Bearer "
        }
        return null;
    }
}
