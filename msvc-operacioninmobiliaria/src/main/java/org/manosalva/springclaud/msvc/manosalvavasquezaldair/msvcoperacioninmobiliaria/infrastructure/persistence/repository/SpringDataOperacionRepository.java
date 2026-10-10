package org.manosalva.springclaud.msvc.manosalvavasquezaldair.msvcoperacioninmobiliaria.infrastructure.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.manosalva.springclaud.msvc.manosalvavasquezaldair.msvcoperacioninmobiliaria.infrastructure.persistence.entity.OperacionEntity;

public interface SpringDataOperacionRepository extends JpaRepository<OperacionEntity, Long> {
}
