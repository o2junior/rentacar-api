package br.com.rentacar.rentacarapi.usecase.car;

import br.com.rentacar.rentacarapi.controller.dto.CarResponse;
import br.com.rentacar.rentacarapi.controller.dto.CustomPage;
import br.com.rentacar.rentacarapi.controller.dto.GetCarsRequest;
import br.com.rentacar.rentacarapi.model.Car;
import br.com.rentacar.rentacarapi.repository.CarRepository;
import br.com.rentacar.rentacarapi.usecase.UseCase;
import br.com.rentacar.rentacarapi.usecase.adapter.CarResponseAdapter;
import br.com.rentacar.rentacarapi.usecase.adapter.MakeResponseAdapter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class GetListCarsUseCase implements UseCase<GetCarsRequest, CustomPage<CarResponse>> {

    private static final String LOG_PREFIX = "[GET-CARS-USE-CASE]";
    private final CarRepository carRepository;
    private final CarResponseAdapter carResponseAdapter;
    private final MakeResponseAdapter makeResponseAdapter;

    @Override
    public CustomPage<CarResponse> execute(GetCarsRequest getCarsRequest) {
        var pageable = getCarsRequest.getPageable();
        var onlyAvailable = getCarsRequest.getOnlyAvailable();
        var model = getCarsRequest.getModel();

        Page<Car> page;
        if(Objects.nonNull(onlyAvailable))
            page = carRepository.findAllByAvailable(onlyAvailable, pageable);
        else if(Objects.nonNull(model))
            page = carRepository.findAllByModel(model, pageable);
        else
            page = carRepository.findAll(pageable);
        Page<CarResponse> pageResponse = page.map(car -> {
            var carResponse = carResponseAdapter.adapt(car);
            carResponse.setMake(makeResponseAdapter.adapt(car.getMake()));
            return carResponse;
        });
        log.info("{} - Cars Listed.", LOG_PREFIX);
        return new CustomPage<>(pageResponse);
    }

}
