package com.cloudcomputing.erp.dto;

import com.google.gson.annotations.SerializedName;
import java.util.Date;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@NoArgsConstructor
@Setter
@Getter
public class ContabilidadDTO {

    @SerializedName("_id")
    private String idTransaccion;

    @SerializedName("fecha_mov")
    private Date fechaMov;

    @SerializedName("tipo_mov")
    private String tipo_Mov;

    @SerializedName("monto")
    private double monto;

    @SerializedName("cuenta_movimiento")
    private String cuenta;
    
    @SerializedName("descripcion")
    private String descripcion;

}
