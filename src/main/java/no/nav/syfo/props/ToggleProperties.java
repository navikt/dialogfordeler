package no.nav.syfo.props;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@ConfigurationProperties("TOGGLE")
@Component
@Validated
public class ToggleProperties {
    @NotNull
    private boolean leggMeldingerPaKo;
}
