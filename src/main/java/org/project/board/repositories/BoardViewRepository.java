package org.project.board.repositories;

import org.project.board.entities.BoardView;
import org.project.board.entities.BoardViewId;
import org.project.board.entities.QBoardView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface BoardViewRepository extends JpaRepository<BoardView, BoardViewId>, QuerydslPredicateExecutor<BoardView> {

    /**
     * 게시글별 조회수 조회
     * @return
     */
    default long getHit(Long id) {
        QBoardView boardView = QBoardView.boardView;

        return count(boardView.id.eq(id));
    }

}