package org.manosalva.springclaud.msvc.manosalvavasquezaldair.msvcoperacioninmobiliaria.infrastructure.rest.dto;

import java.util.*;
import java.math.BigDecimal;
import java.time.*;
import org.manosalva.springclaud.msvc.manosalvavasquezaldair.msvcoperacioninmobiliaria.domain.model.*;
import org.manosalva.springclaud.msvc.manosalvavasquezaldair.msvcoperacioninmobiliaria.domain.enums.*;
import org.manosalva.springclaud.msvc.manosalvavasquezaldair.msvcoperacioninmobiliaria.domain.ids.*;

public class IniciarOperacionRequestDto {
    private Long propiedadId;
    private ModalidadComercial modalidad;
    private Set<ParticipanteDto> participantes;

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

    public Set<ParticipanteDto> getParticipantes() {
        return participantes;
    }

    public void setParticipantes(Set<ParticipanteDto> participantes) {
        this.participantes = participantes;
    }


}
