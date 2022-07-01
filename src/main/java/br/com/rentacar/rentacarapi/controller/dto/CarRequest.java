package br.com.rentacar.rentacarapi.controller.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CarRequest {

    @JsonIgnore
    private UUID id;

    @NotNull
    @JsonAlias({"makeId", "make_id"})
    private Long makeId;

    @NotBlank
    private String model;

    private Boolean available;

}
