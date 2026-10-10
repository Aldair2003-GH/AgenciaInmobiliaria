package org.manosalva.springclaud.msvc.manosalvavasquezaldair.msvcoperacioninmobiliaria.infrastructure.rest.dto;

import java.util.*;
import java.math.BigDecimal;
import java.time.*;
import org.manosalva.springclaud.msvc.manosalvavasquezaldair.msvcoperacioninmobiliaria.domain.model.*;
import org.manosalva.springclaud.msvc.manosalvavasquezaldair.msvcoperacioninmobiliaria.domain.enums.*;
import org.manosalva.springclaud.msvc.manosalvavasquezaldair.msvcoperacioninmobiliaria.domain.ids.*;

public class CondicionesContratoDto {
    private BigDecimal importeAcordado;
    private Moneda moneda;
    private LocalDate fechaAcuerdo;
    private LocalDate inicioAlquiler;
    private LocalDate finAlquiler;
    private String formalizacion;

    public BigDecimal getImporteAcordado() {
        return importeAcordado;
    }

    public void setImporteAcordado(BigDecimal importeAcordado) {
        this.importeAcordado = importeAcordado;
    }

    public Moneda getMoneda() {
        return moneda;
    }

    public void setMoneda(Moneda moneda) {
        this.moneda = moneda;
    }

    public LocalDate getFechaAcuerdo() {
        return fechaAcuerdo;
    }

    public void setFechaAcuerdo(LocalDate fechaAcuerdo) {
        this.fechaAcuerdo = fechaAcuerdo;
    }

    public LocalDate getInicioAlquiler() {
        return inicioAlquiler;
    }

    public void setInicioAlquiler(LocalDate inicioAlquiler) {
        this.inicioAlquiler = inicioAlquiler;
    }

    public LocalDate getFinAlquiler() {
        return finAlquiler;
    }

    public void setFinAlquiler(LocalDate finAlquiler) {
        this.finAlquiler = finAlquiler;
    }

    public String getFormalizacion() {
        return formalizacion;
    }

    public void setFormalizacion(String formalizacion) {
        this.formalizacion = formalizacion;
    }

    public CondicionesContrato toDomain() {
        return new CondicionesContrato(importeAcordado, moneda, fechaAcuerdo, inicioAlquiler, finAlquiler, formalizacion);
    }

    public static CondicionesContratoDto from(CondicionesContrato valor) {
        if (valor == null) return null;
        CondicionesContratoDto dto = new CondicionesContratoDto();
        dto.setImporteAcordado(valor.getImporteAcordado());
        dto.setMoneda(valor.getMoneda());
        dto.setFechaAcuerdo(valor.getFechaAcuerdo());
        dto.setInicioAlquiler(valor.getInicioAlquiler());
        dto.setFinAlquiler(valor.getFinAlquiler());
        dto.setFormalizacion(valor.getFormalizacion());
        return dto;
    }
}
