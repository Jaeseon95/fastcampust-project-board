package com.fastcampus.projectboard.domain;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@EntityListeners(AuditingEntityListener.class)
@MappedSuperclass
// 추상클래스로 선언한 이유는 Auditing Fields단독으로 사용되지 않기 때문
public abstract class AuditingFields {
	// jpa auditing
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
	@CreatedDate
	@Column(nullable = false, updatable = false)
	private LocalDateTime createdAt; // 생성일자
	@CreatedBy
	@Column(nullable = false, length = 100, updatable = false)
	private String createdBy; // 생성자
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
	@LastModifiedDate
	@Column(nullable = false)
	private LocalDateTime modifiedAt; // 수정일자
	@LastModifiedBy
	@Column(nullable = false, length = 100)
	private String modifiedBy; // 수정자
}
