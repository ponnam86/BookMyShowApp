package com.sapient.movie.repository;

import com.sapient.movie.dto.Seat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
@Repository
public interface SeatRepo extends JpaRepository<Seat, Long> {
	
	@Query(value = "select * from seat where customer_customer_id=?", nativeQuery = true)
	public List<Seat> getAllSeat(long id);
	
	@Query(value = "select * from seat inner join seat_seat_no"
			+ " on seat.seat_id = seat_seat_no.seat_seat_id"
			+ " inner join current_date_operation"
			+ " on seat.operation_date_id = current_date_operation.date_id"
			+ " where show_date = ? and show_time = ?", nativeQuery = true)
	public List<Seat> getAllByDate(LocalDate date, String time);

}
