package no.nav.syfo.repository;

import no.nav.syfo.domain.enums.FellesformatType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.sql.ResultSet;
import java.util.Optional;

import static java.util.Collections.*;
import static no.nav.syfo.domain.enums.FellesformatType.SYFO_MELDING;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class MeldingRepositoryTest {
    @Mock
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    @InjectMocks
    private MeldingRepository meldingRepository;

    @Test
    public void finnMeldingstypeForMeldingIdSet() {
        when(namedParameterJdbcTemplate.query(anyString(), any(MapSqlParameterSource.class), any(RowMapper.class)))
                .thenReturn(singletonList(SYFO_MELDING));

        Optional<FellesformatType> fellesformatType =
                meldingRepository.finnMeldingstypeForMeldingIdSet(singleton("meldingId"));

        verify(namedParameterJdbcTemplate).query(anyString(), any(MapSqlParameterSource.class), any(RowMapper.class));
        assertThat(fellesformatType).hasValue(SYFO_MELDING);
    }

    @Test
    public void finnMeldingstypeForMeldingIdSetRowMapperTest() throws Exception {
        when(namedParameterJdbcTemplate.query(anyString(), any(MapSqlParameterSource.class), any(RowMapper.class)))
                .thenReturn(singletonList(SYFO_MELDING));

        meldingRepository.finnMeldingstypeForMeldingIdSet(singleton("meldingId"));

        ArgumentCaptor<RowMapper<FellesformatType>> captor = ArgumentCaptor.forClass(RowMapper.class);
        verify(namedParameterJdbcTemplate).query(anyString(), any(MapSqlParameterSource.class), captor.capture());

        RowMapper<FellesformatType> rowMapper = captor.getValue();
        ResultSet resultSet = mock(ResultSet.class);
        when(resultSet.getString("type")).thenReturn("SYFO_MELDING");
        assertThat(rowMapper.mapRow(resultSet, 0)).isSameAs(SYFO_MELDING);
    }

    @Test
    public void finnMeldingstypeForMeldingIdSetIngenMeldingId() {
        Optional<FellesformatType> fellesformatType =
                meldingRepository.finnMeldingstypeForMeldingIdSet(emptySet());

        verify(namedParameterJdbcTemplate, never()).query(anyString(), any(MapSqlParameterSource.class), any(RowMapper.class));
        assertThat(fellesformatType).isEmpty();
    }

    @Test
    public void finnMeldingstypeForDokumentIdSet() {
        when(namedParameterJdbcTemplate.query(anyString(), any(MapSqlParameterSource.class), any(RowMapper.class)))
                .thenReturn(singletonList(SYFO_MELDING));

        Optional<FellesformatType> fellesformatType =
                meldingRepository.finnMeldingstypeForDokumentIdSet(singleton("dokumentId"));

        verify(namedParameterJdbcTemplate).query(anyString(), any(MapSqlParameterSource.class), any(RowMapper.class));
        assertThat(fellesformatType).hasValue(SYFO_MELDING);
    }

    @Test
    public void finnMeldingstypeForDokumentIdSetRowMapperTest() throws Exception {
        when(namedParameterJdbcTemplate.query(anyString(), any(MapSqlParameterSource.class), any(RowMapper.class)))
                .thenReturn(singletonList(SYFO_MELDING));

        meldingRepository.finnMeldingstypeForDokumentIdSet(singleton("dokumentId"));

        ArgumentCaptor<RowMapper<FellesformatType>> captor = ArgumentCaptor.forClass(RowMapper.class);
        verify(namedParameterJdbcTemplate).query(anyString(), any(MapSqlParameterSource.class), captor.capture());

        RowMapper<FellesformatType> rowMapper = captor.getValue();
        ResultSet resultSet = mock(ResultSet.class);
        when(resultSet.getString("type")).thenReturn("SYFO_MELDING");
        assertThat(rowMapper.mapRow(resultSet, 0)).isSameAs(SYFO_MELDING);
    }

    @Test
    public void finnMeldingstypeForDokumentIdSetIngenDokumentId() {
        Optional<FellesformatType> fellesformatType =
                meldingRepository.finnMeldingstypeForDokumentIdSet(emptySet());

        verify(namedParameterJdbcTemplate, never()).query(anyString(), any(MapSqlParameterSource.class), any(RowMapper.class));
        assertThat(fellesformatType).isEmpty();
    }
}