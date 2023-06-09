package org.project.board.models.board;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.project.board.commons.MemberUtil;
import org.project.board.entities.BoardData;
import org.project.board.entities.BoardView;
import org.project.board.repositories.BoardDataRepository;
import org.project.board.repositories.BoardViewRepository;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * 조회수 업데이트
 */

@Service
@RequiredArgsConstructor
public class UpdateHitService {

    private final BoardViewRepository boardViewRepository;
    private final BoardDataRepository boardDataRepository;
    private final HttpServletRequest request;
    private final MemberUtil memberUtil;

    public void update(Long id) {
        try {
            BoardView boardView = new BoardView();
            boardView.setId(id);
            boardView.setUid("" + getUid()); // int라서 ""
            boardViewRepository.saveAndFlush(boardView);
        } catch (Exception e) {}

        long cnt = boardViewRepository.getHit(id);
        BoardData boardData = boardDataRepository.findById(id).orElse(null);
        if (boardData != null) {
            boardData.setHit((int) cnt);
            boardDataRepository.flush();
        }
    }

    private int getUid() {
        String ip = request.getRemoteAddr();
        String ua = request.getHeader("User-Agent");

        // 로그인 했을 때는 회원번호로 uid 생성, 미로그인 상태는 hash ( ip + ua )
        return memberUtil.isLogin() ? memberUtil.getMember().getUserNo().intValue() : Objects.hash(ip, ua);
    }
}