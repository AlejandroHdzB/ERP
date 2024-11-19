package com.cloudcomputing.erp.controllers.rh;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;
import java.io.Serializable;

@Named
@RequestScoped
public class DashboardController implements Serializable{
    
    public String verEmpleados(){
        System.out.println("Redirejiendo...........");
        return "/rh/listaEmpleados.xhtml?faces-redirect=true";
    }
    
    public String verNomina(){
        return "";
    }
    
    public String verReportes(){
        return "";
    }
}
