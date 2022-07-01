package br.com.rentacar.rentacarapi.usecase.car;

import br.com.rentacar.rentacarapi.controller.dto.CarResponse;
import br.com.rentacar.rentacarapi.controller.handler.exception.NotFoundException;
import br.com.rentacar.rentacarapi.model.Car;
import br.com.rentacar.rentacarapi.repository.CarRepository;
import br.com.rentacar.rentacarapi.usecase.adapter.CarResponseAdapter;
import br.com.rentacar.rentacarapi.usecase.car.GetCarUseCase;
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
class GetCarUseCaseTest {

    @Mock CarRepository carRepository;
    @Mock CarResponseAdapter carResponseAdapter;

    private GetCarUseCase getCarUseCase;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        this.getCarUseCase =
                new GetCarUseCase(carRepository, carResponseAdapter);
    }

    @Test
    void getCarShouldReturnSuccessfully() {
        // given
        final UUID uuid = UUID.randomUUID();
        Mockito.when(carRepository.findById(uuid))
               .thenReturn(Optional.of(new Car()));
        Mockito.when(carResponseAdapter.adapt(Mockito.any()))
               .thenReturn(CarResponse.builder().id(uuid).build());

        // when
        final CarResponse actual = getCarUseCase.execute(uuid);

        // then
        Assertions.assertNotNull(actual);
        Assertions.assertEquals(uuid, actual.getId());
    }

    @Test
    void getCarMissIdShouldReturnException() {
        // given
        final UUID uuid = UUID.randomUUID();
        Mockito.when(carRepository.findById(uuid))
                .thenReturn(Optional.ofNullable(null));

        // when/then
        Assertions.assertThrows(NotFoundException.class, () -> getCarUseCase.execute(uuid));
    }

}
