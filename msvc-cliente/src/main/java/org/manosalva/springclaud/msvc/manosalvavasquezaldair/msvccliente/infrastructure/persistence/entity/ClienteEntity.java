package org.manosalva.springclaud.msvc.manosalvavasquezaldair.msvccliente.infrastructure.persistence.entity;

import jakarta.persistence.*;
import java.util.*;
import java.math.BigDecimal;
import java.time.*;
import org.manosalva.springclaud.msvc.manosalvavasquezaldair.msvccliente.domain.enums.*;

@Entity
@Table(name = "clientes")
public class ClienteEntity {
    @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Version
    private Long version;

    @Column(name = "nombre_completo", nullable = false)
    private String nombreCompleto;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_documento", nullable = false)
    private TipoDocumento tipoDocumento;

    @Column(name = "numero_documento", nullable = false)
    private String numeroDocumento;

    @Column(name = "telefono", nullable = false)
    private String telefono;

    @Column(name = "correo", nullable = true)
    private String correo;

    @Enumerated(EnumType.STRING)
    @Column(name = "preferencia_modalidad", nullable = true)
    private ModalidadComercial preferenciaModalidad;

    @Enumerated(EnumType.STRING)
    @Column(name = "preferencia_tipo_inmueble", nullable = true)
    private TipoInmueble preferenciaTipoInmueble;

    @Column(name = "preferencia_distrito", nullable = true)
    private String preferenciaDistrito;

    @Column(name = "preferencia_presupuesto_maximo", nullable = true, precision = 19, scale = 4)
    private BigDecimal preferenciaPresupuestoMaximo;

    @Enumerated(EnumType.STRING)
    @Column(name = "preferencia_moneda", nullable = true)
    private Moneda preferenciaMoneda;

    @ElementCollection
    @CollectionTable(name = "cliente_roles", joinColumns = @JoinColumn(name = "cliente_id"))
    @Enumerated(EnumType.STRING)
    @Column(name = "rol", nullable = false)
    private Set<RolCliente> roles = new LinkedHashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public TipoDocumento getTipoDocumento() {
        return tipoDocumento;
    }

    public void setTipoDocumento(TipoDocumento tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    public String getNumeroDocumento() {
        return numeroDocumento;
    }

    public void setNumeroDocumento(String numeroDocumento) {
        this.numeroDocumento = numeroDocumento;
    }

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

    public ModalidadComercial getPreferenciaModalidad() {
        return preferenciaModalidad;
    }

    public void setPreferenciaModalidad(ModalidadComercial preferenciaModalidad) {
        this.preferenciaModalidad = preferenciaModalidad;
    }

    public TipoInmueble getPreferenciaTipoInmueble() {
        return preferenciaTipoInmueble;
    }

    public void setPreferenciaTipoInmueble(TipoInmueble preferenciaTipoInmueble) {
        this.preferenciaTipoInmueble = preferenciaTipoInmueble;
    }

    public String getPreferenciaDistrito() {
        return preferenciaDistrito;
    }

    public void setPreferenciaDistrito(String preferenciaDistrito) {
        this.preferenciaDistrito = preferenciaDistrito;
    }

    public BigDecimal getPreferenciaPresupuestoMaximo() {
        return preferenciaPresupuestoMaximo;
    }

    public void setPreferenciaPresupuestoMaximo(BigDecimal preferenciaPresupuestoMaximo) {
        this.preferenciaPresupuestoMaximo = preferenciaPresupuestoMaximo;
    }

    public Moneda getPreferenciaMoneda() {
        return preferenciaMoneda;
    }

    public void setPreferenciaMoneda(Moneda preferenciaMoneda) {
        this.preferenciaMoneda = preferenciaMoneda;
    }

    public Set<RolCliente> getRoles() {
        return roles;
    }
}
