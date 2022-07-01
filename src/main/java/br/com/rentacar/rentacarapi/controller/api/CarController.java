package br.com.rentacar.rentacarapi.controller.api;

import br.com.rentacar.rentacarapi.controller.dto.CarRequest;
import br.com.rentacar.rentacarapi.controller.dto.CarResponse;
import br.com.rentacar.rentacarapi.controller.dto.CustomPage;
import br.com.rentacar.rentacarapi.controller.dto.GetCarsRequest;
import br.com.rentacar.rentacarapi.usecase.car.CreateCarUseCase;
import br.com.rentacar.rentacarapi.usecase.car.DeleteCarUseCase;
import br.com.rentacar.rentacarapi.usecase.car.GetCarUseCase;
import br.com.rentacar.rentacarapi.usecase.car.GetListCarsUseCase;
import br.com.rentacar.rentacarapi.usecase.car.UpdateCarUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/v1/api/car", produces = MediaType.APPLICATION_JSON_VALUE)
public class CarController {

    private final CreateCarUseCase createCarUseCase;
    private final GetListCarsUseCase getListCarsUseCase;
    private final GetCarUseCase getCarUseCase;
    private final UpdateCarUseCase updateCarUseCase;
    private final DeleteCarUseCase deleteCarUseCase;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public CarResponse createCar(@RequestBody @Valid CarRequest carRequestDto) {
        return createCarUseCase.execute(carRequestDto);
    }

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public CustomPage<CarResponse> getCars(@PageableDefault(page = 0, size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable,
                                           @RequestParam(value = "onlyAvailable", required = false) Boolean onlyAvailable,
                                           @RequestParam(value = "model", required = false) String model) {
        return getListCarsUseCase.execute(GetCarsRequest
                                            .builder()
                                            .pageable(pageable)
                                            .onlyAvailable(onlyAvailable)
                                            .model(model)
                                            .build());
    }

    @GetMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CarResponse getCarById(@PathVariable("id") UUID id) {
        return getCarUseCase.execute(id);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public CarResponse updateCar(@PathVariable("id") UUID id,
                                            @RequestBody CarRequest carRequestDto) {
        carRequestDto.setId(id);
        return updateCarUseCase.execute(carRequestDto);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Void deleteCar(@PathVariable("id") UUID id) {
        deleteCarUseCase.execute(id);
        return null;
    }

}
