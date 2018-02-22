package no.nav.syfo.security.expression;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;

import static java.util.Collections.emptyList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CustomMethodSecurityExpressionRootTest {
    @Mock
    private Authentication authentication;
    private CustomMethodSecurityExpressionRoot expressionRoot;

    @Before
    public void setUp() throws Exception {
        expressionRoot = new CustomMethodSecurityExpressionRoot(authentication);
    }

    @Test
    public void erServicebruker() {
        User user = new User("srvUsername", "Password", emptyList());
        when(authentication.getPrincipal()).thenReturn(user);

        assertThat(expressionRoot.erServicebruker()).isTrue();
    }

    @Test
    public void erIkkeServicebruker() {
        User user = new User("username", "Password", emptyList());
        when(authentication.getPrincipal()).thenReturn(user);

        assertThat(expressionRoot.erServicebruker()).isFalse();
    }

    @Test
    public void testFilterObject() {
        Object o = new Object();
        expressionRoot.setFilterObject(o);
        assertThat(expressionRoot.getFilterObject()).isSameAs(o);
    }

    @Test
    public void testReturnObject() {
        Object o = new Object();
        expressionRoot.setReturnObject(o);
        assertThat(expressionRoot.getReturnObject()).isSameAs(o);
    }

    @Test
    public void testThis() {
        Object o = new Object();
        expressionRoot.setThis(o);
        assertThat(expressionRoot.getThis()).isSameAs(o);
    }
}