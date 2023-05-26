package org.project.board.entities;

import jakarta.persistence.*;
import lombok.Data;

/**
 * 사이트 설정이 많이 추가됨을 고려해서 key, value 값으로 편리하게 관리
 */
@Entity
@Data
public class Configs {

    @Id
    @Column(name = "code_", length = 45)
    private String code;

    @Lob
    @Column(name="value_")
    private String value;
}
