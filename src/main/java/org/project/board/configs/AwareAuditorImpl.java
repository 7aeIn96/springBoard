package org.project.board.configs;

import lombok.RequiredArgsConstructor;
import org.project.board.commons.MemberUtil;
import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class AwareAuditorImpl implements AuditorAware<String> { // <String> -> 아이디로 사용
    private final MemberUtil memberUtil;

    /**
     * 로그인한 상태이면 userId를 가져오고 , 아니면 null 값
     * @return
     */
    @Override
    public Optional<String> getCurrentAuditor() {
        String userId = memberUtil.isLogin() ? memberUtil.getMember().getUserId() : null;

        return Optional.ofNullable(userId);
    }
}