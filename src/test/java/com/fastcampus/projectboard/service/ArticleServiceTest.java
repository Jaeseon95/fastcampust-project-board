package com.fastcampus.projectboard.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import com.fastcampus.projectboard.domain.Article;
import com.fastcampus.projectboard.domain.UserAccount;
import com.fastcampus.projectboard.domain.type.SearchType;
import com.fastcampus.projectboard.dto.ArticleDto;
import com.fastcampus.projectboard.dto.ArticleUpdateDto;
import com.fastcampus.projectboard.dto.ArticleWithCommentsDto;
import com.fastcampus.projectboard.dto.UserAccountDto;
import com.fastcampus.projectboard.repository.ArticleRepository;

import jakarta.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.List;

import java.util.Optional;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@DisplayName("비즈니스 로직 - 게시글")
@ExtendWith(MockitoExtension.class)
class ArticleServiceTest {

// system under test
@InjectMocks
private ArticleService sut;
@Mock
private ArticleRepository articleRepository;
@DisplayName("검색어 없이 게시글을 검색하면, 게시글 페이지를 반환한다.")
@Test
void givenNoSearchParameter_whenSearchingArticles_thenReturnsArticleList(){
	// Given
	Pageable pageable = Pageable.ofSize(20);
	given(articleRepository.findAll(pageable)).willReturn(Page.empty());
	// When
	Page<ArticleDto> articles = sut.searchArticles(null, null, pageable);
	// Then
	assertThat(articles).isEmpty();
	then(articleRepository).should().findAll(pageable);
}

@DisplayName("게시글을 검색하면, 게시글 리스트를 반환한다.")
@Test
void givenSearchParameter_whenSearchingArticle_thenReturnsArticleList() {
	// Given
	Pageable pageable = Pageable.ofSize(20);
	SearchType searchType = SearchType.TITLE;
	String searchKeyword = "title";
	given(articleRepository.findByTitleContaining(searchKeyword, pageable)).willReturn(Page.empty());
	// When
	//List<ArticleDto> articles = sut.searchArticles(SearchType.TITLE, "search keyword"); // 제목, 본문, 해시태그, ID, 닉네임
	Page<ArticleDto> articles = sut.searchArticles(SearchType.TITLE, searchKeyword, pageable); // 제목, 본문, 해시태그, ID, 닉네임

	// Then
	assertThat(articles).isEmpty();
	then(articleRepository).should().findByTitleContaining(searchKeyword,pageable);
}

@DisplayName("게시글을 조회하면, 게시글을 반환한다.")
@Test
void givenArticleId_whenSearchingArticle_thenReturnsArticle() {
	// Given
	Long articleId = 1L;
	Article article = createArticle();
	given(articleRepository.findById(articleId)).willReturn(Optional.of(article));
	// When

	//	ArticleDto article = sut.searchArticle(1L);
	ArticleWithCommentsDto dto = sut.getArticle(articleId);

	// Then
	assertThat(dto)
		.hasFieldOrPropertyWithValue("title", article.getTitle())
		.hasFieldOrPropertyWithValue("content",article.getContent())
		.hasFieldOrPropertyWithValue("hashtag", article.getHashtag());
	then(articleRepository).should().findById(articleId);
}


@DisplayName("없는 게시물을 조회하면 예외를 던진다.")
@Test
void givenNoneExistingArticleId_whenSearchingArticle_thenThrowException(){
	Long articleId = 0L;
	given(articleRepository.findById(articleId)).willReturn(Optional.empty());

	Throwable t = catchThrowable(() -> sut.getArticle(articleId));

	// Then
	assertThat(t)
		.isInstanceOf(EntityNotFoundException.class)
		.hasMessage("게시글이 없습니다. -> articleId: " + articleId);
	then(articleRepository).should().findById(articleId);
}


@DisplayName("게시글 정보를 입력하면, 게시글을 생성한다.")
@Test
void givenArticleInfo_whenSavingArticle_thenSaveArticle() {
	ArticleDto dto = createArticleDto();
	//Given
	given(articleRepository.save(any(Article.class))).willReturn(createArticle());

	//When
	sut.saveArticle(dto);
//		sut.saveArticle(ArticleDto.of(LocalDateTime.now(), "hadu", "title", "content", "#spring"));

	//Then
	then(articleRepository).should().save(any(Article.class));
}


@DisplayName("게시글 ID와 수정 정보를 입력하면, 게시글을 수정한다.")
@Test
void givenArticleIdAndModifiedInfo_whenUpdatingArticle_thenUpdatesArticle() {
	Article article = createArticle();
	ArticleDto dto = createArticleDto("새 타이틀", "새 내용");
	//Given
	given(articleRepository.getReferenceById(dto.id())).willReturn(article);
	//When
	sut.updateArticle(dto);

	//Then
	assertThat(article)
		.hasFieldOrPropertyWithValue("title", dto.title())
		.hasFieldOrPropertyWithValue("content", dto.content())
		.hasFieldOrPropertyWithValue("hashtag", dto.hashtag());
	then(articleRepository).should().getReferenceById(dto.id());
}


@DisplayName("없는 게시물의 수정 정보를 입력하면 경고로그를 찍고 아무것도 하지 않는다.")
@Test
void givenNoneExistArticleIdAndModifiedInfo_whenUpdatingArticle_thenLogsWarningAndDoNothing() {
	ArticleDto dto = createArticleDto("새 타이틀", "새 내용");
	given(articleRepository.getReferenceById(dto.id())).willThrow(EntityNotFoundException.class);

	// When
	sut.updateArticle(dto);

	// Then
	then(articleRepository).should().getReferenceById(dto.id());
}

@Disabled("개발 중 ")
@DisplayName("게시글 ID를 입력하면, 게시글을 삭제한다.")
@Test
void givenArticleId_whenDeletingArticle_thenDeleteArticle() {
	//Given
	Long articleId = 1L;

//	willDoNothing().given(articleRepository.deleteById(articleId)).;

	//When
	sut.deleteArticle(articleId);

	//Then
	then(articleRepository).should().deleteById(articleId);
}


private UserAccount createUserAccount() {
	return UserAccount.of(
		"test",
		"test",
		"test@gmail.com",
		"testNick",
		null);
}

private UserAccountDto createUserAccountDto() {
	return UserAccountDto.from(createUserAccount());
}

private Article createArticle() {
	return Article.of(
		createUserAccount(),
		"title",
		"content",
		"#spring"
	);
}

private ArticleDto createArticleDto() {
	return ArticleDto.from(createArticle());
}

private ArticleDto createArticleDto(String title, String content) {
	return ArticleDto.of(
		1L,
		createUserAccountDto(),
		title,
		content,
		null,
		LocalDateTime.now(),
		"hadu",
		LocalDateTime.now(),
		"hadu");
}
}