package org.project.board.commons.validators;

public interface LengthValidator {

    /**
     * 문자열 체크
     * @param str
     * @param min : 최소 문자 갯수 -> 0일때는 체크 X
     * @param max : 최대 문자 갯수 -> 0일때는 체크 X
     */
    default void lengthCheck(String str, int min, int max, RuntimeException e) { // 최소 최대 범위 내에서 체크
        if (str == null || (min > 0 && str.length() < min) || (max > 0 && str.length() > max)) {
            throw e;
        }
    }

    default void lengthCheck(String str, int min, RuntimeException e) { // ~이상인 경우
        lengthCheck(str, min, 0, e);
    }

    /**
     * 숫자 범위 체크
     * @param num
     * @param min
     * @param max
     * @param e
     */
    default void lengthCheck(long num, int min, int max, RuntimeException e) {
        if (num < min || num > max) {
            throw e;
        }
    }

    default void lengthCheck(long num, int min, RuntimeException e) {
        if (num < min) {
            throw e;
        }
    }
}
