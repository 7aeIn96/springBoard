package org.project.board.models.board.config;

import org.project.board.commons.CommonException;
import org.springframework.http.HttpStatus;

public class BoardNotAllowedAccessException extends CommonException { // 접근 권한 X

    public BoardNotAllowedAccessException() {
        super(bundleValidation.getString("Validation.board.notAllowedAccess"), HttpStatus.UNAUTHORIZED);
    }
}
