package dao;

import domain.Pedido;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;
import org.jdbi.v3.sqlobject.statement.UseRowMapper;

import java.util.List;

public interface PedidoDao {

    @SqlQuery("SELECT * FROM Pedidos")
    @UseRowMapper(PedidoMapper.class)
    List<Pedido> getAllPedido();

    @SqlQuery("SELECT * FROM Pedidos WHERE id = :id")
    @UseRowMapper(PedidoMapper.class)
    Pedido getPedido(@Bind("id") int id);

    @SqlUpdate("INSERT INTO Pedidos (nombre, envio, precio, descripcion) VALUES (:nombre, :envio, :precio, :descripcion)")
    int addPedido(@Bind("nombre") String nombre, @Bind("envio") String envio, @Bind("precio") float precio, @Bind("descripcion") String descripcion);

    @SqlUpdate("UPDATE Pedidos SET nombre = :nombre, envio = :envio, precio = :precio, descripcion = :descripcion WHERE id = :id")
    int updatePedido(@Bind("nombre") String nombre, @Bind("envio") String envio, @Bind("precio") float precio, @Bind("descripcion") String descripcion, @Bind("id") int id);

    @SqlUpdate("DELETE FROM Pedidos WHERE id = :id")
    int removePedido(@Bind("id") int id);

    @SqlQuery("SELECT * FROM Pedidos WHERE nombre LIKE CONCAT('%',:searchTerm,'%') OR envio LIKE CONCAT('%',:searchTerm,'%')")
    @UseRowMapper(PedidoMapper.class)
    List<Pedido> getPedidoBySearchTerm(@Bind("searchTerm") String searchTerm);

}
