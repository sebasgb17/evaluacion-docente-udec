package com.udec.evaluacion.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Evaluacion {
    private String id;
    private String titulo;
    private String tipoEvaluacion;
    private boolean esAutoevaluacion;
    private LocalDateTime fechaInicio;
    private LocalDateTime fechaFin;
    private List<Pregunta> preguntas = new ArrayList<>();

    public Evaluacion() {}

    public Evaluacion(String id, String titulo, String tipoEvaluacion, boolean esAutoevaluacion) {
        this.id = id;
        this.titulo = titulo;
        this.tipoEvaluacion = (tipoEvaluacion != null && !tipoEvaluacion.trim().isEmpty()) ? tipoEvaluacion : "General";
        this.esAutoevaluacion = esAutoevaluacion;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getTipoEvaluacion() { return tipoEvaluacion; }
    public void setTipoEvaluacion(String tipoEvaluacion) { this.tipoEvaluacion = tipoEvaluacion; }

    public boolean isEsAutoevaluacion() { return esAutoevaluacion; }
    public void setEsAutoevaluacion(boolean esAutoevaluacion) { this.esAutoevaluacion = esAutoevaluacion; }

    public LocalDateTime getFechaInicio() { return fechaInicio; }
    public void setFechaInicio(LocalDateTime fechaInicio) { this.fechaInicio = fechaInicio; }

    public LocalDateTime getFechaFin() { return fechaFin; }
    public void setFechaFin(LocalDateTime fechaFin) { this.fechaFin = fechaFin; }

    public List<Pregunta> getPreguntas() { return preguntas; }
    public void setPreguntas(List<Pregunta> preguntas) { this.preguntas = preguntas; }
}