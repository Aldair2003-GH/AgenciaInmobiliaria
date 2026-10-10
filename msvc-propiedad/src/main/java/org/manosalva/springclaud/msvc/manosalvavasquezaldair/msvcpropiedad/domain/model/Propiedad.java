package org.manosalva.springclaud.msvc.manosalvavasquezaldair.msvcpropiedad.domain.model;

import java.util.*;
import java.math.BigDecimal;
import java.time.*;
import org.manosalva.springclaud.msvc.manosalvavasquezaldair.msvcpropiedad.domain.enums.*;
import org.manosalva.springclaud.msvc.manosalvavasquezaldair.msvcpropiedad.domain.ids.*;

public class Propiedad {
    private final PropiedadId id;
    private final ClienteId propietarioId;
    private final Direccion direccion;
    private final Caracteristicas caracteristicas;
    private EstadoDisponibilidad disponibilidad = EstadoDisponibilidad.DISPONIBLE;
    private boolean publicada;
    private final Set<PrecioComercial> precios = new LinkedHashSet<>();
    private final List<Fotografia> fotografias = new ArrayList<>();

    public Propiedad(PropiedadId id, ClienteId propietarioId, Direccion direccion,
                     Caracteristicas caracteristicas, Set<PrecioComercial> precios, List<Fotografia> fotografias) {
        // El ID es nulo solo antes del primer almacenamiento; lo asigna el repositorio.
        this.id = id;
        this.propietarioId = Objects.requireNonNull(propietarioId, "propietarioId es obligatorio");
        this.direccion = Objects.requireNonNull(direccion, "direccion es obligatoria");
        this.caracteristicas = Objects.requireNonNull(caracteristicas, "caracteristicas es obligatorio");
        if (precios == null || precios.isEmpty() || precios.size() > 2) throw new IllegalArgumentException("Se requiere uno o dos precios");
        for (PrecioComercial precio : precios) {
            if (this.precios.stream().anyMatch(p -> p.getModalidad() == precio.getModalidad())) throw new IllegalArgumentException("Solo un precio por modalidad");
            establecerPrecio(precio);
        }
        for (Fotografia fotografia : Objects.requireNonNull(fotografias, "fotografias es obligatorio")) {
            Objects.requireNonNull(fotografia, "fotografia es obligatoria");
            if (this.fotografias.stream().anyMatch(f -> f.getId().equals(fotografia.getId()))) throw new IllegalArgumentException("Identificador de fotografía repetido");
            this.fotografias.add(fotografia);
        }
    }

    /** Recupera el estado almacenado sin volver a ejecutar publicación o reserva. */
    public static Propiedad reconstruir(PropiedadId id, ClienteId propietarioId, Direccion direccion,
                                        Caracteristicas caracteristicas, Set<PrecioComercial> precios,
                                        List<Fotografia> fotografias, EstadoDisponibilidad disponibilidad, boolean publicada) {
        Propiedad propiedad = new Propiedad(id, propietarioId, direccion, caracteristicas, precios, fotografias);
        propiedad.disponibilidad = Objects.requireNonNull(disponibilidad);
        propiedad.publicada = publicada;
        return propiedad;
    }

    public void establecerPrecio(PrecioComercial precio) {
        Objects.requireNonNull(precio, "precio es obligatorio");
        precios.removeIf(p -> p.getModalidad() == precio.getModalidad());
        precios.add(precio);
    }

    public void publicar() {
        if (disponibilidad != EstadoDisponibilidad.DISPONIBLE) throw new IllegalStateException("Solo se publica una propiedad disponible");
        publicada = true;
    }

    public void reservar(OperacionId operacionId) {
        Objects.requireNonNull(operacionId, "operacionId es obligatorio");
        if (disponibilidad != EstadoDisponibilidad.DISPONIBLE) throw new IllegalStateException("La propiedad no está disponible");
        disponibilidad = EstadoDisponibilidad.RESERVADA;
        publicada = false;
    }

    public void actualizarDisponibilidad(EstadoDisponibilidad estado) {
        disponibilidad = Objects.requireNonNull(estado, "estado es obligatorio");
        if (estado != EstadoDisponibilidad.DISPONIBLE) publicada = false;
    }

    public PropiedadId getId() {
        return id;
    }

    public ClienteId getPropietarioId() {
        return propietarioId;
    }

    public Direccion getDireccion() {
        return direccion;
    }

    public Caracteristicas getCaracteristicas() {
        return caracteristicas;
    }

    public EstadoDisponibilidad getDisponibilidad() {
        return disponibilidad;
    }

    public boolean isPublicada() {
        return publicada;
    }

    public Set<PrecioComercial> getPrecios() {
        return Collections.unmodifiableSet(precios);
    }

    public List<Fotografia> getFotografias() {
        return Collections.unmodifiableList(fotografias);
    }
}
