package dao;

import domain.Videojuego;
import org.jdbi.v3.core.mapper.RowMapper;
import org.jdbi.v3.core.statement.StatementContext;

import java.sql.ResultSet;
import java.sql.SQLException;

public class VideojuegoMapper implements RowMapper<Videojuego> {

    @Override
    public Videojuego map(ResultSet rs, StatementContext ctx) throws SQLException {
        return new Videojuego(rs.getInt("id"),
                rs.getString("titulo"),
                rs.getString("genero"),
                rs.getFloat("precio"),
                rs.getString("descripcion"));
    }
}
