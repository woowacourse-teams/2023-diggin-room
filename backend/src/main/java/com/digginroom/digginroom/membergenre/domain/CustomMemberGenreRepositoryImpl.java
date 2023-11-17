package com.digginroom.digginroom.membergenre.domain;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;

@AllArgsConstructor
public class CustomMemberGenreRepositoryImpl implements CustomMemberGenreRepository {

    private final JdbcTemplate jdbcTemplate;

    public void saveAll(final MemberGenres memberGenres) {
        final List<MemberGenre> genres = memberGenres.getMemberGenres();
        jdbcTemplate.batchUpdate("insert into member_genre(weight, member_id, genre, created_at, updated_at) "
                        + "values (?, ?, ?, ?, ?)",
                new BatchPreparedStatementSetter() {
                    @Override
                    public void setValues(PreparedStatement ps, int i) throws SQLException {
                        ps.setInt(1, genres.get(i).getWeight());
                        ps.setLong(2, genres.get(i).getMemberId());
                        ps.setString(3, genres.get(i).getGenre().name());
                        ps.setTimestamp(4, Timestamp.valueOf(LocalDateTime.now()));
                        ps.setTimestamp(5, Timestamp.valueOf(LocalDateTime.now()));
                    }

                    @Override
                    public int getBatchSize() {
                        return genres.size();
                    }
                });
    }
}
