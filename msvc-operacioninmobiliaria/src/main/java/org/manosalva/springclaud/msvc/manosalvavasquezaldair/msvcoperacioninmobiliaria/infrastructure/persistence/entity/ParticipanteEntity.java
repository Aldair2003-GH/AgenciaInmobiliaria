package org.manosalva.springclaud.msvc.manosalvavasquezaldair.msvcoperacioninmobiliaria.infrastructure.persistence.entity;

import jakarta.persistence.*;
import java.util.*;
import java.math.BigDecimal;
import java.time.*;
import org.manosalva.springclaud.msvc.manosalvavasquezaldair.msvcoperacioninmobiliaria.domain.enums.*;

@Embeddable
public class ParticipanteEntity {
    @Column(name = "cliente_id", nullable = false)
    private Long clienteId;

    @Enumerated(EnumType.STRING)
    @Column(name = "rol", nullable = false)
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
}
