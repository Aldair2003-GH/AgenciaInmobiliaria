package org.manosalva.springclaud.msvc.manosalvavasquezaldair.msvcoperacioninmobiliaria;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.*;
import java.math.BigDecimal;
import java.time.*;
import org.manosalva.springclaud.msvc.manosalvavasquezaldair.msvcoperacioninmobiliaria.domain.model.*;
import org.manosalva.springclaud.msvc.manosalvavasquezaldair.msvcoperacioninmobiliaria.domain.ids.*;
import org.manosalva.springclaud.msvc.manosalvavasquezaldair.msvcoperacioninmobiliaria.domain.enums.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import org.springframework.http.MediaType;
import java.util.regex.Pattern;

@SpringBootTest
@AutoConfigureMockMvc
class OperacionRestTests {
    @Autowired
    private MockMvc mvc;
    private long crear(String ruta, String json) throws Exception {
        String respuesta = mvc.perform(post(ruta).contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isCreated()).andReturn().getResponse().getContentAsString();
        var matcher = Pattern.compile("\"id\":(\\d+)").matcher(respuesta);
        assertTrue(matcher.find());
        return Long.parseLong(matcher.group(1));
    }

    private long iniciar(String modalidad) throws Exception {
        return crear("/api/operaciones", """
                {"propiedadId":1,"modalidad":"%s","participantes":[
                {"clienteId":1,"rol":"PROPIETARIO"},{"clienteId":2,"rol":"POSTULANTE"}]}
                """.formatted(modalidad));
    }
    @Test
    void flujoHttpDeVentaCompleto() throws Exception {
        long id = iniciar("VENTA");
        mvc.perform(put("/api/operaciones/{id}/cierre", id)).andExpect(status().isConflict());
        mvc.perform(post("/api/operaciones/{id}/visitas", id).contentType(MediaType.APPLICATION_JSON)
                .content("{\"clienteId\":2,\"fecha\":\"2026-10-20T10:00:00\"}"))
                .andExpect(status().isOk()).andExpect(jsonPath("$.visitas[0].id").value(1));
        mvc.perform(put("/api/operaciones/{id}/visitas/1/fecha", id).contentType(MediaType.APPLICATION_JSON)
                .content("{\"fecha\":\"2026-10-21T11:00:00\"}"))
                .andExpect(status().isOk());
        mvc.perform(put("/api/operaciones/{id}/visitas/1/resultado", id).contentType(MediaType.APPLICATION_JSON)
                .content("{\"resultado\":\"REALIZADA\"}"))
                .andExpect(status().isOk()).andExpect(jsonPath("$.visitas[0].estado").value("REALIZADA"));
        mvc.perform(post("/api/operaciones/{id}/ofertas", id).contentType(MediaType.APPLICATION_JSON)
                .content("{\"clienteId\":2,\"monto\":100000,\"moneda\":\"USD\"}"))
                .andExpect(status().isOk()).andExpect(jsonPath("$.ofertas[0].id").value(1));
        mvc.perform(put("/api/operaciones/{id}/ofertas/1/aceptacion", id)).andExpect(status().isOk());
        mvc.perform(put("/api/operaciones/{id}/condiciones", id).contentType(MediaType.APPLICATION_JSON).content("""
                {"importeAcordado":100000,"moneda":"USD","fechaAcuerdo":"2026-10-21","formalizacion":"Contrato firmado"}
                """)).andExpect(status().isOk()).andExpect(jsonPath("$.estado").value("ACORDADA"));
        mvc.perform(get("/api/operaciones/{id}/puede-cerrar", id)).andExpect(status().isOk()).andExpect(content().string("true"));
        mvc.perform(put("/api/operaciones/{id}/cierre", id)).andExpect(status().isOk()).andExpect(jsonPath("$.estado").value("CERRADA"));
        mvc.perform(post("/api/operaciones/{id}/participantes", id).contentType(MediaType.APPLICATION_JSON)
                .content("{\"clienteId\":3,\"rol\":\"POSTULANTE\"}"))
                .andExpect(status().isConflict());
    }
    @Test
    void flujoHttpDeAlquiler() throws Exception {
        long id = iniciar("ALQUILER");
        mvc.perform(post("/api/operaciones/{id}/ofertas", id).contentType(MediaType.APPLICATION_JSON)
                .content("{\"clienteId\":2,\"monto\":1800,\"moneda\":\"PEN\"}"))
                .andExpect(status().isOk());
        mvc.perform(put("/api/operaciones/{id}/ofertas/1/aceptacion", id)).andExpect(status().isOk());
        mvc.perform(put("/api/operaciones/{id}/condiciones", id).contentType(MediaType.APPLICATION_JSON).content("""
                {"importeAcordado":1800,"moneda":"PEN","fechaAcuerdo":"2026-10-21",
                 "inicioAlquiler":"2026-11-01","finAlquiler":"2027-11-01","formalizacion":"Contrato de alquiler"}
                """)).andExpect(status().isOk());
        mvc.perform(put("/api/operaciones/{id}/cierre", id)).andExpect(status().isOk()).andExpect(jsonPath("$.estado").value("CERRADA"));
    }
    @Test
    void rechazoCancelacionYErrores() throws Exception {
        long id = iniciar("VENTA");
        mvc.perform(post("/api/operaciones/{id}/ofertas", id).contentType(MediaType.APPLICATION_JSON)
                .content("{\"clienteId\":2,\"monto\":100,\"moneda\":\"PEN\"}"))
                .andExpect(status().isOk());
        mvc.perform(put("/api/operaciones/{id}/ofertas/1/rechazo", id)).andExpect(status().isOk()).andExpect(jsonPath("$.ofertas[0].estado").value("RECHAZADA"));
        mvc.perform(put("/api/operaciones/{id}/cancelacion", id).contentType(MediaType.APPLICATION_JSON)
                .content("{\"motivo\":\"Desistimiento\"}"))
                .andExpect(status().isOk()).andExpect(jsonPath("$.estado").value("CANCELADA"));
        mvc.perform(get("/api/operaciones/999999")).andExpect(status().isNotFound());
        mvc.perform(post("/api/operaciones").contentType(MediaType.APPLICATION_JSON).content("{}"))
                .andExpect(status().isBadRequest());
    }
}
