package com.sapient.movie.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class MovieExceptionHandler{

    @ExceptionHandler(value = MovieNotFoundException.class)
    public ResponseEntity<Object> exception(MovieNotFoundException exception){
        return new ResponseEntity<>("Movie not found, please check!", HttpStatus.OK);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Object> exception(UserNotFoundException exception){
        return new ResponseEntity<>("User not found, please register!", HttpStatus.OK);
    }

    @ExceptionHandler(MissingUserCredentialException.class)
    public ResponseEntity<Object> exception(MissingUserCredentialException exception){
        return new ResponseEntity<>("Credentials are wrong, please check!", HttpStatus.OK);
    }

    @ExceptionHandler(AlreadyReservedException.class)
    public ResponseEntity<Object> exception(AlreadyReservedException exception){
        return new ResponseEntity<>("Choosen seats were already booked, please select different seats!", HttpStatus.CONFLICT);
    }

}
