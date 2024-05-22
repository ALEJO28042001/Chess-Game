package com.springChess.springChess.service;

import com.springChess.springChess.exceptions.UserNotFoundException;
import com.springChess.springChess.model.Token;
import com.springChess.springChess.model.entities.Game;
import com.springChess.springChess.model.entities.Usuario;
import com.springChess.springChess.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public Usuario saveUsuario(Usuario u){
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String encodedPassword = encoder.encode(u.getPassword());
        u.setPassword(encodedPassword);
        return usuarioRepository.save(u);

    }

    public Token loginUsuario(Usuario u){
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        Optional<Usuario> optionalUsuario = usuarioRepository.findById(u.getUsername());
        if(optionalUsuario.isPresent()){
            Usuario dbUser = optionalUsuario.get();
            if(encoder.matches(u.getPassword(), dbUser.getPassword())){
                return new Token(dbUser.getUsername(), encoder.encode(dbUser.getPassword()));
            }
        } else {
            throw new UserNotFoundException("User not found");

        }
        return null;
    }



}
