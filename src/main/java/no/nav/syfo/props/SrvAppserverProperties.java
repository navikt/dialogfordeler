package no.nav.syfo.props;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.annotation.PostConstruct;

@Getter
@Setter
@ConfigurationProperties("SRVAPPSERVER")
@Component
@Validated
public class SrvAppserverProperties {
    @NotEmpty
    private String username;
    private String password;

    @PostConstruct
    public void postConstruct() {
        if (password == null) {
            password = "";
        }
    }
}
