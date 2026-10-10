package org.manosalva.springclaud.msvc.manosalvavasquezaldair.msvccliente.infrastructure.rest.dto;

import java.util.*;
import java.math.BigDecimal;
import java.time.*;
import org.manosalva.springclaud.msvc.manosalvavasquezaldair.msvccliente.domain.model.*;
import org.manosalva.springclaud.msvc.manosalvavasquezaldair.msvccliente.domain.enums.*;
import org.manosalva.springclaud.msvc.manosalvavasquezaldair.msvccliente.domain.ids.*;

public class DocumentoIdentidadDto {
    private TipoDocumento tipo;
    private String numero;

    public TipoDocumento getTipo() {
        return tipo;
    }

    public void setTipo(TipoDocumento tipo) {
        this.tipo = tipo;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public DocumentoIdentidad toDomain() {
        return new DocumentoIdentidad(tipo, numero);
    }

    public static DocumentoIdentidadDto from(DocumentoIdentidad valor) {
        if (valor == null) return null;
        DocumentoIdentidadDto dto = new DocumentoIdentidadDto();
        dto.setTipo(valor.getTipo());
        dto.setNumero(valor.getNumero());
        return dto;
    }
}
