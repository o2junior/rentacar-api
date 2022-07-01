package br.com.rentacar.rentacarapi.usecase;

import org.springframework.stereotype.Component;

@Component
public interface UseCase<Input, Output> {

    Output execute(Input entry);

}
