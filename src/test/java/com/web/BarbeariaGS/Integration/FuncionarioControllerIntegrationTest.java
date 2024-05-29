package com.web.BarbeariaGS.Integration;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.Optional;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.web.BarbeariaGS.controllers.ClienteController;
import com.web.BarbeariaGS.controllers.FuncionarioController;
import com.web.BarbeariaGS.models.Cliente;
import com.web.BarbeariaGS.repository.AgendamentoRepo;
import com.web.BarbeariaGS.repository.AdminRepo;
import com.web.BarbeariaGS.repository.ClientesRepo;
import com.web.BarbeariaGS.repository.FuncionariosRepo;
import com.web.BarbeariaGS.repository.HorarioRepo;
import com.web.BarbeariaGS.repository.ServicoRepo;
import com.web.BarbeariaGS.services.CookieService;

@ActiveProfiles("test")
@WebMvcTest(FuncionarioController.class)
@AutoConfigureTestDatabase
public class FuncionarioControllerIntegrationTest {

   
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ClientesRepo clientesRepo;

    @MockBean
    private HorarioRepo horarioRepo;

    @MockBean
    private ServicoRepo servicoRepo;

    @MockBean
    private AgendamentoRepo AgendamentoRepo;

    @MockBean
    private FuncionariosRepo funcionariosRepo;

    @MockBean
    private AdminRepo adminRepo;

    @MockBean
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private WebApplicationContext context;

    @BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context).build();
       

    }

    @Test
    public void testAgendamentosFuncionario_WhenNotLoggedIn_ShouldRedirectToLogin() throws Exception {
         // Clear any existing static mocks
         Mockito.framework().clearInlineMocks();
                mockMvc.perform(get("/funcionarios"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/error/404"));
    }

    @Test
    public void testGerenciarFuncionarios_WhenNotLoggedIn_ShouldRedirectToLogin() throws Exception {
        // Clear any existing static mocks
        Mockito.framework().clearInlineMocks();
        mockMvc.perform(get("/gerenciar/funcionarios"))
        .andExpect(status().is3xxRedirection())
        .andExpect(redirectedUrl("/error/404"));
    }

    @Test
    public void testGerenciarNovoFuncionario_WhenNotLoggedIn_ShouldRedirectToLogin() throws Exception {
        // Clear any existing static mocks
        Mockito.framework().clearInlineMocks();
        mockMvc.perform(get("/gerenciar/funcionarios/novo"))
        .andExpect(status().is3xxRedirection())
        .andExpect(redirectedUrl("/error/404"));
    }

    @Test
    public void testGerenciarEditFuncionario_WhenNotLoggedIn_ShouldRedirectToLogin() throws Exception {
        // Clear any existing static mocks
        Mockito.framework().clearInlineMocks();
        mockMvc.perform(get("/gerenciar/funcionarios/1"))
        .andExpect(status().is3xxRedirection())
        .andExpect(redirectedUrl("/error/404"));
    }

}
