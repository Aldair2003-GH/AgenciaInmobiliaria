package org.manosalva.springclaud.msvc.manosalvavasquezaldair.msvccliente.infrastructure.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.manosalva.springclaud.msvc.manosalvavasquezaldair.msvccliente.infrastructure.persistence.entity.ClienteEntity;

public interface SpringDataClienteRepository extends JpaRepository<ClienteEntity, Long> {
}
