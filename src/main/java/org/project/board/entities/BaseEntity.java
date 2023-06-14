package org.project.board.entities;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@Setter
@MappedSuperclass // 상위 클래스 공통 속성 정의
@EntityListeners(AuditingEntityListener.class) // 날짜 관련 사용하기 위해서 필요!
public abstract class BaseEntity {

    @CreatedDate
    @Column(updatable = false) // 수정때는 추가 X
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(insertable = false) // 수정시에만 추가
    private LocalDateTime modifiedAt;

    @Column(insertable = false)
    private LocalDateTime deletedAt; // 삭제 일시
}
