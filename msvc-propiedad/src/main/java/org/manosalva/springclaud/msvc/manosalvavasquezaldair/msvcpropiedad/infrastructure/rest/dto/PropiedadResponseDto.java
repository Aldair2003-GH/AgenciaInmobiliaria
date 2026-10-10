package org.manosalva.springclaud.msvc.manosalvavasquezaldair.msvcpropiedad.infrastructure.rest.dto;

import java.util.*;
import java.math.BigDecimal;
import java.time.*;
import org.manosalva.springclaud.msvc.manosalvavasquezaldair.msvcpropiedad.domain.model.*;
import org.manosalva.springclaud.msvc.manosalvavasquezaldair.msvcpropiedad.domain.enums.*;
import org.manosalva.springclaud.msvc.manosalvavasquezaldair.msvcpropiedad.domain.ids.*;

public class PropiedadResponseDto {
    private Long id;
    private Long propietarioId;
    private DireccionDto direccion;
    private CaracteristicasDto caracteristicas;
    private EstadoDisponibilidad disponibilidad;
    private boolean publicada;
    private List<PrecioComercialDto> precios;
    private List<FotografiaDto> fotografias;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public EstadoDisponibilidad getDisponibilidad() {
        return disponibilidad;
    }

    public void setDisponibilidad(EstadoDisponibilidad disponibilidad) {
        this.disponibilidad = disponibilidad;
    }

    public boolean getPublicada() {
        return publicada;
    }

    public void setPublicada(boolean publicada) {
        this.publicada = publicada;
    }

    public List<PrecioComercialDto> getPrecios() {
        return precios;
    }

    public void setPrecios(List<PrecioComercialDto> precios) {
        this.precios = precios;
    }

    public List<FotografiaDto> getFotografias() {
        return fotografias;
    }

    public void setFotografias(List<FotografiaDto> fotografias) {
        this.fotografias = fotografias;
    }

    public static PropiedadResponseDto from(Propiedad valor) {
        PropiedadResponseDto dto = new PropiedadResponseDto();
        dto.setId(valor.getId().getValue());
        dto.setPropietarioId(valor.getPropietarioId().getValue());
        dto.setDireccion(DireccionDto.from(valor.getDireccion()));
        dto.setCaracteristicas(CaracteristicasDto.from(valor.getCaracteristicas()));
        dto.setDisponibilidad(valor.getDisponibilidad());
        dto.setPublicada(valor.isPublicada());
        dto.setPrecios(valor.getPrecios().stream().map(PrecioComercialDto::from).toList());
        dto.setFotografias(valor.getFotografias().stream().map(FotografiaDto::from).toList());
        return dto;
    }
}
