package com.fastcampus.projectboard.repository;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import com.fastcampus.projectboard.config.JpaConfig;
import com.fastcampus.projectboard.domain.Article;

// @ActiveProfiles("testdb")
// 자동으로 test용 db를 띄우는데 그것을 막기위해 자동으로 띄우는 것을 막아야함
// @AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
// slice Test
@DisplayName("JPA 연결 테스트")
// config 파일의 존재 auditing을 모르기 때문에 넣어줘야함
@Import(JpaConfig.class)
// 자동으로 transactional
@DataJpaTest
class JpaRepositoryTest {
	private final ArticleRepository articleRepository;
	private final ArticleCommentRepository articleCommentRepository;

	public JpaRepositoryTest(
		@Autowired ArticleRepository articleRepository,
		@Autowired ArticleCommentRepository articleCommentRepository
	) {
		this.articleRepository = articleRepository;
		this.articleCommentRepository = articleCommentRepository;
	}

	@DisplayName("select 테스트")
	@Test
	void givenTestData_whenSelecting_thenWorksFine() {
		//Given
		//When
		List<Article> articles = articleRepository.findAll();

		//Then
		assertThat(articles)
			.isNotNull()
			.hasSize(123);
	}

	@DisplayName("insert 테스트")
	@Test
	void ginvenTestData_whenInserting_thenWorksFine() {
		//Given
		long previousCount = articleRepository.count();
		//When
		Article savedArticle = articleRepository.save(Article.of("new article", "new content", "#new"));
		//Then
		assertThat(articleRepository.count()).isEqualTo(previousCount +1);
	}

	@DisplayName("update 테스트")
	@Test
	void ginvenTestData_whenUpdating_thenWorksFine() {
		//Given
		Article article = articleRepository.findById(1L).orElseThrow();
		String updatedHashtag = "#springboot";
		article.setHashtag(updatedHashtag);
		//When
		Article savedArticle = articleRepository.saveAndFlush(article);
		//Then
		assertThat(savedArticle).hasFieldOrPropertyWithValue("hashtag", updatedHashtag);
	}

	@DisplayName("delete 테스트")
	@Test
	void ginvenTestData_whenDeleting_thenWorksFine() {
		//Given
		Article article = articleRepository.findById(1L).orElseThrow();
		long previousArticleCount = articleRepository.count();
		// 댓글도 지워야함
		long previousArticleCommentCount = articleCommentRepository.count();
		int deletedCommentSize = article.getArticleComments().size();
		//When
		articleRepository.delete(article);
		//Then
		assertThat(articleRepository.count()).isEqualTo(previousArticleCount - 1);
		assertThat(articleCommentRepository.count()).isEqualTo(previousArticleCommentCount - deletedCommentSize);
	}
}