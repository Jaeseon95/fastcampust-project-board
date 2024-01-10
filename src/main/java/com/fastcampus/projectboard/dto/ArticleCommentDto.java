package com.fastcampus.projectboard.dto;

import com.fastcampus.projectboard.domain.ArticleComment;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * DTO for {@link com.fastcampus.projectboard.domain.ArticleComment}
 */
public record ArticleCommentDto(Long id, UserAccountDto userAccountDto, ArticleDto articleDto, String content,
								LocalDateTime createdAt, String createdBy, LocalDateTime modifiedAt, String modifiedBy) {
	public static ArticleCommentDto of(Long id, UserAccountDto userAccountDto, ArticleDto articleDto, String content, LocalDateTime createdAt, String createdBy, LocalDateTime modifiedAt, String modifiedBy) {
		return new ArticleCommentDto(id, userAccountDto, articleDto, content, createdAt, createdBy, modifiedAt, modifiedBy);
	}

	public static ArticleCommentDto from(ArticleComment entity) {
		return new ArticleCommentDto(
			entity.getId(),
			UserAccountDto.from(entity.getUserAccount()),
			ArticleDto.from(entity.getArticle()),
			entity.getContent(),
			entity.getCreatedAt(),
			entity.getCreatedBy(),
			entity.getModifiedAt(),
			entity.getModifiedBy()
		);
	}

	public ArticleComment toEntity(){
		return  ArticleComment.of(
			userAccountDto.toEntity(),
			articleDto.toEntity(),
			content
		);
	}
}