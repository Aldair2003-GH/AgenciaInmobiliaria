package org.manosalva.springclaud.msvc.manosalvavasquezaldair.msvcoperacioninmobiliaria.infrastructure.persistence;

import java.util.*;
import org.springframework.transaction.annotation.Transactional;
import org.manosalva.springclaud.msvc.manosalvavasquezaldair.msvcoperacioninmobiliaria.domain.model.*;
import org.manosalva.springclaud.msvc.manosalvavasquezaldair.msvcoperacioninmobiliaria.domain.ids.*;
import org.manosalva.springclaud.msvc.manosalvavasquezaldair.msvcoperacioninmobiliaria.application.port.out.OperacionRepositoryPort;
import org.manosalva.springclaud.msvc.manosalvavasquezaldair.msvcoperacioninmobiliaria.infrastructure.persistence.entity.*;
import org.manosalva.springclaud.msvc.manosalvavasquezaldair.msvcoperacioninmobiliaria.infrastructure.persistence.repository.SpringDataOperacionRepository;

public class OperacionPersistenceAdapter implements OperacionRepositoryPort {
    private final SpringDataOperacionRepository repository;

    public OperacionPersistenceAdapter(SpringDataOperacionRepository repository) {
        this.repository = repository;
    }

    @Override
    @Transactional
    public OperacionInmobiliaria guardar(OperacionInmobiliaria valor) {
        OperacionEntity entity = valor.getId() == null ? new OperacionEntity()
                : repository.findById(valor.getId().getValue()).orElseThrow(() -> new IllegalArgumentException("El agregado ya no existe"));
        copiar(valor, entity);
        return toDomain(repository.saveAndFlush(entity));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<OperacionInmobiliaria> porId(OperacionId id) {
        Optional<OperacionEntity> encontrado = repository.findById(id.getValue());
        if (encontrado.isPresent()) {
            return Optional.of(toDomain(encontrado.get()));
        }
        return Optional.empty();
    }

    private void copiar(OperacionInmobiliaria valor, OperacionEntity entity) {
        entity.setPropiedadId(valor.getPropiedadId().getValue());
        entity.setModalidad(valor.getModalidad());
        entity.setEstado(valor.getEstado());
        entity.setFechaInicio(valor.getFechaInicio());
        entity.getParticipantes().clear();
        for (Participante participante : valor.getParticipantes()) {
            ParticipanteEntity registro = new ParticipanteEntity();
            registro.setClienteId(participante.getClienteId().getValue());
            registro.setRol(participante.getRol());
            entity.getParticipantes().add(registro);
        }
        List<OfertaEntity> ofertas = new ArrayList<>();
        for (Oferta oferta : valor.getOfertas()) {
            OfertaEntity registro = entity.getOfertas().stream()
                    .filter(o -> o.getOfertaId().equals(oferta.getId().getValue())).findFirst().orElse(new OfertaEntity());
            registro.setOfertaId(oferta.getId().getValue());
            registro.setClienteId(oferta.getClienteId().getValue());
            registro.setImporte(oferta.getImporte());
            registro.setMoneda(oferta.getMoneda());
            registro.setFecha(oferta.getFecha());
            registro.setEstado(oferta.getEstado());
            registro.setOperacion(entity);
            ofertas.add(registro);
        }
        entity.getOfertas().clear();
        entity.getOfertas().addAll(ofertas);
        List<VisitaEntity> visitas = new ArrayList<>();
        for (Visita visita : valor.getVisitas()) {
            VisitaEntity registro = entity.getVisitas().stream()
                    .filter(v -> v.getVisitaId().equals(visita.getId().getValue())).findFirst().orElse(new VisitaEntity());
            registro.setVisitaId(visita.getId().getValue());
            registro.setClienteId(visita.getClienteId().getValue());
            registro.setFecha(visita.getFecha());
            registro.setEstado(visita.getEstado());
            registro.setOperacion(entity);
            visitas.add(registro);
        }
        entity.getVisitas().clear();
        entity.getVisitas().addAll(visitas);
        CondicionesContrato condiciones = valor.getCondicionesContrato();
        entity.setImporteAcordado(condiciones == null ? null : condiciones.getImporteAcordado());
        entity.setMonedaAcuerdo(condiciones == null ? null : condiciones.getMoneda());
        entity.setFechaAcuerdo(condiciones == null ? null : condiciones.getFechaAcuerdo());
        entity.setInicioAlquiler(condiciones == null ? null : condiciones.getInicioAlquiler());
        entity.setFinAlquiler(condiciones == null ? null : condiciones.getFinAlquiler());
        entity.setFormalizacion(condiciones == null ? null : condiciones.getFormalizacion());
    }

    private OperacionInmobiliaria toDomain(OperacionEntity entity) {
        Set<Participante> participantes = new LinkedHashSet<>();
        for (ParticipanteEntity participante : entity.getParticipantes()) {
            participantes.add(new Participante(new ClienteId(participante.getClienteId()), participante.getRol()));
        }
        List<Oferta> ofertas = new ArrayList<>();
        for (OfertaEntity oferta : entity.getOfertas()) {
            ofertas.add(Oferta.reconstruir(new OfertaId(oferta.getOfertaId()), new ClienteId(oferta.getClienteId()),
                    oferta.getImporte(), oferta.getMoneda(), oferta.getFecha(), oferta.getEstado()));
        }
        List<Visita> visitas = new ArrayList<>();
        for (VisitaEntity visita : entity.getVisitas()) {
            visitas.add(Visita.reconstruir(new VisitaId(visita.getVisitaId()), new ClienteId(visita.getClienteId()), visita.getFecha(), visita.getEstado()));
        }
        CondicionesContrato condiciones = entity.getImporteAcordado() == null ? null
                : new CondicionesContrato(entity.getImporteAcordado(), entity.getMonedaAcuerdo(), entity.getFechaAcuerdo(),
                        entity.getInicioAlquiler(), entity.getFinAlquiler(), entity.getFormalizacion());
        return OperacionInmobiliaria.reconstruir(new OperacionId(entity.getId()), new PropiedadId(entity.getPropiedadId()),
                entity.getModalidad(), entity.getFechaInicio(), participantes, entity.getEstado(), ofertas, visitas, condiciones);
    }
}
