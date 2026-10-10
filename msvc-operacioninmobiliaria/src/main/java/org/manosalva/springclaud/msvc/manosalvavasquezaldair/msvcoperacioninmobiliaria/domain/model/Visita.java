package org.manosalva.springclaud.msvc.manosalvavasquezaldair.msvcoperacioninmobiliaria.domain.model;

import java.util.*;
import java.math.BigDecimal;
import java.time.*;
import org.manosalva.springclaud.msvc.manosalvavasquezaldair.msvcoperacioninmobiliaria.domain.enums.*;
import org.manosalva.springclaud.msvc.manosalvavasquezaldair.msvcoperacioninmobiliaria.domain.ids.*;

public class Visita {
    private final VisitaId id;
    private final ClienteId clienteId;
    private LocalDateTime fecha;
    private EstadoVisita estado = EstadoVisita.PROGRAMADA;
    public Visita(VisitaId id, ClienteId clienteId, LocalDateTime fecha) {
        this.id = Objects.requireNonNull(id, "id es obligatorio");
        this.clienteId = Objects.requireNonNull(clienteId, "clienteId es obligatorio");
        this.fecha = Objects.requireNonNull(fecha, "fecha es obligatoria");
    }

    public static Visita reconstruir(VisitaId id, ClienteId clienteId, LocalDateTime fecha, EstadoVisita estado) {
        Visita valor = new Visita(id, clienteId, fecha);
        valor.estado = Objects.requireNonNull(estado);
        return valor;
    }

    public void reprogramar(LocalDateTime fecha) {
        if (estado != EstadoVisita.PROGRAMADA) throw new IllegalStateException("Solo se reprograma una visita programada");
        this.fecha = Objects.requireNonNull(fecha, "fecha es obligatoria");
    }

    public void registrarResultado(EstadoVisita resultado) {
        Objects.requireNonNull(resultado, "resultado es obligatorio");
        if (estado != EstadoVisita.PROGRAMADA || resultado == EstadoVisita.PROGRAMADA) throw new IllegalStateException("Resultado de visita inválido");
        estado = resultado;
    }

    public VisitaId getId() {
        return id;
    }

    public ClienteId getClienteId() {
        return clienteId;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public EstadoVisita getEstado() {
        return estado;
    }
}
