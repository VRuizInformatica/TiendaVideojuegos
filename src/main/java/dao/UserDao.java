package dao;

import domain.User;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;
import org.jdbi.v3.sqlobject.statement.UseRowMapper;

import java.util.List;

public interface UserDao {

    @SqlQuery("SELECT * FROM Usuarios")
    @UseRowMapper(UserMapper.class)
    List<User> getAllUsers();

    @SqlQuery("SELECT * FROM Usuarios WHERE id = ?")
    @UseRowMapper(UserMapper.class)
    User getUser(int id);

    @SqlQuery("SELECT * FROM Usuarios WHERE correo_electronico = ? AND contrasena = ?")
    @UseRowMapper(UserMapper.class)
    User getUser(String correoElectronico, String contrasena);

    @SqlUpdate("INSERT INTO Usuarios (nombre, correo_electronico, contrasena) VALUES (?, ?, ?)")
    int addUser(String nombre, String correoElectronico, String contrasena);

    @SqlUpdate("UPDATE Usuarios SET nombre = ?, correo_electronico = ?, contrasena = ? WHERE id = ?")
    int updateUser(String nombre, String correoElectronico, String contrasena, int id);

    @SqlUpdate("DELETE FROM Usuarios WHERE id = ?")
    int removeUser(int id);
}
