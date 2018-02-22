package no.nav.syfo.security;

import no.nav.syfo.security.expression.CustomMethodExpressionHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class MethodSecurityConfig extends GlobalMethodSecurityConfiguration {

    private CustomMethodExpressionHandler handler;

    public MethodSecurityConfig(CustomMethodExpressionHandler handler) {
        this.handler = handler;
    }

    @Override
    protected MethodSecurityExpressionHandler createExpressionHandler() {
        return handler;
    }
}
