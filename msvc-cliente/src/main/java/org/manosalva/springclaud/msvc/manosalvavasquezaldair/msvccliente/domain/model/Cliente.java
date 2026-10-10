package org.manosalva.springclaud.msvc.manosalvavasquezaldair.msvccliente.domain.model;

import java.util.*;
import java.math.BigDecimal;
import java.time.*;
import org.manosalva.springclaud.msvc.manosalvavasquezaldair.msvccliente.domain.enums.*;
import org.manosalva.springclaud.msvc.manosalvavasquezaldair.msvccliente.domain.ids.*;

public class Cliente {
    private final ClienteId id;
    private final String nombreCompleto;
    private final DocumentoIdentidad documentoIdentidad;
    private DatosContacto datosContacto;
    private Preferencia preferencia;
    private final Set<RolCliente> roles = new LinkedHashSet<>();

    public Cliente(ClienteId id, String nombreCompleto, DocumentoIdentidad documentoIdentidad,
                   DatosContacto datosContacto, Set<RolCliente> roles) {
        // El ID es nulo solo antes del primer almacenamiento; lo asigna el repositorio.
        this.id = id;
        if (nombreCompleto == null || nombreCompleto.isBlank()) throw new IllegalArgumentException("nombreCompleto es obligatorio");
        this.nombreCompleto = nombreCompleto;
        this.documentoIdentidad = Objects.requireNonNull(documentoIdentidad, "documentoIdentidad es obligatorio");
        actualizarContacto(datosContacto);
        Objects.requireNonNull(roles, "roles es obligatorio").forEach(this::agregarRol);
    }

    public void actualizarContacto(DatosContacto datos) {
        this.datosContacto = Objects.requireNonNull(datos, "datosContacto es obligatorio");
    }

    public void definirPreferencia(Preferencia preferencia) {
        this.preferencia = preferencia;
    }

    public void agregarRol(RolCliente rol) {
        roles.add(Objects.requireNonNull(rol, "rol es obligatorio"));
    }

    public ClienteId getId() {
        return id;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public DocumentoIdentidad getDocumentoIdentidad() {
        return documentoIdentidad;
    }

    public DatosContacto getDatosContacto() {
        return datosContacto;
    }

    public Preferencia getPreferencia() {
        return preferencia;
    }

    public Set<RolCliente> getRoles() {
        return Collections.unmodifiableSet(roles);
    }
}
