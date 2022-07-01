package br.com.rentacar.rentacarapi.usecase.adapter;

import br.com.rentacar.rentacarapi.adapter.AbstractAdapter;
import br.com.rentacar.rentacarapi.controller.dto.CarRequest;
import br.com.rentacar.rentacarapi.model.Car;
import org.springframework.stereotype.Component;

@Component
public class CarAdapter extends AbstractAdapter<CarRequest, Car> {
    public CarAdapter() {
        super(Car.class);
    }
}
