package br.com.rentacar.rentacarapi.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Pageable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetCarsRequest {
    private Pageable pageable;
    private Boolean onlyAvailable;
    private String model;
}
