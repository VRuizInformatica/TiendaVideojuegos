package dao;

import domain.Empleado;
import org.jdbi.v3.core.mapper.RowMapper;
import org.jdbi.v3.core.statement.StatementContext;

import java.sql.ResultSet;
import java.sql.SQLException;

public class EmpleadoMapper implements RowMapper<Empleado> {

    @Override
    public Empleado map(ResultSet rs, StatementContext ctx) throws SQLException {
        return new Empleado(rs.getInt("id"),
                rs.getString("nombre"),
                rs.getString("email"),
                rs.getFloat("sueldo"),
                rs.getString("descripcion"));
    }
}
