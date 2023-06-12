package org.project.board.controllers.boards;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Builder;

import java.util.UUID;

/**
 * BoardData 커맨드객체
 */
@Data
@Builder @NoArgsConstructor @AllArgsConstructor
public class BoardForm {
    private Long id; // 게시글 번호

    @NotBlank
    private String bId;

    @NotBlank
    private String gid = UUID.randomUUID().toString(); // 그룹 아이디 ( 파일 전송시 필요 ) : 없으면 랜덤으로 자동 생성

    @NotBlank
    private String poster; // 작성자

    private String guestPw; // 비회원 비밀번호

    private String category; // 게시판 분류

    @NotBlank
    private String subject; // 제목

    @NotBlank
    private String content; // 내용

    private Long userNo; // 회원번호
}
