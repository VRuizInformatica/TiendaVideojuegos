package domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Pedido {
    private int id;
    private String nombre;
    private String envio;
    private float precio;
    private String descripcion;
}
