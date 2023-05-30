package org.project.board.models.board.config;

import com.querydsl.core.BooleanBuilder;
import lombok.RequiredArgsConstructor;
import org.project.board.controllers.admins.BoardSearch;
import org.project.board.entities.Board;
import org.project.board.entities.QBoard;
import org.project.board.repositories.BoardRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import static org.springframework.data.domain.Sort.Order.desc;

/**
 *  게시판 설정 목록 가져오기
 */

@Service
@RequiredArgsConstructor
public class BoardConfigListService {

    private final BoardRepository boardRepository;


    public Page<Board> gets(BoardSearch boardSearch) {
        QBoard board = QBoard.board;
        BooleanBuilder andBuilder = new BooleanBuilder();

        int page = boardSearch.getPage();
        int limit = boardSearch.getLimit();

        page = page < 1 ? 1 : page;
        limit = limit < 1 ? 20 : limit; // 1 미만이면 20개로 고정!

        /** 검색 조건 처리 S */
        String sopt = boardSearch.getSopt();
        String skey = boardSearch.getSkey();
        if (sopt != null && !sopt.isBlank() && skey != null && !skey.isBlank()) {
            sopt = sopt.trim(); // 공백 제거
            skey = skey.trim(); // 공백 제거

            if (sopt.equals("all")) { // 통합 검색 - bId, bName으로 검색
                BooleanBuilder orBuilder = new BooleanBuilder();
                orBuilder.or(board.bId.contains(skey))
                        .or(board.bName.contains(skey));
                andBuilder.and(orBuilder); // 둘 중에 하나만 있다면 나올 수 있도록 andBuilder로 묶어줌

            } else if (sopt.equals("bId")) { // 게시판 아이디 bId으로 검색
                andBuilder.and(board.bId.contains(skey));
            } else if (sopt.equals("bName")) { // 게시판명 bName으로 검색
                andBuilder.and(board.bName.contains(skey));
            }
        }
        /** 검색 조건 처리 E */

        Pageable pageable = PageRequest.of(page - 1, limit, Sort.by(desc("createdAt"))); // 최신 순 정렬
        Page<Board> data = boardRepository.findAll(andBuilder, pageable);

        return data;
    }
}
