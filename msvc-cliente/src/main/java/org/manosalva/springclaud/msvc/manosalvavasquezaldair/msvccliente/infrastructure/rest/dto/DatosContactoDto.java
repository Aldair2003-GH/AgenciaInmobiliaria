package org.manosalva.springclaud.msvc.manosalvavasquezaldair.msvccliente.infrastructure.rest.dto;

import java.util.*;
import java.math.BigDecimal;
import java.time.*;
import org.manosalva.springclaud.msvc.manosalvavasquezaldair.msvccliente.domain.model.*;
import org.manosalva.springclaud.msvc.manosalvavasquezaldair.msvccliente.domain.enums.*;
import org.manosalva.springclaud.msvc.manosalvavasquezaldair.msvccliente.domain.ids.*;

public class DatosContactoDto {
    private String telefono;
    private String correo;

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public DatosContacto toDomain() {
        return new DatosContacto(telefono, correo);
    }

    public static DatosContactoDto from(DatosContacto valor) {
        if (valor == null) return null;
        DatosContactoDto dto = new DatosContactoDto();
        dto.setTelefono(valor.getTelefono());
        dto.setCorreo(valor.getCorreo());
        return dto;
    }
}
