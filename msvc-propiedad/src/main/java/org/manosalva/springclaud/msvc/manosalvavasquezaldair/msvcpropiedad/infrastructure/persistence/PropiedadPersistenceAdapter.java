package org.manosalva.springclaud.msvc.manosalvavasquezaldair.msvcpropiedad.infrastructure.persistence;

import java.util.*;
import org.springframework.transaction.annotation.Transactional;
import org.manosalva.springclaud.msvc.manosalvavasquezaldair.msvcpropiedad.domain.model.*;
import org.manosalva.springclaud.msvc.manosalvavasquezaldair.msvcpropiedad.domain.ids.*;
import org.manosalva.springclaud.msvc.manosalvavasquezaldair.msvcpropiedad.application.port.out.PropiedadRepositoryPort;
import org.manosalva.springclaud.msvc.manosalvavasquezaldair.msvcpropiedad.infrastructure.persistence.entity.*;
import org.manosalva.springclaud.msvc.manosalvavasquezaldair.msvcpropiedad.infrastructure.persistence.repository.SpringDataPropiedadRepository;

public class PropiedadPersistenceAdapter implements PropiedadRepositoryPort {
    private final SpringDataPropiedadRepository repository;

    public PropiedadPersistenceAdapter(SpringDataPropiedadRepository repository) {
        this.repository = repository;
    }

    @Override
    @Transactional
    public Propiedad guardar(Propiedad valor) {
        PropiedadEntity entity = valor.getId() == null ? new PropiedadEntity()
                : repository.findById(valor.getId().getValue()).orElseThrow(() -> new IllegalArgumentException("El agregado ya no existe"));
        copiar(valor, entity);
        return toDomain(repository.saveAndFlush(entity));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Propiedad> porId(PropiedadId id) {
        Optional<PropiedadEntity> encontrado = repository.findById(id.getValue());
        if (encontrado.isPresent()) {
            return Optional.of(toDomain(encontrado.get()));
        }
        return Optional.empty();
    }

    private void copiar(Propiedad valor, PropiedadEntity entity) {
        entity.setPropietarioId(valor.getPropietarioId().getValue());
        entity.setDepartamento(valor.getDireccion().getDepartamento());
        entity.setProvincia(valor.getDireccion().getProvincia());
        entity.setDistrito(valor.getDireccion().getDistrito());
        entity.setDetalle(valor.getDireccion().getDetalle());
        entity.setTipoInmueble(valor.getCaracteristicas().getTipoInmueble());
        entity.setAreaM2(valor.getCaracteristicas().getAreaM2());
        entity.setDormitorios(valor.getCaracteristicas().getDormitorios());
        entity.setBanos(valor.getCaracteristicas().getBanos());
        entity.setDisponibilidad(valor.getDisponibilidad());
        entity.setPublicada(valor.isPublicada());
        entity.getPrecios().clear();
        for (PrecioComercial precio : valor.getPrecios()) {
            PrecioComercialEntity registro = new PrecioComercialEntity();
            registro.setModalidad(precio.getModalidad());
            registro.setImporte(precio.getImporte());
            registro.setMoneda(precio.getMoneda());
            entity.getPrecios().add(registro);
        }
        List<FotografiaEntity> fotografias = new ArrayList<>();
        for (Fotografia fotografia : valor.getFotografias()) {
            FotografiaEntity registro = entity.getFotografias().stream()
                    .filter(f -> f.getFotografiaId().equals(fotografia.getId().getValue())).findFirst().orElse(new FotografiaEntity());
            registro.setFotografiaId(fotografia.getId().getValue());
            registro.setUrl(fotografia.getUrl());
            registro.setOrden(fotografia.getOrden());
            registro.setPropiedad(entity);
            fotografias.add(registro);
        }
        entity.getFotografias().clear();
        entity.getFotografias().addAll(fotografias);
    }

    private Propiedad toDomain(PropiedadEntity entity) {
        Set<PrecioComercial> precios = new LinkedHashSet<>();
        for (PrecioComercialEntity precio : entity.getPrecios()) {
            precios.add(new PrecioComercial(precio.getModalidad(), precio.getImporte(), precio.getMoneda()));
        }
        List<Fotografia> fotografias = new ArrayList<>();
        for (FotografiaEntity fotografia : entity.getFotografias()) {
            fotografias.add(new Fotografia(new FotografiaId(fotografia.getFotografiaId()), fotografia.getUrl(), fotografia.getOrden()));
        }
        return Propiedad.reconstruir(new PropiedadId(entity.getId()), new ClienteId(entity.getPropietarioId()),
                new Direccion(entity.getDepartamento(), entity.getProvincia(), entity.getDistrito(), entity.getDetalle()),
                new Caracteristicas(entity.getTipoInmueble(), entity.getAreaM2(), entity.getDormitorios(), entity.getBanos()),
                precios, fotografias, entity.getDisponibilidad(), entity.getPublicada());
    }
}
