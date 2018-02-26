package no.nav.syfo.repository;

import lombok.extern.slf4j.Slf4j;
import no.nav.syfo.domain.enums.FellesformatType;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@Slf4j
public class MeldingIdRepository {
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public MeldingIdRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    public Optional<FellesformatType> finnMeldingstype(Set<String> meldingsid) {
        List<FellesformatType> list = namedParameterJdbcTemplate.query(
                "SELECT type FROM melding WHERE melding_id IN (:ids)",
                new MapSqlParameterSource("ids", meldingsid),
                (rs, i) -> FellesformatType.valueOf(rs.getString("type")));
        return list.stream().findFirst();
    }
}
