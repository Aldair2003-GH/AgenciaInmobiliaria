package org.manosalva.springclaud.msvc.manosalvavasquezaldair.msvccliente.infrastructure.rest.dto;

import java.util.*;
import java.math.BigDecimal;
import java.time.*;
import org.manosalva.springclaud.msvc.manosalvavasquezaldair.msvccliente.domain.model.*;
import org.manosalva.springclaud.msvc.manosalvavasquezaldair.msvccliente.domain.enums.*;
import org.manosalva.springclaud.msvc.manosalvavasquezaldair.msvccliente.domain.ids.*;

public class ClienteRequestDto {
    private String nombreCompleto;
    private DocumentoIdentidadDto documentoIdentidad;
    private DatosContactoDto datosContacto;
    private Set<RolCliente> roles;

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public DocumentoIdentidadDto getDocumentoIdentidad() {
        return documentoIdentidad;
    }

    public void setDocumentoIdentidad(DocumentoIdentidadDto documentoIdentidad) {
        this.documentoIdentidad = documentoIdentidad;
    }

    public DatosContactoDto getDatosContacto() {
        return datosContacto;
    }

    public void setDatosContacto(DatosContactoDto datosContacto) {
        this.datosContacto = datosContacto;
    }

    public Set<RolCliente> getRoles() {
        return roles;
    }

    public void setRoles(Set<RolCliente> roles) {
        this.roles = roles;
    }


}
