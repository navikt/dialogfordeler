package no.nav.syfo.repository;

import no.nav.syfo.exception.MeldingInboundException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.jdbc.core.JdbcTemplate;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AppRecRepositoryTest {
    @Mock
    private JdbcTemplate jdbcTemplate;
    @InjectMocks
    private AppRecRepository appRecRepository;

    @Test
    public void registrerMottattAppRec() throws Exception {
        when(jdbcTemplate.update(anyString(), any(LocalDateTime.class), eq("meldingId"))).thenReturn(1);

        appRecRepository.registrerMottattAppRec("meldingId");

        verify(jdbcTemplate).update(anyString(), any(LocalDateTime.class), eq("meldingId"));
    }

    @Test
    public void registrerMottattAppRecFlereRader() throws Exception {
        when(jdbcTemplate.update(anyString(), any(LocalDateTime.class), eq("meldingId"))).thenReturn(2);

        appRecRepository.registrerMottattAppRec("meldingId");

        verify(jdbcTemplate).update(anyString(), any(LocalDateTime.class), eq("meldingId"));
    }

    @Test(expected = MeldingInboundException.class)
    public void registrerMottattAppRecIngenRader() throws Exception {
        when(jdbcTemplate.update(anyString(), any(LocalDateTime.class), eq("meldingId"))).thenReturn(0);

        appRecRepository.registrerMottattAppRec("meldingId");
    }
}