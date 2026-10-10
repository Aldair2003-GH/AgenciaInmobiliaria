package org.manosalva.springclaud.msvc.manosalvavasquezaldair.msvcoperacioninmobiliaria.infrastructure.rest;

import java.util.*;
import java.util.stream.Collectors;
import org.manosalva.springclaud.msvc.manosalvavasquezaldair.msvcoperacioninmobiliaria.domain.model.OperacionInmobiliaria;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.manosalva.springclaud.msvc.manosalvavasquezaldair.msvcoperacioninmobiliaria.domain.ids.*;
import org.manosalva.springclaud.msvc.manosalvavasquezaldair.msvcoperacioninmobiliaria.application.port.in.OperacionUseCase;
import org.manosalva.springclaud.msvc.manosalvavasquezaldair.msvcoperacioninmobiliaria.infrastructure.rest.dto.*;

@RestController
@RequestMapping("/api/operaciones")
public class OperacionController {
    private final OperacionUseCase service;

    public OperacionController(OperacionUseCase service) {
        this.service = service;
    }

    @GetMapping("/{id}")
    public ResponseEntity<OperacionResponseDto> detalle(@PathVariable Long id) {
        Optional<OperacionInmobiliaria> encontrado = service.porId(new OperacionId(id));
        if (encontrado.isPresent()) {
            OperacionResponseDto respuesta = OperacionResponseDto.from(encontrado.get());
            return ResponseEntity.ok(respuesta);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<OperacionResponseDto> iniciar(@RequestBody IniciarOperacionRequestDto datos) {
        OperacionInmobiliaria operacion = service.iniciar(new PropiedadId(datos.getPropiedadId()), datos.getModalidad(), datos.getParticipantes().stream().map(ParticipanteDto::toDomain).collect(Collectors.toCollection(LinkedHashSet::new)));
        OperacionResponseDto respuesta = OperacionResponseDto.from(operacion);
        return ResponseEntity.status(HttpStatus.CREATED).body(respuesta);
    }

    @PostMapping("/{id}/participantes")
    public ResponseEntity<OperacionResponseDto> registrarParticipante(@PathVariable Long id, @RequestBody ParticipanteDto datos) {
        Optional<OperacionInmobiliaria> encontrado = service.registrarParticipante(new OperacionId(id), datos.toDomain());
        if (encontrado.isPresent()) {
            OperacionResponseDto respuesta = OperacionResponseDto.from(encontrado.get());
            return ResponseEntity.ok(respuesta);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/{id}/visitas")
    public ResponseEntity<OperacionResponseDto> programarVisita(@PathVariable Long id, @RequestBody VisitaRequestDto datos) {
        Optional<OperacionInmobiliaria> encontrado = service.programarVisita(new OperacionId(id), new ClienteId(datos.getClienteId()), datos.getFecha());
        if (encontrado.isPresent()) {
            OperacionResponseDto respuesta = OperacionResponseDto.from(encontrado.get());
            return ResponseEntity.ok(respuesta);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/{id}/ofertas")
    public ResponseEntity<OperacionResponseDto> registrarOferta(@PathVariable Long id, @RequestBody OfertaRequestDto datos) {
        Optional<OperacionInmobiliaria> encontrado = service.registrarOferta(new OperacionId(id), new ClienteId(datos.getClienteId()), datos.getMonto(), datos.getMoneda());
        if (encontrado.isPresent()) {
            OperacionResponseDto respuesta = OperacionResponseDto.from(encontrado.get());
            return ResponseEntity.ok(respuesta);
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}/ofertas/{ofertaId}/aceptacion")
    public ResponseEntity<OperacionResponseDto> aceptarOferta(@PathVariable Long id, @PathVariable Long ofertaId) {
        Optional<OperacionInmobiliaria> encontrado = service.aceptarOferta(new OperacionId(id), new OfertaId(ofertaId));
        if (encontrado.isPresent()) {
            OperacionResponseDto respuesta = OperacionResponseDto.from(encontrado.get());
            return ResponseEntity.ok(respuesta);
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}/ofertas/{ofertaId}/rechazo")
    public ResponseEntity<OperacionResponseDto> rechazarOferta(@PathVariable Long id, @PathVariable Long ofertaId) {
        Optional<OperacionInmobiliaria> encontrado = service.rechazarOferta(new OperacionId(id), new OfertaId(ofertaId));
        if (encontrado.isPresent()) {
            OperacionResponseDto respuesta = OperacionResponseDto.from(encontrado.get());
            return ResponseEntity.ok(respuesta);
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}/visitas/{visitaId}/fecha")
    public ResponseEntity<OperacionResponseDto> reprogramarVisita(@PathVariable Long id, @PathVariable Long visitaId, @RequestBody FechaVisitaRequestDto datos) {
        Optional<OperacionInmobiliaria> encontrado = service.reprogramarVisita(new OperacionId(id), new VisitaId(visitaId), datos.getFecha());
        if (encontrado.isPresent()) {
            OperacionResponseDto respuesta = OperacionResponseDto.from(encontrado.get());
            return ResponseEntity.ok(respuesta);
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}/visitas/{visitaId}/resultado")
    public ResponseEntity<OperacionResponseDto> registrarResultado(@PathVariable Long id, @PathVariable Long visitaId, @RequestBody ResultadoVisitaRequestDto datos) {
        Optional<OperacionInmobiliaria> encontrado = service.registrarResultado(new OperacionId(id), new VisitaId(visitaId), datos.getResultado());
        if (encontrado.isPresent()) {
            OperacionResponseDto respuesta = OperacionResponseDto.from(encontrado.get());
            return ResponseEntity.ok(respuesta);
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}/condiciones")
    public ResponseEntity<OperacionResponseDto> acordarCondiciones(@PathVariable Long id, @RequestBody CondicionesContratoDto datos) {
        Optional<OperacionInmobiliaria> encontrado = service.acordarCondiciones(new OperacionId(id), datos.toDomain());
        if (encontrado.isPresent()) {
            OperacionResponseDto respuesta = OperacionResponseDto.from(encontrado.get());
            return ResponseEntity.ok(respuesta);
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}/cierre")
    public ResponseEntity<OperacionResponseDto> cerrar(@PathVariable Long id) {
        Optional<OperacionInmobiliaria> encontrado = service.cerrar(new OperacionId(id));
        if (encontrado.isPresent()) {
            OperacionResponseDto respuesta = OperacionResponseDto.from(encontrado.get());
            return ResponseEntity.ok(respuesta);
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}/cancelacion")
    public ResponseEntity<OperacionResponseDto> cancelar(@PathVariable Long id, @RequestBody CancelarRequestDto datos) {
        Optional<OperacionInmobiliaria> encontrado = service.cancelar(new OperacionId(id), datos.getMotivo());
        if (encontrado.isPresent()) {
            OperacionResponseDto respuesta = OperacionResponseDto.from(encontrado.get());
            return ResponseEntity.ok(respuesta);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/{id}/puede-cerrar")
    public ResponseEntity<Boolean> puedeCerrar(@PathVariable Long id) {
        Optional<Boolean> encontrado = service.puedeCerrar(new OperacionId(id));
        if (encontrado.isPresent()) {
            return ResponseEntity.ok(encontrado.get());
        }
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler({IllegalArgumentException.class, NullPointerException.class})
    public ResponseEntity<Map<String, String>> datosInvalidos(RuntimeException exception) {
        String mensaje = exception instanceof NullPointerException ? "Faltan datos obligatorios" : exception.getMessage();
        return ResponseEntity.badRequest().body(Collections.singletonMap("mensaje", mensaje));
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<Map<String, String>> estadoInvalido(IllegalStateException exception) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(Collections.singletonMap("mensaje", exception.getMessage()));
    }
}
