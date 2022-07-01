package br.com.rentacar.rentacarapi.usecase.adapter;

import br.com.rentacar.rentacarapi.config.adapter.AbstractAdapter;
import br.com.rentacar.rentacarapi.controller.dto.MakeRequest;
import br.com.rentacar.rentacarapi.model.Make;
import org.springframework.stereotype.Component;

@Component
public class MakeAdapter extends AbstractAdapter<MakeRequest, Make> {
    public MakeAdapter() {
        super(Make.class);
    }
}
