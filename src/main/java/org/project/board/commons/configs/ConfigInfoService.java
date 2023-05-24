package org.project.board.commons.configs;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.project.board.entities.Configs;
import org.project.board.repositories.ConfigsRepository;
import org.springframework.stereotype.Service;

/**
 * 조회 서비스
 */
@Service
@RequiredArgsConstructor
public class ConfigInfoService {
    private final ConfigsRepository repository;

    public <T> T get(String code, Class<T> clazz) { // 가져오는 객체의 타입이 여러개라서 제네릭 사용
        Configs configs = repository.findById(code).orElse(null); // code가 없으면 null
        if (configs == null || configs.getValue() == null || configs.getValue().isBlank()) {
            return null;
        }
        String value = configs.getValue();

        ObjectMapper om = new ObjectMapper(); // JSON과 자바 객체간의 변환을 도와줌
        T data = null;
        try {
            if (clazz == null) data = om.readValue(value, clazz); // JSON 형태를 자바 객체 형태로 변환
            else data = om.readValue(value, clazz);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return data;
    }
}
