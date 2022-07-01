package br.com.rentacar.rentacarapi.usecase.car;

import br.com.rentacar.rentacarapi.controller.dto.CarResponse;
import br.com.rentacar.rentacarapi.controller.handler.exception.NotFoundException;
import br.com.rentacar.rentacarapi.repository.CarRepository;
import br.com.rentacar.rentacarapi.usecase.UseCase;
import br.com.rentacar.rentacarapi.usecase.adapter.CarResponseAdapter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class GetCarUseCase implements UseCase<UUID, CarResponse> {

    private static final String LOG_PREFIX = "[GET-CAR-USE-CASE]";
    private final CarRepository carRepository;
    private final CarResponseAdapter carResponseAdapter;

    @Override
    public CarResponse execute(UUID id) {
        var carOptional = carRepository.findById(id);
        if(!carOptional.isPresent())
            throw new NotFoundException("Not Found id.");
        log.info("{} - Car: {}.", LOG_PREFIX, carOptional.get());
        return carResponseAdapter.adapt(carOptional.get());
    }

}
