package org.project.board.entities;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/**
 * 회원 생성, 수정 날짜 최상위 엔티티 클래스
 * Auditing 기능 사용시 필요 클래스
 * 날짜와 시간 외에 사용자 까지도 볼 수 있도록
 * JPA에서 인식 가능 할 수 있도록 해줌
 */
@Getter @Setter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseMemberEntity extends BaseEntity {

    @CreatedBy
    @Column(length = 40, updatable = false)
    private String createdBy;

    @LastModifiedBy
    @Column(length = 40, insertable = false)
    private String modifiedBy;
}
