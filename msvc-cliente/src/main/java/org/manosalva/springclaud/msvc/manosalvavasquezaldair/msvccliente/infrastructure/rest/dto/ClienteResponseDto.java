package org.manosalva.springclaud.msvc.manosalvavasquezaldair.msvccliente.infrastructure.rest.dto;

import java.util.*;
import java.math.BigDecimal;
import java.time.*;
import org.manosalva.springclaud.msvc.manosalvavasquezaldair.msvccliente.domain.model.*;
import org.manosalva.springclaud.msvc.manosalvavasquezaldair.msvccliente.domain.enums.*;
import org.manosalva.springclaud.msvc.manosalvavasquezaldair.msvccliente.domain.ids.*;

public class ClienteResponseDto {
    private Long id;
    private String nombreCompleto;
    private DocumentoIdentidadDto documentoIdentidad;
    private DatosContactoDto datosContacto;
    private PreferenciaDto preferencia;
    private Set<RolCliente> roles;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public PreferenciaDto getPreferencia() {
        return preferencia;
    }

    public void setPreferencia(PreferenciaDto preferencia) {
        this.preferencia = preferencia;
    }

    public Set<RolCliente> getRoles() {
        return roles;
    }

    public void setRoles(Set<RolCliente> roles) {
        this.roles = roles;
    }

    public static ClienteResponseDto from(Cliente valor) {
        ClienteResponseDto dto = new ClienteResponseDto();
        dto.setId(valor.getId().getValue());
        dto.setNombreCompleto(valor.getNombreCompleto());
        dto.setDocumentoIdentidad(DocumentoIdentidadDto.from(valor.getDocumentoIdentidad()));
        dto.setDatosContacto(DatosContactoDto.from(valor.getDatosContacto()));
        dto.setPreferencia(PreferenciaDto.from(valor.getPreferencia()));
        dto.setRoles(Set.copyOf(valor.getRoles()));
        return dto;
    }
}
