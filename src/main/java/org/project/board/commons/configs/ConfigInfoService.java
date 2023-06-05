package org.project.board.commons.configs;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
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

    public <T> T get(String code, Class<T> clazz) {
        return get(code, clazz, null);
    }

    public <T> T get(String code, TypeReference<T> type) {
        return get(code, null, type);
    }

    public <T> T get(String code, Class<T> clazz, TypeReference<T> typeReference) {
        try {
        // 가져오는 객체의 타입이 여러개라서 제네릭 사용
        // clazz가 null 값이면 TypeReference 사용, null이 아니면 clazz 사용
        Configs configs = repository.findById(code).orElse(null); // code가 없으면 null
        if (configs == null || configs.getValue() == null || configs.getValue().isBlank()) {
            return null;
        }
        String value = configs.getValue();
        ObjectMapper om = new ObjectMapper(); // JSON과 자바 객체간의 변환을 도와줌

        T data = null;
        try {
            // JSON 형태를 자바 객체 형태로 변환
            if (clazz == null) data = om.readValue(value, typeReference); // Map, List, 다른형태
            else data = om.readValue(value, clazz); // 일반객체
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return data;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
