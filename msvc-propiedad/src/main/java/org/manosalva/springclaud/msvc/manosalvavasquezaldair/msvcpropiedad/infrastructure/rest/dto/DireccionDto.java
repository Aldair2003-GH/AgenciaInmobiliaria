package org.manosalva.springclaud.msvc.manosalvavasquezaldair.msvcpropiedad.infrastructure.rest.dto;

import java.util.*;
import java.math.BigDecimal;
import java.time.*;
import org.manosalva.springclaud.msvc.manosalvavasquezaldair.msvcpropiedad.domain.model.*;
import org.manosalva.springclaud.msvc.manosalvavasquezaldair.msvcpropiedad.domain.enums.*;
import org.manosalva.springclaud.msvc.manosalvavasquezaldair.msvcpropiedad.domain.ids.*;

public class DireccionDto {
    private String departamento;
    private String provincia;
    private String distrito;
    private String detalle;

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    public String getDistrito() {
        return distrito;
    }

    public void setDistrito(String distrito) {
        this.distrito = distrito;
    }

    public String getDetalle() {
        return detalle;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }

    public Direccion toDomain() {
        return new Direccion(departamento, provincia, distrito, detalle);
    }

    public static DireccionDto from(Direccion valor) {
        if (valor == null) return null;
        DireccionDto dto = new DireccionDto();
        dto.setDepartamento(valor.getDepartamento());
        dto.setProvincia(valor.getProvincia());
        dto.setDistrito(valor.getDistrito());
        dto.setDetalle(valor.getDetalle());
        return dto;
    }
}
