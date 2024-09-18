package com.copomex.copomex.entities;

import java.util.List;
import lombok.Data;

@Data
public class CopomexResponse {
    private boolean error;
    private int code_error; // Asegúrate de que el nombre coincida con el JSON
    private String error_message; // Asegúrate de que el nombre coincida con el JSON
    private Response response;

    @Data
    public static class Response {
        private String cp;
        private String asentamiento;
        private String tipo_asentamiento; // Asegúrate de que el nombre coincida con el JSON
        private String municipio;
        private String estado;
        private String ciudad;
        private String pais;
    }

    
}
