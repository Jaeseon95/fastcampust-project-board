package com.fastcampus.projectboard.controller;

import static org.junit.jupiter.api.Assertions.*;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fastcampus.projectboard.dto.ArticleDto;
import com.fastcampus.projectboard.dto.ArticleWithCommentsDto;
import com.fastcampus.projectboard.dto.UserAccountDto;
import com.fastcampus.projectboard.service.ArticleService;
import java.time.LocalDateTime;
import java.util.Set;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fastcampus.projectboard.config.SecurityConfig;

@DisplayName("View 컨트롤러 - 게시글")
@Import(SecurityConfig.class)
@WebMvcTest(ArticleController.class)
class ArticleControllerTest {
    private final MockMvc mvc;

    @MockBean private ArticleService articleService;

    public ArticleControllerTest(@Autowired MockMvc mvc) {
        this.mvc = mvc;
    }

    @DisplayName("[view] [GET] 게시글 리스트")
    @Test
    public void givenNothing_whenRequestingArticlesView_thenReturnArticlesView() throws Exception {
        // Given
        given(articleService.searchArticles(eq(null),eq(null), any(Pageable.class))).willReturn(Page.empty());

        // When & Then
        mvc.perform(get("/articles"))
            .andExpect(status().isOk())
            .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
            .andExpect(view().name("articles/index"))
            // model attribute가 있나 없나 확인까지 가능
            .andExpect(model().attributeExists("articles"));
            //.andExpect(model().attributeExists("searchTypes"));
        then(articleService).should().searchArticles(eq(null),eq(null), any(Pageable.class));

    }


    @DisplayName("[view] [GET] 게시글 상세 페이지 조회")
    @Test
    public void givenNothing_whenRequestingArticleView_thenReturnArticleView() throws Exception {
        // Given
        long articleId = 1L;
        given(articleService.getArticle(articleId)).willReturn(createArticleWithCommentsDto());

        // When & Then
        mvc.perform(get("/articles/1"))
            .andExpect(status().isOk())
            .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
            .andExpect(view().name("articles/detail"))
            // model attribute가 있나 없나 확인까지 가능
            .andExpect(model().attributeExists("article"))
            .andExpect(model().attributeExists("articleComments"));
        then(articleService).should().getArticle(articleId);
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

    private ArticleDto createArticleDto() {
        return ArticleDto.of(
            1L,
            createUserAccountDto(),
            "title",
            "content",
            "#java",
            LocalDateTime.now(),
            "hadue",
            LocalDateTime.now(),
            "hadue"
        );
    }

    private ArticleWithCommentsDto createArticleWithCommentsDto() {
        return ArticleWithCommentsDto.of(
            1L,
            createUserAccountDto(),
            "title",
            "content",
            "#java",
             Set.of(),
            LocalDateTime.now(),
            "uno",
            LocalDateTime.now(),
            "uno"
        );
    }

    private UserAccountDto createUserAccountDto() {
        return UserAccountDto.of(
            "hadue",
            "hadue",
            "hadue@mail.com",
            "hadue",
            "memo",
            LocalDateTime.now(),
            "hadue",
            LocalDateTime.now(),
            "hadue"
        );
    }
}

