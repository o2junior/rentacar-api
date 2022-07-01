package br.com.rentacar.rentacarapi.controller.dto;

import br.com.rentacar.rentacarapi.model.Car;
import br.com.rentacar.rentacarapi.model.Make;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CarResponse {

    private UUID id;
    private MakeResponse make;
    private String model;
    private Boolean available;

}
