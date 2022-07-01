package br.com.rentacar.rentacarapi.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

public abstract class BaseTestsController {

    MockMvc mockMvc;
    final ObjectMapper objectMapper = new ObjectMapper();
    static final String TOKEN = "eyJhbGciOiJIUzI1NiIsImN0eSI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwidXNlcm5hbWUiOiJNYXJjaW8gSnIiLCJpYXQiOjE1MTYyMzkwMjJ9.rT7fYAjRnlCdGWcQL6xVFyCvEB6tTNsVMGJu2K40QHg";

    ResultActions getPerformHttpResult(String payload,
                                               MockHttpServletRequestBuilder method) throws Exception {
        return mockMvc.perform(
                method.contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, TOKEN)
                        .content(payload != null ? payload : "{}"));
    }
}
