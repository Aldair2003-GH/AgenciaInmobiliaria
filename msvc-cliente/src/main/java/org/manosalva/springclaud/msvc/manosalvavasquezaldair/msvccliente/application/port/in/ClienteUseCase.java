package org.manosalva.springclaud.msvc.manosalvavasquezaldair.msvccliente.application.port.in;

import java.util.*;
import java.math.BigDecimal;
import java.time.*;
import org.manosalva.springclaud.msvc.manosalvavasquezaldair.msvccliente.domain.model.*;
import org.manosalva.springclaud.msvc.manosalvavasquezaldair.msvccliente.domain.ids.*;
import org.manosalva.springclaud.msvc.manosalvavasquezaldair.msvccliente.domain.enums.*;

public interface ClienteUseCase {
    Optional<Cliente> porId(ClienteId id);
    Cliente crear(DocumentoIdentidad documentoIdentidad, String nombreCompleto, DatosContacto datosContacto, Set<RolCliente> roles);
    Optional<Cliente> actualizarContacto(ClienteId id, DatosContacto datos);
    Optional<Cliente> definirPreferencia(ClienteId id, Preferencia preferencia);
    Optional<Cliente> agregarRol(ClienteId id, RolCliente rol);
}
