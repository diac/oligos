package com.diac.oligos.authentication.filter;

import com.diac.oligos.authentication.model.TokenEntity;
import com.diac.oligos.authentication.service.TokenService;
import com.diac.oligos.authentication.util.Util;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class JwtVerifierFilter extends OncePerRequestFilter {

    @Value("${securityPrefix}")
    private String securityPrefix;

    @Value("${jwtIssuer}")
    private String jwtIssuer;

    @Value("${securityKey}")
    private String securityKey;

    private final TokenService tokenService;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
        String bearerToken = request.getHeader("Authorization");
        if (
                !Util.validString(bearerToken)
                        || !bearerToken.startsWith(securityPrefix)
        ) {
            filterChain.doFilter(request, response);
            return;
        }
        String authToken = bearerToken.replace(securityPrefix, "");
        Optional<TokenEntity> tokenEntity = tokenService.findById(authToken);
        if (tokenEntity.isEmpty()) {
            filterChain.doFilter(request, response);
            return;
        }
        String token = tokenEntity.get().getAuthenticationToken();
        Jws<Claims> authClaim = Jwts.parser()
                .setSigningKey(securityKey)
                .requireIssuer(jwtIssuer)
                .parseClaimsJws(token);
        String username = authClaim.getBody().getSubject();
        List<Map<String, String>> authorities = (List<Map<String, String>>) authClaim.getBody().get("authorities");
        List<GrantedAuthority> grantedAuthorities = authorities.stream()
                .map(
                        map -> new SimpleGrantedAuthority(map.get("authority"))
                ).collect(Collectors.toList());
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                username,
                null,
                grantedAuthorities
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        request.setAttribute("username", username);
        request.setAttribute("authorities", grantedAuthorities);
        request.setAttribute("jwt", token);
        filterChain.doFilter(request, response);
    }
}