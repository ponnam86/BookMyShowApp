package com.sapient.movie.repository;

import com.sapient.movie.dto.OrderHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HistoryRepo extends JpaRepository<OrderHistory, Long> {
	
	@Query(value = "select * from order_history where customer_customer_id=? ORDER BY history_id DESC", nativeQuery = true)
	public List<OrderHistory> getAllHistory(long id);

}
