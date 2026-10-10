package org.manosalva.springclaud.msvc.manosalvavasquezaldair.msvcpropiedad.infrastructure.rest.dto;

import java.util.*;
import java.math.BigDecimal;
import java.time.*;
import org.manosalva.springclaud.msvc.manosalvavasquezaldair.msvcpropiedad.domain.model.*;
import org.manosalva.springclaud.msvc.manosalvavasquezaldair.msvcpropiedad.domain.enums.*;
import org.manosalva.springclaud.msvc.manosalvavasquezaldair.msvcpropiedad.domain.ids.*;

public class PropiedadRequestDto {
    private Long propietarioId;
    private DireccionDto direccion;
    private CaracteristicasDto caracteristicas;
    private Set<PrecioComercialDto> precios;
    private List<FotografiaDto> fotografias;

    public Long getPropietarioId() {
        return propietarioId;
    }

    public void setPropietarioId(Long propietarioId) {
        this.propietarioId = propietarioId;
    }

    public DireccionDto getDireccion() {
        return direccion;
    }

    public void setDireccion(DireccionDto direccion) {
        this.direccion = direccion;
    }

    public CaracteristicasDto getCaracteristicas() {
        return caracteristicas;
    }

    public void setCaracteristicas(CaracteristicasDto caracteristicas) {
        this.caracteristicas = caracteristicas;
    }

    public Set<PrecioComercialDto> getPrecios() {
        return precios;
    }

    public void setPrecios(Set<PrecioComercialDto> precios) {
        this.precios = precios;
    }

    public List<FotografiaDto> getFotografias() {
        return fotografias;
    }

    public void setFotografias(List<FotografiaDto> fotografias) {
        this.fotografias = fotografias;
    }


}
