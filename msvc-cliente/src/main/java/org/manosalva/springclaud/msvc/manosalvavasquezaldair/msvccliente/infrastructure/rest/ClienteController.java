package org.manosalva.springclaud.msvc.manosalvavasquezaldair.msvccliente.infrastructure.rest;

import java.util.*;
import java.util.stream.Collectors;
import org.manosalva.springclaud.msvc.manosalvavasquezaldair.msvccliente.domain.model.Cliente;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.manosalva.springclaud.msvc.manosalvavasquezaldair.msvccliente.domain.ids.*;
import org.manosalva.springclaud.msvc.manosalvavasquezaldair.msvccliente.application.port.in.ClienteUseCase;
import org.manosalva.springclaud.msvc.manosalvavasquezaldair.msvccliente.infrastructure.rest.dto.*;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {
    private final ClienteUseCase service;

    public ClienteController(ClienteUseCase service) {
        this.service = service;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClienteResponseDto> detalle(@PathVariable Long id) {
        Optional<Cliente> encontrado = service.porId(new ClienteId(id));
        if (encontrado.isPresent()) {
            ClienteResponseDto respuesta = ClienteResponseDto.from(encontrado.get());
            return ResponseEntity.ok(respuesta);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<ClienteResponseDto> crear(@RequestBody ClienteRequestDto datos) {
        Cliente cliente = service.crear(datos.getDocumentoIdentidad().toDomain(), datos.getNombreCompleto(), datos.getDatosContacto().toDomain(), datos.getRoles());
        ClienteResponseDto respuesta = ClienteResponseDto.from(cliente);
        return ResponseEntity.status(HttpStatus.CREATED).body(respuesta);
    }

    @PutMapping("/{id}/contacto")
    public ResponseEntity<ClienteResponseDto> actualizarContacto(@PathVariable Long id, @RequestBody DatosContactoDto datos) {
        Optional<Cliente> encontrado = service.actualizarContacto(new ClienteId(id), datos.toDomain());
        if (encontrado.isPresent()) {
            ClienteResponseDto respuesta = ClienteResponseDto.from(encontrado.get());
            return ResponseEntity.ok(respuesta);
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}/preferencia")
    public ResponseEntity<ClienteResponseDto> definirPreferencia(@PathVariable Long id, @RequestBody PreferenciaDto datos) {
        Optional<Cliente> encontrado = service.definirPreferencia(new ClienteId(id), datos.toDomain());
        if (encontrado.isPresent()) {
            ClienteResponseDto respuesta = ClienteResponseDto.from(encontrado.get());
            return ResponseEntity.ok(respuesta);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/{id}/roles")
    public ResponseEntity<ClienteResponseDto> agregarRol(@PathVariable Long id, @RequestBody RolRequestDto datos) {
        Optional<Cliente> encontrado = service.agregarRol(new ClienteId(id), datos.getRol());
        if (encontrado.isPresent()) {
            ClienteResponseDto respuesta = ClienteResponseDto.from(encontrado.get());
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
