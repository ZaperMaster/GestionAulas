package com.alejandronieves.service;

import java.util.List;
import java.util.Optional;

import com.alejandronieves.model.Usuario;

public interface UsuarioService {
    List<Usuario> listAll();
    Usuario guardarUsuario(Usuario usuario);
    Optional<Usuario> buscarPorEmail(String email);
    Usuario findById(Long id);
    void save(Usuario usuario);
}
