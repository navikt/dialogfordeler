package no.nav.syfo.web.rest.dialogmelding;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.Date;

import static no.nav.syfo.domain.enums.FellesformatType.SYFO_APPREC;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DialogmeldingRespositoryTest {
    @Mock
    private JdbcTemplate jdbcTemplate;
    @InjectMocks
    private DialogmeldingRespository dialogmeldingRespository;

    @Test
    public void registrerDialogmelding() {
        when(jdbcTemplate.queryForObject("SELECT MELDING_ID_SEQ.nextval FROM dual", Long.class)).thenReturn(1L);

        dialogmeldingRespository.registrerDialogmelding("meldingId", SYFO_APPREC);

        verify(jdbcTemplate).update(eq("INSERT INTO MELDING (id, melding_id, type, registrert) VALUES (?, ?, ?, ?)"),
                eq(1L),
                eq("meldingId"),
                eq(SYFO_APPREC.name()),
                any(Date.class));
    }
}