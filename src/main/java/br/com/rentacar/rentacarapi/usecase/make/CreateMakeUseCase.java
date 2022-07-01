package br.com.rentacar.rentacarapi.usecase.make;

import br.com.rentacar.rentacarapi.config.security.AuthService;
import br.com.rentacar.rentacarapi.config.security.JwtUser;
import br.com.rentacar.rentacarapi.controller.dto.MakeRequest;
import br.com.rentacar.rentacarapi.controller.dto.MakeResponse;
import br.com.rentacar.rentacarapi.controller.handler.exception.BadRequestException;
import br.com.rentacar.rentacarapi.model.Make;
import br.com.rentacar.rentacarapi.repository.MakeRepository;
import br.com.rentacar.rentacarapi.usecase.UseCase;
import br.com.rentacar.rentacarapi.usecase.adapter.MakeAdapter;
import br.com.rentacar.rentacarapi.usecase.adapter.MakeResponseAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CreateMakeUseCase implements UseCase<MakeRequest, MakeResponse> {

    private final MakeRepository makeRepository;
    private final AuthService authService;
    private final MakeAdapter makeAdapter;
    private final MakeResponseAdapter makeResponseAdapter;

    @Override
    public MakeResponse execute(MakeRequest makeRequestDto) {
        Optional<JwtUser> jwtUser = authService.getJwtUser();
        validateMakeName(makeRequestDto.getName());
        Make make = saveMake(makeRequestDto, jwtUser);
        return makeResponseAdapter.adapt(make);
    }

    private void validateMakeName(String name) {
        if (makeRepository.existsByName(name))
            throw new BadRequestException("Make name is already defined.");
    }

    private Make saveMake(MakeRequest makeRequestDto, Optional<JwtUser> jwtUser) {
        Make make = makeAdapter.adapt(makeRequestDto);
        var name = jwtUser.isPresent() ? jwtUser.get().getName() : "UNKNOWN";
        make.setCreatedByName(name);
        Make makeSaved = makeRepository.save(make);
        return makeSaved;
    }
}
