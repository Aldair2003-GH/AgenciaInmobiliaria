package org.manosalva.springclaud.msvc.manosalvavasquezaldair.msvccliente.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.manosalva.springclaud.msvc.manosalvavasquezaldair.msvccliente.infrastructure.persistence.repository.SpringDataClienteRepository;
import org.springframework.context.annotation.Configuration;
import org.manosalva.springclaud.msvc.manosalvavasquezaldair.msvccliente.application.port.in.ClienteUseCase;
import org.manosalva.springclaud.msvc.manosalvavasquezaldair.msvccliente.application.port.out.ClienteRepositoryPort;
import org.manosalva.springclaud.msvc.manosalvavasquezaldair.msvccliente.application.service.ClienteService;
import org.manosalva.springclaud.msvc.manosalvavasquezaldair.msvccliente.infrastructure.persistence.ClientePersistenceAdapter;

@Configuration
public class ClienteConfig {
    @Bean
    public ClienteRepositoryPort clienteRepository(SpringDataClienteRepository repository) {
        return new ClientePersistenceAdapter(repository);
    }
    @Bean
    public ClienteUseCase clienteUseCase(ClienteRepositoryPort repository) {
        return new ClienteService(repository);
    }
}
