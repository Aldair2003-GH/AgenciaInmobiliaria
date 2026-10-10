package org.manosalva.springclaud.msvc.manosalvavasquezaldair.msvcpropiedad.infrastructure.rest.dto;

import java.util.*;
import java.math.BigDecimal;
import java.time.*;
import org.manosalva.springclaud.msvc.manosalvavasquezaldair.msvcpropiedad.domain.model.*;
import org.manosalva.springclaud.msvc.manosalvavasquezaldair.msvcpropiedad.domain.enums.*;
import org.manosalva.springclaud.msvc.manosalvavasquezaldair.msvcpropiedad.domain.ids.*;

public class FotografiaDto {
    private Long id;
    private String url;
    private int orden;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getOrden() {
        return orden;
    }

    public void setOrden(int orden) {
        this.orden = orden;
    }

    public Fotografia toDomain() {
        return new Fotografia(new FotografiaId(id), url, orden);
    }

    public static FotografiaDto from(Fotografia valor) {
        if (valor == null) return null;
        FotografiaDto dto = new FotografiaDto();
        dto.setId(valor.getId().getValue());
        dto.setUrl(valor.getUrl());
        dto.setOrden(valor.getOrden());
        return dto;
    }
}
