package org.manosalva.springclaud.msvc.manosalvavasquezaldair.msvcoperacioninmobiliaria.infrastructure.rest.dto;

import java.util.*;
import java.math.BigDecimal;
import java.time.*;
import org.manosalva.springclaud.msvc.manosalvavasquezaldair.msvcoperacioninmobiliaria.domain.model.*;
import org.manosalva.springclaud.msvc.manosalvavasquezaldair.msvcoperacioninmobiliaria.domain.enums.*;
import org.manosalva.springclaud.msvc.manosalvavasquezaldair.msvcoperacioninmobiliaria.domain.ids.*;

public class OperacionResponseDto {
    private Long id;
    private Long propiedadId;
    private ModalidadComercial modalidad;
    private EstadoOperacion estado;
    private LocalDateTime fechaInicio;
    private List<ParticipanteDto> participantes;
    private List<OfertaResponseDto> ofertas;
    private List<VisitaResponseDto> visitas;
    private CondicionesContratoDto condicionesContrato;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPropiedadId() {
        return propiedadId;
    }

    public void setPropiedadId(Long propiedadId) {
        this.propiedadId = propiedadId;
    }

    public ModalidadComercial getModalidad() {
        return modalidad;
    }

    public void setModalidad(ModalidadComercial modalidad) {
        this.modalidad = modalidad;
    }

    public EstadoOperacion getEstado() {
        return estado;
    }

    public void setEstado(EstadoOperacion estado) {
        this.estado = estado;
    }

    public LocalDateTime getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDateTime fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public List<ParticipanteDto> getParticipantes() {
        return participantes;
    }

    public void setParticipantes(List<ParticipanteDto> participantes) {
        this.participantes = participantes;
    }

    public List<OfertaResponseDto> getOfertas() {
        return ofertas;
    }

    public void setOfertas(List<OfertaResponseDto> ofertas) {
        this.ofertas = ofertas;
    }

    public List<VisitaResponseDto> getVisitas() {
        return visitas;
    }

    public void setVisitas(List<VisitaResponseDto> visitas) {
        this.visitas = visitas;
    }

    public CondicionesContratoDto getCondicionesContrato() {
        return condicionesContrato;
    }

    public void setCondicionesContrato(CondicionesContratoDto condicionesContrato) {
        this.condicionesContrato = condicionesContrato;
    }

    public static OperacionResponseDto from(OperacionInmobiliaria valor) {
        OperacionResponseDto dto = new OperacionResponseDto();
        dto.setId(valor.getId().getValue());
        dto.setPropiedadId(valor.getPropiedadId().getValue());
        dto.setModalidad(valor.getModalidad());
        dto.setEstado(valor.getEstado());
        dto.setFechaInicio(valor.getFechaInicio());
        dto.setParticipantes(valor.getParticipantes().stream().map(ParticipanteDto::from).toList());
        dto.setOfertas(valor.getOfertas().stream().map(OfertaResponseDto::from).toList());
        dto.setVisitas(valor.getVisitas().stream().map(VisitaResponseDto::from).toList());
        dto.setCondicionesContrato(CondicionesContratoDto.from(valor.getCondicionesContrato()));
        return dto;
    }
}
