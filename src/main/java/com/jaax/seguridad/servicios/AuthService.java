package com.jaax.seguridad.servicios;

import com.jaax.seguridad.controladores.authdtos.AuthResponse;
import com.jaax.seguridad.controladores.authdtos.AuthenticationRequest;
import com.jaax.seguridad.controladores.authdtos.RegisterRequest;
import org.springframework.stereotype.Service;

@Service
public interface AuthService {
    public AuthResponse register (RegisterRequest request);
    public AuthResponse authenticate (AuthenticationRequest request);
}
