package org.manosalva.springclaud.msvc.manosalvavasquezaldair.msvcpropiedad;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.*;
import java.math.BigDecimal;
import java.time.*;
import org.manosalva.springclaud.msvc.manosalvavasquezaldair.msvcpropiedad.domain.model.*;
import org.manosalva.springclaud.msvc.manosalvavasquezaldair.msvcpropiedad.domain.ids.*;
import org.manosalva.springclaud.msvc.manosalvavasquezaldair.msvcpropiedad.domain.enums.*;
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
class PropiedadRestTests {
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
    void creaPublicaReservaYRechazaNuevaReserva() throws Exception {
        long id = crear("/api/propiedades", """
                {"propietarioId":1,"direccion":{"departamento":"Lima","provincia":"Lima","distrito":"Miraflores","detalle":"Calle 1"},
                 "caracteristicas":{"tipoInmueble":"DEPARTAMENTO","areaM2":80,"dormitorios":2},
                 "precios":[{"modalidad":"VENTA","importe":120000,"moneda":"USD"}],
                 "fotografias":[{"id":1,"url":"https://example.com/casa.jpg","orden":0}]}
                """);
        mvc.perform(put("/api/propiedades/{id}/publicacion", id)).andExpect(status().isOk()).andExpect(jsonPath("$.publicada").value(true));
        mvc.perform(put("/api/propiedades/{id}/precio", id).contentType(MediaType.APPLICATION_JSON)
                .content("{\"modalidad\":\"ALQUILER\",\"importe\":2000,\"moneda\":\"PEN\"}"))
                .andExpect(status().isOk()).andExpect(jsonPath("$.precios.length()").value(2));
        mvc.perform(put("/api/propiedades/{id}/reserva", id).contentType(MediaType.APPLICATION_JSON).content("{\"operacionId\":1}"))
                .andExpect(status().isOk()).andExpect(jsonPath("$.disponibilidad").value("RESERVADA"));
        mvc.perform(put("/api/propiedades/{id}/reserva", id).contentType(MediaType.APPLICATION_JSON).content("{\"operacionId\":2}"))
                .andExpect(status().isConflict());
        mvc.perform(put("/api/propiedades/{id}/disponibilidad", id).contentType(MediaType.APPLICATION_JSON).content("{\"estado\":\"VENDIDA\"}"))
                .andExpect(status().isOk()).andExpect(jsonPath("$.disponibilidad").value("VENDIDA"));
    }
    @Test
    void responde404Y400() throws Exception {
        mvc.perform(get("/api/propiedades/999999")).andExpect(status().isNotFound());
        mvc.perform(post("/api/propiedades").contentType(MediaType.APPLICATION_JSON).content("{}"))
                .andExpect(status().isBadRequest());
    }
}
