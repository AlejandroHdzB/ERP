package com.cloudcomputing.erp.dto;

import jakarta.json.bind.annotation.JsonbDateFormat;
import jakarta.json.bind.annotation.JsonbProperty;
import java.util.Date;


public class ContabilidadDTO {
    
    @JsonbProperty("idMovimiento")
    private String idMovimiento;
    
    @JsonbDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
    @JsonbProperty("fechaMov")
    private Date fechaMov;
    
      @JsonbProperty("tipoMov")
    private String tipoMov;
      
       @JsonbProperty("descripcion")
    private String descripcion;
 
      @JsonbProperty("monto")
    private double monto;
     
       @JsonbProperty("cuentaMovimiento")
    private String cuentaMovimiento;
       
        @JsonbProperty("idCompraVenta")
    private String idCompraVenta;

    public String getIdMovimiento() {
        return idMovimiento;
    }

    public void setIdMovimiento(String idMovimiento) {
        this.idMovimiento = idMovimiento;
    }

    public Date getFechaMov() {
        return fechaMov;
    }

    public void setFechaMov(Date fechaMov) {
        this.fechaMov = fechaMov;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

    public String getCuentaMovimiento() {
        return cuentaMovimiento;
    }

    public void setCuentaMovimiento(String cuentaMovimiento) {
        this.cuentaMovimiento = cuentaMovimiento;
    }

    public String getIdCompraVenta() {
        return idCompraVenta;
    }

    public void setIdCompraVenta(String idCompraVenta) {
        this.idCompraVenta = idCompraVenta;
    }
    
    public String getTipoMov() {
        return tipoMov;
    }

    public void setTipoMov(String tipoMov) {
        this.tipoMov = tipoMov;
    }

       public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
      
    
}
