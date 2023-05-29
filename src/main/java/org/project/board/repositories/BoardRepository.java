package org.project.board.repositories;

import org.project.board.entities.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

/**
 * 게시판 관리자 레포지토리 ( BOARD 엔티티 )
 */
public interface BoardRepository extends JpaRepository<Board, String>, QuerydslPredicateExecutor<Board> {
}
