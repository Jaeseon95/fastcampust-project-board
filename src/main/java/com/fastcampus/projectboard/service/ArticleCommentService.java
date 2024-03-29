package com.fastcampus.projectboard.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fastcampus.projectboard.dto.ArticleCommentDto;
import com.fastcampus.projectboard.repository.ArticleCommentRepository;
import com.fastcampus.projectboard.repository.ArticleRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
@Service
public class ArticleCommentService {

	private final ArticleRepository articleRepository;
	private final ArticleCommentRepository articleCommentRepository;

	@Transactional(readOnly = true)
	public List<ArticleCommentDto> searchArticleComment(long l) {
		return List.of();
	}
}
