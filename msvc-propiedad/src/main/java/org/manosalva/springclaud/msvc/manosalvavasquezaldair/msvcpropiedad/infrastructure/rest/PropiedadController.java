package org.manosalva.springclaud.msvc.manosalvavasquezaldair.msvcpropiedad.infrastructure.rest;

import java.util.*;
import java.util.stream.Collectors;
import org.manosalva.springclaud.msvc.manosalvavasquezaldair.msvcpropiedad.domain.model.Propiedad;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.manosalva.springclaud.msvc.manosalvavasquezaldair.msvcpropiedad.domain.ids.*;
import org.manosalva.springclaud.msvc.manosalvavasquezaldair.msvcpropiedad.application.port.in.PropiedadUseCase;
import org.manosalva.springclaud.msvc.manosalvavasquezaldair.msvcpropiedad.infrastructure.rest.dto.*;

@RestController
@RequestMapping("/api/propiedades")
public class PropiedadController {
    private final PropiedadUseCase service;

    public PropiedadController(PropiedadUseCase service) {
        this.service = service;
    }

    @GetMapping("/{id}")
    public ResponseEntity<PropiedadResponseDto> detalle(@PathVariable Long id) {
        Optional<Propiedad> encontrado = service.porId(new PropiedadId(id));
        if (encontrado.isPresent()) {
            PropiedadResponseDto respuesta = PropiedadResponseDto.from(encontrado.get());
            return ResponseEntity.ok(respuesta);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<PropiedadResponseDto> crear(@RequestBody PropiedadRequestDto datos) {
        Propiedad propiedad = service.crear(new ClienteId(datos.getPropietarioId()), datos.getDireccion().toDomain(), datos.getCaracteristicas().toDomain(), datos.getPrecios().stream().map(PrecioComercialDto::toDomain).collect(Collectors.toCollection(LinkedHashSet::new)), datos.getFotografias() == null ? List.of() : datos.getFotografias().stream().map(FotografiaDto::toDomain).toList());
        PropiedadResponseDto respuesta = PropiedadResponseDto.from(propiedad);
        return ResponseEntity.status(HttpStatus.CREATED).body(respuesta);
    }

    @PutMapping("/{id}/precio")
    public ResponseEntity<PropiedadResponseDto> establecerPrecio(@PathVariable Long id, @RequestBody PrecioComercialDto datos) {
        Optional<Propiedad> encontrado = service.establecerPrecio(new PropiedadId(id), datos.toDomain());
        if (encontrado.isPresent()) {
            PropiedadResponseDto respuesta = PropiedadResponseDto.from(encontrado.get());
            return ResponseEntity.ok(respuesta);
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}/publicacion")
    public ResponseEntity<PropiedadResponseDto> publicar(@PathVariable Long id) {
        Optional<Propiedad> encontrado = service.publicar(new PropiedadId(id));
        if (encontrado.isPresent()) {
            PropiedadResponseDto respuesta = PropiedadResponseDto.from(encontrado.get());
            return ResponseEntity.ok(respuesta);
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}/reserva")
    public ResponseEntity<PropiedadResponseDto> reservar(@PathVariable Long id, @RequestBody ReservaRequestDto datos) {
        Optional<Propiedad> encontrado = service.reservar(new PropiedadId(id), new OperacionId(datos.getOperacionId()));
        if (encontrado.isPresent()) {
            PropiedadResponseDto respuesta = PropiedadResponseDto.from(encontrado.get());
            return ResponseEntity.ok(respuesta);
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}/disponibilidad")
    public ResponseEntity<PropiedadResponseDto> actualizarDisponibilidad(@PathVariable Long id, @RequestBody DisponibilidadRequestDto datos) {
        Optional<Propiedad> encontrado = service.actualizarDisponibilidad(new PropiedadId(id), datos.getEstado());
        if (encontrado.isPresent()) {
            PropiedadResponseDto respuesta = PropiedadResponseDto.from(encontrado.get());
            return ResponseEntity.ok(respuesta);
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
