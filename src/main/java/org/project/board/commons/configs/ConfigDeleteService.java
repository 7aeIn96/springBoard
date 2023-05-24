package org.project.board.commons.configs;

import lombok.RequiredArgsConstructor;
import org.project.board.entities.Configs;
import org.project.board.repositories.ConfigsRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ConfigDeleteService {
    private final ConfigsRepository repository;

    public void delete(String code) {
        Configs configs = repository.findById(code).orElse(null);
        if (configs == null) {
            return;
        }
        repository.delete(configs);
        repository.flush();
    }
}