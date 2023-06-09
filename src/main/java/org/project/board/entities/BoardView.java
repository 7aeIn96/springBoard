package org.project.board.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import lombok.Data;

/**
 * 조회수 엔티티
 */
@Entity @Data
@IdClass(BoardView.class)
public class BoardView {

    @Id
    private Long id; // 게시글 번호

    @Id
    @Column(length = 40, name = "uid_")
    private String uid; // IP + UA + 회원 번호

}
