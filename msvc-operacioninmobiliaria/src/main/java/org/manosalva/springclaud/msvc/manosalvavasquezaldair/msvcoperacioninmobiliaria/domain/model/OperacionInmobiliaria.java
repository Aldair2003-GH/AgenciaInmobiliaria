package org.manosalva.springclaud.msvc.manosalvavasquezaldair.msvcoperacioninmobiliaria.domain.model;

import java.util.*;
import java.math.BigDecimal;
import java.time.*;
import org.manosalva.springclaud.msvc.manosalvavasquezaldair.msvcoperacioninmobiliaria.domain.enums.*;
import org.manosalva.springclaud.msvc.manosalvavasquezaldair.msvcoperacioninmobiliaria.domain.ids.*;

public class OperacionInmobiliaria {
    private final OperacionId id;
    private final PropiedadId propiedadId;
    private final ModalidadComercial modalidad;
    private EstadoOperacion estado = EstadoOperacion.INICIADA;
    private final LocalDateTime fechaInicio;
    private final Set<Participante> participantes = new LinkedHashSet<>();
    private final List<Oferta> ofertas = new ArrayList<>();
    private final List<Visita> visitas = new ArrayList<>();
    private CondicionesContrato condicionesContrato;

    public OperacionInmobiliaria(OperacionId id, PropiedadId propiedadId, ModalidadComercial modalidad,
                                LocalDateTime fechaInicio, Set<Participante> participantes) {
        // El ID es nulo solo antes del primer almacenamiento; lo asigna el repositorio.
        this.id = id;
        this.propiedadId = Objects.requireNonNull(propiedadId, "propiedadId es obligatorio");
        this.modalidad = Objects.requireNonNull(modalidad, "modalidad es obligatoria");
        this.fechaInicio = Objects.requireNonNull(fechaInicio, "fechaInicio es obligatoria");
        if (participantes == null || participantes.isEmpty()) throw new IllegalArgumentException("Se requiere al menos un participante");
        participantes.forEach(this::registrarParticipante);
    }

    /** Reconstruye el agregado con sus entidades y estado persistidos. */
    public static OperacionInmobiliaria reconstruir(OperacionId id, PropiedadId propiedadId,
            ModalidadComercial modalidad, LocalDateTime fechaInicio, Set<Participante> participantes,
            EstadoOperacion estado, List<Oferta> ofertas, List<Visita> visitas, CondicionesContrato condiciones) {
        OperacionInmobiliaria operacion = new OperacionInmobiliaria(id, propiedadId, modalidad, fechaInicio, participantes);
        operacion.ofertas.addAll(ofertas);
        operacion.visitas.addAll(visitas);
        operacion.condicionesContrato = condiciones;
        operacion.estado = Objects.requireNonNull(estado);
        return operacion;
    }

    private void comprobarActiva() {
        if (estado == EstadoOperacion.CERRADA || estado == EstadoOperacion.CANCELADA) throw new IllegalStateException("La operación terminó");
    }

    private void comprobarNegociable() {
        comprobarActiva();
        if (estado == EstadoOperacion.ACORDADA) throw new IllegalStateException("La operación ya tiene condiciones acordadas");
    }

    private void comprobarPostulante(ClienteId clienteId) {
        if (participantes.stream().noneMatch(p -> p.getClienteId().equals(clienteId) && p.getRol() == RolParticipacion.POSTULANTE))
            throw new IllegalArgumentException("El cliente debe estar registrado como postulante");
    }

    public void registrarParticipante(Participante participante) {
        comprobarActiva();
        participantes.add(Objects.requireNonNull(participante, "participante es obligatorio"));
    }

    public void programarVisita(ClienteId clienteId, LocalDateTime fecha) {
        comprobarActiva();
        comprobarPostulante(clienteId);
        visitas.add(new Visita(new VisitaId((long) visitas.size() + 1), clienteId, fecha));
    }
    // La moneda se explicita porque Oferta la exige y el agregado no tiene una moneda global.
    public void registrarOferta(ClienteId clienteId, BigDecimal monto, Moneda moneda) {
        comprobarNegociable();
        comprobarPostulante(clienteId);
        ofertas.add(new Oferta(new OfertaId((long) ofertas.size() + 1), clienteId, monto, moneda, LocalDateTime.now()));
        estado = EstadoOperacion.EN_NEGOCIACION;
    }

    public void aceptarOferta(OfertaId ofertaId) {
        comprobarNegociable();
        if (ofertas.stream().anyMatch(o -> o.getEstado() == EstadoOferta.ACEPTADA)) throw new IllegalStateException("Ya hay una oferta aceptada");
        Oferta oferta = ofertas.stream().filter(o -> o.getId().equals(ofertaId)).findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Oferta inexistente"));
        oferta.aceptar();
    }

    public void acordarCondiciones(CondicionesContrato condiciones) {
        comprobarNegociable();
        Objects.requireNonNull(condiciones, "condiciones es obligatorio");
        Oferta aceptada = ofertas.stream().filter(o -> o.getEstado() == EstadoOferta.ACEPTADA).findFirst()
                .orElseThrow(() -> new IllegalStateException("Se requiere una oferta aceptada"));
        if (aceptada.getMoneda() != condiciones.getMoneda() || aceptada.getImporte().compareTo(condiciones.getImporteAcordado()) != 0)
            throw new IllegalArgumentException("Las condiciones deben coincidir con la oferta aceptada");
        if (modalidad == ModalidadComercial.VENTA && (condiciones.getInicioAlquiler() != null || condiciones.getFinAlquiler() != null))
            throw new IllegalArgumentException("Una venta no tiene fechas de alquiler");
        if (modalidad == ModalidadComercial.ALQUILER && condiciones.getInicioAlquiler() == null)
            throw new IllegalArgumentException("El alquiler requiere fecha de inicio");
        condicionesContrato = condiciones;
        estado = EstadoOperacion.ACORDADA;
    }

    public boolean puedeCerrar() {
        return estado == EstadoOperacion.ACORDADA && condicionesContrato != null
                && ofertas.stream().anyMatch(o -> o.getEstado() == EstadoOferta.ACEPTADA)
                && participantes.stream().anyMatch(p -> p.getRol() == RolParticipacion.PROPIETARIO)
                && participantes.stream().anyMatch(p -> p.getRol() == RolParticipacion.POSTULANTE);
    }

    public void cerrar() {
        if (!puedeCerrar()) throw new IllegalStateException("La operación no cumple las condiciones de cierre");
        estado = EstadoOperacion.CERRADA;
    }

    public void cancelar(String motivo) {
        comprobarActiva();
        if (motivo == null || motivo.isBlank()) throw new IllegalArgumentException("motivo es obligatorio");
        estado = EstadoOperacion.CANCELADA;
    }

    public OperacionId getId() {
        return id;
    }

    public PropiedadId getPropiedadId() {
        return propiedadId;
    }

    public ModalidadComercial getModalidad() {
        return modalidad;
    }

    public EstadoOperacion getEstado() {
        return estado;
    }

    public LocalDateTime getFechaInicio() {
        return fechaInicio;
    }

    public Set<Participante> getParticipantes() {
        return Collections.unmodifiableSet(participantes);
    }

    public List<Oferta> getOfertas() {
        return Collections.unmodifiableList(ofertas);
    }

    public List<Visita> getVisitas() {
        return Collections.unmodifiableList(visitas);
    }

    public CondicionesContrato getCondicionesContrato() {
        return condicionesContrato;
    }
}
