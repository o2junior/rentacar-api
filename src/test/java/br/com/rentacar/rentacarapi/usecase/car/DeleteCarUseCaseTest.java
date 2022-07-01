package br.com.rentacar.rentacarapi.usecase.car;

import br.com.rentacar.rentacarapi.repository.CarRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.config.EnableSpringDataWebSupport;

import java.util.UUID;

@SpringBootTest
@EnableSpringDataWebSupport
class DeleteCarUseCaseTest {

    @Mock CarRepository carRepository;
    private DeleteCarUseCase deleteCarUseCase;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        this.deleteCarUseCase =
                new DeleteCarUseCase(carRepository);
    }

    @Test
    void deleteCarShouldReturnSuccessfully() {
        // given
        final UUID uuid = UUID.randomUUID();

        // when
        final Void actual = deleteCarUseCase.execute(uuid);

        // then
        Assertions.assertNull(actual);
    }

}
