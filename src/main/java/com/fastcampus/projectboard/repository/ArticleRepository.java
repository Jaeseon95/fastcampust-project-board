package com.fastcampus.projectboard.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.fastcampus.projectboard.domain.Article;
import com.fastcampus.projectboard.domain.QArticle;
import com.querydsl.core.types.dsl.DateTimeExpression;
import com.querydsl.core.types.dsl.StringExpression;

@RepositoryRestResource
public interface ArticleRepository extends
	JpaRepository<Article, Long>
	// 검색 기능 추가 ExactMatching
	, QuerydslPredicateExecutor<Article>
	, QuerydslBinderCustomizer<QArticle>
{
	Page<Article> findByTitleContaining(String title, Pageable pageable);
	Page<Article> findByContentContaining(String content, Pageable pageable);
	Page<Article> findByUserAccount_UserIdContaining(String userId, Pageable pageable);
	Page<Article> findByUserAccount_NicknameContaining(String nickname, Pageable pageable);
	Page<Article> findByHashtag(String hashtag, Pageable pageable);


	@Override
	default void customize(QuerydslBindings bindings, QArticle root){
		// 특정 property에 대해서만 검색을 열기
		bindings.excludeUnlistedProperties(true);
		bindings.including(root.title, root.hashtag, root.content, root.createdAt, root.createdBy);
		// bindings.bind(root.title).first(((path, value) -> path.eq(value)));
		// bindings.bind(root.title).first(StringExpression::likeIgnoreCase); // likie `${v}$`
		bindings.bind(root.title).first(StringExpression::containsIgnoreCase); // like `%${v}%`
		bindings.bind(root.content).first(StringExpression::containsIgnoreCase); // like `%${v}%`
		bindings.bind(root.hashtag).first(StringExpression::containsIgnoreCase); // like `%${v}%`
		bindings.bind(root.createdAt).first(DateTimeExpression::eq); // like `%${v}%`
		bindings.bind(root.createdBy).first(StringExpression::containsIgnoreCase); // like `%${v}%`
	};
}