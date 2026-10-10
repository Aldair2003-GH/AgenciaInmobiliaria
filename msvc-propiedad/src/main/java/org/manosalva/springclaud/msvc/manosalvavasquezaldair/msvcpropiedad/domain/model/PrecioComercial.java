package org.manosalva.springclaud.msvc.manosalvavasquezaldair.msvcpropiedad.domain.model;

import java.util.*;
import java.math.BigDecimal;
import java.time.*;
import org.manosalva.springclaud.msvc.manosalvavasquezaldair.msvcpropiedad.domain.enums.*;
import org.manosalva.springclaud.msvc.manosalvavasquezaldair.msvcpropiedad.domain.ids.*;

public final class PrecioComercial {
    private final ModalidadComercial modalidad;
    private final BigDecimal importe;
    private final Moneda moneda;

    public PrecioComercial(ModalidadComercial modalidad, BigDecimal importe, Moneda moneda) {
        Objects.requireNonNull(modalidad, "modalidad es obligatorio");
        Objects.requireNonNull(moneda, "moneda es obligatorio");
        if (importe == null || importe.signum() <= 0) throw new IllegalArgumentException("importe debe ser positivo");
        this.modalidad = modalidad;
        this.importe = importe;
        this.moneda = moneda;
    }

    public ModalidadComercial getModalidad() {
        return modalidad;
    }

    public BigDecimal getImporte() {
        return importe;
    }

    public Moneda getMoneda() {
        return moneda;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (!(object instanceof PrecioComercial other)) return false;
        return Objects.equals(modalidad, other.modalidad) && Objects.equals(importe, other.importe) && Objects.equals(moneda, other.moneda);
    }

    @Override
    public int hashCode() {
        return Objects.hash(modalidad, importe, moneda);
    }
}
