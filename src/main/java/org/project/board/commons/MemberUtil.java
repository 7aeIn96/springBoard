package org.project.board.commons;

import jakarta.servlet.http.HttpSession;
import org.modelmapper.ModelMapper;
import org.project.board.commons.constants.Role;
import org.project.board.entities.Member;
import org.project.board.models.member.MemberInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *  세션에 있는 정보를 가져와서 회원 정보, 관리자인지 체크
 *  Principal 대신 사용
 */

@Component
public class MemberUtil {

    @Autowired
    private HttpSession session;

    /**
     * 로그인 여부
     * @return
     */
    public boolean isLogin() {
        return getMember() != null; // null이면 로그인 상태
    }

    /**
     * 관리자 여부
     * @return
     */
    public boolean isAdmin() { // 로그인 상태 + Role이 ADMIN일 때 관리자
        return isLogin() && getMember().getRoles() == Role.ADMIN;
    }

    /**
     * 회원 조회 ( MemberInfo 사용 )
     * @return
     */
    public MemberInfo getMember() {
        MemberInfo memberInfo = (MemberInfo) session.getAttribute("memberInfo");
        // LoginSuccessHandler에서 session.setAttribute로 저장한 값

        return memberInfo;
    }

    /**
     * 로그인한 상태일 때 MemberInfo(UserDetails 구현 클래스) 엔티티로 변환
     * @return
     */
    public Member getEntity() {

        if(isLogin()) {
            Member member = new ModelMapper().map(getMember(), Member.class);
            return member;
        }

        return null;
    }
}