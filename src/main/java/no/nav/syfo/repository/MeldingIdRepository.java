package no.nav.syfo.repository;

import lombok.extern.slf4j.Slf4j;
import no.nav.syfo.domain.enums.FellesformatType;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static java.util.Optional.empty;

@Service
@Slf4j
public class MeldingIdRepository {
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public MeldingIdRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    public Optional<FellesformatType> finnMeldingstypeForMeldingIdSet(Set<String> meldingIdSet) {
        if (meldingIdSet.isEmpty()) {
            return empty();
        }

        List<FellesformatType> list = namedParameterJdbcTemplate.query(
                "SELECT type FROM melding WHERE melding_id IN (:ids)",
                new MapSqlParameterSource("ids", meldingIdSet),
                (rs, i) -> FellesformatType.valueOf(rs.getString("type")));
        return list.stream().findFirst();
    }

    public Optional<FellesformatType> finnMeldingstypeForDokumentIdSet(Set<String> dokumentIdSet) {
        if (dokumentIdSet.isEmpty()) {
            return empty();
        }

        List<FellesformatType> list = namedParameterJdbcTemplate.query(
                "SELECT type FROM melding WHERE dokument_id IN (:ids)",
                new MapSqlParameterSource("ids", dokumentIdSet),
                (rs, i) -> FellesformatType.valueOf(rs.getString("type")));
        return list.stream().findFirst();
    }
}
