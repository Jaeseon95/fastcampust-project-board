package com.fastcampus.projectboard.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import com.fastcampus.projectboard.domain.ArticleComment;
import com.fastcampus.projectboard.domain.UserAccount;
import com.fastcampus.projectboard.repository.UserAccountRepository;
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
	@Mock
	private UserAccountRepository userAccountRepository;

	@DisplayName("게시글 ID로 조회하면, 해당 게시글의 댓글 목록을 가져온다.")
	@Test
	void  givenArticleId_whenSearchingArticleComments_thenReturnsArticleComment(){
		//Given
		Long articleId = 1L;
		ArticleComment expected = createArticleComment("content");
		given(articleCommentRepository.findById(articleId)).willReturn(List.of(expected));

		//When
		List<ArticleCommentDto> actual = sut.searchArticleComment(1L);

		//Then
		assertThat(actual).hasSize(1)
			.first().hasFieldOrPropertyWithValue("content", expected.getContent());
		//then(articleRepository).should().findByA

	}

	private ArticleComment createArticleComment(String content) {
		return ArticleComment.of(createUserAccount(),
			Article.of(createUserAccount(),"title","content", "#hastag"),
			content
		);
	}

	private UserAccount createUserAccount() {
		return UserAccount.of("test", "test", "test@gmail.com", "test", null);
	}

}