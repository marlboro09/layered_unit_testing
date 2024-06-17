package com.sparta.layered_unit_testing.domain.comment.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.security.Principal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.layered_unit_testing.domain.comment.dto.CommentRequestDto;
import com.sparta.layered_unit_testing.domain.comment.dto.CommentResponseDto;
import com.sparta.layered_unit_testing.domain.comment.dto.CommentUpdateRequestDto;
import com.sparta.layered_unit_testing.domain.comment.service.CommentService;
import com.sparta.layered_unit_testing.domain.user.entity.User;
import com.sparta.layered_unit_testing.domain.user.entity.UserStatusEnum;
import com.sparta.layered_unit_testing.domain.user.security.UserDetailsImpl;
import com.sparta.layered_unit_testing.mvc.MockSpringSecurityFilter;

import com.navercorp.fixturemonkey.FixtureMonkey;
import com.navercorp.fixturemonkey.api.introspector.BuilderArbitraryIntrospector;

@WebMvcTest(
	controllers = CommentController.class,
	excludeFilters = {
		@ComponentScan.Filter(
			type = FilterType.ASSIGNABLE_TYPE,
			classes = SecurityConfig.class
		)
	}
)
class CommentControllerTest {

	private MockMvc mockMvc;

	private Principal mockPrincipal;

	@Autowired
	private WebApplicationContext context;

	@Autowired
	private ObjectMapper objectMapper;

	@MockBean
	private CommentService commentService;

	private FixtureMonkey fixtureMonkey;

	@BeforeEach
	public void setup() {
		mockMvc = MockMvcBuilders.webAppContextSetup(context)
			.apply(springSecurity(new MockSpringSecurityFilter()))
			.build();
		mockUserSetup();

		fixtureMonkey = FixtureMonkey.builder()
			.objectIntrospector(BuilderArbitraryIntrospector.INSTANCE)
			.build();
	}

	private void mockUserSetup() {
		User user = User.builder()
			.userId("testUser")
			.email("test@example.com")
			.password("testPassword")
			.name("Test")
			.intro("Ttestuser")
			.userStatusEnum(UserStatusEnum.NORMAL)
			.build();

		UserDetailsImpl userDetails = new UserDetailsImpl(user);
		mockPrincipal = new UsernamePasswordAuthenticationToken(userDetails, user.getPassword(), userDetails.getAuthorities());
	}

	@Test
	@DisplayName("createComment 테스트")
	void testCreateComment() throws Exception {
		// given
		CommentRequestDto requestDto = fixtureMonkey.giveMeBuilder(CommentRequestDto.class)
			.setNotNull("contents")
			.sample();

		CommentResponseDto responseDto = new CommentResponseDto();
		when(commentService.createComment(any(CommentRequestDto.class), any(Long.class), any(User.class))).thenReturn(responseDto);

		// when
		ResultActions actions = mockMvc.perform(post("/api/boards/1/comments")
			.content(objectMapper.writeValueAsString(requestDto))
			.contentType(MediaType.APPLICATION_JSON)
			.accept(MediaType.APPLICATION_JSON)
			.principal(mockPrincipal)
		);

		// then
		actions.andExpect(status().isOk())
			.andDo(print());
	}

	@Test
	@DisplayName("updateComment 테스트")
	void testUpdateComment() throws Exception {
		// given
		CommentUpdateRequestDto requestDto = fixtureMonkey.giveMeBuilder(CommentUpdateRequestDto.class)
			.setNotNull("contents")
			.sample();

		CommentResponseDto responseDto = new CommentResponseDto();
		when(commentService.updateComment(any(Long.class), any(User.class), any(CommentUpdateRequestDto.class))).thenReturn(responseDto);

		// when
		ResultActions actions = mockMvc.perform(put("/api/boards/1/comments/1")
			.content(objectMapper.writeValueAsString(requestDto))
			.contentType(MediaType.APPLICATION_JSON)
			.accept(MediaType.APPLICATION_JSON)
			.principal(mockPrincipal)
		);

		// then
		actions.andExpect(status().isOk())
			.andDo(print());
	}

	@Test
	@DisplayName("deleteComment 테스트")
	void testDeleteComment() throws Exception {
		// when
		ResultActions actions = mockMvc.perform(delete("/api/boards/1/comments/1")
			.contentType(MediaType.APPLICATION_JSON)
			.accept(MediaType.APPLICATION_JSON)
			.principal(mockPrincipal)
		);

		// then
		actions.andExpect(status().isNoContent())
			.andDo(print());
	}
}