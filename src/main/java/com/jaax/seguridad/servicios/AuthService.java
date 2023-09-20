package com.jaax.seguridad.servicios;

import com.jaax.seguridad.controladores.authdtos.AuthResponse;
import com.jaax.seguridad.controladores.authdtos.AuthenticationRequest;
import com.jaax.seguridad.controladores.authdtos.RegisterRequest;

public interface AuthService {
    public AuthResponse register (RegisterRequest request);
    public AuthResponse authenticate (AuthenticationRequest request);
}
