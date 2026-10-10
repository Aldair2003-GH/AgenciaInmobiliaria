package org.manosalva.springclaud.msvc.manosalvavasquezaldair.msvcoperacioninmobiliaria.infrastructure.rest.dto;

import java.util.*;
import java.math.BigDecimal;
import java.time.*;
import org.manosalva.springclaud.msvc.manosalvavasquezaldair.msvcoperacioninmobiliaria.domain.model.*;
import org.manosalva.springclaud.msvc.manosalvavasquezaldair.msvcoperacioninmobiliaria.domain.enums.*;
import org.manosalva.springclaud.msvc.manosalvavasquezaldair.msvcoperacioninmobiliaria.domain.ids.*;

public class ParticipanteDto {
    private Long clienteId;
    private RolParticipacion rol;

    public Long getClienteId() {
        return clienteId;
    }

    public void setClienteId(Long clienteId) {
        this.clienteId = clienteId;
    }

    public RolParticipacion getRol() {
        return rol;
    }

    public void setRol(RolParticipacion rol) {
        this.rol = rol;
    }

    public Participante toDomain() {
        return new Participante(new ClienteId(clienteId), rol);
    }

    public static ParticipanteDto from(Participante valor) {
        if (valor == null) return null;
        ParticipanteDto dto = new ParticipanteDto();
        dto.setClienteId(valor.getClienteId().getValue());
        dto.setRol(valor.getRol());
        return dto;
    }
}
