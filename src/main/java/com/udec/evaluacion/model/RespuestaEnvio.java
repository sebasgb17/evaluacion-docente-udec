package com.udec.evaluacion.model;

import java.util.Map;

public class RespuestaEnvio {
    private String idEvaluacion;
    private String correoUsuario;
    private Map<String, String> respuestas;
    private String observacion;

    public RespuestaEnvio() {}

    public RespuestaEnvio(String idEvaluacion, String correoUsuario, Map<String, String> respuestas, String observacion) {
        this.idEvaluacion = idEvaluacion;
        this.correoUsuario = correoUsuario;
        this.respuestas = respuestas;
        this.observacion = observacion;
    }

    public String getIdEvaluacion() { return idEvaluacion; }
    public void setIdEvaluacion(String idEvaluacion) { this.idEvaluacion = idEvaluacion; }

    public String getCorreoUsuario() { return correoUsuario; }
    public void setCorreoUsuario(String correoUsuario) { this.correoUsuario = correoUsuario; }

    public Map<String, String> getRespuestas() { return respuestas; }
    public void setRespuestas(Map<String, String> respuestas) { this.respuestas = respuestas; }

    public String getObservacion() { return observacion; }
    public void setObservacion(String observacion) { this.observacion = observacion; }
}