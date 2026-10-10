package org.manosalva.springclaud.msvc.manosalvavasquezaldair.msvcoperacioninmobiliaria.domain.model;

import java.util.*;
import java.math.BigDecimal;
import java.time.*;
import org.manosalva.springclaud.msvc.manosalvavasquezaldair.msvcoperacioninmobiliaria.domain.enums.*;
import org.manosalva.springclaud.msvc.manosalvavasquezaldair.msvcoperacioninmobiliaria.domain.ids.*;

public final class Participante {
    private final ClienteId clienteId;
    private final RolParticipacion rol;

    public Participante(ClienteId clienteId, RolParticipacion rol) {
        Objects.requireNonNull(clienteId, "clienteId es obligatorio");
        Objects.requireNonNull(rol, "rol es obligatorio");
        this.clienteId = clienteId;
        this.rol = rol;
    }

    public ClienteId getClienteId() {
        return clienteId;
    }

    public RolParticipacion getRol() {
        return rol;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (!(object instanceof Participante other)) return false;
        return Objects.equals(clienteId, other.clienteId) && Objects.equals(rol, other.rol);
    }

    @Override
    public int hashCode() {
        return Objects.hash(clienteId, rol);
    }
}
