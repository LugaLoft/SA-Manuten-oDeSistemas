package com.tcc2.ellemVeigaOficial.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.tcc2.ellemVeigaOficial.config.security.RecoveryJwtTokenDto;
import com.tcc2.ellemVeigaOficial.models.Login;
import com.tcc2.ellemVeigaOficial.services.UsuarioService;
import lombok.AllArgsConstructor;

@RequestMapping("/login")
@RestController
@AllArgsConstructor
public class LoginController {

    @Autowired
    private UsuarioService service;

    @PostMapping
    public ResponseEntity<RecoveryJwtTokenDto> authenticateUser(@RequestBody Login loginUserDto) {
        try {
            RecoveryJwtTokenDto token = service.authenticateUser(loginUserDto);
            return new ResponseEntity<>(token, HttpStatus.OK);
        } catch (AuthenticationException e) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }
    
}