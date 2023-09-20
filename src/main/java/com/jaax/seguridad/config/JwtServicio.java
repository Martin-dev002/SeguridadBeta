package com.jaax.seguridad.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

//Hacer en servicios
@Service
//validador de JWT
public class JwtServicio {
    private static  final  String SECRET_KEY="4c658d96fa0a154260c704a4f175d87f49c9ceb6f3a1a716e9b429ec15e72a33";

    public String generarToken (UserDetails userDetails){
        return generarToken(new HashMap<>(),userDetails);

    }

    public String generarToken(Map<String,Object> extractClaims, UserDetails userDetails){//literalmente genera el token
        return Jwts.builder().setClaims(extractClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 *60*24))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String getUserName(String tkn){
        return getClaim(tkn, Claims::getSubject);
    } // utiliza el metodo obtenerPeticion, le enviamos el token del request e inyectamos el metodo getSubject de la interface claims para obtener el usuario del token

    public <T> T getClaim(String tkn, Function<Claims,T> claimsResolver){
        final Claims claims = getAllClaims(tkn);
        return claimsResolver.apply(claims);
    }//Crea un objeto de tipo claims y utiliza getAllClaims para enviar el token

    private Claims getAllClaims(String tkn) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJwt(tkn)
                .getBody();
    }//utiliza la clase Jwts, toma la secret key la decodifica en base 64 y crea una firma nueva
    private Key getSignInKey(){
        byte[] keyBytes= Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public boolean validarToken(String token, UserDetails userDetails) {
        final String username = getUserName(token);

        return (username.equals(userDetails.getUsername()) && !isTokenExpirated(token));
    }

    private boolean isTokenExpirated(String token) {
        return getExpiration(token).before(new Date());
    }

    private Date getExpiration(String token) {
        return getClaim(token,Claims::getExpiration);
    }
}
