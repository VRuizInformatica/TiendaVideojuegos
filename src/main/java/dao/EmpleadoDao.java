package dao;

import domain.Empleado;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;
import org.jdbi.v3.sqlobject.statement.UseRowMapper;

import java.util.List;

public interface EmpleadoDao {

    @SqlQuery("SELECT * FROM Empleados")
    @UseRowMapper(EmpleadoMapper.class)
    List<Empleado> getAllEmpleado();

    @SqlQuery("SELECT * FROM Empleados WHERE id = :id")
    @UseRowMapper(EmpleadoMapper.class)
    Empleado getEmpleado(@Bind("id") int id);

    @SqlUpdate("INSERT INTO Empleados (nombre, email, sueldo, descripcion) VALUES (:nombre, :email, :sueldo, :descripcion)")
    int addEmpleado(@Bind("nombre") String nombre, @Bind("email") String email, @Bind("sueldo") float sueldo, @Bind("descripcion") String descripcion);

    @SqlUpdate("UPDATE Empleados SET nombre = :nombre, email = :email, sueldo = :sueldo, descripcion = :descripcion WHERE id = :id")
    int updateEmpleado(@Bind("nombre") String nombre, @Bind("email") String email, @Bind("sueldo") float sueldo, @Bind("descripcion") String descripcion, @Bind("id") int id);

    @SqlUpdate("DELETE FROM Empleados WHERE id = :id")
    int removeEmpleado(@Bind("id") int id);

    @SqlQuery("SELECT * FROM Empleados WHERE nombre LIKE CONCAT('%',:searchTerm,'%') OR email LIKE CONCAT('%',:searchTerm,'%')")
    @UseRowMapper(EmpleadoMapper.class)
    List<Empleado> getEmpleadoBySearchTerm(@Bind("searchTerm") String searchTerm);

}
