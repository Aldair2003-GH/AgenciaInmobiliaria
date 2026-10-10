package org.manosalva.springclaud.msvc.manosalvavasquezaldair.msvcoperacioninmobiliaria.infrastructure.persistence.entity;

import jakarta.persistence.*;
import java.util.*;
import java.math.BigDecimal;
import java.time.*;
import org.manosalva.springclaud.msvc.manosalvavasquezaldair.msvcoperacioninmobiliaria.domain.enums.*;

@Entity
@Table(name = "operaciones_inmobiliarias")
public class OperacionEntity {
    @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Version
    private Long version;

    @Column(name = "propiedad_id", nullable = false)
    private Long propiedadId;

    @Enumerated(EnumType.STRING)
    @Column(name = "modalidad", nullable = false)
    private ModalidadComercial modalidad;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado", nullable = false)
    private EstadoOperacion estado;

    @Column(name = "fecha_inicio", nullable = false)
    private LocalDateTime fechaInicio;

    @Column(name = "importe_acordado", nullable = true, precision = 19, scale = 4)
    private BigDecimal importeAcordado;

    @Enumerated(EnumType.STRING)
    @Column(name = "moneda_acuerdo", nullable = true)
    private Moneda monedaAcuerdo;

    @Column(name = "fecha_acuerdo", nullable = true)
    private LocalDate fechaAcuerdo;

    @Column(name = "inicio_alquiler", nullable = true)
    private LocalDate inicioAlquiler;

    @Column(name = "fin_alquiler", nullable = true)
    private LocalDate finAlquiler;

    @Column(name = "formalizacion", nullable = true)
    private String formalizacion;

    @ElementCollection
    @CollectionTable(name = "operacion_participantes", joinColumns = @JoinColumn(name = "operacion_id"))
    private List<ParticipanteEntity> participantes = new ArrayList<>();

    @OneToMany(mappedBy = "operacion", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("ofertaId ASC")
    private List<OfertaEntity> ofertas = new ArrayList<>();

    @OneToMany(mappedBy = "operacion", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("visitaId ASC")
    private List<VisitaEntity> visitas = new ArrayList<>();

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

    public Long getPropiedadId() {
        return propiedadId;
    }

    public void setPropiedadId(Long propiedadId) {
        this.propiedadId = propiedadId;
    }

    public ModalidadComercial getModalidad() {
        return modalidad;
    }

    public void setModalidad(ModalidadComercial modalidad) {
        this.modalidad = modalidad;
    }

    public EstadoOperacion getEstado() {
        return estado;
    }

    public void setEstado(EstadoOperacion estado) {
        this.estado = estado;
    }

    public LocalDateTime getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDateTime fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public BigDecimal getImporteAcordado() {
        return importeAcordado;
    }

    public void setImporteAcordado(BigDecimal importeAcordado) {
        this.importeAcordado = importeAcordado;
    }

    public Moneda getMonedaAcuerdo() {
        return monedaAcuerdo;
    }

    public void setMonedaAcuerdo(Moneda monedaAcuerdo) {
        this.monedaAcuerdo = monedaAcuerdo;
    }

    public LocalDate getFechaAcuerdo() {
        return fechaAcuerdo;
    }

    public void setFechaAcuerdo(LocalDate fechaAcuerdo) {
        this.fechaAcuerdo = fechaAcuerdo;
    }

    public LocalDate getInicioAlquiler() {
        return inicioAlquiler;
    }

    public void setInicioAlquiler(LocalDate inicioAlquiler) {
        this.inicioAlquiler = inicioAlquiler;
    }

    public LocalDate getFinAlquiler() {
        return finAlquiler;
    }

    public void setFinAlquiler(LocalDate finAlquiler) {
        this.finAlquiler = finAlquiler;
    }

    public String getFormalizacion() {
        return formalizacion;
    }

    public void setFormalizacion(String formalizacion) {
        this.formalizacion = formalizacion;
    }

    public List<ParticipanteEntity> getParticipantes() {
        return participantes;
    }

    public List<OfertaEntity> getOfertas() {
        return ofertas;
    }

    public List<VisitaEntity> getVisitas() {
        return visitas;
    }
}
