package com.fastcampus.projectboard.dto.response;

import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

import com.fastcampus.projectboard.dto.ArticleWithCommentsDto;

/**
 * DTO for {@link com.fastcampus.projectboard.domain.Article}
 */
public record ArticleWithCommentsResponse(Long id, String title, String content, String hashtag,
										  LocalDateTime createdAt, String email, String nickanme, Set<ArticleCommentResponse> articleCommentResponse)
{
	public static ArticleWithCommentsResponse of(Long id, String title, String content, String hashtag, LocalDateTime createdAt,
		String email, String nickanme, Set<ArticleCommentResponse> articleCommentResponses) {
		return new ArticleWithCommentsResponse(id, title, content, hashtag, createdAt, email, nickanme, articleCommentResponses);
	}


	public static ArticleWithCommentsResponse from(ArticleWithCommentsDto dto) {
		String nickname = dto.userAccountDto().nickname();
		if (nickname == null || nickname.isBlank()){
			nickname = dto.userAccountDto().userId();
		}
		return new ArticleWithCommentsResponse(
			dto.id(),
			dto.title(),
			dto.content(),
			dto.hashtag(),
			dto.createdAt(),
			dto.userAccountDto().email(),
			nickname,
			dto.articleCommentDtos().stream()
				.map(ArticleCommentResponse::from)
				.collect(Collectors.toCollection(LinkedHashSet::new))
		);
	}

}