package br.com.rentacar.rentacarapi.controller.handler;

import br.com.rentacar.rentacarapi.controller.handler.dto.GeneralErrorResponse;
import br.com.rentacar.rentacarapi.controller.handler.exception.BadRequestException;
import br.com.rentacar.rentacarapi.controller.handler.exception.NotFoundException;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Map;

@ControllerAdvice(annotations = Controller.class)
@NoArgsConstructor
public class GlobalControllerExceptionHandler {

	@ExceptionHandler(Exception.class)
	public ResponseEntity<Map<String, GeneralErrorResponse>> handleException(final Exception exception) {
		return ResponseEntity.status(HttpStatus.FORBIDDEN)
				.body(Map.of("error", GeneralErrorResponse
						.builder()
						.detail(exception.getMessage())
						.build()));
	}

	@ExceptionHandler(NotFoundException.class)
	public ResponseEntity<Map<String, GeneralErrorResponse>> handleNotFoundException(final NotFoundException exception) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND)
				.body(Map.of("error", GeneralErrorResponse
						.builder()
						.detail(exception.getMessage())
						.build()));
	}

	@ExceptionHandler({BadRequestException.class})
	public ResponseEntity<Map<String, GeneralErrorResponse>> handleBadRequestException(final BadRequestException exception) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
				.body(Map.of("error", GeneralErrorResponse
						.builder()
						.detail(exception.getMessage())
						.build()));
	}

	@ExceptionHandler({MethodArgumentNotValidException.class})
	public ResponseEntity<Map<String, GeneralErrorResponse>> handleMethodArgumentNotValidException(final MethodArgumentNotValidException exception) {
		StringBuilder message = new StringBuilder("Validation failed for field");
		exception.getBindingResult()
				 .getAllErrors()
				 .forEach(err -> message.append(", [ ")
										 .append(((FieldError)err).getField())
										 .append(" : cannot be ")
										 .append(((FieldError)err).getRejectedValue())
										 .append(" ]"));
		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
				.body(Map.of("error", GeneralErrorResponse
						.builder()
						.detail(message.toString())
						.build()));
	}

}