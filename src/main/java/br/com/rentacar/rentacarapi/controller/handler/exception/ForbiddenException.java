package br.com.rentacar.rentacarapi.controller.handler.exception;

public class ForbiddenException extends RuntimeException {

    public ForbiddenException(String message) {
        super(message);
    }

}
