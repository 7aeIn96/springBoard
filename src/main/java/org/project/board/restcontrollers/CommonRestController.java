package org.project.board.restcontrollers;

import org.project.board.commons.CommonException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @Controller와 @ResponseBody 조합
 */
@RestControllerAdvice("org.project.board.restcontrollers") // 적용범위
public class CommonRestController {
}
