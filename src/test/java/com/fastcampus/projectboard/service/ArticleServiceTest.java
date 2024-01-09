package com.fastcampus.projectboard.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import com.fastcampus.projectboard.domain.Article;
import com.fastcampus.projectboard.domain.type.SearchType;
import com.fastcampus.projectboard.dto.ArticleDto;
import com.fastcampus.projectboard.dto.ArticleUpdateDto;
import com.fastcampus.projectboard.repository.ArticleRepository;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;

@DisplayName("비즈니스 로직 - 게시글")
@ExtendWith(MockitoExtension.class)
class ArticleServiceTest {

	// system under test
	@InjectMocks
	private ArticleService sut;
	@Mock
	private ArticleRepository articleRepository;

	@DisplayName("게시글을 검색하면, 게시글 리스트를 반환한다.")
	@Test
	void givenSearchParameter_whenSearchingArticle_thenReturnsArticleList() {
		// Given
		// When
		//List<ArticleDto> articles = sut.searchArticles(SearchType.TITLE, "search keyword"); // 제목, 본문, 해시태그, ID, 닉네임
		Page<ArticleDto> articles = sut.searchArticles(SearchType.TITLE, "search keyword"); // 제목, 본문, 해시태그, ID, 닉네임

		// Then
		assertThat(articles).isNotNull();
	}

	@DisplayName("게시글을 조회하면, 게시글을 반환한다.")
	@Test
	void givenArticleId_whenSearchingArticle_thenReturnsArticle() {
		// Given

		// When
		ArticleDto article = sut.searchArticle(1L);

		// Then
		assertThat(article).isNotNull();
	}

	@DisplayName("게시글 정보를 입력하면, 게시글을 생성한다.")
	@Test
	void givenArticleInfo_whenSavingArticle_thenSaveArticle() {
		//Given
		given(articleRepository.save(any(Article.class))).willReturn(null);

		//When
		sut.saveArticle(ArticleDto.of(LocalDateTime.now(), "hadu", "title", "content", "#spring"));

		//Then
		then(articleRepository).should().save(any(Article.class));
	}

	@DisplayName("게시글 ID와 수정 정보를 입력하면, 게시글을 수정한다.")
	@Test
	void givenArticleIdAndModifiedInfo_whenUpdatingArticle_thenUpdatesArticle() {
		//Given
		given(articleRepository.save(any(Article.class))).willReturn(null);

		//When
		sut.updateArticle(1L, ArticleUpdateDto.of("title", "content", "#spring"));

		//Then
		then(articleRepository).should().save(any(Article.class));
	}

	@DisplayName("게시글 ID를 입력하면, 게시글을 삭제한다.")
	@Test
	void givenArticleId_whenDeletingArticle_thenDeleteArticle() {
		//Given

		willDoNothing().given(articleRepository).delete(any(Article.class));

		//When
		sut.deleteArticle(1L);

		//Then
		then(articleRepository).should().delete(any(Article.class));
	}
}