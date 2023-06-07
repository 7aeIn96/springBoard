package org.project.board.models.board;

import org.project.board.commons.CommonException;
import org.springframework.http.HttpStatus;

public class BoardDataNotExistException extends CommonException {

    public BoardDataNotExistException() { // validation.properties 에서 정의한 문구로 고정
        super(bundleValidation.getString("Validation.boardData.notExist"), HttpStatus.BAD_REQUEST);
    }
}