package org.manosalva.springclaud.msvc.manosalvavasquezaldair.msvccliente;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.*;
import java.math.BigDecimal;
import java.time.*;
import org.manosalva.springclaud.msvc.manosalvavasquezaldair.msvccliente.domain.model.*;
import org.manosalva.springclaud.msvc.manosalvavasquezaldair.msvccliente.domain.ids.*;
import org.manosalva.springclaud.msvc.manosalvavasquezaldair.msvccliente.domain.enums.*;
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
class ClienteRestTests {
    @Autowired
    private MockMvc mvc;
    private long crear(String ruta, String json) throws Exception {
        String respuesta = mvc.perform(post(ruta).contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isCreated()).andReturn().getResponse().getContentAsString();
        var matcher = Pattern.compile("\"id\":(\\d+)").matcher(respuesta);
        assertTrue(matcher.find());
        return Long.parseLong(matcher.group(1));
    }

    @Test
    void creaConsultaYActualizaElCore() throws Exception {
        long id = crear("/api/clientes", """
                {"nombreCompleto":"Ana Pérez","documentoIdentidad":{"tipo":"DNI","numero":"12345678"},
                 "datosContacto":{"telefono":"999888777"},"roles":["PROPIETARIO"]}
                """);
        mvc.perform(get("/api/clientes/{id}", id)).andExpect(status().isOk()).andExpect(jsonPath("$.nombreCompleto").value("Ana Pérez"));
        mvc.perform(put("/api/clientes/{id}/contacto", id).contentType(MediaType.APPLICATION_JSON)
                .content("{\"telefono\":\"900000000\",\"correo\":\"ana@example.com\"}"))
                .andExpect(status().isOk()).andExpect(jsonPath("$.datosContacto.correo").value("ana@example.com"));
        mvc.perform(post("/api/clientes/{id}/roles", id).contentType(MediaType.APPLICATION_JSON).content("{\"rol\":\"COMPRADOR\"}"))
                .andExpect(status().isOk()).andExpect(jsonPath("$.roles.length()").value(2));
        mvc.perform(put("/api/clientes/{id}/preferencia", id).contentType(MediaType.APPLICATION_JSON).content("""
                {"modalidad":"VENTA","tipoInmueble":"CASA","distrito":"Lima","presupuestoMaximo":100000,"moneda":"USD"}
                """)).andExpect(status().isOk()).andExpect(jsonPath("$.preferencia.moneda").value("USD"));
    }
    @Test
    void responde404Y400() throws Exception {
        mvc.perform(get("/api/clientes/999999")).andExpect(status().isNotFound());
        mvc.perform(post("/api/clientes").contentType(MediaType.APPLICATION_JSON).content("{}"))
                .andExpect(status().isBadRequest()).andExpect(jsonPath("$.mensaje").exists());
        mvc.perform(post("/api/clientes").contentType(MediaType.APPLICATION_JSON).content("{malformed"))
                .andExpect(status().isBadRequest());
    }
}
