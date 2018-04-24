package no.nav.syfo.repository;

import lombok.extern.slf4j.Slf4j;
import no.nav.syfo.domain.enums.MeldingLoggType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import static java.time.LocalDateTime.now;

@Service
@Slf4j
public class MeldingLoggRepository {
    private JdbcTemplate jdbcTemplate;

    public MeldingLoggRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void loggMelding(String melding, Long meldingId, MeldingLoggType type) {
        Long id = jdbcTemplate.queryForObject("SELECT MELDING_LOGG_ID_SEQ.nextval FROM dual", Long.class);
        jdbcTemplate.update("INSERT INTO MELDING_LOGG (id, melding_id, type, melding, registrert) VALUES (?, ?, ?, ?, ?)",
                id,
                meldingId,
                type.name(),
                melding,
                now()
        );
    }
}
