package org.project.board.controllers.members;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Member DTO 클래스 ( 양식 데이터 )
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JoinForm {

    @NotBlank
    @Size(min = 6, max = 20)
    private String userId;

    @NotBlank
    @Size(min = 8)
    private String userPw;

    @NotBlank
    private String userPwRe;

    @NotBlank
    private String userNm;

    @NotBlank @Email
    private String email;

    private String mobile;

    private boolean[] agrees; // 약관 - 체크박스 ( 추가해야하니까 배열 )
}
