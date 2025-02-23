package com.alejandronieves.config;

import com.alejandronieves.model.Usuario;
import com.alejandronieves.service.UsuarioService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    // Bean para encriptar contraseñas con BCrypt
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
    // Configuración del UserDetailsService que asigna el rol según el tipo de usuario
    @Bean
    public UserDetailsService userDetailsService(UsuarioService usuarioService) {
        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
                // Obtener el usuario del Optional (lanzando excepción si no se encuentra)
                Usuario usuario = usuarioService.buscarPorEmail(email)
                        .orElseThrow(() -> new UsernameNotFoundException("No se encontró el usuario con email: " + email));
                
                // Asignar rol según el id del tipo de usuario
                String role;
                if (usuario.getTipoUsuario().getId() == 1) {
                    role = "ADMIN";
                } else if (usuario.getTipoUsuario().getId() == 3) {
                    role = "USER";
                } else {
                    role = "USER"; // Valor por defecto
                }
                
                return User.builder()
                        .username(usuario.getEmail())
                        .password(usuario.getPassword())
                        // Spring Security antepone "ROLE_" automáticamente
                        .roles(role)
                        .build();
            }
        };
    }
    
    // Configuración del AuthenticationProvider
    @Bean
    public DaoAuthenticationProvider authenticationProvider(UserDetailsService userDetailsService, 
                                                              PasswordEncoder passwordEncoder) {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder);
        return provider;
    }
    
    // SuccessHandler que redirige según el rol del usuario
    @Bean
    public AuthenticationSuccessHandler myAuthenticationSuccessHandler() {
        return (request, response, authentication) -> {
            boolean isAdmin = authentication.getAuthorities().stream()
                    .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN"));
            if (isAdmin) {
                response.sendRedirect(request.getContextPath() + "/menu");
            } else {
                // Aquí se asume que los usuarios con tipo 3 tienen el rol "USER"
                response.sendRedirect(request.getContextPath() + "/menuUsuario");
            }
        };
    }
    
    // Configuración de la cadena de filtros de seguridad
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, 
                                                     DaoAuthenticationProvider authenticationProvider) throws Exception {
        http
            .authenticationProvider(authenticationProvider)
            .authorizeRequests(authorize -> authorize
                .antMatchers("/css/**", "/js/**", "/img/**", "/login", "/registro", "/error").permitAll()
                // Se establece que /menu es solo para ADMIN y /menuUsuario para USER
                .antMatchers("/menu").hasRole("ADMIN")
                .antMatchers("/menuUsuario").hasRole("USER")
                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                .loginPage("/login")
                .successHandler(myAuthenticationSuccessHandler())
                .failureUrl("/login?error")
                .permitAll()
            )
            .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login?logout")
                .permitAll()
            );
        return http.build();
    }
}
