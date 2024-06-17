package com.sparta.layered_unit_testing.domain.board.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.security.Principal;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.layered_unit_testing.domain.board.dto.BoardRequestDto;
import com.sparta.layered_unit_testing.domain.board.dto.BoardResponseDto;
import com.sparta.layered_unit_testing.domain.board.dto.UpdateRequestDto;
import com.sparta.layered_unit_testing.domain.board.service.BoardService;
import com.sparta.layered_unit_testing.domain.user.entity.User;
import com.sparta.layered_unit_testing.domain.user.entity.UserStatusEnum;
import com.sparta.layered_unit_testing.domain.user.security.UserDetailsImpl;
import com.sparta.layered_unit_testing.mvc.MockSpringSecurityFilter;

import com.navercorp.fixturemonkey.FixtureMonkey;
import com.navercorp.fixturemonkey.api.introspector.BuilderArbitraryIntrospector;

@WebMvcTest(
	controllers = BoardController.class,
	excludeFilters = {
		@ComponentScan.Filter(
			type = FilterType.ASSIGNABLE_TYPE,
			classes = SecurityConfig.class
		)
	}
)
class BoardControllerTest {

	private MockMvc mockMvc;

	private Principal mockPrincipal;

	@Autowired
	private WebApplicationContext context;

	@Autowired
	private ObjectMapper objectMapper;

	@MockBean
	private BoardService boardService;

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
	@DisplayName("createBoard 테스트")
	void testCreateBoard() throws Exception {
		// given
		mockUserSetup();
		BoardRequestDto requestDto = fixtureMonkey.giveMeBuilder(BoardRequestDto.class)
			.setNotNull("title")
			.setNotNull("contents")
			.sample();

		MockMultipartFile file = new MockMultipartFile(
			"files",
			"profileImage.png",
			"multipart/form-data",
			"image".getBytes()
		);

		MockMultipartFile mockBoard = new MockMultipartFile(
			"board",
			"",
			"application/json",
			objectMapper.writeValueAsBytes(requestDto)
		);

		BoardResponseDto responseDto = new BoardResponseDto();
		when(boardService.createBoard(any(BoardRequestDto.class), any(User.class), any())).thenReturn(responseDto);

		// when
		mockMvc.perform(multipart("/api/boards")
				.file(mockBoard)
				.file(file)
				.principal(mockPrincipal))
			.andExpect(status().isOk())
			.andDo(print());
	}

	@Test
	@DisplayName("updateBoard 테스트")
	void testUpdateBoard() throws Exception {
		// given
		mockUserSetup();
		UpdateRequestDto requestDto = fixtureMonkey.giveMeBuilder(UpdateRequestDto.class)
			.setNotNull("title")
			.setNotNull("contents")
			.sample();

		MockMultipartFile file = new MockMultipartFile(
			"files",
			"profileImage.png",
			"multipart/form-data",
			"image".getBytes()
		);

		MockMultipartFile mockBoard = new MockMultipartFile(
			"board",
			"",
			"application/json",
			objectMapper.writeValueAsBytes(requestDto)
		);

		BoardResponseDto responseDto = new BoardResponseDto();
		when(boardService.updateBoard(any(Long.class), any(UpdateRequestDto.class), any(User.class), any())).thenReturn(responseDto);

		mockMvc.perform(multipart("/api/boards")
				.file(mockBoard)
				.file(file)
				.principal(mockPrincipal))
			.andExpect(status().isOk())
			.andDo(print());
	}

	@Test
	@DisplayName("deleteBoard 테스트")
	void testDeleteBoard() throws Exception {
		// given
		mockUserSetup();

		// when
		ResultActions actions = mockMvc.perform(delete("/api/boards/1")
			.contentType(MediaType.APPLICATION_JSON)
			.accept(MediaType.APPLICATION_JSON)
			.principal(mockPrincipal)
		);

		// then
		actions.andExpect(status().isNoContent())
			.andDo(print());
	}

	@Test
	@DisplayName("getAllBoards 테스트")
	void testGetAllBoards() throws Exception {
		// given
		List<BoardResponseDto> responseDtos = List.of(new BoardResponseDto());
		when(boardService.getAllBoard()).thenReturn(responseDtos);

		// when
		ResultActions actions = mockMvc.perform(get("/api/boards/list")
			.contentType(MediaType.APPLICATION_JSON)
			.accept(MediaType.APPLICATION_JSON)
		);

		// then
		actions.andExpect(status().isOk())
			.andDo(print());
	}

	@Test
	@DisplayName("getBoard 테스트")
	void testGetBoard() throws Exception {
		// given
		BoardResponseDto responseDto = new BoardResponseDto();
		when(boardService.getBoardById(any(Long.class))).thenReturn(responseDto);

		// when
		ResultActions actions = mockMvc.perform(get("/api/boards/1")
			.contentType(MediaType.APPLICATION_JSON)
			.accept(MediaType.APPLICATION_JSON)
		);

		// then
		actions.andExpect(status().isOk())
			.andDo(print());
	}

	@Test
	@DisplayName("getBoards 테스트")
	void testGetBoards() throws Exception {
		// given
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("page", "1");
		params.add("size", "10");
		params.add("sort", "updatedAt");
		params.add("direction", "DESC");

		Page<BoardResponseDto> responseDto = Page.empty();
		when(boardService.paging(any())).thenReturn(responseDto);

		// when
		ResultActions actions = mockMvc.perform(get("/api/boards")
			.params(params)
			.contentType(MediaType.APPLICATION_JSON)
			.accept(MediaType.APPLICATION_JSON)
		);

		// then
		actions.andExpect(status().isOk())
			.andDo(print());
	}
}
