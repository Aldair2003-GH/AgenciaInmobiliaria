package org.manosalva.springclaud.msvc.manosalvavasquezaldair.msvccliente.infrastructure.rest.dto;

import java.util.*;
import java.math.BigDecimal;
import java.time.*;
import org.manosalva.springclaud.msvc.manosalvavasquezaldair.msvccliente.domain.model.*;
import org.manosalva.springclaud.msvc.manosalvavasquezaldair.msvccliente.domain.enums.*;
import org.manosalva.springclaud.msvc.manosalvavasquezaldair.msvccliente.domain.ids.*;

public class PreferenciaDto {
    private ModalidadComercial modalidad;
    private TipoInmueble tipoInmueble;
    private String distrito;
    private BigDecimal presupuestoMaximo;
    private Moneda moneda;

    public ModalidadComercial getModalidad() {
        return modalidad;
    }

    public void setModalidad(ModalidadComercial modalidad) {
        this.modalidad = modalidad;
    }

    public TipoInmueble getTipoInmueble() {
        return tipoInmueble;
    }

    public void setTipoInmueble(TipoInmueble tipoInmueble) {
        this.tipoInmueble = tipoInmueble;
    }

    public String getDistrito() {
        return distrito;
    }

    public void setDistrito(String distrito) {
        this.distrito = distrito;
    }

    public BigDecimal getPresupuestoMaximo() {
        return presupuestoMaximo;
    }

    public void setPresupuestoMaximo(BigDecimal presupuestoMaximo) {
        this.presupuestoMaximo = presupuestoMaximo;
    }

    public Moneda getMoneda() {
        return moneda;
    }

    public void setMoneda(Moneda moneda) {
        this.moneda = moneda;
    }

    public Preferencia toDomain() {
        return new Preferencia(modalidad, tipoInmueble, distrito, presupuestoMaximo, moneda);
    }

    public static PreferenciaDto from(Preferencia valor) {
        if (valor == null) return null;
        PreferenciaDto dto = new PreferenciaDto();
        dto.setModalidad(valor.getModalidad());
        dto.setTipoInmueble(valor.getTipoInmueble());
        dto.setDistrito(valor.getDistrito());
        dto.setPresupuestoMaximo(valor.getPresupuestoMaximo());
        dto.setMoneda(valor.getMoneda());
        return dto;
    }
}
