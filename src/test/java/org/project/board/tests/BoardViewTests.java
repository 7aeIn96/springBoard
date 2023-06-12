package org.project.board.tests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.project.board.commons.constants.Role;
import org.project.board.entities.Board;
import org.project.board.controllers.boards.BoardForm;
import org.project.board.models.board.BoardDataInfoService;
import org.project.board.models.board.BoardDataNotExistsException;
import org.project.board.models.board.BoardDataSaveService;
import org.project.board.models.board.config.BoardConfigInfoService;
import org.project.board.models.board.config.BoardConfigSaveService;
import org.project.board.models.board.config.BoardNotAllowAccessException;
import org.project.board.repositories.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
@AutoConfigureMockMvc
@Transactional
public class BoardViewTests {
    private Board board;
    private Long id; // 게시글 번호

    @Autowired
    private BoardRepository boardRepository;
    @Autowired
    private BoardDataSaveService saveService;
    @Autowired
    private BoardConfigInfoService configInfoService;
    @Autowired
    private BoardConfigSaveService configSaveService; // 게시판 추가
    @Autowired
    private BoardDataInfoService infoService;

    private BoardForm boardForm2;
    private String bId = "freetalk";

    private Board getBoard() {
        Board board = configInfoService.get(bId, true);
        return board;
    }

    @BeforeEach
    void init() {
        // 게시글 설정 추가
        org.project.board.controllers.admins.BoardForm boardForm = new org.project.board.controllers.admins.BoardForm();
        boardForm.setBId(bId);
        boardForm.setBName("자유게시판");
        boardForm.setUse(true);
        configSaveService.save(boardForm);
        board = getBoard();

        // 테스트용 기본 게시글 추가
        boardForm2 = BoardForm.builder()
                .bId(board.getBId())
                .gid(UUID.randomUUID().toString())
                .poster("작성자!")
                .guestPw("12345678")
                .subject("제목!")
                .content("내용!")
                .category(board.getCategories() == null ? null : board.getCategories()[0])
                .build();

        saveService.save(boardForm2);

        id = boardForm2.getId(); // 아이디로 테스트
    }

    @Test
    @DisplayName("게시글 조회 성공시 예외 없음")
    void getBoardDataSuccessTest() {
        assertDoesNotThrow(()-> {
            infoService.get(id);
        });
    }

    @Test
    @DisplayName("등록되지 않은 게시글이면 BoardDataNotExistException 발생")
    void getBoardDataNotExistsTest() {
        assertThrows(BoardDataNotExistsException.class , () -> {
            infoService.get(id + 10);
        });
    }

    @Test
    @DisplayName("게시판 사용 여부(use)가 false 접근 불가 - BoardNotAllowAccessException")
    void accessAuthCheck1Test() {
        assertThrows(BoardNotAllowAccessException.class, () -> {
            Board board = getBoard();
            board.setUse(false);
            boardRepository.saveAndFlush(board);

            infoService.get(id);
        });
    }

    @Test
    @DisplayName("회원 전용 글보기 권한 - 비회원 접속시 BoardNotAllowAccessException")
    void accessAuthCheck2Test() {

        assertThrows(BoardNotAllowAccessException.class, () -> {
            Board board = getBoard();
            board.setUse(true);
            board.setViewAccessRole(Role.USER);
            boardRepository.saveAndFlush(board);

            infoService.get(id);
        });
    }

}
