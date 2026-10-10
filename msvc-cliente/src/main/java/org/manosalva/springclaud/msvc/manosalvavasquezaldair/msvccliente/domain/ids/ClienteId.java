package org.manosalva.springclaud.msvc.manosalvavasquezaldair.msvccliente.domain.ids;


public final class ClienteId {
    private final Long value;
    public ClienteId(Long value) {
        if (value == null || value <= 0) throw new IllegalArgumentException("El identificador debe ser positivo");
        this.value = value;
    }

    public Long getValue() {
        return value;
    }

    @Override
    public boolean equals(Object object) {
        return object instanceof ClienteId other && value.equals(other.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
