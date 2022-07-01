package br.com.rentacar.rentacarapi.api;

import br.com.rentacar.rentacarapi.controller.api.CarController;
import br.com.rentacar.rentacarapi.controller.dto.CarRequest;
import br.com.rentacar.rentacarapi.controller.handler.GlobalControllerExceptionHandler;
import br.com.rentacar.rentacarapi.usecase.car.CreateCarUseCase;
import br.com.rentacar.rentacarapi.usecase.car.DeleteCarUseCase;
import br.com.rentacar.rentacarapi.usecase.car.GetCarUseCase;
import br.com.rentacar.rentacarapi.usecase.car.GetListCarsUseCase;
import br.com.rentacar.rentacarapi.usecase.car.UpdateCarUseCase;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@EnableSpringDataWebSupport
class CarControllerTest {

    @Mock CreateCarUseCase createCarUseCase;
    @Mock DeleteCarUseCase deleteCarUseCase;
    @Mock
    GetListCarsUseCase getListCarsUseCase;
    @Mock GetCarUseCase getCarUseCase;
    @Mock UpdateCarUseCase updateCarUseCase;

    private MockMvc mockMvc;
    final ObjectMapper objectMapper = new ObjectMapper();
    private final String token = "eyJhbGciOiJIUzI1NiIsImN0eSI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwidXNlcm5hbWUiOiJNYXJjaW8gSnIiLCJpYXQiOjE1MTYyMzkwMjJ9.rT7fYAjRnlCdGWcQL6xVFyCvEB6tTNsVMGJu2K40QHg";

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        final CarController controller =
                new CarController(createCarUseCase, getListCarsUseCase, getCarUseCase, updateCarUseCase, deleteCarUseCase);
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setControllerAdvice(new GlobalControllerExceptionHandler())
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .build();
    }

    @Test
    void postCarShouldReturnSuccessfully() throws Exception {
        // given
        final CarRequest payload = CarRequest.builder()
                                             .makeId(10012L)
                                             .model("Model Teste")
                                             .build();
        final String path = "/v1/api/car";

        // when
        final ResultActions resultActions =
                getPerformHttpResult(objectMapper.writeValueAsString(payload), post(path));

        // then
        resultActions
                .andExpect(status().isCreated());
    }

    @Test
    void postCarInvalidShouldReturnBadRequest() throws Exception {
        // given
        final CarRequest payload = CarRequest.builder()
                                             .makeId(null)
                                             .model(null)
                                             .build();
        final String path = "/v1/api/car";

        // when
        final ResultActions resultActions =
                getPerformHttpResult(objectMapper.writeValueAsString(payload), post(path));

        // then
        resultActions
                .andExpect(status().isBadRequest());
    }

    @Test
    void getListCarsShouldReturnSuccessfully() throws Exception {
        // given
        final String path = "/v1/api/car";

        // when
        final ResultActions resultActions =
                getPerformHttpResult(null, get(path));

        // then
        resultActions
                .andExpect(status().isOk());
    }

    @Test
    void getCarByIdShouldReturnSuccessfully() throws Exception {
        // given
        final String path = "/v1/api/car/25bd0b1e-3a2a-42a8-bb12-f78ef4996297";

        // when
        final ResultActions resultActions =
                getPerformHttpResult(null, get(path));

        // then
        resultActions
                .andExpect(status().isOk());
    }

    @Test
    void getCarByInvalidIdShouldReturnBadRequest() throws Exception {
        // given
        final String path = "/v1/api/car/24";

        // when
        final ResultActions resultActions =
                getPerformHttpResult(null, get(path));

        // then
        resultActions
                .andExpect(status().isBadRequest());
    }

    private ResultActions getPerformHttpResult(String payload,
                                               MockHttpServletRequestBuilder method) throws Exception {
        return mockMvc.perform(
                method.contentType(MediaType.APPLICATION_JSON)
                      .accept(MediaType.APPLICATION_JSON)
                      .header(HttpHeaders.AUTHORIZATION, token)
                      .content(payload != null ? payload : "{}"));
    }
}
