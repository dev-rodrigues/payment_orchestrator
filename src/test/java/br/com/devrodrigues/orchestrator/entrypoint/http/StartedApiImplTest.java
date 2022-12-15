package br.com.devrodrigues.orchestrator.entrypoint.http;

import br.com.devrodrigues.orchestrator.fixture.Fixture;
import br.com.devrodrigues.orchestrator.service.BillingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class StartedApiImplTest {

    @Autowired
    private MockMvc mockMvc;

    @InjectMocks
    StartedApiImpl controller;

    @Mock
    BillingService service;

    static final String PATH = "/started";

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void should_return_200_and_list_of_billing_when_request_is_valid() throws Exception {
        var response = Fixture.getBillingEntity();
        response.setId(1L);

        Mockito.when(service.findAll()).thenReturn(List.of(response));

        mockMvc.perform(MockMvcRequestBuilders.get(PATH)
                .contentType(APPLICATION_JSON)
        ).andExpect(status().isOk());
    }

}