package com.aitem.connect.repository;

import com.aitem.connect.model.Zip;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ParameterizedPreparedStatementSetter;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@Repository
public class ZipBulkRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Transactional
    public int[][] batchInsert(List<Zip> books, int batchSize) {

        int[][] updateCounts = jdbcTemplate.batchUpdate(
                "insert into zip (zip, city, state, lon ,lat) " +
                        "values (?,?,?,?,?)",
                books,
                batchSize,
                new ParameterizedPreparedStatementSetter<Zip>() {
                    public void setValues(PreparedStatement ps, Zip argument) throws SQLException {
                        ps.setString(1, argument.getZip());
                        ps.setString(2, argument.getCity());
                        ps.setString(3, argument.getState());
                        ps.setString(4, argument.getLon());
                        ps.setString(5, argument.getLat());
                    }
                });
        return updateCounts;
    }
}
