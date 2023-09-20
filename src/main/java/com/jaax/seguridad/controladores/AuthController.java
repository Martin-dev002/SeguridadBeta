package com.jaax.seguridad.controladores;

import com.jaax.seguridad.controladores.authdtos.AuthResponse;
import com.jaax.seguridad.controladores.authdtos.AuthenticationRequest;
import com.jaax.seguridad.controladores.authdtos.RegisterRequest;
import com.jaax.seguridad.servicios.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> registrarse(@RequestBody RegisterRequest request){
        return ResponseEntity.ok(authService.register(request));
    }
    @PostMapping("/authenticate")
    public ResponseEntity<AuthResponse> authenticate(@RequestBody AuthenticationRequest request){
        return ResponseEntity.ok(authService.authenticate(request));

    }
}
