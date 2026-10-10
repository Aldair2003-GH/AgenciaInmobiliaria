package org.manosalva.springclaud.msvc.manosalvavasquezaldair.msvcpropiedad.application.port.in;

import java.util.*;
import java.math.BigDecimal;
import java.time.*;
import org.manosalva.springclaud.msvc.manosalvavasquezaldair.msvcpropiedad.domain.model.*;
import org.manosalva.springclaud.msvc.manosalvavasquezaldair.msvcpropiedad.domain.ids.*;
import org.manosalva.springclaud.msvc.manosalvavasquezaldair.msvcpropiedad.domain.enums.*;

public interface PropiedadUseCase {
    Optional<Propiedad> porId(PropiedadId id);
    Propiedad crear(ClienteId propietarioId, Direccion direccion, Caracteristicas caracteristicas, Set<PrecioComercial> precios, List<Fotografia> fotografias);
    Optional<Propiedad> establecerPrecio(PropiedadId id, PrecioComercial precio);
    Optional<Propiedad> publicar(PropiedadId id);
    Optional<Propiedad> reservar(PropiedadId id, OperacionId operacionId);
    Optional<Propiedad> actualizarDisponibilidad(PropiedadId id, EstadoDisponibilidad estado);
}
