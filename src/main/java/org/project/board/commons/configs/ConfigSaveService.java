package org.project.board.commons.configs;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.project.board.entities.Configs;
import org.project.board.repositories.ConfigsRepository;
import org.springframework.stereotype.Service;

/**
 * 데이터가 기존에 있으면 수정, 없으면 생성해주는 서비스
 */
@Service
@RequiredArgsConstructor
public class ConfigSaveService {
    private final ConfigsRepository repository;
    public <T> void save(String code, T t) {
        Configs configs = repository.findById(code).orElseGet(Configs::new); // 코드를 조회해서 있으면 수정, 없을 때는 생성

        ObjectMapper om = new ObjectMapper(); // JSON과 자바 객체간의 변환을 도와줌
        String value = null;
        try {
            value = om.writeValueAsString(t); // value 객체를 JSON 형태로 변환 후 저장
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        configs.setCode(code);
        configs.setValue(value);

        repository.saveAndFlush(configs);
    }
}
