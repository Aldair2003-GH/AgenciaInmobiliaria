package org.manosalva.springclaud.msvc.manosalvavasquezaldair.msvcpropiedad.domain.model;

import java.util.*;
import java.math.BigDecimal;
import java.time.*;
import org.manosalva.springclaud.msvc.manosalvavasquezaldair.msvcpropiedad.domain.enums.*;
import org.manosalva.springclaud.msvc.manosalvavasquezaldair.msvcpropiedad.domain.ids.*;

public final class Direccion {
    private final String departamento;
    private final String provincia;
    private final String distrito;
    private final String detalle;

    public Direccion(String departamento, String provincia, String distrito, String detalle) {
        if (departamento == null || departamento.isBlank()) throw new IllegalArgumentException("departamento es obligatorio");
        if (provincia == null || provincia.isBlank()) throw new IllegalArgumentException("provincia es obligatorio");
        if (distrito == null || distrito.isBlank()) throw new IllegalArgumentException("distrito es obligatorio");
        if (detalle == null || detalle.isBlank()) throw new IllegalArgumentException("detalle es obligatorio");
        this.departamento = departamento;
        this.provincia = provincia;
        this.distrito = distrito;
        this.detalle = detalle;
    }

    public String getDepartamento() {
        return departamento;
    }

    public String getProvincia() {
        return provincia;
    }

    public String getDistrito() {
        return distrito;
    }

    public String getDetalle() {
        return detalle;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (!(object instanceof Direccion other)) return false;
        return Objects.equals(departamento, other.departamento) && Objects.equals(provincia, other.provincia) && Objects.equals(distrito, other.distrito) && Objects.equals(detalle, other.detalle);
    }

    @Override
    public int hashCode() {
        return Objects.hash(departamento, provincia, distrito, detalle);
    }
}
