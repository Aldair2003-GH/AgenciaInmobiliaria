package org.manosalva.springclaud.msvc.manosalvavasquezaldair.msvcpropiedad.infrastructure.persistence.entity;

import jakarta.persistence.*;
import java.util.*;
import java.math.BigDecimal;
import java.time.*;
import org.manosalva.springclaud.msvc.manosalvavasquezaldair.msvcpropiedad.domain.enums.*;

@Entity
@Table(name = "propiedades")
public class PropiedadEntity {
    @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Version
    private Long version;

    @Column(name = "propietario_id", nullable = false)
    private Long propietarioId;

    @Column(name = "departamento", nullable = false)
    private String departamento;

    @Column(name = "provincia", nullable = false)
    private String provincia;

    @Column(name = "distrito", nullable = false)
    private String distrito;

    @Column(name = "detalle", nullable = false)
    private String detalle;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_inmueble", nullable = false)
    private TipoInmueble tipoInmueble;

    @Column(name = "area_m2", nullable = false, precision = 19, scale = 4)
    private BigDecimal areaM2;

    @Column(name = "dormitorios", nullable = true)
    private Integer dormitorios;

    @Column(name = "banos", nullable = true)
    private Integer banos;

    @Enumerated(EnumType.STRING)
    @Column(name = "disponibilidad", nullable = false)
    private EstadoDisponibilidad disponibilidad;

    @Column(name = "publicada", nullable = false)
    private boolean publicada;

    @ElementCollection
    @CollectionTable(name = "propiedad_precios", joinColumns = @JoinColumn(name = "propiedad_id"))
    private List<PrecioComercialEntity> precios = new ArrayList<>();

    @OneToMany(mappedBy = "propiedad", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("orden ASC, fotografiaId ASC")
    private List<FotografiaEntity> fotografias = new ArrayList<>();

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

    public Long getPropietarioId() {
        return propietarioId;
    }

    public void setPropietarioId(Long propietarioId) {
        this.propietarioId = propietarioId;
    }

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    public String getDistrito() {
        return distrito;
    }

    public void setDistrito(String distrito) {
        this.distrito = distrito;
    }

    public String getDetalle() {
        return detalle;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }

    public TipoInmueble getTipoInmueble() {
        return tipoInmueble;
    }

    public void setTipoInmueble(TipoInmueble tipoInmueble) {
        this.tipoInmueble = tipoInmueble;
    }

    public BigDecimal getAreaM2() {
        return areaM2;
    }

    public void setAreaM2(BigDecimal areaM2) {
        this.areaM2 = areaM2;
    }

    public Integer getDormitorios() {
        return dormitorios;
    }

    public void setDormitorios(Integer dormitorios) {
        this.dormitorios = dormitorios;
    }

    public Integer getBanos() {
        return banos;
    }

    public void setBanos(Integer banos) {
        this.banos = banos;
    }

    public EstadoDisponibilidad getDisponibilidad() {
        return disponibilidad;
    }

    public void setDisponibilidad(EstadoDisponibilidad disponibilidad) {
        this.disponibilidad = disponibilidad;
    }

    public boolean getPublicada() {
        return publicada;
    }

    public void setPublicada(boolean publicada) {
        this.publicada = publicada;
    }

    public List<PrecioComercialEntity> getPrecios() {
        return precios;
    }

    public List<FotografiaEntity> getFotografias() {
        return fotografias;
    }
}
