package org.manosalva.springclaud.msvc.manosalvavasquezaldair.msvcpropiedad.application.port.out;

import java.util.*;
import java.math.BigDecimal;
import java.time.*;
import org.manosalva.springclaud.msvc.manosalvavasquezaldair.msvcpropiedad.domain.model.*;
import org.manosalva.springclaud.msvc.manosalvavasquezaldair.msvcpropiedad.domain.ids.*;
import org.manosalva.springclaud.msvc.manosalvavasquezaldair.msvcpropiedad.domain.enums.*;

public interface PropiedadRepositoryPort {
    Propiedad guardar(Propiedad propiedad);
    Optional<Propiedad> porId(PropiedadId id);
}
