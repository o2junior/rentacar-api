package br.com.rentacar.rentacarapi.usecase.car;

import br.com.rentacar.rentacarapi.controller.dto.CarResponse;
import br.com.rentacar.rentacarapi.controller.dto.CustomPage;
import br.com.rentacar.rentacarapi.controller.dto.GetCarsRequest;
import br.com.rentacar.rentacarapi.controller.dto.MakeResponse;
import br.com.rentacar.rentacarapi.model.Car;
import br.com.rentacar.rentacarapi.model.Make;
import br.com.rentacar.rentacarapi.repository.CarRepository;
import br.com.rentacar.rentacarapi.usecase.adapter.CarResponseAdapter;
import br.com.rentacar.rentacarapi.usecase.adapter.MakeResponseAdapter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.config.EnableSpringDataWebSupport;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

@SpringBootTest
@EnableSpringDataWebSupport
class GetListCarsUseCaseTest {

    @Mock CarRepository carRepository;
    @Mock CarResponseAdapter carResponseAdapter;
    @Mock MakeResponseAdapter makeResponseAdapter;

    private GetListCarsUseCase getListCarsUseCase;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        this.getListCarsUseCase =
                new GetListCarsUseCase(carRepository, carResponseAdapter, makeResponseAdapter);
    }


    @Test
    void getListCarsShouldReturnSuccessfully() {
        // given
        PageRequest pageable = PageRequest.of(1, 10, Sort.by(Sort.Direction.DESC, "createdAt"));
        List<Car> listCars = List.of(new Car(), new Car());
        Page<Car> cars = new PageImpl(listCars, pageable, 2L);

        when(carRepository.findAll(pageable))
                .thenReturn(cars);
        when(carResponseAdapter.adapt(new Car()))
                .thenReturn(CarResponse.builder().build());
        when(makeResponseAdapter.adapt(new Make()))
                .thenReturn(MakeResponse.builder().build());

        // when
        final CustomPage<CarResponse> actual = getListCarsUseCase.execute(GetCarsRequest.builder().pageable(pageable).build());

        // then
        Assertions.assertNotNull(actual);
        Assertions.assertEquals(2L, actual.getContent().size());
        Assertions.assertTrue(actual.getContent().stream().allMatch(c -> c instanceof CarResponse));
    }

    @Test
    void getEmptyListCarsShouldReturnSuccessfully() {
        // given
        PageRequest pageable = PageRequest.of(1, 10, Sort.by(Sort.Direction.DESC, "createdAt"));
        List<Car> listCars = new ArrayList<>();
        Page<Car> cars = new PageImpl(listCars, pageable, 0L);

        when(carRepository.findAll(pageable))
                .thenReturn(cars);
        when(carResponseAdapter.adapt(new Car()))
                .thenReturn(CarResponse.builder().build());

        // when
        final CustomPage<CarResponse> actual = getListCarsUseCase.execute(GetCarsRequest.builder().pageable(pageable).build());

        // then
        Assertions.assertNotNull(actual);
        Assertions.assertEquals(0L, actual.getContent().size());
    }

}
