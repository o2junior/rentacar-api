package br.com.rentacar.rentacarapi.usecase.car;

import br.com.rentacar.rentacarapi.controller.dto.CarRequest;
import br.com.rentacar.rentacarapi.controller.dto.CarResponse;
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
class UpdateCarUseCaseTest {

    public static final Car CAR = new Car();
    @Mock CarRepository carRepository;
    @Mock MakeRepository makeRepository;
    @Mock CarAdapter carAdapter;
    @Mock CarResponseAdapter carResponseAdapter;

    private UpdateCarUseCase updateCarUseCase;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        this.updateCarUseCase =
                new UpdateCarUseCase(carRepository, makeRepository, carAdapter, carResponseAdapter);
    }

    @Test
    void updateCarShouldReturnSuccessfully() {
        // given
        final UUID uuid = UUID.randomUUID();
        Mockito.when(carRepository.findById(Mockito.any()))
               .thenReturn(Optional.of(CAR));
        Mockito.when(makeRepository.findById(Mockito.any()))
                .thenReturn(Optional.ofNullable(new Make()));
        Mockito.when(carRepository.findById(Mockito.any()))
                .thenReturn(Optional.of(CAR));
        Mockito.when(carResponseAdapter.adapt(Mockito.any()))
               .thenReturn(CarResponse.builder().id(uuid).build());

        // when
        final CarResponse actual = updateCarUseCase.execute(new CarRequest());

        // then
        Assertions.assertNotNull(actual);
        Assertions.assertEquals(uuid, actual.getId());
    }

    @Test
    void updateCarWithInvalidMakeIdShouldReturnException() {
        // given
        final UUID uuid = UUID.randomUUID();
        Mockito.when(carRepository.findById(Mockito.any()))
                .thenReturn(Optional.ofNullable(CAR));
        Mockito.when(makeRepository.findById(Mockito.any()))
                .thenReturn(Optional.ofNullable(null));

        // when/then
        Assertions.assertThrows(NotFoundException.class, () -> updateCarUseCase.execute(CarRequest.builder().id(uuid).makeId(1L).build()));
    }

    @Test
    void getCarMissIdShouldReturnException() {
        // given
        final UUID uuid = UUID.randomUUID();
        Mockito.when(carRepository.findById(Mockito.any()))
                .thenReturn(Optional.ofNullable(null));

        // when/then
        Assertions.assertThrows(NotFoundException.class, () -> updateCarUseCase.execute(CarRequest.builder().build()));
    }

}
