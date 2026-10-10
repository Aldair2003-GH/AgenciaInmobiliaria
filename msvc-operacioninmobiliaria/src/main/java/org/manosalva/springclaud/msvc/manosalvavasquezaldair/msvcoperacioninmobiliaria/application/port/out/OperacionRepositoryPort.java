package org.manosalva.springclaud.msvc.manosalvavasquezaldair.msvcoperacioninmobiliaria.application.port.out;

import java.util.*;
import java.math.BigDecimal;
import java.time.*;
import org.manosalva.springclaud.msvc.manosalvavasquezaldair.msvcoperacioninmobiliaria.domain.model.*;
import org.manosalva.springclaud.msvc.manosalvavasquezaldair.msvcoperacioninmobiliaria.domain.ids.*;
import org.manosalva.springclaud.msvc.manosalvavasquezaldair.msvcoperacioninmobiliaria.domain.enums.*;

public interface OperacionRepositoryPort {
    OperacionInmobiliaria guardar(OperacionInmobiliaria operacion);
    Optional<OperacionInmobiliaria> porId(OperacionId id);
}
