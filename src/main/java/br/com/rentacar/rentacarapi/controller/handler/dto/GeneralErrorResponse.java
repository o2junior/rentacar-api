package br.com.rentacar.rentacarapi.controller.handler.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.RequiredArgsConstructor;

@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class GeneralErrorResponse {
    private String detail;
}
