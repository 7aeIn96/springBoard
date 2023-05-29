package org.project.board.models.board.config;

import lombok.RequiredArgsConstructor;
import org.project.board.commons.MemberUtil;
import org.project.board.commons.constants.Role;
import org.project.board.entities.Board;
import org.project.board.repositories.BoardRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BoardConfigInfoService {

    private final BoardRepository boardRepository;
    private final MemberUtil memberUtil;

    public Board get(String bId, String location) { // front, 접근 권한 체크
        return get(bId, false, location);
    }

    public Board get(String bId, boolean isAdmin) { // 관리자페이지, 접근 권한 체크 필요없음.
        return get(bId, isAdmin, null);
    }

    /**
     * 게시판 설정 조회
     * @param bId
     * @param isAdmin : true -> 권한 체크 X
     *                : false -> 권한 체크, location으로 목록, 보기, 글쓰기, 답글, 댓글
     * @param location : 기능 위치 ( list, view, write, reply, comment )
     * @return
     */
    public Board get(String bId, boolean isAdmin, String location) {

        // bId를 찾지 못하면 BoardConfigNotExistException 에외로 던지기
        Board board = boardRepository.findById(bId).orElseThrow(BoardConfigNotExistException::new);

        if (!isAdmin) { // 권한 체크
            accessCheck(board, location);
        }
        return board;
    }

    /**
     * 접근 권한 체크
     * @param board
     */
    private void accessCheck(Board board, String location) {
        /**
         * use - false : 모든 항목 접근 불가, 단 관리자만 가능
         */
        if (!board.isUse() && !memberUtil.isAdmin()) {
            throw new BoardNotAllowedAccessException();
        }

        Role role = Role.ALL;
        if (location.equals("list")) { // 목록 접근 권한
            role = board.getListAccessRole();
        } else if (location.equals("view")) { // 게시글 접근 권한
            role = board.getViewAccessRole();
        } else if (location.equals("write")) { // 게시글 작성 권한
            role = board.getWriteAccessRole();
            /** 비회원 게시글 여부 */
            if (!memberUtil.isLogin()) board.setGuest(true);
        } else if (location.equals("reply")) { // 답글 권한
            role = board.getReplyAccessRole();
        } else if (location.equals("comment")) { // 댓글 권한
            role = board.getCommentAccessRole();
        }

        if ((role == Role.USER && !memberUtil.isLogin()) || (role == Role.ADMIN && !memberUtil.isAdmin())) {
            throw new BoardNotAllowedAccessException();
        }

    }
}
