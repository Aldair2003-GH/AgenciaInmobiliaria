package org.manosalva.springclaud.msvc.manosalvavasquezaldair.msvcoperacioninmobiliaria.domain.model;

import java.util.*;
import java.math.BigDecimal;
import java.time.*;
import org.manosalva.springclaud.msvc.manosalvavasquezaldair.msvcoperacioninmobiliaria.domain.enums.*;
import org.manosalva.springclaud.msvc.manosalvavasquezaldair.msvcoperacioninmobiliaria.domain.ids.*;

public final class CondicionesContrato {
    private final BigDecimal importeAcordado;
    private final Moneda moneda;
    private final LocalDate fechaAcuerdo;
    private final LocalDate inicioAlquiler;
    private final LocalDate finAlquiler;
    private final String formalizacion;

    public CondicionesContrato(BigDecimal importeAcordado, Moneda moneda, LocalDate fechaAcuerdo, LocalDate inicioAlquiler, LocalDate finAlquiler, String formalizacion) {
        if (importeAcordado == null || importeAcordado.signum() <= 0) throw new IllegalArgumentException("importeAcordado debe ser positivo");
        Objects.requireNonNull(moneda, "moneda es obligatorio");
        Objects.requireNonNull(fechaAcuerdo, "fechaAcuerdo es obligatorio");
        if (formalizacion == null || formalizacion.isBlank()) throw new IllegalArgumentException("formalizacion es obligatorio");
        if (finAlquiler != null && (inicioAlquiler == null || !finAlquiler.isAfter(inicioAlquiler))) throw new IllegalArgumentException("El fin del alquiler debe ser posterior al inicio");
        this.importeAcordado = importeAcordado;
        this.moneda = moneda;
        this.fechaAcuerdo = fechaAcuerdo;
        this.inicioAlquiler = inicioAlquiler;
        this.finAlquiler = finAlquiler;
        this.formalizacion = formalizacion;
    }

    public BigDecimal getImporteAcordado() {
        return importeAcordado;
    }

    public Moneda getMoneda() {
        return moneda;
    }

    public LocalDate getFechaAcuerdo() {
        return fechaAcuerdo;
    }

    public LocalDate getInicioAlquiler() {
        return inicioAlquiler;
    }

    public LocalDate getFinAlquiler() {
        return finAlquiler;
    }

    public String getFormalizacion() {
        return formalizacion;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (!(object instanceof CondicionesContrato other)) return false;
        return Objects.equals(importeAcordado, other.importeAcordado) && Objects.equals(moneda, other.moneda) && Objects.equals(fechaAcuerdo, other.fechaAcuerdo) && Objects.equals(inicioAlquiler, other.inicioAlquiler) && Objects.equals(finAlquiler, other.finAlquiler) && Objects.equals(formalizacion, other.formalizacion);
    }

    @Override
    public int hashCode() {
        return Objects.hash(importeAcordado, moneda, fechaAcuerdo, inicioAlquiler, finAlquiler, formalizacion);
    }
}
