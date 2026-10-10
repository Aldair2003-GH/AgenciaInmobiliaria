package org.manosalva.springclaud.msvc.manosalvavasquezaldair.msvccliente;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.*;
import java.math.BigDecimal;
import java.time.*;
import org.manosalva.springclaud.msvc.manosalvavasquezaldair.msvccliente.domain.model.*;
import org.manosalva.springclaud.msvc.manosalvavasquezaldair.msvccliente.domain.ids.*;
import org.manosalva.springclaud.msvc.manosalvavasquezaldair.msvccliente.domain.enums.*;

class ClienteCoreTests {
    private Cliente cliente() {
        return new Cliente(new ClienteId(1L), "Ana Pérez", new DocumentoIdentidad(TipoDocumento.DNI, "12345678"),
                new DatosContacto("999888777", null), Set.of(RolCliente.PROPIETARIO));
    }
    @Test
    void actualizaContactoPreferenciaYRolesSinDuplicados() {
        Cliente cliente = cliente();
        cliente.actualizarContacto(new DatosContacto("900000000", "ana@example.com"));
        Preferencia preferencia = new Preferencia(ModalidadComercial.ALQUILER, TipoInmueble.DEPARTAMENTO, "Lima", new BigDecimal("1800"), Moneda.PEN);
        cliente.definirPreferencia(preferencia);
        cliente.agregarRol(RolCliente.ARRENDATARIO);
        cliente.agregarRol(RolCliente.ARRENDATARIO);
        assertEquals("ana@example.com", cliente.getDatosContacto().getCorreo());
        assertEquals(preferencia, cliente.getPreferencia());
        assertEquals(2, cliente.getRoles().size());
        cliente.definirPreferencia(null);
        assertNull(cliente.getPreferencia());
    }
    @Test
    void protegeColeccionesYValidaDatosObligatorios() {
        assertThrows(UnsupportedOperationException.class, () -> cliente().getRoles().clear());
        assertThrows(IllegalArgumentException.class, () -> new DocumentoIdentidad(TipoDocumento.DNI, " "));
        assertThrows(IllegalArgumentException.class, () -> new Preferencia(ModalidadComercial.VENTA, TipoInmueble.CASA, "Lima", BigDecimal.ZERO, Moneda.USD));
        assertEquals(new DocumentoIdentidad(TipoDocumento.DNI, "123"), new DocumentoIdentidad(TipoDocumento.DNI, "123"));
    }
}
