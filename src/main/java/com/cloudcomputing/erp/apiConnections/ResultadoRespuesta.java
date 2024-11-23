
package com.cloudcomputing.erp.apiConnections;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResultadoRespuesta {
    private int estado;
    private String mensaje;

    public ResultadoRespuesta(int estado, String mensaje) {
        this.estado = estado;
        this.mensaje = mensaje;
    }
    
    @Override
    public String toString() {
        return "Estado: " + estado + ", Mensaje: " + mensaje;
    }
}
