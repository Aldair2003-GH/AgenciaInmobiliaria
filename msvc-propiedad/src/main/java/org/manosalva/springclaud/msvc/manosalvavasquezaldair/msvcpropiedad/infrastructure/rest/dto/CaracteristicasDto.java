package org.manosalva.springclaud.msvc.manosalvavasquezaldair.msvcpropiedad.infrastructure.rest.dto;

import java.util.*;
import java.math.BigDecimal;
import java.time.*;
import org.manosalva.springclaud.msvc.manosalvavasquezaldair.msvcpropiedad.domain.model.*;
import org.manosalva.springclaud.msvc.manosalvavasquezaldair.msvcpropiedad.domain.enums.*;
import org.manosalva.springclaud.msvc.manosalvavasquezaldair.msvcpropiedad.domain.ids.*;

public class CaracteristicasDto {
    private TipoInmueble tipoInmueble;
    private BigDecimal areaM2;
    private Integer dormitorios;
    private Integer banos;

    public TipoInmueble getTipoInmueble() {
        return tipoInmueble;
    }

    public void setTipoInmueble(TipoInmueble tipoInmueble) {
        this.tipoInmueble = tipoInmueble;
    }

    public BigDecimal getAreaM2() {
        return areaM2;
    }

    public void setAreaM2(BigDecimal areaM2) {
        this.areaM2 = areaM2;
    }

    public Integer getDormitorios() {
        return dormitorios;
    }

    public void setDormitorios(Integer dormitorios) {
        this.dormitorios = dormitorios;
    }

    public Integer getBanos() {
        return banos;
    }

    public void setBanos(Integer banos) {
        this.banos = banos;
    }

    public Caracteristicas toDomain() {
        return new Caracteristicas(tipoInmueble, areaM2, dormitorios, banos);
    }

    public static CaracteristicasDto from(Caracteristicas valor) {
        if (valor == null) return null;
        CaracteristicasDto dto = new CaracteristicasDto();
        dto.setTipoInmueble(valor.getTipoInmueble());
        dto.setAreaM2(valor.getAreaM2());
        dto.setDormitorios(valor.getDormitorios());
        dto.setBanos(valor.getBanos());
        return dto;
    }
}
