package br.com.rentacar.rentacarapi.usecase.car;

import br.com.rentacar.rentacarapi.config.security.AuthService;
import br.com.rentacar.rentacarapi.controller.dto.CarRequest;
import br.com.rentacar.rentacarapi.controller.dto.CarResponse;
import br.com.rentacar.rentacarapi.controller.handler.exception.ForbiddenException;
import br.com.rentacar.rentacarapi.controller.handler.exception.NotFoundException;
import br.com.rentacar.rentacarapi.model.Car;
import br.com.rentacar.rentacarapi.model.Make;
import br.com.rentacar.rentacarapi.repository.CarRepository;
import br.com.rentacar.rentacarapi.repository.MakeRepository;
import br.com.rentacar.rentacarapi.usecase.adapter.CarAdapter;
import br.com.rentacar.rentacarapi.usecase.adapter.CarResponseAdapter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.config.EnableSpringDataWebSupport;

import java.util.Optional;
import java.util.UUID;

@SpringBootTest
@EnableSpringDataWebSupport
class CreateCarUseCaseTest {

    @Mock CarRepository carRepository;
    @Mock MakeRepository makeRepository;
    @Mock AuthService authService;
    @Mock CarAdapter carAdapter;
    @Mock CarResponseAdapter carResponseAdapter;

    private CreateCarUseCase createCarUseCase;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        this.createCarUseCase =
                new CreateCarUseCase(carRepository, makeRepository, authService, carAdapter, carResponseAdapter);
    }

    @Test
    void createCarShouldReturnSuccessfully() {
        // given
        final UUID uuid = UUID.randomUUID();
        Mockito.when(makeRepository.findById(Mockito.any()))
               .thenReturn(Optional.of(new Make()));
        Mockito.when(carAdapter.adapt(Mockito.any()))
                .thenReturn(new Car());
        Mockito.when(carRepository.existsById(Mockito.any()))
               .thenReturn(false);
        Mockito.when(carRepository.save(Mockito.any()))
               .thenReturn(new Car());
        Mockito.when(carResponseAdapter.adapt(Mockito.any()))
               .thenReturn(CarResponse.builder().id(uuid).build());

        // when
        final CarResponse actual = createCarUseCase.execute(new CarRequest());

        // then
        Assertions.assertNotNull(actual);
        Assertions.assertEquals(uuid, actual.getId());
    }

    @Test
    void createCarWithDuplicatedsIdsShouldReturnException() {
        // given
        Mockito.when(makeRepository.findById(Mockito.any()))
                .thenReturn(Optional.of(new Make()));
        Mockito.when(carRepository.existsById(Mockito.any()))
                                  .thenReturn(true);

        // when/then
        Assertions.assertThrows(ForbiddenException.class, () -> createCarUseCase.execute(new CarRequest()));
    }

    @Test
    void createCarWithInvalidMakeIdShouldReturnException() {
        // given
        Mockito.when(makeRepository.findById(Mockito.any()))
                .thenReturn(Optional.ofNullable(null));

        // when/then
        Assertions.assertThrows(NotFoundException.class, () -> createCarUseCase.execute(new CarRequest()));
    }

}
