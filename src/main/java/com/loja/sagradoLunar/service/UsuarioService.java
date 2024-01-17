package com.loja.sagradoLunar.service;

import java.nio.charset.Charset;
import java.util.Base64;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.loja.sagradoLunar.model.UserLogin;
import com.loja.sagradoLunar.model.Usuario;
import com.loja.sagradoLunar.repository.UsuarioRepository;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository repository;

    // Cadastro de usuario e encriptamento de senha
    public Optional<Usuario> CadastrarUsuario(Usuario usuario) {
        if (repository.findByUsuario(usuario.getUsuario()).isPresent()) {
            return null;
        }
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String senhaEncoder = encoder.encode(usuario.getSenha());

        return Optional.of(repository.save(usuario));
    }

    // Validação de login
    public Optional<UserLogin> Logar(Optional<UserLogin> user) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        Optional<Usuario> usuario = repository.findByUsuario(user.get().getUsuario());

        if (usuario.isPresent()) {
            if (encoder.matches(user.get().getSenha(), usuario.get().getSenha())) {
                String auth = user.get().getUsuario() + "+" + user.get().getSenha();
                byte[] encodeAuth = Base64.getEncoder().encode(auth.getBytes(Charset.forName("US-ASCII")));
                String authHeader = "Basic " + new String(encodeAuth);

                user.get().setToken(authHeader);
                user.get().setNome(usuario.get().getNome());
                user.get().setSenha(usuario.get().getSenha());
                user.get().setTipoUsuario(usuario.get().getTipo());

                return user;
            }
        }
        return null;
    }

}
