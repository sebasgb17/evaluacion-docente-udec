package com.udec.evaluacion.controller;

import com.udec.evaluacion.model.*;
import com.udec.evaluacion.service.PlataformaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@Controller
public class AppController {

    @Autowired
    private PlataformaService servicio;

    @GetMapping("/")
    public String index() {
        return "login";
    }

    // Autenticación
    @PostMapping("/login")
    public String login(@RequestParam String correo, @RequestParam String clave, HttpSession session, Model model) {
        Usuario user = servicio.autenticar(correo, clave);
        if (user != null) {
            session.setAttribute("usuario", user);
            return "redirect:/dashboard";
        }
        model.addAttribute("error", "Credenciales incorrectas o usuario inexistente.");
        return "login";
    }

    // Registro de Usuarios
    @PostMapping("/registro")
    public String registro(@RequestParam String correo, @RequestParam String clave, 
                           @RequestParam String nombre, @RequestParam Rol rol, Model model) {
        boolean exito = servicio.registrarUsuario(correo, clave, nombre, rol);
        if (!exito) {
            model.addAttribute("errorRegistro", "El usuario ya existe o los datos ingresados están incompletos.");
            return "login";
        }
        model.addAttribute("mensajeRegistro", "Cuenta creada exitosamente. Ya puedes iniciar sesión.");
        return "login";
    }

    // Vista Principal
    @GetMapping("/dashboard")
    public String dashboard(HttpSession session, Model model) {
        Usuario user = (Usuario) session.getAttribute("usuario");
        if (user == null) return "redirect:/";

        model.addAttribute("usuario", user);
        model.addAttribute("evaluaciones", servicio.obtenerTodasEvaluaciones());
        return "dashboard";
    }

    // Interfaz para Responder
    @GetMapping("/evaluacion/{id}")
    public String verEvaluacion(@PathVariable String id, HttpSession session, Model model) {
        Usuario user = (Usuario) session.getAttribute("usuario");
        if (user == null) return "redirect:/";

        Evaluacion ev = servicio.obtenerEvaluacion(id);
        model.addAttribute("evaluacion", ev);
        model.addAttribute("usuario", user);
        return "responder";
    }

    @PostMapping("/evaluacion/guardar")
    public String guardarEvaluacion(@RequestParam String idEvaluacion,
                                    @RequestParam Map<String, String> allParams,
                                    HttpSession session, Model model) {
        Usuario user = (Usuario) session.getAttribute("usuario");
        if (user == null) return "redirect:/";

        String observacion = allParams.get("observacion");
        Map<String, String> respuestas = new HashMap<>();

        for (Map.Entry<String, String> entry : allParams.entrySet()) {
            if (entry.getKey().startsWith("preg_")) {
                respuestas.put(entry.getKey().replace("preg_", ""), entry.getValue());
            }
        }

        RespuestaEnvio envio = new RespuestaEnvio(idEvaluacion, user.getCorreo(), respuestas, observacion);
        boolean exito = servicio.guardarRespuestas(envio);

        if (!exito) {
            model.addAttribute("error", "Error al enviar: Responde todas las preguntas y asegúrate de que la observación no supere los 200 caracteres.");
            model.addAttribute("evaluacion", servicio.obtenerEvaluacion(idEvaluacion));
            model.addAttribute("usuario", user);
            return "responder";
        }

        return "redirect:/dashboard?exito=true";
    }

    // Reportes y Resultados
    @GetMapping("/evaluacion/respuestas/{id}")
    public String verRespuestas(@PathVariable String id, HttpSession session, Model model) {
        Usuario user = (Usuario) session.getAttribute("usuario");
        if (user == null) return "redirect:/";

        Evaluacion ev = servicio.obtenerEvaluacion(id);
        var respuestas = servicio.obtenerRespuestasPorEvaluacion(id);

        model.addAttribute("evaluacion", ev);
        model.addAttribute("respuestas", respuestas);
        model.addAttribute("usuario", user);
        return "respuestas";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }
}