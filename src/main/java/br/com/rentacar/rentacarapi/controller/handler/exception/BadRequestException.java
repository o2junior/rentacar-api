package br.com.rentacar.rentacarapi.controller.handler.exception;

public class BadRequestException extends RuntimeException {

    public BadRequestException(String message) {
        super(message);
    }

}
