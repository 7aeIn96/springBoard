package org.project.board.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.project.board.commons.constants.Role;

@Entity @Data
@Builder @NoArgsConstructor @AllArgsConstructor
public class Board extends BaseMemberEntity { // 관리자 게시판 관련 엔티티
    @Id // NOT NULL + UNIQUE = PK
    @Column(length = 30)
    private String bId; // 게시판 ID

    @Column(length = 60, nullable = false)
    private String bName; // 게시판명

    @Column(name="isUse")
    private boolean use; // 게시판 사용 여부

    private int rowsOfPage = 20; // 한 페이지당 게시글 수 ( 기본 = 20개 )

    private boolean showViewList; // 게시글 하단 목록 노출

    @Lob
    private String category; // 게시판 분류 ( 줄개행으로 여러개 입력 가능 )

    @Enumerated(EnumType.STRING) // ENUM 타입을 String으로 받아오기
    @Column(length = 10, nullable = false)
    private Role listAccessRole = Role.ALL; // 목록 접근 권한 ( 기본 = 전체 사용자 )

    @Enumerated(EnumType.STRING) // ENUM 타입을 String으로 받아오기
    @Column(length = 10, nullable = false)
    private Role viewAccessRole = Role.ALL; // 글보기 접근 권한 ( 기본 = 전체 사용자 )

    @Enumerated(EnumType.STRING) // ENUM 타입을 String으로 받아오기
    @Column(length = 10, nullable = false)
    private Role writeAccessRole = Role.ALL; // 글쓰기 접근 권한 ( 기본 = 전체 사용자 )

    @Enumerated(EnumType.STRING) // ENUM 타입을 String으로 받아오기
    @Column(length = 10, nullable = false)
    private Role replyAccessRole = Role.ALL; // 답글 접근 권한 ( 기본 = 전체 사용자 )

    @Enumerated(EnumType.STRING) // ENUM 타입을 String으로 받아오기
    @Column(length = 10, nullable = false)
    private Role commentAccessRole = Role.ALL; // 댓글 접근 권한 ( 기본 = 전체 사용자 )

    private boolean useEditor; // 에디터 사용 여부

    private boolean useAttachFile; // 파일 첨부 사용 여부

    private boolean useAttachImage; // 이미지 첨부 사용 여부

    @Column(length = 10, nullable = false)
    private String locationAfterWriting = "view"; // 글 작성 후 이동할 페이지 ( 기본 = 게시글 보기)

    private boolean useReply; // 답글 사용 여부

    private boolean useComment; // 댓글 사용 여부

    @Transient
    private boolean isGuest; // 비회원 작성, 수정 모드 여부

    @Column(length = 20, nullable = false)
    private String skin = "default"; // 게시판 스킨

    /**
     * 게시판 분류 목록 ( category )
     * @return
     */
    public String[] getCategories() {

        if (category == null) {
            return null;
        }

        // trim으로 공백 제거, 리눅스 사용 시 \\r 제거 후 \\n 기준으로 나눔
        String[] categories = category.replaceAll("\\r","").trim().split("\\n");
        return categories;
    }

}
