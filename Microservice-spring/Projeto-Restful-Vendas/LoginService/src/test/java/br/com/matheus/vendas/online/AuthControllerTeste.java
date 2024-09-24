package br.com.matheus.vendas.online;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.matheus.vendas.online.controllers.AuthController;
import br.com.matheus.vendas.online.domain.users.User;
import br.com.matheus.vendas.online.dto.LoginRequestDTO;
import br.com.matheus.vendas.online.dto.RegisterRequestDTO;
import br.com.matheus.vendas.online.infra.security.TokenService;
import br.com.matheus.vendas.online.repository.UserRepository;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class AuthControllerTeste {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private UserRepository repository;

	@MockBean
	private PasswordEncoder passwordEncoder;

	@MockBean
	private TokenService tokenService;

	@InjectMocks
	private AuthController authController;

	private ObjectMapper objectMapper;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		objectMapper = new ObjectMapper();
	}

	@Test
	public void testeLoginSucesso() throws JsonProcessingException, Exception {
		LoginRequestDTO loginRequestDTO = new LoginRequestDTO("user@gmail.com", "12345678");
		User user = User.builder()
				.id("A1")
				.email("user@gmail.com")
				.password(passwordEncoder.encode("12345678"))
				.name("teste")
				.build();

		when(repository.findByEmail("user@gmail.com")).thenReturn(Optional.of(user));
		when(passwordEncoder.matches("12345678", user.getPassword())).thenReturn(true);
		when(tokenService.generateToken(user)).thenReturn("generated_token");

		mockMvc.perform(post("/auth/login")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(loginRequestDTO)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.name").value("teste"))
				.andExpect(jsonPath("$.token").value("generated_token"));

	}

	@Test
	public void testParaFalhaLogin() throws JsonProcessingException, Exception {
		LoginRequestDTO loginRequestDTO = new LoginRequestDTO("user@gmail.com", "12345678");

		when(repository.findByEmail("user@gmail.com")).thenReturn(Optional.of(new User()));
		when(passwordEncoder.matches("12345678", "")).thenReturn(false);

		mockMvc.perform(post("/auth/login")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(loginRequestDTO)))
				.andExpect(status().isBadRequest());
	}

	@Test
	public void testRegisterSucesso() throws JsonProcessingException, Exception {

		RegisterRequestDTO request = new RegisterRequestDTO("Test User", "test@example.com", "password");
		User newUser = new User();
		newUser.setId("A2");
		newUser.setEmail(request.email());
		newUser.setName(request.name());

		when(repository.findByEmail(request.email())).thenReturn(Optional.empty());
		when(passwordEncoder.encode(request.password())).thenReturn("encodedPassword");
		when(repository.save(any(User.class))).thenReturn(newUser);
		when(tokenService.generateToken(any(User.class))).thenReturn("token");

		mockMvc.perform(post("/auth/register")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(request)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.name").value("Test User"))
				.andExpect(jsonPath("$.token").value("token"));

	}

	@Test
	public void testRegisterUsuarioJaExiste() throws JsonProcessingException, Exception {

		RegisterRequestDTO request = new RegisterRequestDTO("Test User", "test@example.com", "password");
		User existingUser = new User();
		existingUser.setEmail(request.email());
		existingUser.setName(request.name());

		when(repository.findByEmail(request.email())).thenReturn(Optional.of(existingUser));
																								

		mockMvc.perform(post("/auth/register")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(request)))
				.andExpect(status().isBadRequest());
	}
}
