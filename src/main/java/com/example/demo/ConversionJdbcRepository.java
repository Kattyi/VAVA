package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class ConversionJdbcRepository {

    @Autowired
    JdbcTemplate jdbcTemplate;

    class ConversionRowMapper implements RowMapper < Conversion > {
        @Override
        public Conversion mapRow(ResultSet rs, int rowNum) throws SQLException {
            Conversion conversion = new Conversion();
            conversion.setId(rs.getInt("id"));
            conversion.setTimestamp(rs.getTimestamp("timestamp"));
            conversion.setFilename(rs.getString("file_name"));
            conversion.setSize(rs.getInt("size"));
            return conversion;
        }
    }

    public Conversion findById(Integer id) {
        return jdbcTemplate.queryForObject("select * from conversion where id=?", new Object[] {
                id
        }, new BeanPropertyRowMapper<Conversion>(Conversion.class));
    }

    public int insert(Conversion conversion) {
        return jdbcTemplate.update("insert into conversion (id, timestamp, file_name, size)" + "values(?, ?, ?, ?)",
                new Object[] {
                        conversion.getId(), conversion.getTimestamp(), conversion.getFilename(), conversion.getSize()
                });
    }

}
