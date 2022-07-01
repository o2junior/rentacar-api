package br.com.rentacar.rentacarapi.controller.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Transient;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CarRequest {

    @JsonIgnore
    @Transient
    private UUID id;

    @NotNull
    @JsonAlias({"makeId", "make_id"})
    private Long makeId;

    @NotBlank
    private String model;

    private Boolean available;

}
