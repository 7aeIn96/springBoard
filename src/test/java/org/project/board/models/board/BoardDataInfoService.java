package org.project.board.models.board;

import lombok.RequiredArgsConstructor;
import org.project.board.entities.BoardData;
import org.project.board.models.board.config.BoardConfigInfoService;
import org.project.board.repositories.BoardDataRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BoardDataInfoService {

    private final BoardDataRepository boardDataRepository;
    private final BoardConfigInfoService configInfoService;

    public BoardData get(Long id) {
        BoardData boardData = boardDataRepository.findById(id).orElseThrow(BoardDataNotExistException::new);
        // 게시글 데이터를 조회, 없으면 BoardDataNotExistException 발생

        // 게시판 설정 조회 + 접근 권한체크
        configInfoService.get(boardData.getBoard().getBId(), "view");

        return boardData;
    }
}
