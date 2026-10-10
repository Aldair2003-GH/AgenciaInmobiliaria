package org.manosalva.springclaud.msvc.manosalvavasquezaldair.msvcoperacioninmobiliaria.domain.model;

import java.util.*;
import java.math.BigDecimal;
import java.time.*;
import org.manosalva.springclaud.msvc.manosalvavasquezaldair.msvcoperacioninmobiliaria.domain.enums.*;
import org.manosalva.springclaud.msvc.manosalvavasquezaldair.msvcoperacioninmobiliaria.domain.ids.*;

public class Oferta {
    private final OfertaId id;
    private final ClienteId clienteId;
    private final BigDecimal importe;
    private final Moneda moneda;
    private final LocalDateTime fecha;
    private EstadoOferta estado = EstadoOferta.PENDIENTE;
    public Oferta(OfertaId id, ClienteId clienteId, BigDecimal importe, Moneda moneda, LocalDateTime fecha) {
        this.id = Objects.requireNonNull(id, "id es obligatorio");
        this.clienteId = Objects.requireNonNull(clienteId, "clienteId es obligatorio");
        if (importe == null || importe.signum() <= 0) throw new IllegalArgumentException("importe debe ser positivo");
        this.importe = importe;
        this.moneda = Objects.requireNonNull(moneda, "moneda es obligatoria");
        this.fecha = Objects.requireNonNull(fecha, "fecha es obligatoria");
    }

    public static Oferta reconstruir(OfertaId id, ClienteId clienteId, BigDecimal importe, Moneda moneda, LocalDateTime fecha, EstadoOferta estado) {
        Oferta valor = new Oferta(id, clienteId, importe, moneda, fecha);
        valor.estado = Objects.requireNonNull(estado);
        return valor;
    }

    public void aceptar() {
        if (estado != EstadoOferta.PENDIENTE) throw new IllegalStateException("La oferta ya fue resuelta");
        estado = EstadoOferta.ACEPTADA;
    }

    public void rechazar() {
        if (estado != EstadoOferta.PENDIENTE) throw new IllegalStateException("La oferta ya fue resuelta");
        estado = EstadoOferta.RECHAZADA;
    }

    public OfertaId getId() {
        return id;
    }

    public ClienteId getClienteId() {
        return clienteId;
    }

    public BigDecimal getImporte() {
        return importe;
    }

    public Moneda getMoneda() {
        return moneda;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public EstadoOferta getEstado() {
        return estado;
    }
}
