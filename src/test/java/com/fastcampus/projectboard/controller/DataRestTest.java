package com.fastcampus.projectboard.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

@Disabled("Spring Data REST 통합테스트는 불필요하기 때문에 제외시킴")
// 내가 작성한 API가 아니라 기본으로 제공해줌
//Controller와 상관된 애들만 로드함 data REST와 관련된 configuration
//@WebMvcTest
@DisplayName("Data REST - API 테스트")
@Transactional
@AutoConfigureMockMvc
@SpringBootTest
public class DataRestTest {

	private final MockMvc mvc;

	public DataRestTest(@Autowired MockMvc mvc) {
		this.mvc = mvc;
	}

	@DisplayName("[api] 게시글 리스트 조회")
	@Test
	void givenNothing_whenRequestingArticles_thenReturnArticlesJsonResponse() throws Exception {
		// Give
		// When & Then
		mvc.perform(get("/api/articles"))
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.valueOf("application/hal+json")));
	}

	@DisplayName("[api] 게시글 단건 조회")
	@Test
	void givenNothing_whenRequestingArticles_thenReturnArticleJsonResponse() throws Exception {
		// Give
		// When & Then
		mvc.perform(get("/api/articles/1"))
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.valueOf("application/hal+json")));
	}

	@DisplayName("[api] 게시글 -> 댓글 리스트 조회")
	@Test
	void givenNothing_whenRequestingArticleCommentsFromArticle_thenReturnArticleJsonResponse() throws Exception {
		// Give
		// When & Then
		mvc.perform(get("/api/articles/1/articleComments"))
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.valueOf("application/hal+json")));
	}


	@DisplayName("[api] 댓글 리스트 조회")
	@Test
	void givenNothing_whenRequestingArticleComments_thenReturnArticleJsonResponse() throws Exception {
		// Give
		// When & Then
		mvc.perform(get("/api/articleComments"))
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.valueOf("application/hal+json")));
	}


	@DisplayName("[api] 댓글 단건 조회")
	@Test
	void givenNothing_whenRequestingArticleComment_thenReturnArticleJsonResponse() throws Exception {
		// Give
		// When & Then
		mvc.perform(get("/api/articleComments/1"))
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.valueOf("application/hal+json")));
	}
}
