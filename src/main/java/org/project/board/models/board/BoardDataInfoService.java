package org.project.board.models.board;

import lombok.RequiredArgsConstructor;
import org.project.board.commons.MemberUtil;
import org.project.board.entities.BoardData;
import org.project.board.models.board.config.BoardConfigInfoService;
import org.project.board.repositories.BoardDataRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BoardDataInfoService {
    private final BoardDataRepository boardDataRepository;
    private final BoardConfigInfoService configInfoService;
    private final MemberUtil memberUtil;

    public BoardData get(Long id) { // 기본적인 게시글 조회
        return get(id, "view");
    }

    public BoardData get(Long id, String location) { // 다양한 권한 체크일 때
        BoardData boardData = boardDataRepository.findById(id).orElseThrow(BoardDataNotExistsException::new);
        // 게시글 데이터를 조회, 없으면 BoardDataNotExistException 발생

        // 게시판 설정 조회 + 접근 권한체크
        configInfoService.get(boardData.getBoard().getBId(), location);

        // 게시글 삭제 여부 체크(소프트 삭제)
        if (!memberUtil.isAdmin() && boardData.getDeletedAt() != null) { // 삭제된 게시글
            throw new BoardDataNotExistsException();
        }

        return boardData;
    }
}
