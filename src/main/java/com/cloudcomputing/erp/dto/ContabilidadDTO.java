package com.cloudcomputing.erp.dto;

import com.google.gson.annotations.SerializedName;
import java.time.LocalDate;
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
    
    @SerializedName("fecha_alta")
    private LocalDate fechaAlta;

    @SerializedName("tipo_mov")
    private String tipoMov;

    @SerializedName("monto")
    private double monto;

    @SerializedName("cuenta_movimiento")
    private String cuenta;
    
    @SerializedName("descripcion")
    private String descripcion;
    
    private String documentoURL;

}
