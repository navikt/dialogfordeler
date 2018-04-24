package no.nav.syfo.web.rest.dialogmelding;

import no.nav.syfo.domain.enums.FellesformatType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import static java.time.LocalDateTime.now;

@Component
public class DialogmeldingRespository {
    private JdbcTemplate jdbcTemplate;

    public DialogmeldingRespository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Long registrerDialogmelding(String dokumentId, String meldingId, FellesformatType type) {
        Long id = jdbcTemplate.queryForObject("SELECT MELDING_ID_SEQ.nextval FROM dual", Long.class);
        jdbcTemplate.update("INSERT INTO MELDING (id, dokument_id, melding_id, type, registrert) VALUES (?, ?, ?, ?, ?)",
                id,
                dokumentId,
                meldingId,
                type.name(),
                now()
        );
        return id;
    }
}
