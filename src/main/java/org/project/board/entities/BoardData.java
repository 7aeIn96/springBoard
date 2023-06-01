package org.project.board.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity @Data
@Builder @NoArgsConstructor @AllArgsConstructor
@Table(indexes = {
        @Index(name = "idx_boarddata_category", columnList = "category DESC"), // 카테고리 최신 기준으로 정렬
        @Index(name = "idx_boarddata_createdAt", columnList = "createdAt DESC")
})
public class BoardData extends BaseEntity {

    @Id @GeneratedValue
    private Long id; // 게시글 번호

    // 게시판 하나 당 여러개의 게시글을 가짐.
    @ManyToOne(fetch = FetchType.LAZY) // 게시글 설정은 필요할때만 조회
    @JoinColumn(name="bId")
    private Board board; // 게시판 아이디

    @Column(length = 65, nullable = false)
    private String gid = UUID.randomUUID().toString(); // 그룹 아이디 ( 파일 전송시 필요 )

    @Column(length = 40, nullable = false)
    private String poster; // 작성자

    @Column(length = 65)
    private String guestPw; // 비회원 비밀번호

    @Column(length = 60)
    private String category; // 게시판 분류

    @Column(nullable = false) // 길이 지정 안하면 default 255
    private String subject; // 제목

    @Lob
    @Column(nullable = false)
    private String content; // 내용

    private int hit; // 조회수

    @Column(length = 125)
    private String ua; // User-Agent : 브라우저 정보

    @Column(length = 20)
    private String ip; // 작성자 IP 주소 ( 신고 기능 시 필요한 정보 )

    private int commentCnt; // 댓글 수

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="userNo")
    private Member member; // 작성 회원

}
