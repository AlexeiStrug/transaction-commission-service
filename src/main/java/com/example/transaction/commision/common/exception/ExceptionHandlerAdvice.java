package com.example.transaction.commision.common.exception;

import com.example.transaction.commision.common.response.ErrorResponse;
import feign.RetryableException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.*;

@Slf4j
@ControllerAdvice
public class ExceptionHandlerAdvice {

    private static final String BAD_REQUEST_MESSAGE = "Bad request error";
    private static final String FEIGN_SERVICE_UNAVAILABLE_MESSAGE = "Feign  Service Unavailable. Contact administrator";
    private static final String INTERNAL_SERVER_ERROR_MSG = "Internal server error. Contact administrator";

    @ExceptionHandler({BadRequestException.class})
    @ResponseStatus(BAD_REQUEST)
    @ResponseBody
    ResponseEntity handleClientExceptions(Exception ex) {
        log.warn("Bad request warn", ex);
        return new ResponseEntity(getErrorResponse(BAD_REQUEST_MESSAGE, ex.getMessage(), BAD_REQUEST.value()), BAD_REQUEST);
    }

    @ExceptionHandler(RetryableException.class)
    @ResponseStatus(SERVICE_UNAVAILABLE)
    @ResponseBody
    public ResponseEntity handleFeignErrors(Exception ex) {
        log.error("Feign Service Unavailable error", ex);
        return new ResponseEntity(getErrorResponse(FEIGN_SERVICE_UNAVAILABLE_MESSAGE, ex.getMessage(), SERVICE_UNAVAILABLE.value()), SERVICE_UNAVAILABLE);
    }


    @ExceptionHandler(Exception.class)
    @ResponseStatus(INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ResponseEntity handleInternalErrors(Exception ex) {
        log.error("Internal server error", ex);
        return new ResponseEntity(getErrorResponse(INTERNAL_SERVER_ERROR_MSG, ex.getMessage(), INTERNAL_SERVER_ERROR.value()), INTERNAL_SERVER_ERROR);
    }

    private ErrorResponse getErrorResponse(String statusName, String description, int statusCode) {
        return ErrorResponse.builder()
                .message(statusName)
                .description(description)
                .statusCode(statusCode)
                .build();
    }
}