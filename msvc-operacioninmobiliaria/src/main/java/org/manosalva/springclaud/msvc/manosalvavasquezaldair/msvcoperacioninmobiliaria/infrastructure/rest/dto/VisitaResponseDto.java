package org.manosalva.springclaud.msvc.manosalvavasquezaldair.msvcoperacioninmobiliaria.infrastructure.rest.dto;

import java.util.*;
import java.math.BigDecimal;
import java.time.*;
import org.manosalva.springclaud.msvc.manosalvavasquezaldair.msvcoperacioninmobiliaria.domain.model.*;
import org.manosalva.springclaud.msvc.manosalvavasquezaldair.msvcoperacioninmobiliaria.domain.enums.*;
import org.manosalva.springclaud.msvc.manosalvavasquezaldair.msvcoperacioninmobiliaria.domain.ids.*;

public class VisitaResponseDto {
    private Long id;
    private Long clienteId;
    private LocalDateTime fecha;
    private EstadoVisita estado;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getClienteId() {
        return clienteId;
    }

    public void setClienteId(Long clienteId) {
        this.clienteId = clienteId;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public EstadoVisita getEstado() {
        return estado;
    }

    public void setEstado(EstadoVisita estado) {
        this.estado = estado;
    }

    public static VisitaResponseDto from(Visita valor) {
        VisitaResponseDto dto = new VisitaResponseDto();
        dto.setId(valor.getId().getValue());
        dto.setClienteId(valor.getClienteId().getValue());
        dto.setFecha(valor.getFecha());
        dto.setEstado(valor.getEstado());
        return dto;
    }
}
