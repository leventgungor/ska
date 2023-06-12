package com.gungor.ska.exception;

import com.gungor.ska.dto.global.APIResponse;
import com.gungor.ska.dto.global.ErrorDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    private static final String HATA = "Hatalı bir durum meyadana geldi.";

    @ExceptionHandler({RuntimeException.class, NullPointerException.class})
    public ResponseEntity<Object> handleRuntimeExceptions(RuntimeException ex) {

        log.error(ex.getMessage());

        APIResponse<ErrorDTO> response = new APIResponse<>();
        response.setStatus(HATA);
        response.setErrors(Collections.singletonList(new ErrorDTO("", "Sunucu hatası meyadana geldi.")));

        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler({BusinessException.class})
    public ResponseEntity<Object> handleOtherExceptions(BusinessException ex) {

        log.error(ex.getMessage());

        APIResponse<ErrorDTO> response = new APIResponse<>();
        response.setStatus(HATA);
        response.setErrors(Collections.singletonList(new ErrorDTO("", ex.getMessage())));

        return new ResponseEntity<>(response, ex.getHttpStatusCode());
    }

    @ExceptionHandler( DataAccessException.class)
    public ResponseEntity<Object> handleDataAccessExceptions(DataAccessException ex) {

        log.error(ex.getMessage());

        APIResponse<ErrorDTO> response = new APIResponse<>();
        response.setStatus(HATA);
        response.setErrors(Collections.singletonList(new ErrorDTO("", "Veritabanına erişimde bir problem yaşandı.")));

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<Object> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException ex) {

        log.error(ex.getMessage());

        APIResponse<ErrorDTO> response = new APIResponse<>();
        response.setStatus(HATA);
        response.setErrors(Collections.singletonList(new ErrorDTO("", "Bu urlde kullanılan metod desteklenmemektedir.")));

        return new ResponseEntity<>(response, HttpStatus.METHOD_NOT_ALLOWED);
    }

    @ExceptionHandler({MethodArgumentNotValidException.class, MissingServletRequestParameterException.class, MissingPathVariableException.class})
    public ResponseEntity<Object> handleValidationExceptions(Exception exception) {

        APIResponse<ErrorDTO> response = new APIResponse<>();
        response.setStatus(HATA);

        List<ErrorDTO> errors = new ArrayList<>();
        if (exception instanceof MethodArgumentNotValidException ex) {

            ex.getBindingResult().getAllErrors().forEach(error -> {
                String fieldName = ((FieldError) error).getField();
                String errorMessage = error.getDefaultMessage();
                errors.add(new ErrorDTO(fieldName, errorMessage));
            });

        } else if (exception instanceof MissingServletRequestParameterException ex) {

            String parameterName = ex.getParameterName();
            errors.add(new ErrorDTO("", "Eksik parametre: " + parameterName));

        } else if (exception instanceof MissingPathVariableException ex) {

            String variableName = ex.getVariableName();
            errors.add(new ErrorDTO("", "Eksik path variable: " + variableName));
        }

        response.setErrors(errors);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

}
