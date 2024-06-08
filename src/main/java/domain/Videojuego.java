package domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Videojuego {
    private int id;
    private String titulo;
    private String genero;
    private float precio;
    private String descripcion;
}
