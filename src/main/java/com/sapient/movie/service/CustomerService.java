package com.sapient.movie.service;

import com.sapient.movie.repository.CustomerRepo;
import com.sapient.movie.dto.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepo customerRepo;

    public Customer login(String email, String password) {
        return customerRepo.findByEmailAndPassword(email, password);
    }

    public List<Customer> getAll(){
        List<Customer> findAll = customerRepo.findAll();
        return findAll;
    }

    public void save(Customer customer) {
        customerRepo.save(customer);
    }
}
