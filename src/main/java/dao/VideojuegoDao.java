package dao;

import domain.Videojuego;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;
import org.jdbi.v3.sqlobject.statement.UseRowMapper;

import java.util.List;

public interface VideojuegoDao {

    @SqlQuery("SELECT * FROM Videojuegos")
    @UseRowMapper(VideojuegoMapper.class)
    List<Videojuego> getAllVideojuegos();

    @SqlQuery("SELECT * FROM Videojuegos WHERE id = :id")
    @UseRowMapper(VideojuegoMapper.class)
    Videojuego getVideojuego(@Bind("id") int id);

    @SqlUpdate("INSERT INTO Videojuegos (titulo, genero, precio, descripcion) VALUES (:titulo, :genero, :precio, :descripcion)")
    int addVideojuego(@Bind("titulo") String titulo, @Bind("genero") String genero, @Bind("precio") float precio, @Bind("descripcion") String descripcion);

    @SqlUpdate("UPDATE Videojuegos SET titulo = :titulo, genero = :genero, precio = :precio, descripcion = :descripcion WHERE id = :id")
    int updateVideojuego(@Bind("titulo") String titulo, @Bind("genero") String genero, @Bind("precio") float precio, @Bind("descripcion") String descripcion, @Bind("id") int id);

    @SqlUpdate("DELETE FROM Videojuegos WHERE id = :id")
    int removeVideojuego(@Bind("id") int id);

    @SqlQuery("SELECT * FROM Videojuegos WHERE titulo LIKE CONCAT('%',:searchTerm,'%') OR genero LIKE CONCAT('%',:searchTerm,'%')")
    @UseRowMapper(VideojuegoMapper.class)
    List<Videojuego> getVideojuegosBySearchTerm(@Bind("searchTerm") String searchTerm);

}
