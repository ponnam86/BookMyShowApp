package com.sapient.movie.repository;

import com.sapient.movie.dto.MovieDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface MovieRepo extends JpaRepository<MovieDetails, Long> {

    @Query(value="select seat from order_history a inner join order_history_seat h on history_id=order_history_history_id \n" +
            "where movie_name=? and show_on_date=? and show_time=? and h.seat in ?", nativeQuery = true)
    List<Integer> checkAvailability(String name, LocalDate showDate, String showTime, List<String> seatNo);

    @Query(value="select history_id from order_history where movie_name=? and show_on_date=? and show_time=?", nativeQuery = true)
    List<Integer> getHistory(String name, LocalDate showDate, String showTime);

    @Query(value="select count(*) from order_history_seat where order_history_history_id in ?", nativeQuery = true)
    int getSeat(List<Integer> hid);
}
