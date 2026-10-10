package org.manosalva.springclaud.msvc.manosalvavasquezaldair.msvcoperacioninmobiliaria.application.service;

import java.util.*;
import java.math.BigDecimal;
import java.time.*;
import org.manosalva.springclaud.msvc.manosalvavasquezaldair.msvcoperacioninmobiliaria.domain.model.*;
import org.manosalva.springclaud.msvc.manosalvavasquezaldair.msvcoperacioninmobiliaria.domain.ids.*;
import org.manosalva.springclaud.msvc.manosalvavasquezaldair.msvcoperacioninmobiliaria.domain.enums.*;
import org.manosalva.springclaud.msvc.manosalvavasquezaldair.msvcoperacioninmobiliaria.application.port.in.OperacionUseCase;
import org.manosalva.springclaud.msvc.manosalvavasquezaldair.msvcoperacioninmobiliaria.application.port.out.OperacionRepositoryPort;

public class OperacionService implements OperacionUseCase {
    private final OperacionRepositoryPort repository;

    public OperacionService(OperacionRepositoryPort repository) {
        this.repository = repository;
    }

    @Override
    public synchronized Optional<OperacionInmobiliaria> porId(OperacionId id) {
        return repository.porId(id);
    }

    @Override
    public synchronized OperacionInmobiliaria iniciar(PropiedadId propiedadId, ModalidadComercial modalidad, Set<Participante> participantes) {
        OperacionInmobiliaria operacion = new OperacionInmobiliaria(null, propiedadId, modalidad, LocalDateTime.now(), participantes);
        return repository.guardar(operacion);
    }

    @Override
    public synchronized Optional<OperacionInmobiliaria> registrarParticipante(OperacionId id, Participante participante) {
        Optional<OperacionInmobiliaria> encontrado = repository.porId(id);
        if (encontrado.isPresent()) {
            OperacionInmobiliaria operacion = encontrado.get();
            operacion.registrarParticipante(participante);
            return Optional.of(repository.guardar(operacion));
        }
        return Optional.empty();
    }

    @Override
    public synchronized Optional<OperacionInmobiliaria> programarVisita(OperacionId id, ClienteId clienteId, LocalDateTime fecha) {
        Optional<OperacionInmobiliaria> encontrado = repository.porId(id);
        if (encontrado.isPresent()) {
            OperacionInmobiliaria operacion = encontrado.get();
            operacion.programarVisita(clienteId, fecha);
            return Optional.of(repository.guardar(operacion));
        }
        return Optional.empty();
    }

    @Override
    public synchronized Optional<OperacionInmobiliaria> registrarOferta(OperacionId id, ClienteId clienteId, BigDecimal monto, Moneda moneda) {
        Optional<OperacionInmobiliaria> encontrado = repository.porId(id);
        if (encontrado.isPresent()) {
            OperacionInmobiliaria operacion = encontrado.get();
            operacion.registrarOferta(clienteId, monto, moneda);
            return Optional.of(repository.guardar(operacion));
        }
        return Optional.empty();
    }

    @Override
    public synchronized Optional<OperacionInmobiliaria> aceptarOferta(OperacionId id, OfertaId ofertaId) {
        Optional<OperacionInmobiliaria> encontrado = repository.porId(id);
        if (encontrado.isPresent()) {
            OperacionInmobiliaria operacion = encontrado.get();
            operacion.aceptarOferta(ofertaId);
            return Optional.of(repository.guardar(operacion));
        }
        return Optional.empty();
    }

    @Override
    public synchronized Optional<OperacionInmobiliaria> acordarCondiciones(OperacionId id, CondicionesContrato condiciones) {
        Optional<OperacionInmobiliaria> encontrado = repository.porId(id);
        if (encontrado.isPresent()) {
            OperacionInmobiliaria operacion = encontrado.get();
            operacion.acordarCondiciones(condiciones);
            return Optional.of(repository.guardar(operacion));
        }
        return Optional.empty();
    }

    @Override
    public synchronized Optional<OperacionInmobiliaria> cerrar(OperacionId id) {
        Optional<OperacionInmobiliaria> encontrado = repository.porId(id);
        if (encontrado.isPresent()) {
            OperacionInmobiliaria operacion = encontrado.get();
            operacion.cerrar();
            return Optional.of(repository.guardar(operacion));
        }
        return Optional.empty();
    }

    @Override
    public synchronized Optional<OperacionInmobiliaria> cancelar(OperacionId id, String motivo) {
        Optional<OperacionInmobiliaria> encontrado = repository.porId(id);
        if (encontrado.isPresent()) {
            OperacionInmobiliaria operacion = encontrado.get();
            operacion.cancelar(motivo);
            return Optional.of(repository.guardar(operacion));
        }
        return Optional.empty();
    }

    @Override
    public synchronized Optional<OperacionInmobiliaria> rechazarOferta(OperacionId id, OfertaId ofertaId) {
        Optional<OperacionInmobiliaria> encontrado = repository.porId(id);
        if (encontrado.isPresent()) {
            OperacionInmobiliaria operacion = encontrado.get();
            comprobarActiva(operacion);
            operacion.getOfertas().stream().filter(o -> o.getId().equals(ofertaId)).findFirst().orElseThrow(() -> new IllegalArgumentException("Oferta inexistente")).rechazar();
            return Optional.of(repository.guardar(operacion));
        }
        return Optional.empty();
    }

    @Override
    public synchronized Optional<OperacionInmobiliaria> reprogramarVisita(OperacionId id, VisitaId visitaId, LocalDateTime fecha) {
        Optional<OperacionInmobiliaria> encontrado = repository.porId(id);
        if (encontrado.isPresent()) {
            OperacionInmobiliaria operacion = encontrado.get();
            comprobarActiva(operacion);
            operacion.getVisitas().stream().filter(v -> v.getId().equals(visitaId)).findFirst().orElseThrow(() -> new IllegalArgumentException("Visita inexistente")).reprogramar(fecha);
            return Optional.of(repository.guardar(operacion));
        }
        return Optional.empty();
    }

    @Override
    public synchronized Optional<OperacionInmobiliaria> registrarResultado(OperacionId id, VisitaId visitaId, EstadoVisita resultado) {
        Optional<OperacionInmobiliaria> encontrado = repository.porId(id);
        if (encontrado.isPresent()) {
            OperacionInmobiliaria operacion = encontrado.get();
            comprobarActiva(operacion);
            operacion.getVisitas().stream().filter(v -> v.getId().equals(visitaId)).findFirst().orElseThrow(() -> new IllegalArgumentException("Visita inexistente")).registrarResultado(resultado);
            return Optional.of(repository.guardar(operacion));
        }
        return Optional.empty();
    }

    @Override
    public synchronized Optional<Boolean> puedeCerrar(OperacionId id) {
        Optional<OperacionInmobiliaria> encontrado = repository.porId(id);
        if (encontrado.isPresent()) {
            return Optional.of(encontrado.get().puedeCerrar());
        }
        return Optional.empty();
    }

    private void comprobarActiva(OperacionInmobiliaria operacion) {
        if (operacion.getEstado() == EstadoOperacion.CERRADA || operacion.getEstado() == EstadoOperacion.CANCELADA)
            throw new IllegalStateException("La operación terminó");
    }
}
