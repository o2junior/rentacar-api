package br.com.rentacar.rentacarapi.usecase.adapter;

import br.com.rentacar.rentacarapi.config.adapter.AbstractAdapter;
import br.com.rentacar.rentacarapi.controller.dto.MakeResponse;
import br.com.rentacar.rentacarapi.model.Make;
import org.springframework.stereotype.Component;

@Component
public class MakeResponseAdapter extends AbstractAdapter<Make, MakeResponse> {
    public MakeResponseAdapter() {
        super(MakeResponse.class);
    }
}
