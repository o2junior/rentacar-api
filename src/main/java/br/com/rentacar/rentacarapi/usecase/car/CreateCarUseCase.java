package br.com.rentacar.rentacarapi.usecase.car;

import br.com.rentacar.rentacarapi.config.security.AuthService;
import br.com.rentacar.rentacarapi.config.security.JwtUser;
import br.com.rentacar.rentacarapi.controller.dto.CarRequest;
import br.com.rentacar.rentacarapi.controller.dto.CarResponse;
import br.com.rentacar.rentacarapi.controller.handler.exception.ForbiddenException;
import br.com.rentacar.rentacarapi.controller.handler.exception.NotFoundException;
import br.com.rentacar.rentacarapi.model.Car;
import br.com.rentacar.rentacarapi.model.Make;
import br.com.rentacar.rentacarapi.repository.CarRepository;
import br.com.rentacar.rentacarapi.repository.MakeRepository;
import br.com.rentacar.rentacarapi.usecase.UseCase;
import br.com.rentacar.rentacarapi.usecase.adapter.CarAdapter;
import br.com.rentacar.rentacarapi.usecase.adapter.CarResponseAdapter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class CreateCarUseCase implements UseCase<CarRequest, CarResponse> {

    private static final String LOG_PREFIX = "[CREATE-CAR-USE-CASE]";
    private static final String UNKNOWN_USER = "UNKNOWN_USER";
    public static final int RETRIES = 3;

    private final CarRepository carRepository;
    private final MakeRepository makeRepository;
    private final AuthService authService;
    private final CarAdapter carAdapter;
    private final CarResponseAdapter carResponseAdapter;

    @Override
    @Transactional
    public CarResponse execute(CarRequest carRequestDto) {
        Optional<JwtUser> jwtUser = authService.getJwtUser();
        Make make = validateMakeId(carRequestDto.getMakeId());
        Car car = saveCar(carRequestDto, make, jwtUser);
        return carResponseAdapter.adapt(car);
    }

    private Make validateMakeId(Long id) {
        Optional<Make> makeOptional = makeRepository.findById(id);
        if(!makeOptional.isPresent()) {
            log.info("{} - Make Id doesn't exists", LOG_PREFIX);
            throw new NotFoundException("Make Id doesn't exists.");
        }
        return makeOptional.get();
    }

    private Car saveCar(CarRequest carRequestDto, Make make, Optional<JwtUser> jwtUser) {
        Car car = carAdapter.adapt(carRequestDto);
        car.setId(getValidUUID());
        car.setMake(make);
        String name = jwtUser.isPresent() ? jwtUser.get().getName() : UNKNOWN_USER;
        car.setCreatedByName(name);
        return carRepository.save(car);
    }

    private UUID getValidUUID() {
        UUID randomId = UUID.randomUUID();
        boolean isUnavailable = carRepository.existsById(randomId);
        if (isUnavailable) {
            for (int i = 0; i < RETRIES; i++) {
                log.info("{} - UUID[{}] already used. Generating another...", LOG_PREFIX, randomId.toString());
                randomId = UUID.randomUUID();
                if (!carRepository.existsById(randomId)){
                    isUnavailable = false;
                    break;
                }
            }
            if(isUnavailable)
                throw new ForbiddenException("Fail to create Car. Contact Administrators.");
        }
        return randomId;
    }
}
