package no.nav.syfo.security.jwt;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtAuthenticationProcessingFilter extends AbstractAuthenticationProcessingFilter {
    public JwtAuthenticationProcessingFilter(RequestMatcher requiresAuthenticationRequestMatcher) {
        super(requiresAuthenticationRequestMatcher);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        String rawToken = request.getHeader("Authorization");

        if (rawToken == null) {
            throw new BadCredentialsException("Missing Authorization-header");
        }

        if (!rawToken.startsWith("Bearer")) {
            throw new BadCredentialsException("Missing Bearer-prefix");
        }

        rawToken = rawToken.replace("Bearer", "").trim();
        String[] rawTokenSplit = rawToken.split("\\.");

        if (rawTokenSplit.length < 2){
            throw new BadCredentialsException("Invalid Authorization token");
        }

        JwtUsernamePasswordAuthenticationToken authentication = new JwtUsernamePasswordAuthenticationToken(null, rawToken);
        authentication.setDetails(rawTokenSplit[1]);
        return getAuthenticationManager().authenticate(authentication);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        SecurityContextHolder.getContext().setAuthentication(authResult);

        chain.doFilter(request, response);
    }
}
