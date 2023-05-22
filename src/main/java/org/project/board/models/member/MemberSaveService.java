package org.project.board.models.member;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.project.board.commons.constants.Role;
import org.project.board.controllers.members.JoinForm;
import org.project.board.entities.Member;
import org.project.board.repositories.MemberRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * 회원 정보 추가, 수정 -> 저장 서비스
 * - 수정시 비밀번호는 값이 있을때만 수정
 */
@Service
@RequiredArgsConstructor
public class MemberSaveService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder; // BCrypt로 비밀번호 해시처리

    public void save(JoinForm joinForm) {
        Member member = new ModelMapper().map(joinForm, Member.class); // Dto를 엔티티에 매핑
        member.setRoles(Role.USER); // 기본권한은 User
        member.setUserPw(passwordEncoder.encode(joinForm.getUserPw())); // 입력받은 비밀번호 해시처리

        memberRepository.saveAndFlush(member); // 회원가입처리, 레포지토리에 저장!
    }
}
