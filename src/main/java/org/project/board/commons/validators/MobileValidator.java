package org.project.board.commons.validators;

/**
 *  휴대전화번호 형식 체크 Validator
 */
public interface MobileValidator {
    default boolean mobileNumCheck(String mobile) {
        /**
         * 010 - 1234 - 5678
         * 010 _ 1234 _ 5678
         * 010 1234 5678
         *
         * 1. 형식의 통일화 : 숫자가 아닌 문자 전부 제거 -> 숫자로만 이루어지게!
         * 2. 패턴 생성 체크
         */

        mobile = mobile.replaceAll("\\D", ""); // 숫자 아닌 것 전부 제거
        String pattern = "^01[016]\\d{3,4}\\d{4}$";
        // 시작은 ^ , 끝은 $ , d는 숫자 , { 범위 } Ex ) {3,4}-> 3자리 이상, 4자리 이하

        return mobile.matches(pattern);
    }

}
