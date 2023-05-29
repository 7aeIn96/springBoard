package org.project.board.models.board.config;

import org.project.board.commons.CommonException;
import org.springframework.http.HttpStatus;

public class DuplicateBoardConfigException extends CommonException {
    public DuplicateBoardConfigException() { // 메세지 고정
        super("이미 등록된 게시판입니다.", HttpStatus.BAD_REQUEST);
    }
}
