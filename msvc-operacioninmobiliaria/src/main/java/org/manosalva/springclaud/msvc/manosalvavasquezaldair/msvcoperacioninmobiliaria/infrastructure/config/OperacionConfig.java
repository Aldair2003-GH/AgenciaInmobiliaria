package org.manosalva.springclaud.msvc.manosalvavasquezaldair.msvcoperacioninmobiliaria.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.manosalva.springclaud.msvc.manosalvavasquezaldair.msvcoperacioninmobiliaria.infrastructure.persistence.repository.SpringDataOperacionRepository;
import org.springframework.context.annotation.Configuration;
import org.manosalva.springclaud.msvc.manosalvavasquezaldair.msvcoperacioninmobiliaria.application.port.in.OperacionUseCase;
import org.manosalva.springclaud.msvc.manosalvavasquezaldair.msvcoperacioninmobiliaria.application.port.out.OperacionRepositoryPort;
import org.manosalva.springclaud.msvc.manosalvavasquezaldair.msvcoperacioninmobiliaria.application.service.OperacionService;
import org.manosalva.springclaud.msvc.manosalvavasquezaldair.msvcoperacioninmobiliaria.infrastructure.persistence.OperacionPersistenceAdapter;

@Configuration
public class OperacionConfig {
    @Bean
    public OperacionRepositoryPort operacionRepository(SpringDataOperacionRepository repository) {
        return new OperacionPersistenceAdapter(repository);
    }
    @Bean
    public OperacionUseCase operacionUseCase(OperacionRepositoryPort repository) {
        return new OperacionService(repository);
    }
}
