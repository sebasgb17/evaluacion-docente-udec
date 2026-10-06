package com.udec.evaluacion.service;

import com.udec.evaluacion.model.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class PlataformaService {

    private final Map<String, Usuario> usuarios = new HashMap<>();
    private final Map<String, Evaluacion> evaluaciones = new HashMap<>();
    private final List<RespuestaEnvio> envios = new ArrayList<>();

    public PlataformaService() {
        // Usuarios iniciales para pruebas
        registrarUsuario("admin@udec.edu.co", "1234", "Administrador PGC", Rol.ADMINISTRADOR);
        registrarUsuario("docente@udec.edu.co", "1234", "Profesor Gabriel", Rol.DOCENTE);
        registrarUsuario("estudiante@udec.edu.co", "1234", "Juan Sebastián", Rol.ESTUDIANTE);

        // Evaluación inicial de prueba
        crearEvaluacion("EV-01", "Evaluación Docente 2026-1", "Parcial", false);
        Evaluacion ev = obtenerEvaluacion("EV-01");
        ev.setFechaInicio(LocalDateTime.now());
        ev.setFechaFin(LocalDateTime.now().plusDays(30));

        // Preguntas iniciales
        Pregunta p1 = new Pregunta("P1", "¿El docente demuestra dominio del tema expuesto?", "Selección Múltiple", List.of("Excelente", "Bueno", "Aceptable", "Insuficiente"), "Excelente");
        Pregunta p2 = new Pregunta("P2", "¿Se cumplen los horarios establecidos para la clase?", "Verdadero/Falso", List.of("Verdadero", "Falso"), "Verdadero");

        agregarPreguntaAEvaluacion("EV-01", p1);
        agregarPreguntaAEvaluacion("EV-01", p2);

        // Autoevaluación Docente inicial
        crearEvaluacion("AUTO-01", "Autoevaluación de Desempeño Docente", "Formativa", true);
        Evaluacion autoEv = obtenerEvaluacion("AUTO-01");
        autoEv.setFechaInicio(LocalDateTime.now());
        autoEv.setFechaFin(LocalDateTime.now().plusDays(30));
        
        Pregunta p3 = new Pregunta("P3", "¿Cumplí con la entrega oportuna de notas y retroalimentación?", "Verdadero/Falso", List.of("Verdadero", "Falso"), "Verdadero");
        agregarPreguntaAEvaluacion("AUTO-01", p3);
    }

    // Registro de Usuario
    public boolean registrarUsuario(String correo, String clave, String nombre, Rol rol) {
        if (correo == null || correo.trim().isEmpty() || clave == null || clave.trim().isEmpty() || usuarios.containsKey(correo)) {
            return false;
        }
        usuarios.put(correo, new Usuario(correo, clave, nombre, rol));
        return true;
    }

    // Autenticación de Usuario
    public Usuario autenticar(String correo, String clave) {
        Usuario u = usuarios.get(correo);
        if (u != null && u.getClave().equals(clave)) {
            return u;
        }
        return null;
    }

    // Eliminar Usuario
    public boolean eliminarUsuario(String correo) {
        if (usuarios.containsKey(correo)) {
            usuarios.remove(correo);
            return true;
        }
        return false;
    }

    // Definición de Fechas
    public boolean definirFechasEvaluacion(String idEval, LocalDateTime inicio, LocalDateTime fin) {
        if (fin.isBefore(inicio)) {
            return false;
        }
        Evaluacion ev = evaluaciones.get(idEval);
        if (ev != null) {
            ev.setFechaInicio(inicio);
            ev.setFechaFin(fin);
            return true;
        }
        return false;
    }

    // Crear Evaluación o Autoevaluación
    public boolean crearEvaluacion(String id, String titulo, String tipo, boolean esAutoev) {
        if (titulo == null || titulo.trim().isEmpty()) {
            return false;
        }
        Evaluacion ev = new Evaluacion(id, titulo, tipo, esAutoev);
        evaluaciones.put(id, ev);
        return true;
    }

    // Agregar Preguntas
    public boolean agregarPreguntaAEvaluacion(String idEval, Pregunta pregunta) {
        if (pregunta.getEnunciado() == null || pregunta.getEnunciado().trim().isEmpty()) {
            return false;
        }
        if ("Selección Múltiple".equalsIgnoreCase(pregunta.getTipoRespuesta()) && 
           (pregunta.getRespuestaCorrecta() == null || pregunta.getRespuestaCorrecta().trim().isEmpty())) {
            return false;
        }

        Evaluacion ev = evaluaciones.get(idEval);
        if (ev != null) {
            ev.getPreguntas().add(pregunta);
            return true;
        }
        return false;
    }

    // Guardar Respuestas y Observaciones
    public boolean guardarRespuestas(RespuestaEnvio envio) {
        Evaluacion ev = evaluaciones.get(envio.getIdEvaluacion());
        if (ev == null) return false;

        // Límite de caracteres en observaciones (máximo 200)
        if (envio.getObservacion() != null && envio.getObservacion().length() > 200) {
            return false;
        }

        // Validación de preguntas obligatorias
        if (envio.getRespuestas().size() < ev.getPreguntas().size()) {
            return false;
        }

        envios.add(envio);
        return true;
    }

    // Obtener Respuestas
    public List<RespuestaEnvio> obtenerRespuestasPorEvaluacion(String idEval) {
        List<RespuestaEnvio> res = new ArrayList<>();
        for (RespuestaEnvio r : envios) {
            if (r.getIdEvaluacion().equals(idEval)) {
                res.add(r);
            }
        }
        return res;
    }

    public List<Evaluacion> obtenerTodasEvaluaciones() {
        return new ArrayList<>(evaluaciones.values());
    }

    public Evaluacion obtenerEvaluacion(String id) {
        return evaluaciones.get(id);
    }
}