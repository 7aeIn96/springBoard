package org.project.board.models.board.config;

import org.project.board.commons.CommonException;
import org.springframework.http.HttpStatus;

public class BoardConfigNotExistException extends CommonException {
    public BoardConfigNotExistException() { // validations.properties 에 정의되어있는 메세지 출력
        super(bundleValidation.getString("Validation.board.notExists"), HttpStatus.BAD_REQUEST);
    }
}
