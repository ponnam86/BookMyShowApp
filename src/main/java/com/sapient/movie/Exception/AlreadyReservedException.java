package com.sapient.movie.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

public class AlreadyReservedException extends RuntimeException{
    private String message;
    public AlreadyReservedException(List<Integer> i) {
        super();
        this.message = "Choosen seats ["+i.toString()+"] were already booked, please select different seats!";
    }
}
