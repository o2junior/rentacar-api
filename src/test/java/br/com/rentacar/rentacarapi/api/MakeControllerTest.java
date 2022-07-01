package br.com.rentacar.rentacarapi.api;

import br.com.rentacar.rentacarapi.controller.api.MakeController;
import br.com.rentacar.rentacarapi.controller.dto.MakeRequest;
import br.com.rentacar.rentacarapi.controller.handler.GlobalControllerExceptionHandler;
import br.com.rentacar.rentacarapi.usecase.make.CreateMakeUseCase;
import br.com.rentacar.rentacarapi.usecase.make.GetListMakesUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@EnableSpringDataWebSupport
class MakeControllerTest extends BaseTestsController {

    @Mock CreateMakeUseCase createMakeCase;
    @Mock
    GetListMakesUseCase getListMakesUseCase;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        final MakeController controller =
                new MakeController(createMakeCase, getListMakesUseCase);
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setControllerAdvice(new GlobalControllerExceptionHandler())
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .build();
    }

    @Test
    void postMakeShouldReturnSuccessfully() throws Exception {
        // given
        final MakeRequest payload = MakeRequest.builder()
                                               .name("Make Teste")
                                               .build();
        final String path = "/v1/api/make";

        // when
        final ResultActions resultActions =
                getPerformHttpResult(objectMapper.writeValueAsString(payload), post(path));

        // then
        resultActions
                .andExpect(status().isCreated());
    }

    @Test
    void postMakeInvalidShouldReturnBadRequest() throws Exception {
        // given
        final MakeRequest payload = MakeRequest.builder()
                                                .name(null)
                                                .build();
        final String path = "/v1/api/make";

        // when
        final ResultActions resultActions =
                getPerformHttpResult(objectMapper.writeValueAsString(payload), post(path));

        // then
        resultActions
                .andExpect(status().isBadRequest());
    }

    @Test
    void getListMakeShouldReturnSuccessfully() throws Exception {
        // given
        final String path = "/v1/api/make";

        // when
        final ResultActions resultActions =
                getPerformHttpResult(null, get(path));

        // then
        resultActions
                .andExpect(status().isOk());
    }

}
