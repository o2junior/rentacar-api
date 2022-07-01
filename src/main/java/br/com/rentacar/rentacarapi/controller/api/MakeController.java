package br.com.rentacar.rentacarapi.controller.api;

import br.com.rentacar.rentacarapi.controller.dto.CustomPage;
import br.com.rentacar.rentacarapi.controller.dto.MakeRequest;
import br.com.rentacar.rentacarapi.controller.dto.MakeResponse;
import br.com.rentacar.rentacarapi.usecase.make.CreateMakeUseCase;
import br.com.rentacar.rentacarapi.usecase.make.GetListMakesUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/v1/api/make", produces = MediaType.APPLICATION_JSON_VALUE)
public class MakeController {

    private final CreateMakeUseCase createMakeUseCase;
    private final GetListMakesUseCase getListMakesUseCase;

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public CustomPage<MakeResponse> getMakes(@PageableDefault(page = 0, size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        return getListMakesUseCase.execute(pageable);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public MakeResponse createMake(@RequestBody @Valid MakeRequest makeDto) {
        return createMakeUseCase.execute(makeDto);
    }
}
