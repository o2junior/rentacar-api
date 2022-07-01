package br.com.rentacar.rentacarapi.controller.handler.dto;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Builder
@Data
public class GeneralErrorResponse implements Serializable {
    private String detail;
}
