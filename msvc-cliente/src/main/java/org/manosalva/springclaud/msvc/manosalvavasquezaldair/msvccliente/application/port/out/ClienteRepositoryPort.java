package org.manosalva.springclaud.msvc.manosalvavasquezaldair.msvccliente.application.port.out;

import java.util.*;
import java.math.BigDecimal;
import java.time.*;
import org.manosalva.springclaud.msvc.manosalvavasquezaldair.msvccliente.domain.model.*;
import org.manosalva.springclaud.msvc.manosalvavasquezaldair.msvccliente.domain.ids.*;
import org.manosalva.springclaud.msvc.manosalvavasquezaldair.msvccliente.domain.enums.*;

public interface ClienteRepositoryPort {
    Cliente guardar(Cliente cliente);
    Optional<Cliente> porId(ClienteId id);
}
