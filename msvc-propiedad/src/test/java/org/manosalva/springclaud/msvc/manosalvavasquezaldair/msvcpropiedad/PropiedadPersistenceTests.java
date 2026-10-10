package org.manosalva.springclaud.msvc.manosalvavasquezaldair.msvcpropiedad;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.*;
import java.util.*;
import java.math.BigDecimal;
import java.time.*;
import org.manosalva.springclaud.msvc.manosalvavasquezaldair.msvcpropiedad.domain.model.*;
import org.manosalva.springclaud.msvc.manosalvavasquezaldair.msvcpropiedad.domain.ids.*;
import org.manosalva.springclaud.msvc.manosalvavasquezaldair.msvcpropiedad.domain.enums.*;
import org.manosalva.springclaud.msvc.manosalvavasquezaldair.msvcpropiedad.application.service.PropiedadService;
import org.manosalva.springclaud.msvc.manosalvavasquezaldair.msvcpropiedad.application.port.out.PropiedadRepositoryPort;

@SpringBootTest
class PropiedadPersistenceTests {
    @Autowired
    private PropiedadRepositoryPort repository;

    @Test
    void recuperaReservaPreciosYFotografiasSinDuplicarlas() {
        PropiedadService service = new PropiedadService(repository);
        Propiedad propiedad = service.crear(new ClienteId(1L), new Direccion("Lima", "Lima", "Surco", "Calle 3"),
                new Caracteristicas(TipoInmueble.CASA, new BigDecimal("100.5"), null, null),
                Set.of(new PrecioComercial(ModalidadComercial.VENTA, new BigDecimal("120000"), Moneda.USD)),
                List.of(new Fotografia(new FotografiaId(1L), "https://example.com/casa.jpg", 0)));
        service.publicar(propiedad.getId());
        service.establecerPrecio(propiedad.getId(), new PrecioComercial(ModalidadComercial.VENTA, new BigDecimal("125000"), Moneda.USD));
        service.establecerPrecio(propiedad.getId(), new PrecioComercial(ModalidadComercial.ALQUILER, new BigDecimal("1800"), Moneda.PEN));
        service.reservar(propiedad.getId(), new OperacionId(1L));
        PropiedadService otraInstancia = new PropiedadService(repository);
        Propiedad recuperada = otraInstancia.porId(propiedad.getId()).orElseThrow();
        assertEquals(EstadoDisponibilidad.RESERVADA, recuperada.getDisponibilidad());
        assertFalse(recuperada.isPublicada());
        assertEquals(2, recuperada.getPrecios().size());
        assertEquals(1, recuperada.getFotografias().size());
        assertEquals(new FotografiaId(1L), recuperada.getFotografias().get(0).getId());
        assertNull(recuperada.getCaracteristicas().getDormitorios());
        assertThrows(IllegalStateException.class, () -> otraInstancia.publicar(propiedad.getId()));
        assertEquals(EstadoDisponibilidad.RESERVADA, repository.porId(propiedad.getId()).orElseThrow().getDisponibilidad());
    }
}
