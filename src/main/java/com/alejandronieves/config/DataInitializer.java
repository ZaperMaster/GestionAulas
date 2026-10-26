package com.alejandronieves.config;

import com.alejandronieves.model.TipoUsuario;
import com.alejandronieves.model.Usuario;
import com.alejandronieves.repository.TipoUsuarioRepository;
import com.alejandronieves.repository.UsuarioRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.time.LocalDate;
import java.util.Base64;

/**
 * Crea los datos mínimos para poder usar la aplicación en una base de datos vacía:
 * los tipos de usuario (1 = ADMIN, 3 = USER) y un administrador si todavía no hay ninguno.
 */
@Component
public class DataInitializer implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(DataInitializer.class);

    private final JdbcTemplate jdbc;
    private final TipoUsuarioRepository tipoUsuarioRepository;
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${app.admin.email:admin@gestionaulas.local}")
    private String adminEmail;

    // Si no se indica, se genera una contraseña aleatoria y se muestra una sola vez en el log
    @Value("${app.admin.password:}")
    private String adminPassword;

    public DataInitializer(JdbcTemplate jdbc, TipoUsuarioRepository tipoUsuarioRepository,
                           UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.jdbc = jdbc;
        this.tipoUsuarioRepository = tipoUsuarioRepository;
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        // Los ids son fijos porque la seguridad asigna el rol según el id del tipo
        jdbc.update("INSERT IGNORE INTO TIPOUSUARIO (idtipousuario, tipo, descripcion, activo) "
                + "VALUES (1, 'ADMIN', 'Administrador', 1), (3, 'USER', 'Usuario', 1)");

        boolean hayAdmin = usuarioRepository.findAll().stream()
                .anyMatch(u -> u.getTipoUsuario() != null && u.getTipoUsuario().getId() == 1);
        if (hayAdmin) {
            return;
        }

        String password = adminPassword;
        if (password == null || password.isEmpty()) {
            byte[] bytes = new byte[12];
            new SecureRandom().nextBytes(bytes);
            password = Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);
        }
        TipoUsuario tipoAdmin = tipoUsuarioRepository.findById(1L).orElseThrow(IllegalStateException::new);

        Usuario admin = new Usuario();
        admin.setNombre("Administrador");
        admin.setApellido("");
        admin.setEmail(adminEmail);
        admin.setPassword(passwordEncoder.encode(password));
        admin.setActivo(true);
        admin.setFechaActivacion(LocalDate.now());
        admin.setTipoUsuario(tipoAdmin);
        usuarioRepository.save(admin);

        if (adminPassword == null || adminPassword.isEmpty()) {
            log.warn("Creado el administrador {} con la contraseña {} (cámbiala o define app.admin.password)",
                    adminEmail, password);
        } else {
            log.info("Creado el administrador {}", adminEmail);
        }
    }
}
