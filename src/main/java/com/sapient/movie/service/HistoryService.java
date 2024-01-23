package com.sapient.movie.service;

import com.sapient.movie.repository.HistoryRepo;
import com.sapient.movie.dto.Customer;
import com.sapient.movie.dto.OrderHistory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class HistoryService {

    @Autowired
    private HistoryRepo historyRepo;

    public OrderHistory saveHistory(OrderHistory history, Customer customer) {
        List<OrderHistory> orderHistories = new ArrayList<OrderHistory>();
        orderHistories.add(history);
        customer.setHistory( orderHistories);
        OrderHistory save = historyRepo.save(history);
        return save;
    }

    public List<OrderHistory> getAllHistory(long id){
        List<OrderHistory> list = historyRepo.getAllHistory(id);
        return list;
    }
}
