package com.blogapplication.exception;


import com.blogapplication.payload.ErrorDetails;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.nio.file.AccessDeniedException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


//ControllerAdvice annotation is used to handle the exceptions globally and the classes annotated with ControllerAdvice are
// automatically available for component scan because inside they are annotated with @Component and can be used as a spring bean.
@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    //we can handle specific exceptions or global exceptions from this class.



        @ExceptionHandler(ResourceNotFoundException.class)
         public ResponseEntity<ErrorDetails> handleResourceNotFoundException(ResourceNotFoundException
                                                                             exception, WebRequest webRequest)
         {
             //we are not going to send whole details of the request to the client about the request that's why false.
             ErrorDetails errorDetails = new ErrorDetails(new Date(),exception.getMessage(),webRequest.getDescription(false));
             return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);

         }




    @ExceptionHandler(BlogAPIException.class)
    public ResponseEntity<ErrorDetails> handleResourceNotFoundException(BlogAPIException
                                                                                exception, WebRequest webRequest)
    {
        //we are not going to send whole details of the request to the client about the request that's why false.
        ErrorDetails errorDetails = new ErrorDetails(new Date(),exception.getMessage(),webRequest.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDetails> handleResourceNotFoundException(Exception
                                                                                exception, WebRequest webRequest)
    {
        //we are not going to send whole details of the request to the client about the request that's why false.
        ErrorDetails errorDetails = new ErrorDetails(new Date(),exception.getMessage(),webRequest.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
       // return super.handleMethodArgumentNotValid(ex, headers, status, request);
        Map<String,String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error)->{
            String fieldName = ((FieldError)error).getField();
            String message = error.getDefaultMessage();
            errors.put(fieldName,message);
        });

        return new ResponseEntity<>(errors,HttpStatus.BAD_REQUEST);
        }


    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorDetails> handleResourceNotFoundException(AccessDeniedException
                                                                                exception, WebRequest webRequest)
    {
        //we are not going to send whole details of the request to the client about the request that's why false.
        ErrorDetails errorDetails = new ErrorDetails(new Date(),exception.getMessage(),webRequest.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.UNAUTHORIZED);
    }
}
