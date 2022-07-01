package br.com.rentacar.rentacarapi.usecase.adapter;

import br.com.rentacar.rentacarapi.adapter.AbstractAdapter;
import br.com.rentacar.rentacarapi.controller.dto.CarResponse;
import br.com.rentacar.rentacarapi.model.Car;
import org.springframework.stereotype.Component;

@Component
public class CarResponseAdapter extends AbstractAdapter<Car, CarResponse> {
    public CarResponseAdapter() {
        super(CarResponse.class);
    }
}
