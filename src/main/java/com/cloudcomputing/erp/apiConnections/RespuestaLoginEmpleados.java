package com.cloudcomputing.erp.apiConnections;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RespuestaLoginEmpleados {
    private String role;
    private String estatus;
    private boolean success;
    private String message;
}
