package dao;

import domain.Pedido;
import org.jdbi.v3.core.mapper.RowMapper;
import org.jdbi.v3.core.statement.StatementContext;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PedidoMapper implements RowMapper<Pedido> {

    @Override
    public Pedido map(ResultSet rs, StatementContext ctx) throws SQLException {
        return new Pedido(rs.getInt("id"),
                rs.getString("nombre"),
                rs.getString("envio"),
                rs.getFloat("precio"),
                rs.getString("descripcion"));
    }
}
