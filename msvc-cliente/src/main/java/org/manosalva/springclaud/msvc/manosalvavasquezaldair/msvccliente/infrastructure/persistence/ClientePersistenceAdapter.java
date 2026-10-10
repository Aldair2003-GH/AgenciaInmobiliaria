package org.manosalva.springclaud.msvc.manosalvavasquezaldair.msvccliente.infrastructure.persistence;

import java.util.*;
import org.springframework.transaction.annotation.Transactional;
import org.manosalva.springclaud.msvc.manosalvavasquezaldair.msvccliente.domain.model.*;
import org.manosalva.springclaud.msvc.manosalvavasquezaldair.msvccliente.domain.ids.*;
import org.manosalva.springclaud.msvc.manosalvavasquezaldair.msvccliente.application.port.out.ClienteRepositoryPort;
import org.manosalva.springclaud.msvc.manosalvavasquezaldair.msvccliente.infrastructure.persistence.entity.*;
import org.manosalva.springclaud.msvc.manosalvavasquezaldair.msvccliente.infrastructure.persistence.repository.SpringDataClienteRepository;

public class ClientePersistenceAdapter implements ClienteRepositoryPort {
    private final SpringDataClienteRepository repository;

    public ClientePersistenceAdapter(SpringDataClienteRepository repository) {
        this.repository = repository;
    }

    @Override
    @Transactional
    public Cliente guardar(Cliente valor) {
        ClienteEntity entity = valor.getId() == null ? new ClienteEntity()
                : repository.findById(valor.getId().getValue()).orElseThrow(() -> new IllegalArgumentException("El agregado ya no existe"));
        copiar(valor, entity);
        return toDomain(repository.saveAndFlush(entity));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Cliente> porId(ClienteId id) {
        Optional<ClienteEntity> encontrado = repository.findById(id.getValue());
        if (encontrado.isPresent()) {
            return Optional.of(toDomain(encontrado.get()));
        }
        return Optional.empty();
    }

    private void copiar(Cliente valor, ClienteEntity entity) {
        entity.setNombreCompleto(valor.getNombreCompleto());
        entity.setTipoDocumento(valor.getDocumentoIdentidad().getTipo());
        entity.setNumeroDocumento(valor.getDocumentoIdentidad().getNumero());
        entity.setTelefono(valor.getDatosContacto().getTelefono());
        entity.setCorreo(valor.getDatosContacto().getCorreo());
        entity.getRoles().clear();
        entity.getRoles().addAll(valor.getRoles());
        Preferencia preferencia = valor.getPreferencia();
        entity.setPreferenciaModalidad(preferencia == null ? null : preferencia.getModalidad());
        entity.setPreferenciaTipoInmueble(preferencia == null ? null : preferencia.getTipoInmueble());
        entity.setPreferenciaDistrito(preferencia == null ? null : preferencia.getDistrito());
        entity.setPreferenciaPresupuestoMaximo(preferencia == null ? null : preferencia.getPresupuestoMaximo());
        entity.setPreferenciaMoneda(preferencia == null ? null : preferencia.getMoneda());
    }

    private Cliente toDomain(ClienteEntity entity) {
        Cliente cliente = new Cliente(new ClienteId(entity.getId()), entity.getNombreCompleto(),
                new DocumentoIdentidad(entity.getTipoDocumento(), entity.getNumeroDocumento()),
                new DatosContacto(entity.getTelefono(), entity.getCorreo()), new LinkedHashSet<>(entity.getRoles()));
        if (entity.getPreferenciaModalidad() != null) {
            cliente.definirPreferencia(new Preferencia(entity.getPreferenciaModalidad(), entity.getPreferenciaTipoInmueble(),
                    entity.getPreferenciaDistrito(), entity.getPreferenciaPresupuestoMaximo(), entity.getPreferenciaMoneda()));
        }
        return cliente;
    }
}
