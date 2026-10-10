package org.manosalva.springclaud.msvc.manosalvavasquezaldair.msvccliente.domain.model;

import java.util.*;
import java.math.BigDecimal;
import java.time.*;
import org.manosalva.springclaud.msvc.manosalvavasquezaldair.msvccliente.domain.enums.*;
import org.manosalva.springclaud.msvc.manosalvavasquezaldair.msvccliente.domain.ids.*;

public final class DatosContacto {
    private final String telefono;
    private final String correo;

    public DatosContacto(String telefono, String correo) {
        if (telefono == null || telefono.isBlank()) throw new IllegalArgumentException("telefono es obligatorio");
        if (correo != null && correo.isBlank()) throw new IllegalArgumentException("correo no puede estar vacío");
        this.telefono = telefono;
        this.correo = correo;
    }

    public String getTelefono() {
        return telefono;
    }

    public String getCorreo() {
        return correo;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (!(object instanceof DatosContacto other)) return false;
        return Objects.equals(telefono, other.telefono) && Objects.equals(correo, other.correo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(telefono, correo);
    }
}
