package org.manosalva.springclaud.msvc.manosalvavasquezaldair.msvccliente;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.*;
import java.util.*;
import java.math.BigDecimal;
import java.time.*;
import org.manosalva.springclaud.msvc.manosalvavasquezaldair.msvccliente.domain.model.*;
import org.manosalva.springclaud.msvc.manosalvavasquezaldair.msvccliente.domain.ids.*;
import org.manosalva.springclaud.msvc.manosalvavasquezaldair.msvccliente.domain.enums.*;
import org.manosalva.springclaud.msvc.manosalvavasquezaldair.msvccliente.application.service.ClienteService;
import org.manosalva.springclaud.msvc.manosalvavasquezaldair.msvccliente.application.port.out.ClienteRepositoryPort;

@SpringBootTest
class ClientePersistenceTests {
    @Autowired
    private ClienteRepositoryPort repository;

    @Test
    void conservaDatosYGeneraIdsSinDependerDeLaInstanciaDelServicio() {
        ClienteService service = new ClienteService(repository);
        Cliente primero = service.crear(new DocumentoIdentidad(TipoDocumento.DNI, "34567890"), "Persistencia Cliente",
                new DatosContacto("999000000", null), Set.of(RolCliente.COMPRADOR));
        service.definirPreferencia(primero.getId(), new Preferencia(ModalidadComercial.VENTA, TipoInmueble.CASA,
                "Lima", new BigDecimal("100000.25"), Moneda.USD));
        service.actualizarContacto(primero.getId(), new DatosContacto("900000000", "cliente@example.com"));
        service.agregarRol(primero.getId(), RolCliente.PROPIETARIO);
        ClienteService otraInstancia = new ClienteService(repository);
        Cliente recuperado = otraInstancia.porId(primero.getId()).orElseThrow();
        assertEquals("cliente@example.com", recuperado.getDatosContacto().getCorreo());
        assertEquals(2, recuperado.getRoles().size());
        assertEquals(0, new BigDecimal("100000.25").compareTo(recuperado.getPreferencia().getPresupuestoMaximo()));
        Cliente segundo = otraInstancia.crear(new DocumentoIdentidad(TipoDocumento.DNI, "34567891"), "Segundo Cliente",
                new DatosContacto("900000001", null), Set.of(RolCliente.COMPRADOR));
        assertTrue(segundo.getId().getValue() > primero.getId().getValue());
        assertEquals("Persistencia Cliente", repository.porId(primero.getId()).orElseThrow().getNombreCompleto());
        otraInstancia.definirPreferencia(primero.getId(), null);
        assertNull(repository.porId(primero.getId()).orElseThrow().getPreferencia());
    }
}
