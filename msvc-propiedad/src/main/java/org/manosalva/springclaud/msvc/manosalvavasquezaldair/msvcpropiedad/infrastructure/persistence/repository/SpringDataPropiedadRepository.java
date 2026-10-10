package org.manosalva.springclaud.msvc.manosalvavasquezaldair.msvcpropiedad.infrastructure.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.manosalva.springclaud.msvc.manosalvavasquezaldair.msvcpropiedad.infrastructure.persistence.entity.PropiedadEntity;

public interface SpringDataPropiedadRepository extends JpaRepository<PropiedadEntity, Long> {
}
