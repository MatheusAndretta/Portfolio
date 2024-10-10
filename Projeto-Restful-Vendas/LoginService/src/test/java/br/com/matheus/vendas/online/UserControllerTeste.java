package br.com.matheus.vendas.online;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
import org.springframework.test.web.servlet.MockMvc;

import br.com.matheus.vendas.online.controllers.UserController;
import br.com.matheus.vendas.online.infra.security.TokenService;
import br.com.matheus.vendas.online.usercase.UserValidation;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class UserControllerTeste {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserValidation validation;

    @MockBean
    private TokenService tokenService;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testeTokenEEmailValidos_DeveRetornarVerdadeiro() throws Exception {
        final String email = "test@example.com";
        final String token = "validToken";
    
       
        when(tokenService.validateToken(token)).thenReturn(email);
        when(validation.isValidado(email)).thenReturn(true);
    
        
        mockMvc.perform(get("/user/{email}/{token}", email, token)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));
    
        
        verify(tokenService).validateToken(token);
        verify(validation).isValidado(email);
    }

    @Test
    public void testeSeTokenInvalido_DeveRetornarFalso() throws Exception {
        String email = "test@example.com";
        String token = "invalidToken";

        when(tokenService.validateToken(token)).thenReturn(null);

        mockMvc.perform(get("/user/{email}/{token}", email, token)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("false"));
    }

    @Test
    public void testeSeEmailNaoValidado_DeveRetornarFalso() throws Exception {
        String email = "test@example.com";
        String token = "validToken";

        when(tokenService.validateToken(token)).thenReturn(email);
        when(validation.isValidado(email)).thenReturn(false);

        mockMvc.perform(get("/user/{email}/{token}", email, token)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("false"));
    }

    @Test
    public void testeGetUser_RetornaSucesso() throws Exception {
        mockMvc.perform(get("/user")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("sucesso!"));
    }
}