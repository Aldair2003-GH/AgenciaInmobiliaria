package org.manosalva.springclaud.msvc.manosalvavasquezaldair.msvcoperacioninmobiliaria;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.*;
import java.math.BigDecimal;
import java.time.*;
import org.manosalva.springclaud.msvc.manosalvavasquezaldair.msvcoperacioninmobiliaria.domain.model.*;
import org.manosalva.springclaud.msvc.manosalvavasquezaldair.msvcoperacioninmobiliaria.domain.ids.*;
import org.manosalva.springclaud.msvc.manosalvavasquezaldair.msvcoperacioninmobiliaria.domain.enums.*;

class OperacionCoreTests {
    private OperacionInmobiliaria operacion(ModalidadComercial modalidad) {
        return new OperacionInmobiliaria(new OperacionId(1L), new PropiedadId(1L), modalidad, LocalDateTime.now(),
                Set.of(new Participante(new ClienteId(1L), RolParticipacion.PROPIETARIO),
                       new Participante(new ClienteId(2L), RolParticipacion.POSTULANTE)));
    }
    private CondicionesContrato condiciones(LocalDate inicio, LocalDate fin) {
        return new CondicionesContrato(new BigDecimal("1000"), Moneda.PEN, LocalDate.now(), inicio, fin, "Contrato firmado");
    }
    private void aceptar(OperacionInmobiliaria operacion) {
        operacion.registrarOferta(new ClienteId(2L), new BigDecimal("1000"), Moneda.PEN);
        operacion.aceptarOferta(new OfertaId(1L));
    }
    @Test
    void cierraVentaConOfertaYCondiciones() {
        OperacionInmobiliaria operacion = operacion(ModalidadComercial.VENTA);
        assertFalse(operacion.puedeCerrar());
        assertThrows(IllegalStateException.class, operacion::cerrar);
        aceptar(operacion);
        operacion.acordarCondiciones(condiciones(null, null));
        assertTrue(operacion.puedeCerrar());
        operacion.cerrar();
        assertEquals(EstadoOperacion.CERRADA, operacion.getEstado());
        assertThrows(IllegalStateException.class, () -> operacion.cancelar("Cambio"));
        assertThrows(IllegalStateException.class, () -> operacion.registrarOferta(new ClienteId(2L), BigDecimal.TEN, Moneda.PEN));
    }
    @Test
    void alquilerExigeInicioYAdmiteFinOpcional() {
        OperacionInmobiliaria operacion = operacion(ModalidadComercial.ALQUILER);
        aceptar(operacion);
        assertThrows(IllegalArgumentException.class, () -> operacion.acordarCondiciones(condiciones(null, null)));
        operacion.acordarCondiciones(condiciones(LocalDate.now(), null));
        operacion.cerrar();
        assertEquals(EstadoOperacion.CERRADA, operacion.getEstado());
        assertThrows(IllegalArgumentException.class, () -> condiciones(LocalDate.now(), LocalDate.now().minusDays(1)));
    }
    @Test
    void rechazaCondicionesIncompatiblesSinCambiarEstado() {
        OperacionInmobiliaria operacion = operacion(ModalidadComercial.VENTA);
        aceptar(operacion);
        assertThrows(IllegalArgumentException.class, () -> operacion.acordarCondiciones(condiciones(LocalDate.now(), null)));
        assertThrows(IllegalArgumentException.class, () -> operacion.acordarCondiciones(new CondicionesContrato(BigDecimal.TEN, Moneda.PEN, LocalDate.now(), null, null, "Contrato")));
        assertThrows(IllegalArgumentException.class, () -> operacion.acordarCondiciones(new CondicionesContrato(new BigDecimal("1000"), Moneda.USD, LocalDate.now(), null, null, "Contrato")));
        assertEquals(EstadoOperacion.EN_NEGOCIACION, operacion.getEstado());
        assertNull(operacion.getCondicionesContrato());
    }
    @Test
    void participantesYUnaSolaOfertaAceptada() {
        OperacionInmobiliaria operacion = operacion(ModalidadComercial.VENTA);
        assertThrows(IllegalArgumentException.class, () -> operacion.registrarOferta(new ClienteId(99L), BigDecimal.TEN, Moneda.PEN));
        operacion.registrarOferta(new ClienteId(2L), BigDecimal.TEN, Moneda.PEN);
        operacion.registrarOferta(new ClienteId(2L), BigDecimal.ONE, Moneda.PEN);
        operacion.aceptarOferta(new OfertaId(1L));
        assertThrows(IllegalStateException.class, () -> operacion.aceptarOferta(new OfertaId(2L)));
        assertEquals(EstadoOferta.PENDIENTE, operacion.getOfertas().get(1).getEstado());
        assertThrows(UnsupportedOperationException.class, () -> operacion.getParticipantes().clear());
    }
    @Test
    void visitaSeReprogramaYRegistraResultado() {
        OperacionInmobiliaria operacion = operacion(ModalidadComercial.VENTA);
        operacion.programarVisita(new ClienteId(2L), LocalDateTime.now());
        Visita visita = operacion.getVisitas().get(0);
        LocalDateTime nuevaFecha = LocalDateTime.now().plusDays(1);
        visita.reprogramar(nuevaFecha);
        assertEquals(nuevaFecha, visita.getFecha());
        visita.registrarResultado(EstadoVisita.REALIZADA);
        assertThrows(IllegalStateException.class, () -> visita.reprogramar(nuevaFecha.plusDays(1)));
    }
    @Test
    void cancelacionEsTerminalYOfertaRechazadaNoSeAcepta() {
        OperacionInmobiliaria operacion = operacion(ModalidadComercial.VENTA);
        operacion.registrarOferta(new ClienteId(2L), BigDecimal.TEN, Moneda.PEN);
        operacion.getOfertas().get(0).rechazar();
        assertThrows(IllegalStateException.class, () -> operacion.aceptarOferta(new OfertaId(1L)));
        assertThrows(IllegalArgumentException.class, () -> operacion.cancelar(" "));
        operacion.cancelar("El postulante desistió");
        assertFalse(operacion.puedeCerrar());
        assertThrows(IllegalStateException.class, () -> operacion.programarVisita(new ClienteId(2L), LocalDateTime.now()));
    }
    @Test
    void exigeParticipantesIniciales() {
        assertThrows(IllegalArgumentException.class, () -> new OperacionInmobiliaria(new OperacionId(1L), new PropiedadId(1L), ModalidadComercial.VENTA, LocalDateTime.now(), Set.of()));
    }
}
