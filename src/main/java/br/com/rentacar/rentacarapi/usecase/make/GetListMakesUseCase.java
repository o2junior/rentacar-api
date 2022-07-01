package br.com.rentacar.rentacarapi.usecase.make;

import br.com.rentacar.rentacarapi.controller.dto.CustomPage;
import br.com.rentacar.rentacarapi.controller.dto.MakeResponse;
import br.com.rentacar.rentacarapi.repository.MakeRepository;
import br.com.rentacar.rentacarapi.usecase.UseCase;
import br.com.rentacar.rentacarapi.usecase.adapter.MakeResponseAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetListMakesUseCase implements UseCase<Pageable, CustomPage<MakeResponse>> {

    private final MakeRepository makeRepository;
    private final MakeResponseAdapter makeResponseAdapter;

    @Override
    public CustomPage<MakeResponse> execute(Pageable pageable) {
        Page<MakeResponse> page = makeRepository.findAll(pageable)
                                                .map(makeResponseAdapter::adapt);
        return new CustomPage<>(page);
    }
}
