package org.project.board.commons.rests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data @Builder
@NoArgsConstructor @AllArgsConstructor
public class JSONData<T> {

    private boolean success; // 성공여부

    private HttpStatus status = HttpStatus.OK; // 200코드 성공

    private String message; // 예외메세지

    private T data; // 성공했을 때 데이터, 형태가 다양해 지네릭형태로 설정

}
