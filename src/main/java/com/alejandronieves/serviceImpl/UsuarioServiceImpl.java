package com.alejandronieves.serviceImpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.alejandronieves.model.TipoUsuario;
import com.alejandronieves.model.Usuario;
import com.alejandronieves.repository.TipoUsuarioRepository;
import com.alejandronieves.repository.UsuarioRepository;
import com.alejandronieves.service.UsuarioService;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private TipoUsuarioRepository tipoUsuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public List<Usuario> listAll() {
        return usuarioRepository.findAll();
    }

    @Override
    public Usuario guardarUsuario(Usuario usuario) {
        // Si el usuario no tiene asignado un tipo, asigna el tipo por defecto (ID 3)
        if (usuario.getTipoUsuario() == null) {
            TipoUsuario defaultRole = tipoUsuarioRepository.findById(3L)
                .orElseThrow(() -> new RuntimeException("No se encontró el rol por defecto con ID 3"));
            usuario.setTipoUsuario(defaultRole);
        }
        // Encripta la contraseña antes de guardar
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        return usuarioRepository.save(usuario);
    }

    @Override
    public Optional<Usuario> buscarPorEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }

    @Override
    public Usuario findById(Long id) {
        return usuarioRepository.findById(id).orElse(null);
    }

    @Override
    public void save(Usuario usuario) {
        guardarUsuario(usuario);
    }
}
