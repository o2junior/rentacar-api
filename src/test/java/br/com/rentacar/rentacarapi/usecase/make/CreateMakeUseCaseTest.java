package br.com.rentacar.rentacarapi.usecase.make;

import br.com.rentacar.rentacarapi.config.security.AuthService;
import br.com.rentacar.rentacarapi.config.security.JwtUser;
import br.com.rentacar.rentacarapi.controller.dto.MakeRequest;
import br.com.rentacar.rentacarapi.controller.dto.MakeResponse;
import br.com.rentacar.rentacarapi.controller.handler.exception.BadRequestException;
import br.com.rentacar.rentacarapi.model.Make;
import br.com.rentacar.rentacarapi.repository.MakeRepository;
import br.com.rentacar.rentacarapi.usecase.adapter.MakeAdapter;
import br.com.rentacar.rentacarapi.usecase.adapter.MakeResponseAdapter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.config.EnableSpringDataWebSupport;

import java.util.Optional;

@SpringBootTest
@EnableSpringDataWebSupport
class CreateMakeUseCaseTest {

    @Mock MakeRepository makeRepository;
    @Mock AuthService authService;
    @Mock MakeAdapter makeAdapter;
    @Mock MakeResponseAdapter makeResponseAdapter;

    private CreateMakeUseCase createMakeUseCase;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        this.createMakeUseCase =
                new CreateMakeUseCase(makeRepository, authService, makeAdapter, makeResponseAdapter);
    }

    @Test
    void createMakeShouldReturnSuccessfully() {
        // given
        Make testMake = new Make();
        testMake.setName("Test");
        Mockito.when(makeRepository.existsByName(testMake.getName()))
               .thenReturn(false);
        Mockito.when(makeAdapter.adapt(Mockito.any()))
                .thenReturn(testMake);
        Mockito.when(authService.getJwtUser())
                .thenReturn(Optional.of(new JwtUser("1234", "Test Username", "123455")));
        Mockito.when(makeRepository.save(Mockito.any()))
               .thenReturn(testMake);
        Mockito.when(makeResponseAdapter.adapt(Mockito.any()))
               .thenReturn(MakeResponse.builder().id(1L).build());

        // when
        final MakeResponse actual = createMakeUseCase.execute(new MakeRequest());

        // then
        Assertions.assertNotNull(actual);
        Assertions.assertEquals(1L, actual.getId());
    }

    @Test
    void createCarWithDuplicatedsNamesShouldReturnException() {
        // given
        Mockito.when(makeRepository.existsByName(Mockito.any()))
               .thenReturn(true);

        // when/then
        Assertions.assertThrows(BadRequestException.class, () -> createMakeUseCase.execute(new MakeRequest()));
    }

}
