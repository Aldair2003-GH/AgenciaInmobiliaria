package org.manosalva.springclaud.msvc.manosalvavasquezaldair.msvcpropiedad.domain.model;

import java.util.*;
import java.math.BigDecimal;
import java.time.*;
import org.manosalva.springclaud.msvc.manosalvavasquezaldair.msvcpropiedad.domain.enums.*;
import org.manosalva.springclaud.msvc.manosalvavasquezaldair.msvcpropiedad.domain.ids.*;

public final class Caracteristicas {
    private final TipoInmueble tipoInmueble;
    private final BigDecimal areaM2;
    private final Integer dormitorios;
    private final Integer banos;

    public Caracteristicas(TipoInmueble tipoInmueble, BigDecimal areaM2, Integer dormitorios, Integer banos) {
        Objects.requireNonNull(tipoInmueble, "tipoInmueble es obligatorio");
        if (areaM2 == null || areaM2.signum() <= 0) throw new IllegalArgumentException("areaM2 debe ser positivo");
        if (dormitorios != null && dormitorios < 0 || banos != null && banos < 0) throw new IllegalArgumentException("Los ambientes no pueden ser negativos");
        this.tipoInmueble = tipoInmueble;
        this.areaM2 = areaM2;
        this.dormitorios = dormitorios;
        this.banos = banos;
    }

    public TipoInmueble getTipoInmueble() {
        return tipoInmueble;
    }

    public BigDecimal getAreaM2() {
        return areaM2;
    }

    public Integer getDormitorios() {
        return dormitorios;
    }

    public Integer getBanos() {
        return banos;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (!(object instanceof Caracteristicas other)) return false;
        return Objects.equals(tipoInmueble, other.tipoInmueble) && Objects.equals(areaM2, other.areaM2) && Objects.equals(dormitorios, other.dormitorios) && Objects.equals(banos, other.banos);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tipoInmueble, areaM2, dormitorios, banos);
    }
}
