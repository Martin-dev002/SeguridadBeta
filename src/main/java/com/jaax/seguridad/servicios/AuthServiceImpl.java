package com.jaax.seguridad.servicios;

import com.jaax.seguridad.config.JwtServicio;
import com.jaax.seguridad.controladores.authdtos.AuthResponse;
import com.jaax.seguridad.controladores.authdtos.AuthenticationRequest;
import com.jaax.seguridad.controladores.authdtos.RegisterRequest;
import com.jaax.seguridad.entidades.Rol;
import com.jaax.seguridad.entidades.Usuario;
import com.jaax.seguridad.repositorios.UsuarioRepositorio;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService{

    private final UsuarioRepositorio usuarioRepositorio;
    private final PasswordEncoder passwordEncoder;
    private final JwtServicio jwtServicio;
    private final AuthenticationManager authenticationManager;

    @Override
    public AuthResponse register(RegisterRequest request) {
        var usuario = Usuario.builder()
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .rol(Rol.USER)
                .build();
        usuarioRepositorio.save(usuario);
        var jwtToken = jwtServicio.generarToken(usuario);
        return AuthResponse.builder()
                .token(jwtToken).build();
    }

    @Override
    public AuthResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var usuario = usuarioRepositorio.findByEmail(request.getEmail()).orElseThrow();
        var jwtToken = jwtServicio.generarToken(usuario);
        return AuthResponse.builder().token(jwtToken).build();
    }
}
