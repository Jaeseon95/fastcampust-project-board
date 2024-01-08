package com.fastcampus.projectboard.domain;

import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderBy;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@ToString
@Table(indexes = {
	@Index(columnList = "title"),
	@Index(columnList = "hashtag"),
	@Index(columnList = "createdAt"),
	@Index(columnList = "createdBy"),
})
@Entity
public class Article extends AuditingFields{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Setter
	@Column(nullable = false)
	private String title; // 제목
	@Setter
	@Column(nullable = false, length = 10000)
	private String content; // 본문
	@Setter
	private String hashtag; // 해시태그


	@ToString.Exclude
	@OrderBy("id")
	// cascade: 양방향 바인딩 일부러 푸는 경우도 있음 추가 공부 필요
	@OneToMany(mappedBy = "article", cascade = CascadeType.ALL)
	private final Set<ArticleComment> articleComments = new LinkedHashSet<>();

	protected Article() {
	}

	private Article(String title, String content, String hashtag) {
		this.title = title;
		this.content = content;
		this.hashtag = hashtag;
	}

	public static Article of(String title, String content, String hashtag) {
		return new Article(title, content, hashtag);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof Article article)) {
			return false;
		}
		return id != null && id.equals(article.id);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}
}

