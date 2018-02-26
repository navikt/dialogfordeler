package no.nav.syfo.web.rest.dialogmelding;

import no.nav.syfo.domain.enums.FellesformatType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.time.ZoneId;

import static java.time.LocalDateTime.now;

@Component
public class DialogmeldingRespository {
    private JdbcTemplate jdbcTemplate;

    public DialogmeldingRespository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void registrerDialogmelding(String meldingId, FellesformatType type) {
        Long id = jdbcTemplate.queryForObject("SELECT MELDING_ID_SEQ.nextval FROM dual", Long.class);
        jdbcTemplate.update("INSERT INTO MELDING (id, melding_id, type, registrert) VALUES (?, ?, ?, ?)",
                id,
                meldingId,
                type.name(),
                Date.from(now().atZone(ZoneId.systemDefault()).toInstant())
        );
    }
}
