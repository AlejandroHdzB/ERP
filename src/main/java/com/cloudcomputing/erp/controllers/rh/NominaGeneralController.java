package com.cloudcomputing.erp.controllers.rh;

import com.cloudcomputing.erp.dto.VistaNominaGeneral;
import com.cloudcomputing.erp.services.NominaService;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.context.ExternalContext;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@Named
@RequestScoped
public class NominaGeneralController implements Serializable {
    private final List<VistaNominaGeneral> listaNominas;
    private final NominaService nominaService;

    public NominaGeneralController() {
        nominaService = new NominaService();
        listaNominas = nominaService.obtenerVistaNominaGeneral();
    }

    public List<VistaNominaGeneral> getListaNominas() {
        return listaNominas;
    }

    public void descargarNomina(VistaNominaGeneral nomina) {
        try {
            // Lógica para descargar el archivo PDF
            String rutaArchivo = nomina.getDocumentoNomina();
            FacesContext facesContext = FacesContext.getCurrentInstance();
            ExternalContext externalContext = facesContext.getExternalContext();
            externalContext.responseReset();
            externalContext.setResponseContentType("application/pdf");
            externalContext.setResponseHeader("Content-Disposition", "attachment;filename=\"" + nomina.getIdEmpleado() + "_nomina.pdf\"");
            try (InputStream input = Files.newInputStream(Paths.get(rutaArchivo));
                 OutputStream output = externalContext.getResponseOutputStream()) {
                byte[] buffer = new byte[1024];
                int bytesRead;
                while ((bytesRead = input.read(buffer)) != -1) {
                    output.write(buffer, 0, bytesRead);
                }
            }
            facesContext.responseComplete();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public String verNominaPorEmpleado(){
        return "gestionNominaEmpleado.xhtml?faces-redirect=true";
    }
}
