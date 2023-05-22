package org.project.board.models.member;

import org.project.board.commons.CommonException;
import org.springframework.http.HttpStatus;

/**
 * 로그인 유효성 검사시 예외 ( 400대 에러 )
 */
public class LoginValidationException extends CommonException {

    private String field;

    /**
     * 일반 검증
     * 코드 입력해서 메세지 가져오고 응답 코드는 400대 오류
     * @param code
     */
    //
    public LoginValidationException(String code) {
        super(bundleValidation.getString(code), HttpStatus.BAD_REQUEST);
    }

    /**
     * 필드를 가진 특정 검증 ( 위의 일반 검증 이용 )
     * @param field
     * @param code
     */
    public LoginValidationException(String field, String code) {
        this(code); // 위에서 정의된 LoginValidationException 불러옴
        this.field = field;
    }

    public String getField() {
        return field;
    }
}
