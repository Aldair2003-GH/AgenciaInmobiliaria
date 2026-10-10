package org.manosalva.springclaud.msvc.manosalvavasquezaldair.msvcpropiedad.application.service;

import java.util.*;
import java.math.BigDecimal;
import java.time.*;
import org.manosalva.springclaud.msvc.manosalvavasquezaldair.msvcpropiedad.domain.model.*;
import org.manosalva.springclaud.msvc.manosalvavasquezaldair.msvcpropiedad.domain.ids.*;
import org.manosalva.springclaud.msvc.manosalvavasquezaldair.msvcpropiedad.domain.enums.*;
import org.manosalva.springclaud.msvc.manosalvavasquezaldair.msvcpropiedad.application.port.in.PropiedadUseCase;
import org.manosalva.springclaud.msvc.manosalvavasquezaldair.msvcpropiedad.application.port.out.PropiedadRepositoryPort;

public class PropiedadService implements PropiedadUseCase {
    private final PropiedadRepositoryPort repository;

    public PropiedadService(PropiedadRepositoryPort repository) {
        this.repository = repository;
    }

    @Override
    public synchronized Optional<Propiedad> porId(PropiedadId id) {
        return repository.porId(id);
    }

    @Override
    public synchronized Propiedad crear(ClienteId propietarioId, Direccion direccion, Caracteristicas caracteristicas, Set<PrecioComercial> precios, List<Fotografia> fotografias) {
        Propiedad propiedad = new Propiedad(null, propietarioId, direccion, caracteristicas, precios, fotografias);
        return repository.guardar(propiedad);
    }

    @Override
    public synchronized Optional<Propiedad> establecerPrecio(PropiedadId id, PrecioComercial precio) {
        Optional<Propiedad> encontrado = repository.porId(id);
        if (encontrado.isPresent()) {
            Propiedad propiedad = encontrado.get();
            propiedad.establecerPrecio(precio);
            return Optional.of(repository.guardar(propiedad));
        }
        return Optional.empty();
    }

    @Override
    public synchronized Optional<Propiedad> publicar(PropiedadId id) {
        Optional<Propiedad> encontrado = repository.porId(id);
        if (encontrado.isPresent()) {
            Propiedad propiedad = encontrado.get();
            propiedad.publicar();
            return Optional.of(repository.guardar(propiedad));
        }
        return Optional.empty();
    }

    @Override
    public synchronized Optional<Propiedad> reservar(PropiedadId id, OperacionId operacionId) {
        Optional<Propiedad> encontrado = repository.porId(id);
        if (encontrado.isPresent()) {
            Propiedad propiedad = encontrado.get();
            propiedad.reservar(operacionId);
            return Optional.of(repository.guardar(propiedad));
        }
        return Optional.empty();
    }

    @Override
    public synchronized Optional<Propiedad> actualizarDisponibilidad(PropiedadId id, EstadoDisponibilidad estado) {
        Optional<Propiedad> encontrado = repository.porId(id);
        if (encontrado.isPresent()) {
            Propiedad propiedad = encontrado.get();
            propiedad.actualizarDisponibilidad(estado);
            return Optional.of(repository.guardar(propiedad));
        }
        return Optional.empty();
    }
}
