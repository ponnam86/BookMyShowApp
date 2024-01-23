package com.sapient.movie.repository;

import com.sapient.movie.dto.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepo extends JpaRepository<Customer, Long> {

	@Query(value = "select * from customer where email=? and password=? LIMIT 1", nativeQuery = true)
	Customer findByEmailAndPassword(String email, String password);

}
