package no.nav.syfo.web.rest.dialogmelding;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.jdbc.core.JdbcTemplate;

import java.time.LocalDateTime;

import static no.nav.syfo.domain.enums.FellesformatType.SYFO_MELDING;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
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

        dialogmeldingRespository.registrerDialogmelding("dokumentId", "meldingId", SYFO_MELDING);

        verify(jdbcTemplate).update(eq("INSERT INTO MELDING (id, dokument_id, melding_id, type, registrert) VALUES (?, ?, ?, ?, ?)"),
                eq(1L),
                eq("dokumentId"),
                eq("meldingId"),
                eq(SYFO_MELDING.name()),
                any(LocalDateTime.class));
    }
}