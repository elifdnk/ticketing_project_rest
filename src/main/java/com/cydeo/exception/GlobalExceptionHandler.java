package com.cydeo.exception;

import com.cydeo.annotation.DefaultExceptionMessage;
import com.cydeo.dto.DefaultExceptionMessageDto;

import com.cydeo.dto.ResponseWrapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.HandlerMethod;

import java.lang.reflect.Method;
import java.util.Optional;

@RestControllerAdvice // is used in Spring to centrally handle exceptions across multiple @Controller or @RestController classes in a RESTful service.
public class GlobalExceptionHandler { //whenever exception happen this class will executed

    @ExceptionHandler(TicketingProjectException.class) //whenever exceptions happen belongs to TicketingProjectExceptions execute this code
    public ResponseEntity<ResponseWrapper> serviceException(TicketingProjectException se){
        String message = se.getMessage();
        return new ResponseEntity<>(ResponseWrapper.builder().success(false).code(HttpStatus.CONFLICT.value()).message(message).build(),HttpStatus.CONFLICT); //JSON output modification
    }

    @ExceptionHandler(AccessDeniedException.class)//whenever exceptions happen belongs to AccessDeniedException execute this code
    public ResponseEntity<ResponseWrapper> accessDeniedException(AccessDeniedException se){
        String message = se.getMessage();
        return new ResponseEntity<>(ResponseWrapper.builder().success(false).code(HttpStatus.FORBIDDEN.value()).message(message).build(),HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler({Exception    .class, RuntimeException.class, Throwable.class, BadCredentialsException.class})
    //whenever exceptions happen belongs to these class execute this code
    public ResponseEntity<ResponseWrapper> genericException(Throwable e, HandlerMethod handlerMethod) {

        Optional<DefaultExceptionMessageDto> defaultMessage = getMessageFromAnnotation(handlerMethod.getMethod());
        if (defaultMessage.isPresent() && !ObjectUtils.isEmpty(defaultMessage.get().getMessage())) {
            //if you have @DefaultExceptionMessage annotation build this code.
            ResponseWrapper response = ResponseWrapper
                    .builder()
                    .success(false)
                    .message(defaultMessage.get().getMessage())
                    .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .build();
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        //if do not have annotation this code will be executed
        return new ResponseEntity<>(ResponseWrapper.builder().success(false).message("Action failed: An error occurred!").code(HttpStatus.INTERNAL_SERVER_ERROR.value()).build(), HttpStatus.INTERNAL_SERVER_ERROR);
    }


    private Optional<DefaultExceptionMessageDto> getMessageFromAnnotation(Method method) {
        DefaultExceptionMessage defaultExceptionMessage = method.getAnnotation(DefaultExceptionMessage.class);
        if (defaultExceptionMessage != null) {
            DefaultExceptionMessageDto defaultExceptionMessageDto = DefaultExceptionMessageDto
                    .builder()
                    .message(defaultExceptionMessage.defaultMessage())
                    .build();
            return Optional.of(defaultExceptionMessageDto);
        }
        return Optional.empty();
    }
}