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
import com.cloudcomputing.erp.utils.SftpConfig;
@Named
@RequestScoped
public class NominaGeneralController implements Serializable {
    private final List<VistaNominaGeneral> listaNominas;
    private final NominaService nominaService;

    public NominaGeneralController() {
        nominaService = new NominaService();
        listaNominas = nominaService.obtenerVistaNominaGeneral();
        listaNominas.stream().forEach(nomina -> {
            String url = "http://" + SftpConfig.SFTP_HOST +"/icons"+ "/" + SftpConfig.REMOTE_DIR + nomina.getDocumentoNomina();
            System.out.println(url);
            nomina.setDocumentoNomina(url);
        });
    }

    public List<VistaNominaGeneral> getListaNominas() {
        return listaNominas;
    }
    
    public String verNominaPorEmpleado(){
        return "gestionNominaEmpleado.xhtml?faces-redirect=true";
    }
}
