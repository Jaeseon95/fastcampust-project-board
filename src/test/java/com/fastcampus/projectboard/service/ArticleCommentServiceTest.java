package com.fastcampus.projectboard.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.fastcampus.projectboard.domain.Article;
import com.fastcampus.projectboard.dto.ArticleCommentDto;
import com.fastcampus.projectboard.repository.ArticleCommentRepository;
import com.fastcampus.projectboard.repository.ArticleRepository;

@DisplayName("비즈니스 로직 - 댓글")
@ExtendWith(MockitoExtension.class)
class ArticleCommentServiceTest {

	@InjectMocks
	private ArticleCommentService sut;
	@Mock
	private ArticleRepository articleRepository;
	@Mock
	private ArticleCommentRepository articleCommentRepository;

	@DisplayName("게시글 ID로 조회하면, 해당 게시글의 댓글 목록을 가져온다.")
	@Test
	void  givenArticleId_whenSearchingArticleComments_thenReturnsArticleComment(){
		//Given
		Long articleId = 1L;
		given(articleRepository.findById(articleId)).willReturn(Optional.of(
			Article.of("title", "content", "#spring")
		));

		//When
		List<ArticleCommentDto> articleComments = sut.searchArticleComment(1L);

		//Then
		assertThat(articleComments).isNotNull();
		then(articleRepository).should().findById(articleId);
	}

}