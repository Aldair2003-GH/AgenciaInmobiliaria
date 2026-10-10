package org.manosalva.springclaud.msvc.manosalvavasquezaldair.msvccliente.application.service;

import java.util.*;
import java.math.BigDecimal;
import java.time.*;
import org.manosalva.springclaud.msvc.manosalvavasquezaldair.msvccliente.domain.model.*;
import org.manosalva.springclaud.msvc.manosalvavasquezaldair.msvccliente.domain.ids.*;
import org.manosalva.springclaud.msvc.manosalvavasquezaldair.msvccliente.domain.enums.*;
import org.manosalva.springclaud.msvc.manosalvavasquezaldair.msvccliente.application.port.in.ClienteUseCase;
import org.manosalva.springclaud.msvc.manosalvavasquezaldair.msvccliente.application.port.out.ClienteRepositoryPort;

public class ClienteService implements ClienteUseCase {
    private final ClienteRepositoryPort repository;

    public ClienteService(ClienteRepositoryPort repository) {
        this.repository = repository;
    }

    @Override
    public synchronized Optional<Cliente> porId(ClienteId id) {
        return repository.porId(id);
    }

    @Override
    public synchronized Cliente crear(DocumentoIdentidad documentoIdentidad, String nombreCompleto, DatosContacto datosContacto, Set<RolCliente> roles) {
        Cliente cliente = new Cliente(null, nombreCompleto, documentoIdentidad, datosContacto, roles);
        return repository.guardar(cliente);
    }

    @Override
    public synchronized Optional<Cliente> actualizarContacto(ClienteId id, DatosContacto datos) {
        Optional<Cliente> encontrado = repository.porId(id);
        if (encontrado.isPresent()) {
            Cliente cliente = encontrado.get();
            cliente.actualizarContacto(datos);
            return Optional.of(repository.guardar(cliente));
        }
        return Optional.empty();
    }

    @Override
    public synchronized Optional<Cliente> definirPreferencia(ClienteId id, Preferencia preferencia) {
        Optional<Cliente> encontrado = repository.porId(id);
        if (encontrado.isPresent()) {
            Cliente cliente = encontrado.get();
            cliente.definirPreferencia(preferencia);
            return Optional.of(repository.guardar(cliente));
        }
        return Optional.empty();
    }

    @Override
    public synchronized Optional<Cliente> agregarRol(ClienteId id, RolCliente rol) {
        Optional<Cliente> encontrado = repository.porId(id);
        if (encontrado.isPresent()) {
            Cliente cliente = encontrado.get();
            cliente.agregarRol(rol);
            return Optional.of(repository.guardar(cliente));
        }
        return Optional.empty();
    }
}
