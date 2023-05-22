package org.project.board.models.member;

import lombok.RequiredArgsConstructor;
import org.project.board.entities.Member;
import org.project.board.repositories.MemberRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberInfoService implements UserDetailsService {

    private final MemberRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException { // user 찾고 없을때 예외
        // 매번 세션에서 ID 값이 넘어옴

        Member member = repository.findByUserId(username);

        if (member == null) {
            throw new UsernameNotFoundException(username);
        }

        // 회원 권한 수정
        List<GrantedAuthority> authorities = Arrays.asList(new SimpleGrantedAuthority(member.getRoles().toString()));

        // UserDetails 구현 클래스 build
        return MemberInfo.builder()
                .userNo(member.getUserNo())
                .userId(member.getUserId())
                .userPw(member.getUserPw())
                .userNm(member.getUserNm())
                .email(member.getEmail())
                .mobile(member.getMobile())
                .roles(member.getRoles())
                .authorities(authorities)
                .build();
    }
}