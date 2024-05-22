package com.springChess.springChess.controller;


import com.springChess.springChess.model.Token;
import com.springChess.springChess.model.entities.Usuario;
import com.springChess.springChess.repository.UsuarioRepository;
import com.springChess.springChess.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/usuarios")
public class UsuarioController {
    
    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("/register")
    public Usuario saveUser(@RequestBody Usuario usuario){
        return usuarioService.saveUsuario(usuario);
    }

    @PostMapping("/login")
    public Token login(@RequestBody Usuario usuario){
        return usuarioService.loginUsuario(usuario);
    }

}
