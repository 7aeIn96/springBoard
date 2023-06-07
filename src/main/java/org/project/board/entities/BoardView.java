package org.project.board.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.IdClass;
import lombok.Data;
import org.springframework.data.annotation.Id;

/**
 * 조회수 엔티티
 */
@Entity @Data
@IdClass(BoardViewId.class)
public class BoardView {

    @Id
    private Long id; // 게시글 번호

    @Id
    @Column(length = 40)
    private String uid; // IP + UA + 회원 번호

}
