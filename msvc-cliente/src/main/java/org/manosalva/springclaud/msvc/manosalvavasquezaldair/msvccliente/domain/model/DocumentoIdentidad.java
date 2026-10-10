package org.manosalva.springclaud.msvc.manosalvavasquezaldair.msvccliente.domain.model;

import java.util.*;
import java.math.BigDecimal;
import java.time.*;
import org.manosalva.springclaud.msvc.manosalvavasquezaldair.msvccliente.domain.enums.*;
import org.manosalva.springclaud.msvc.manosalvavasquezaldair.msvccliente.domain.ids.*;

public final class DocumentoIdentidad {
    private final TipoDocumento tipo;
    private final String numero;

    public DocumentoIdentidad(TipoDocumento tipo, String numero) {
        Objects.requireNonNull(tipo, "tipo es obligatorio");
        if (numero == null || numero.isBlank()) throw new IllegalArgumentException("numero es obligatorio");
        this.tipo = tipo;
        this.numero = numero;
    }

    public TipoDocumento getTipo() {
        return tipo;
    }

    public String getNumero() {
        return numero;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (!(object instanceof DocumentoIdentidad other)) return false;
        return Objects.equals(tipo, other.tipo) && Objects.equals(numero, other.numero);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tipo, numero);
    }
}
