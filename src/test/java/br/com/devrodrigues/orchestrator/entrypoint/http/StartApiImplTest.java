package br.com.devrodrigues.orchestrator.entrypoint.http;

import br.com.devrodrigues.orchestrator.service.Orchestrator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.gson.Gson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static br.com.devrodrigues.orchestrator.fixture.Fixture.getOrchestratorResponse;
import static br.com.devrodrigues.orchestrator.fixture.Fixture.getValidRequest;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class StartApiImplTest {

    @Autowired
    private MockMvc mockMvc;

    @InjectMocks
    StartApiImpl controller;

    @Mock
    Orchestrator orchestrator;

    static final String PATH = "/start";

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void should_return_204_when_request_is_valid() throws Exception {
        // given: valid request
        var request = getValidRequest();
        var json = new Gson().toJson(request);

        // when
        Mockito.when(orchestrator.startProcess(Mockito.any())).thenReturn(getOrchestratorResponse());

        // then
        mockMvc.perform(MockMvcRequestBuilders.post(PATH, 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
        ).andExpect(status().isNoContent());
    }

    @Test
    void should_not_start_process_when_throws_JsonProcessingException() throws Exception {
        // given: valid request
        var request = getValidRequest();
        var json = new Gson().toJson(request);

        // when
        Mockito.when(orchestrator.startProcess(Mockito.any())).thenThrow(JsonProcessingException.class);

        // then
        mockMvc.perform(MockMvcRequestBuilders.post(PATH, 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
        ).andExpect(status().isBadRequest());
    }
}