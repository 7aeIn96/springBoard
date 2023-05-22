package org.project.board.commons;

import org.springframework.http.HttpStatus;

import java.util.ResourceBundle;

/**
 * 공통 예외 ( messages 사용 )
 */
public class CommonException extends RuntimeException {

    // validations 메세지 코드 불러오기
    protected static ResourceBundle bundleValidation; // 하위 접근 가능하게 ( 외부 접근 불가 ) protected

    // errors 메세지 코드 불러오기
    protected static ResourceBundle bundleError;
    protected HttpStatus httpStatus; // 상태 코드

    static {
        bundleValidation = ResourceBundle.getBundle("messages.validations");
        bundleError = ResourceBundle.getBundle("messages.errors");
    }

    public CommonException(String message) {
        super(message);
        httpStatus = HttpStatus.INTERNAL_SERVER_ERROR; // 메세지만 넣었을 때는 500 에러
    }

    public CommonException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }

    public HttpStatus getStatus() {
        return httpStatus;
    }
}