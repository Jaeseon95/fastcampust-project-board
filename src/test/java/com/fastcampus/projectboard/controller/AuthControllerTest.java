package com.fastcampus.projectboard.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@DisplayName("View 컨트롤러 -  인증")
// 얘가 뭔지
@WebMvcTest
public class AuthControllerTest {
	private final MockMvc mvc;

	public AuthControllerTest(@Autowired MockMvc mvc) {
		this.mvc = mvc;
	}

	@DisplayName("[view] [GET] 로그인 페이지 - 정상 호출")
	@Test
	public void givenNothing_whenTryingToLogin_thenReturnsLogInView() throws Exception{
		mvc.perform(get("/login"))
			.andExpect(status().isOk())
			.andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML));
	}
}
