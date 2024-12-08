package com.pastor126.galeriap.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import com.pastor126.galeriap.security.jwt.AuthEntryPointJwt;
import com.pastor126.galeriap.security.jwt.AuthFilterToken;

@Configuration
@EnableMethodSecurity
public class WebSecurityConfig {

    @Autowired
    private AuthEntryPointJwt unauthorizedHandler;
    
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
    
    @Bean
    public AuthFilterToken authFilterToken() {
        return new AuthFilterToken();
    }
    
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.cors(Customizer.withDefaults()).csrf(csrf -> csrf.disable())    
            .exceptionHandling(exception -> exception.authenticationEntryPoint(unauthorizedHandler))
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) //Política de gerenciamento de sessão, sem manutenção de estado, apropriado para APIs RESTful.
            .authorizeHttpRequests(auth -> auth
            	.requestMatchers("/auth/**").permitAll()
                .requestMatchers("/usuario/**").permitAll()
                .requestMatchers("/falecomigo/**").permitAll()
                .anyRequest().authenticated());
        
        http.addFilterBefore(authFilterToken(), UsernamePasswordAuthenticationFilter.class);
        
        return http.build();
    }    
    
    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOrigin("https://galeria-dos-pastores-production.up.railway.app");
        config.addAllowedOrigin("android-app://com.eduardopacheco.galeriaflutter2"); //para app
        config.addAllowedOrigin("http://localhost");
        config.addAllowedOrigin("*");
        config.addAllowedHeader("*"); //Permite todos os cabeçalhos.
        config.addAllowedMethod("*"); //Permite todos os métodos HTTP.
        source.registerCorsConfiguration("/**", config); //Registra a configuração de CORS para todas as rotas (/**).
        return new CorsFilter(source);
    }
}
