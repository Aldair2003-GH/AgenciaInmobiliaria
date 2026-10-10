package org.manosalva.springclaud.msvc.manosalvavasquezaldair.msvcoperacioninmobiliaria.application.port.in;

import java.util.*;
import java.math.BigDecimal;
import java.time.*;
import org.manosalva.springclaud.msvc.manosalvavasquezaldair.msvcoperacioninmobiliaria.domain.model.*;
import org.manosalva.springclaud.msvc.manosalvavasquezaldair.msvcoperacioninmobiliaria.domain.ids.*;
import org.manosalva.springclaud.msvc.manosalvavasquezaldair.msvcoperacioninmobiliaria.domain.enums.*;

public interface OperacionUseCase {
    Optional<OperacionInmobiliaria> porId(OperacionId id);
    OperacionInmobiliaria iniciar(PropiedadId propiedadId, ModalidadComercial modalidad, Set<Participante> participantes);
    Optional<OperacionInmobiliaria> registrarParticipante(OperacionId id, Participante participante);
    Optional<OperacionInmobiliaria> programarVisita(OperacionId id, ClienteId clienteId, LocalDateTime fecha);
    Optional<OperacionInmobiliaria> registrarOferta(OperacionId id, ClienteId clienteId, BigDecimal monto, Moneda moneda);
    Optional<OperacionInmobiliaria> aceptarOferta(OperacionId id, OfertaId ofertaId);
    Optional<OperacionInmobiliaria> acordarCondiciones(OperacionId id, CondicionesContrato condiciones);
    Optional<OperacionInmobiliaria> cerrar(OperacionId id);
    Optional<OperacionInmobiliaria> cancelar(OperacionId id, String motivo);
    Optional<OperacionInmobiliaria> rechazarOferta(OperacionId id, OfertaId ofertaId);
    Optional<OperacionInmobiliaria> reprogramarVisita(OperacionId id, VisitaId visitaId, LocalDateTime fecha);
    Optional<OperacionInmobiliaria> registrarResultado(OperacionId id, VisitaId visitaId, EstadoVisita resultado);
    Optional<Boolean> puedeCerrar(OperacionId id);
}
