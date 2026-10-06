package com.udec.evaluacion.model;

import java.util.List;

public class Pregunta {
    private String id;
    private String enunciado;
    private String tipoRespuesta;
    private List<String> opciones;
    private String respuestaCorrecta;

    public Pregunta() {}

    public Pregunta(String id, String enunciado, String tipoRespuesta, List<String> opciones, String respuestaCorrecta) {
        this.id = id;
        this.enunciado = enunciado;
        this.tipoRespuesta = tipoRespuesta;
        this.opciones = opciones;
        this.respuestaCorrecta = respuestaCorrecta;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getEnunciado() { return enunciado; }
    public void setEnunciado(String enunciado) { this.enunciado = enunciado; }

    public String getTipoRespuesta() { return tipoRespuesta; }
    public void setTipoRespuesta(String tipoRespuesta) { this.tipoRespuesta = tipoRespuesta; }

    public List<String> getOpciones() { return opciones; }
    public void setOpciones(List<String> opciones) { this.opciones = opciones; }

    public String getRespuestaCorrecta() { return respuestaCorrecta; }
    public void setRespuestaCorrecta(String respuestaCorrecta) { this.respuestaCorrecta = respuestaCorrecta; }
}