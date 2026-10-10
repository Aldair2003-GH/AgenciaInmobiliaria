package org.manosalva.springclaud.msvc.manosalvavasquezaldair.msvcpropiedad.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.manosalva.springclaud.msvc.manosalvavasquezaldair.msvcpropiedad.infrastructure.persistence.repository.SpringDataPropiedadRepository;
import org.springframework.context.annotation.Configuration;
import org.manosalva.springclaud.msvc.manosalvavasquezaldair.msvcpropiedad.application.port.in.PropiedadUseCase;
import org.manosalva.springclaud.msvc.manosalvavasquezaldair.msvcpropiedad.application.port.out.PropiedadRepositoryPort;
import org.manosalva.springclaud.msvc.manosalvavasquezaldair.msvcpropiedad.application.service.PropiedadService;
import org.manosalva.springclaud.msvc.manosalvavasquezaldair.msvcpropiedad.infrastructure.persistence.PropiedadPersistenceAdapter;

@Configuration
public class PropiedadConfig {
    @Bean
    public PropiedadRepositoryPort propiedadRepository(SpringDataPropiedadRepository repository) {
        return new PropiedadPersistenceAdapter(repository);
    }
    @Bean
    public PropiedadUseCase propiedadUseCase(PropiedadRepositoryPort repository) {
        return new PropiedadService(repository);
    }
}
