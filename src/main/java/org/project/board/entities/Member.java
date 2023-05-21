package org.project.board.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.project.board.commons.constants.Role;

@Entity @Data
@Builder @NoArgsConstructor @AllArgsConstructor
@Table(indexes = { // 많이 조회할 컬럼 ( Id값은 필요없고 최대 2개 정도만 정의)
        @Index(name="idx_member_userNm", columnList = "userNm"),
        @Index(name="idx_member_email", columnList = "email")
})
public class Member extends BaseEntity {

    @Id @GeneratedValue
    private Long userNo; // 회원 번호

    @Column(length = 40, nullable = false, unique = true) // 유일한 값
    private String userId; // 아이디

    @Column(length = 65, nullable = false)
    private String userPw; // 비밀번호

    @Column(length = 40, nullable = false)
    private String userNm; // 회원명

    @Column(length = 100, nullable = false)
    private String email; // 이메일 - 비밀번호 분실 시 url 전송 후 초기화 위해 사용 ( 토큰 사용 예정 )

    @Column(length = 11)
    private String mobile; // 휴대전화번호

    @Lob // 관리자페이지에서 추가할 수 있도록!
    private String termsAgree; // 약관 동의 내역 ( JSON 형태로 출력 )

    @Enumerated(EnumType.STRING) // Enum 클래스 사용시 필수 ( 상수 -> String 값으로 받아오기 )
    @Column(length = 10, nullable = false)
    private Role roles = Role.USER;
}
