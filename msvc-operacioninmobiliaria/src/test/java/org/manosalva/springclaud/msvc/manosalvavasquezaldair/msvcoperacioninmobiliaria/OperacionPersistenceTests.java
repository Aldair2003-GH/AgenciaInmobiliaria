package org.manosalva.springclaud.msvc.manosalvavasquezaldair.msvcoperacioninmobiliaria;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.*;
import java.util.*;
import java.math.BigDecimal;
import java.time.*;
import org.manosalva.springclaud.msvc.manosalvavasquezaldair.msvcoperacioninmobiliaria.domain.model.*;
import org.manosalva.springclaud.msvc.manosalvavasquezaldair.msvcoperacioninmobiliaria.domain.ids.*;
import org.manosalva.springclaud.msvc.manosalvavasquezaldair.msvcoperacioninmobiliaria.domain.enums.*;
import org.manosalva.springclaud.msvc.manosalvavasquezaldair.msvcoperacioninmobiliaria.application.service.OperacionService;
import org.manosalva.springclaud.msvc.manosalvavasquezaldair.msvcoperacioninmobiliaria.application.port.out.OperacionRepositoryPort;

@SpringBootTest
class OperacionPersistenceTests {
    @Autowired
    private OperacionRepositoryPort repository;

    private OperacionInmobiliaria iniciar(OperacionService service, ModalidadComercial modalidad) {
        return service.iniciar(new PropiedadId(1L), modalidad, Set.of(
                new Participante(new ClienteId(1L), RolParticipacion.PROPIETARIO),
                new Participante(new ClienteId(2L), RolParticipacion.POSTULANTE)));
    }

    @Test
    void recuperaOperacionCerradaConOfertaVisitaYContratoDeAlquiler() {
        OperacionService service = new OperacionService(repository);
        OperacionInmobiliaria operacion = iniciar(service, ModalidadComercial.ALQUILER);
        service.programarVisita(operacion.getId(), new ClienteId(2L), LocalDateTime.of(2026, 10, 20, 10, 0));
        service.reprogramarVisita(operacion.getId(), new VisitaId(1L), LocalDateTime.of(2026, 10, 21, 11, 0));
        service.registrarResultado(operacion.getId(), new VisitaId(1L), EstadoVisita.REALIZADA);
        service.registrarOferta(operacion.getId(), new ClienteId(2L), new BigDecimal("1800"), Moneda.PEN);
        service.aceptarOferta(operacion.getId(), new OfertaId(1L));
        service.acordarCondiciones(operacion.getId(), new CondicionesContrato(new BigDecimal("1800"), Moneda.PEN,
                LocalDate.of(2026, 10, 21), LocalDate.of(2026, 11, 1), null, "Contrato firmado"));
        service.cerrar(operacion.getId());
        OperacionService otraInstancia = new OperacionService(repository);
        OperacionInmobiliaria recuperada = otraInstancia.porId(operacion.getId()).orElseThrow();
        assertEquals(EstadoOperacion.CERRADA, recuperada.getEstado());
        assertEquals(EstadoOferta.ACEPTADA, recuperada.getOfertas().get(0).getEstado());
        assertEquals(EstadoVisita.REALIZADA, recuperada.getVisitas().get(0).getEstado());
        assertEquals(LocalDateTime.of(2026, 10, 21, 11, 0), recuperada.getVisitas().get(0).getFecha());
        assertEquals(LocalDate.of(2026, 11, 1), recuperada.getCondicionesContrato().getInicioAlquiler());
        assertNull(recuperada.getCondicionesContrato().getFinAlquiler());
        assertThrows(IllegalStateException.class, () -> otraInstancia.cancelar(operacion.getId(), "Cambio"));
    }

    @Test
    void conservaCancelacionYOfertasRechazadasConIdsLocalesIndependientes() {
        OperacionService service = new OperacionService(repository);
        OperacionInmobiliaria primera = iniciar(service, ModalidadComercial.VENTA);
        OperacionInmobiliaria segunda = iniciar(service, ModalidadComercial.VENTA);
        service.registrarOferta(primera.getId(), new ClienteId(2L), BigDecimal.TEN, Moneda.PEN);
        service.registrarOferta(segunda.getId(), new ClienteId(2L), BigDecimal.ONE, Moneda.USD);
        service.rechazarOferta(primera.getId(), new OfertaId(1L));
        service.cancelar(primera.getId(), "Desistimiento");
        OperacionInmobiliaria recuperada = repository.porId(primera.getId()).orElseThrow();
        assertEquals(EstadoOperacion.CANCELADA, recuperada.getEstado());
        assertEquals(EstadoOferta.RECHAZADA, recuperada.getOfertas().get(0).getEstado());
        assertEquals(EstadoOferta.PENDIENTE, repository.porId(segunda.getId()).orElseThrow().getOfertas().get(0).getEstado());
        assertThrows(IllegalStateException.class, () -> service.registrarResultado(primera.getId(), new VisitaId(1L), EstadoVisita.CANCELADA));
    }
}
