package org.project.board.models.board.config;

import org.project.board.commons.CommonException;
import org.springframework.http.HttpStatus;

public class BoardNotAllowAccessException extends CommonException { // 접근 권한 X

    public BoardNotAllowAccessException() {
        super(bundleValidation.getString("Validation.board.NotAllowAccess"), HttpStatus.UNAUTHORIZED);
    }
}
