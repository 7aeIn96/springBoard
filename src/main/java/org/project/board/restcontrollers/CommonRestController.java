package org.project.board.restcontrollers;

import org.project.board.commons.CommonException;
import org.project.board.commons.rests.JSONData;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 공통 예외처리
 * @ControllerAdvice와 @ResponseBody 조합
 */
@RestControllerAdvice("org.project.board.restcontrollers") // 적용범위
public class CommonRestController {
    @ExceptionHandler(Exception.class)
    public ResponseEntity<JSONData<Object>> errorHandler(Exception e) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

        if (e instanceof CommonException) { // CommonException에 정의한 예외면 별도의 응답코드 가져올 수 있게 설정
            CommonException commonException = (CommonException) e;
            status = commonException.getStatus();
        }

        // JSON 데이터를 가지고 형식을 통일화
        JSONData<Object> jsonData = JSONData.builder()
                .success(false)
                .message(e.getMessage())
                .status(status)
                .build();

        return ResponseEntity.status(status).body(jsonData);

    }
}
