package no.nav.syfo.repository;

import lombok.extern.slf4j.Slf4j;
import no.nav.syfo.exception.MeldingInboundException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import static java.time.LocalDateTime.now;

@Service
@Slf4j
public class AppRecRepository {
    private JdbcTemplate jdbcTemplate;

    public AppRecRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void registrerMottattAppRec(String meldingId) {
        int oppdaterteRader = jdbcTemplate.update("UPDATE MELDING SET apprec_mottatt_tid = ? WHERE melding_id = ? AND apprec_mottatt_tid IS NULL",
                now(),
                meldingId);
        if (oppdaterteRader == 0) {
            log.error(
                    "Fant ikke melding knyttet til AppRec eller AppRec allerede mottatt for meldingId " + meldingId);
            throw new MeldingInboundException(
                    "Fant ikke melding knyttet til AppRec eller AppRec allerede mottatt for meldingId " + meldingId);
        }
        if (oppdaterteRader > 1) {
            log.warn("Oppdatert {} rader for apprec med meldingId {}", oppdaterteRader, meldingId);
        }
    }
}
