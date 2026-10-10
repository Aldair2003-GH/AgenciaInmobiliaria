package org.manosalva.springclaud.msvc.manosalvavasquezaldair.msvccliente.domain.model;

import java.util.*;
import java.math.BigDecimal;
import java.time.*;
import org.manosalva.springclaud.msvc.manosalvavasquezaldair.msvccliente.domain.enums.*;
import org.manosalva.springclaud.msvc.manosalvavasquezaldair.msvccliente.domain.ids.*;

public final class Preferencia {
    private final ModalidadComercial modalidad;
    private final TipoInmueble tipoInmueble;
    private final String distrito;
    private final BigDecimal presupuestoMaximo;
    private final Moneda moneda;

    public Preferencia(ModalidadComercial modalidad, TipoInmueble tipoInmueble, String distrito, BigDecimal presupuestoMaximo, Moneda moneda) {
        Objects.requireNonNull(modalidad, "modalidad es obligatorio");
        Objects.requireNonNull(tipoInmueble, "tipoInmueble es obligatorio");
        Objects.requireNonNull(moneda, "moneda es obligatorio");
        if (distrito == null || distrito.isBlank()) throw new IllegalArgumentException("distrito es obligatorio");
        if (presupuestoMaximo == null || presupuestoMaximo.signum() <= 0) throw new IllegalArgumentException("presupuestoMaximo debe ser positivo");
        this.modalidad = modalidad;
        this.tipoInmueble = tipoInmueble;
        this.distrito = distrito;
        this.presupuestoMaximo = presupuestoMaximo;
        this.moneda = moneda;
    }

    public ModalidadComercial getModalidad() {
        return modalidad;
    }

    public TipoInmueble getTipoInmueble() {
        return tipoInmueble;
    }

    public String getDistrito() {
        return distrito;
    }

    public BigDecimal getPresupuestoMaximo() {
        return presupuestoMaximo;
    }

    public Moneda getMoneda() {
        return moneda;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (!(object instanceof Preferencia other)) return false;
        return Objects.equals(modalidad, other.modalidad) && Objects.equals(tipoInmueble, other.tipoInmueble) && Objects.equals(distrito, other.distrito) && Objects.equals(presupuestoMaximo, other.presupuestoMaximo) && Objects.equals(moneda, other.moneda);
    }

    @Override
    public int hashCode() {
        return Objects.hash(modalidad, tipoInmueble, distrito, presupuestoMaximo, moneda);
    }
}
