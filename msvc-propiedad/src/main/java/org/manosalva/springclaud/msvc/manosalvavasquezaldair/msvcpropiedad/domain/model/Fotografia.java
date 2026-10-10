package org.manosalva.springclaud.msvc.manosalvavasquezaldair.msvcpropiedad.domain.model;

import java.util.*;
import java.math.BigDecimal;
import java.time.*;
import org.manosalva.springclaud.msvc.manosalvavasquezaldair.msvcpropiedad.domain.enums.*;
import org.manosalva.springclaud.msvc.manosalvavasquezaldair.msvcpropiedad.domain.ids.*;

public class Fotografia {
    private final FotografiaId id;
    private final String url;
    private final int orden;

    public Fotografia(FotografiaId id, String url, int orden) {
        Objects.requireNonNull(id, "id es obligatorio");
        if (url == null || url.isBlank()) throw new IllegalArgumentException("url es obligatorio");
        if (orden < 0) throw new IllegalArgumentException("orden no puede ser negativo");
        this.id = id;
        this.url = url;
        this.orden = orden;
    }

    public FotografiaId getId() {
        return id;
    }

    public String getUrl() {
        return url;
    }

    public int getOrden() {
        return orden;
    }
}
