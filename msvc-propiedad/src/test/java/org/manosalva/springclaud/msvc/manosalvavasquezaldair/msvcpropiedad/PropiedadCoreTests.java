package org.manosalva.springclaud.msvc.manosalvavasquezaldair.msvcpropiedad;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.*;
import java.math.BigDecimal;
import java.time.*;
import org.manosalva.springclaud.msvc.manosalvavasquezaldair.msvcpropiedad.domain.model.*;
import org.manosalva.springclaud.msvc.manosalvavasquezaldair.msvcpropiedad.domain.ids.*;
import org.manosalva.springclaud.msvc.manosalvavasquezaldair.msvcpropiedad.domain.enums.*;

class PropiedadCoreTests {
    private Propiedad propiedad() {
        return new Propiedad(new PropiedadId(1L), new ClienteId(1L), new Direccion("Lima", "Lima", "Miraflores", "Calle 1"),
                new Caracteristicas(TipoInmueble.DEPARTAMENTO, new BigDecimal("80"), 2, null),
                Set.of(new PrecioComercial(ModalidadComercial.VENTA, new BigDecimal("120000"), Moneda.USD)), List.of());
    }
    @Test
    void mantieneUnPrecioPorModalidadYPermiteVentaYAlquiler() {
        Propiedad propiedad = propiedad();
        propiedad.establecerPrecio(new PrecioComercial(ModalidadComercial.VENTA, new BigDecimal("125000"), Moneda.USD));
        propiedad.establecerPrecio(new PrecioComercial(ModalidadComercial.ALQUILER, new BigDecimal("2000"), Moneda.PEN));
        assertEquals(2, propiedad.getPrecios().size());
        assertTrue(propiedad.getPrecios().stream().anyMatch(p -> p.getImporte().compareTo(new BigDecimal("125000")) == 0));
        assertThrows(UnsupportedOperationException.class, () -> propiedad.getPrecios().clear());
    }
    @Test
    void publicaReservaYActualizaDisponibilidad() {
        Propiedad propiedad = propiedad();
        propiedad.publicar();
        assertTrue(propiedad.isPublicada());
        propiedad.reservar(new OperacionId(1L));
        assertEquals(EstadoDisponibilidad.RESERVADA, propiedad.getDisponibilidad());
        assertFalse(propiedad.isPublicada());
        assertThrows(IllegalStateException.class, propiedad::publicar);
        assertThrows(IllegalStateException.class, () -> propiedad.reservar(new OperacionId(2L)));
        propiedad.actualizarDisponibilidad(EstadoDisponibilidad.VENDIDA);
        assertEquals(EstadoDisponibilidad.VENDIDA, propiedad.getDisponibilidad());
    }
    @Test
    void rechazaAreaYAmbientesInvalidos() {
        assertThrows(IllegalArgumentException.class, () -> new Caracteristicas(TipoInmueble.CASA, BigDecimal.ZERO, 1, 1));
        assertThrows(IllegalArgumentException.class, () -> new Caracteristicas(TipoInmueble.CASA, BigDecimal.TEN, -1, 1));
    }
    @Test
    void exigePrecioInicialYRechazaDosPreciosDeLaMismaModalidad() {
        Propiedad original = propiedad();
        assertThrows(IllegalArgumentException.class, () -> new Propiedad(original.getId(), original.getPropietarioId(), original.getDireccion(), original.getCaracteristicas(), Set.of(), List.of()));
        Set<PrecioComercial> precios = Set.of(new PrecioComercial(ModalidadComercial.VENTA, BigDecimal.TEN, Moneda.PEN),
                new PrecioComercial(ModalidadComercial.VENTA, BigDecimal.ONE, Moneda.USD));
        assertThrows(IllegalArgumentException.class, () -> new Propiedad(original.getId(), original.getPropietarioId(), original.getDireccion(), original.getCaracteristicas(), precios, List.of()));
    }
}
