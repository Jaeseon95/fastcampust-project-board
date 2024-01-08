package com.fastcampus.projectboard.controller;

import static org.junit.jupiter.api.Assertions.*;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fastcampus.projectboard.config.SecurityConfig;

@DisplayName("View 컨트롤러 - 게시글")
@Import(SecurityConfig.class)
@WebMvcTest(ArticleController.class)
class ArticleControllerTest {
    private final MockMvc mvc;


    public ArticleControllerTest(@Autowired MockMvc mvc) {
        this.mvc = mvc;
    }

    @DisplayName("[view] [GET] 게시글 리스트")
    @Test
    public void givenNothing_whenRequestingArticlesView_thenReturnArticlesView() throws Exception {
        // Given
        // When & Then
        mvc.perform(get("/articles"))
            .andExpect(status().isOk())
            .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
            .andExpect(view().name("articles/index"))
            // model attribute가 있나 없나 확인까지 가능
            .andExpect(model().attributeExists("articles"));
    }


    @DisplayName("[view] [GET] 게시글 상세 페이지 조회")
    @Test
    public void givenNothing_whenRequestingArticleView_thenReturnArticleView() throws Exception {
        // Given

        // When & Then
        mvc.perform(get("/articles/1"))
            .andExpect(status().isOk())
            .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
            .andExpect(view().name("articles/detail"))
            // model attribute가 있나 없나 확인까지 가능
            .andExpect(model().attributeExists("article"))
            .andExpect(model().attributeExists("articleComments"));
    }

    @Disabled("개발 중")
    @DisplayName("[view] [GET] 게시글 검색 페이지")
    @Test
    public void givenNothing_whenRequestingArticleSearchView_thenReturnArticleSearchView()
        throws Exception {
        // Given

        // When & Then
        mvc.perform(get("/articles/search"))
            .andExpect(status().isOk())
            .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
            .andExpect(view().name("articles/search"));
    }

    @Disabled("개발 중")
    @DisplayName("[view] [GET] 해시태그 검색 페이지")
    @Test
    public void givenNothing_whenRequestingArticleHashtageSearchView_thenReturnArticleHashtagSearchView()
        throws Exception {
        // Given

        // When & Then
        mvc.perform(get("/articles/search-hashtag"))
            .andExpect(status().isOk())
            .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
            .andExpect(view().name("articles/hashtag"));
    }
}

