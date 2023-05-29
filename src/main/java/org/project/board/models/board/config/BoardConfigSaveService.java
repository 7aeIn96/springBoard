package org.project.board.models.board.config;

import lombok.RequiredArgsConstructor;
import org.project.board.commons.constants.Role;
import org.project.board.controllers.admins.BoardForm;
import org.project.board.entities.Board;
import org.project.board.repositories.BoardRepository;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;

/**
 * 게시판 설정 추가, 수정 ( ADMIN, BOARD )
 */
@Service
@RequiredArgsConstructor
public class BoardConfigSaveService {

    private final BoardRepository boardRepository;

    /**
     * 테스트시 한 개씩 제거하면서 확인하기 위해서 사용!
     * @param boardForm
     */
    public void save(BoardForm boardForm) {
        save(boardForm, null);
    }

    public void save(BoardForm boardForm, Errors errors) { // Errors는 Bean Validation에서 검증( 필수 항목 등등 )하는 Error

        /**
         * 에러가 있으면 하단 코드 실행하지 않고, 없으면 save() 실행!
         * 테스트를 위해 errors != null 조건도 추가
         */
        if (errors != null && errors.hasErrors()) {
            return;
        }
        /**
         *  게시판 설정 있으면 조회 -> 없으면 엔티티 생성
         *  게시판 등록 모드인 경우 중복 여부 체크!
         */

        String bId = boardForm.getBId();
        Board board = boardRepository.findById(bId).orElseGet(Board::new); // 있으면 bId 조회, 없으면 생성!
        String mode = boardForm.getMode();
        // 게시판 등록시, 중복이면서 mode가 update가 아니면 Exception 발생
        if ((mode == null || !mode.equals("update")) && board.getBId() != null) {
            throw new DuplicateBoardConfigException();
        }

        board.setBId(bId);
        board.setBName(boardForm.getBName());
        board.setUse(boardForm.isUse());
        board.setRowsOfPage(boardForm.getRowsOfPage());
        board.setShowViewList(boardForm.isShowViewList());
        board.setCategory(boardForm.getCategory());
        board.setListAccessRole(Role.valueOf(boardForm.getListAccessRole()));
        board.setViewAccessRole(Role.valueOf(boardForm.getViewAccessRole()));
        board.setWriteAccessRole(Role.valueOf(boardForm.getWriteAccessRole()));
        board.setReplyAccessRole(Role.valueOf(boardForm.getReplyAccessRole()));
        board.setCommentAccessRole(Role.valueOf(boardForm.getCommentAccessRole()));
        board.setUseEditor(boardForm.isUseEditor());
        board.setUseAttachFile(boardForm.isUseAttachFile());
        board.setUseAttachImage(boardForm.isUseAttachImage());
        board.setLocationAfterWriting(boardForm.getLocationAfterWriting());
        board.setUseReply(boardForm.isUseReply());
        board.setUseComment(boardForm.isUseComment());
        board.setSkin(boardForm.getSkin());

        boardRepository.saveAndFlush(board);

    }

}
