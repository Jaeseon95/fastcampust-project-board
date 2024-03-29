package com.fastcampus.projectboard.dto;

import com.fastcampus.projectboard.domain.Article;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * DTO for {@link com.fastcampus.projectboard.domain.Article}
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public record ArticleWithCommentsDto(Long id, UserAccountDto userAccountDto,String title, String content, String hashtag, Set<ArticleCommentDto> articleCommentDtos,
                          LocalDateTime createdAt, String createdBy, LocalDateTime modifiedAt, String modifiedBy ) {

    public static ArticleWithCommentsDto of(Long id, UserAccountDto userAccountDto, String title, String content, String hashtag, Set<ArticleCommentDto> articleCommentDtos, LocalDateTime createdAt, String createdBy,
        LocalDateTime modifiedAt, String modifiedBy) {
        return new ArticleWithCommentsDto(id, userAccountDto, title, content, hashtag, articleCommentDtos, createdAt, createdBy, modifiedAt, modifiedBy);
    }

    public static ArticleWithCommentsDto from(Article entity) {
        return ArticleWithCommentsDto.of(
            entity.getId(),
            UserAccountDto.from(entity.getUserAccount()),
            entity.getTitle(),
            entity.getContent(),
            entity.getHashtag(),
            entity.getArticleComments().stream()
                .map(ArticleCommentDto::from)
                .collect(Collectors.toCollection(LinkedHashSet::new)),
            entity.getCreatedAt(),
            entity.getCreatedBy(),
            entity.getModifiedAt(),
            entity.getModifiedBy()
        );
    }

}