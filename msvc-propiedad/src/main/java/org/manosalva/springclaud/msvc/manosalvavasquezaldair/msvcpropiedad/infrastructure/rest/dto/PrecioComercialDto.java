package org.manosalva.springclaud.msvc.manosalvavasquezaldair.msvcpropiedad.infrastructure.rest.dto;

import java.util.*;
import java.math.BigDecimal;
import java.time.*;
import org.manosalva.springclaud.msvc.manosalvavasquezaldair.msvcpropiedad.domain.model.*;
import org.manosalva.springclaud.msvc.manosalvavasquezaldair.msvcpropiedad.domain.enums.*;
import org.manosalva.springclaud.msvc.manosalvavasquezaldair.msvcpropiedad.domain.ids.*;

public class PrecioComercialDto {
    private ModalidadComercial modalidad;
    private BigDecimal importe;
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

    public PrecioComercial toDomain() {
        return new PrecioComercial(modalidad, importe, moneda);
    }

    public static PrecioComercialDto from(PrecioComercial valor) {
        if (valor == null) return null;
        PrecioComercialDto dto = new PrecioComercialDto();
        dto.setModalidad(valor.getModalidad());
        dto.setImporte(valor.getImporte());
        dto.setMoneda(valor.getMoneda());
        return dto;
    }
}
