package com.jaax.seguridad.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
//Jwt authentication filter
@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final UserDetailsService userDetailsService;
    private final JwtServicio jwtServicio;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {//request es la peticion del cielnte, response es la respuesaa que le damos y filterchai es un filtro
        final String authHeader= request.getHeader("Authorizathion");
        final String jwt;
        final String usuarioEmail;

        if (authHeader == null || !authHeader.startsWith("Bearer")) {
            filterChain.doFilter(request,response);
            return;
        }//continuarfiltrado

        jwt = authHeader.substring(7);
       usuarioEmail = jwtServicio.getUserName(jwt);
        if (usuarioEmail!=null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails =  this.userDetailsService.loadUserByUsername(usuarioEmail);
            if (jwtServicio.validarToken(jwt,userDetails)) {
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails,null,userDetails.getAuthorities()
                );
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken); // security contex holder

            }
        }
        filterChain.doFilter(request,response);
    }
}
