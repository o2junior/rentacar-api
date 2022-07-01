package br.com.rentacar.rentacarapi.usecase.car;

import br.com.rentacar.rentacarapi.controller.dto.CarRequest;
import br.com.rentacar.rentacarapi.controller.dto.CarResponse;
import br.com.rentacar.rentacarapi.controller.handler.exception.NotFoundException;
import br.com.rentacar.rentacarapi.model.Car;
import br.com.rentacar.rentacarapi.model.Make;
import br.com.rentacar.rentacarapi.repository.CarRepository;
import br.com.rentacar.rentacarapi.repository.MakeRepository;
import br.com.rentacar.rentacarapi.usecase.UseCase;
import br.com.rentacar.rentacarapi.usecase.adapter.CarAdapter;
import br.com.rentacar.rentacarapi.usecase.adapter.CarResponseAdapter;
import br.com.rentacar.rentacarapi.config.utils.BeanUtilsNonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UpdateCarUseCase implements UseCase<CarRequest, CarResponse> {

    private static final String LOG_PREFIX = "[UPDATE-CAR-USE-CASE]";
    private final CarRepository carRepository;
    private final MakeRepository makeRepository;
    private final CarAdapter carAdapter;
    private final CarResponseAdapter carResponseAdapter;

    @Override
    @Transactional
    public CarResponse execute(CarRequest carRequestDto) {
        Optional<Car> carOptional = carRepository.findById(carRequestDto.getId());

        if(!carOptional.isPresent()) {
            log.info("{} - Not Found id {}.", LOG_PREFIX, carRequestDto.getId());
            throw new NotFoundException("Not Found id.");
        }

        Make make = validateMakeId(carRequestDto.getMakeId());
        BeanUtilsNonNull.copyNonNullProperties(carRequestDto, carOptional.get());
        if(make != null) carOptional.get().setMake(make);

        Car carSaved = carRepository.save(carOptional.get());
        log.debug("{} - Car Updated: {}.", LOG_PREFIX, carSaved);
        return carResponseAdapter.adapt(carSaved);
    }

    private Make validateMakeId(Long id) {
        if(id == null) return null;
        Optional<Make> makeOptional = makeRepository.findById(id);
        if(!makeOptional.isPresent()) {
            log.info("{} - Make Id doesn't exists.", LOG_PREFIX);
            throw new NotFoundException("Make Id doesn't exists.");
        }
        return makeOptional.get();
    }

}
