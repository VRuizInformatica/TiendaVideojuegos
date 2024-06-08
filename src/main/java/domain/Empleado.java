package domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Empleado {
    private int id;
    private String nombre;
    private String email;
    private float sueldo;
    private String descripcion;
}
