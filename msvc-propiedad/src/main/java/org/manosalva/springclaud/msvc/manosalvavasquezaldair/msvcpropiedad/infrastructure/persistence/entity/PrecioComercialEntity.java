package org.manosalva.springclaud.msvc.manosalvavasquezaldair.msvcpropiedad.infrastructure.persistence.entity;

import jakarta.persistence.*;
import java.util.*;
import java.math.BigDecimal;
import java.time.*;
import org.manosalva.springclaud.msvc.manosalvavasquezaldair.msvcpropiedad.domain.enums.*;

@Embeddable
public class PrecioComercialEntity {
    @Enumerated(EnumType.STRING)
    @Column(name = "modalidad", nullable = false)
    private ModalidadComercial modalidad;

    @Column(name = "importe", nullable = false, precision = 19, scale = 4)
    private BigDecimal importe;

    @Enumerated(EnumType.STRING)
    @Column(name = "moneda", nullable = false)
    private Moneda moneda;

    public ModalidadComercial getModalidad() {
        return modalidad;
    }

    public void setModalidad(ModalidadComercial modalidad) {
        this.modalidad = modalidad;
    }

    public BigDecimal getImporte() {
        return importe;
    }

    public void setImporte(BigDecimal importe) {
        this.importe = importe;
    }

    public Moneda getMoneda() {
        return moneda;
    }

    public void setMoneda(Moneda moneda) {
        this.moneda = moneda;
    }
}
