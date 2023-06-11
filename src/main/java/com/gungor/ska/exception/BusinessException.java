package com.gungor.ska.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatusCode;

@Getter
@Setter
@AllArgsConstructor
public class BusinessException extends RuntimeException{

    private String message;

    private HttpStatusCode httpStatusCode;
}
