package org.project.board.entities;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@EqualsAndHashCode // 동등성
@NoArgsConstructor
@AllArgsConstructor
public class BoardViewId implements Serializable {
    private Long id;
    private String uid;
}