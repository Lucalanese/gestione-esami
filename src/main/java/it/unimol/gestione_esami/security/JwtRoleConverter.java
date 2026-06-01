package it.unimol.gestione_esami.security;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.Collection;
import java.util.List;

public class JwtRoleConverter implements Converter<Jwt, Collection<GrantedAuthority>> {

    @Override
    public Collection<GrantedAuthority> convert(Jwt jwt) {
        String role = jwt.getClaimAsString("role");

        if (role == null) {
            return List.of();
        }

        String springRole = switch (role) {
            case "student" -> "ROLE_STUDENT";
            case "teach"   -> "ROLE_PROFESSOR";
            case "admin"   -> "ROLE_ADMINISTRATIVE";
            case "sadmin"  -> "ROLE_ADMINISTRATIVE";
            default        -> "ROLE_STUDENT";
        };

        return List.of(new SimpleGrantedAuthority(springRole));
    }
}