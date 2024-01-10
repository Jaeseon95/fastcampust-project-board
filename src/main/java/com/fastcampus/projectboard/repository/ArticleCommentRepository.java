package com.fastcampus.projectboard.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.fastcampus.projectboard.domain.ArticleComment;
import com.fastcampus.projectboard.domain.QArticleComment;
import com.querydsl.core.types.dsl.DateTimeExpression;
import com.querydsl.core.types.dsl.StringExpression;

@RepositoryRestResource
public interface ArticleCommentRepository extends
	JpaRepository<ArticleComment, Long>
	, QuerydslPredicateExecutor<ArticleComment>
	, QuerydslBinderCustomizer<QArticleComment>
{

	// 게시글의 Id이기 때문에 언더스코어가 들어감
	List<ArticleComment> findByArticle_Id(Long articleId);
	@Override
	default void customize(QuerydslBindings bindings, QArticleComment root){
		// 특정 property에 대해서만 검색을 열기
		bindings.excludeUnlistedProperties(true);
		bindings.including(root.content,root.createdAt, root.createdBy);
		// bindings.bind(root.title).first(((path, value) -> path.eq(value)));
		// bindings.bind(root.title).first(StringExpression::likeIgnoreCase); // likie `${v}$`
		bindings.bind(root.content).first(StringExpression::containsIgnoreCase); // like `%${v}%`
		bindings.bind(root.createdAt).first(DateTimeExpression::eq); // like `%${v}%`
		bindings.bind(root.createdBy).first(StringExpression::containsIgnoreCase); // like `%${v}%`
	};
}
