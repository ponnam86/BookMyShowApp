package com.sapient.movie.service;

import com.sapient.movie.repository.SeatRepo;
import com.sapient.movie.dto.CurrentDateOperation;
import com.sapient.movie.dto.Customer;
import com.sapient.movie.dto.Seat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class SeatService {

    @Autowired
    private SeatRepo seatRepo;

    public int saveSeat(Seat seat, Customer customer, LocalDate date, String time){
        List<Seat> list = new ArrayList<Seat>();
        list.add(seat);
        customer.setSeat(list);
        CurrentDateOperation cdo= new CurrentDateOperation();
        cdo.setShowDate(date);
        cdo.setShowTime(time);
        cdo.setSeat(list);

        seat.setOperation(cdo);
        seat.setOperation(cdo);
        seat.setCustomer(customer);
        Seat save = seatRepo.save(seat);
        return 1;
    }
}
