package br.com.rentacar.rentacarapi.controller.handler.exception;

public class NotFoundException extends RuntimeException {

    public NotFoundException(String message) {
        super(message);
    }

}
