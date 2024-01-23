package com.sapient.movie.controller;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.sapient.movie.Exception.AlreadyReservedException;
import com.sapient.movie.Exception.MissingUserCredentialException;
import com.sapient.movie.Exception.MovieNotFoundException;
import com.sapient.movie.Exception.UserNotFoundException;
import com.sapient.movie.dto.Customer;
import com.sapient.movie.dto.MovieDetails;
import com.sapient.movie.dto.OrderHistory;
import com.sapient.movie.dto.Seat;
import com.sapient.movie.service.*;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@RestController
@RequestMapping("/movie")
@Validated
@EnableHystrix
public class MovieController {

	@Autowired
	private MovieService movieService;

	@Autowired
	private CustomerService customerService;

	@Autowired
	private SeatService seatService;

	@Autowired
	private HistoryService historyService;

	@Autowired
	private NotificationService notificationService;

	Logger logger = LoggerFactory.getLogger(MovieController.class);

	//	User save process
	@PostMapping("/register")
	public ResponseEntity<String> save(@Valid @RequestBody Customer customer) {
		customerService.save(customer);
		logger.info("Customer registered");
		return ResponseEntity.ok("Customer registered");
	}

	@GetMapping
	public List<MovieDetails> getMovies(@RequestParam(required = false) Integer id,@RequestParam(required = false) String movieName,
										@RequestParam(required = false) @DateTimeFormat(iso= DateTimeFormat.ISO.DATE) LocalDate date,
										@RequestParam(required = false) String city,
										@RequestParam(required = false) String language,
										@RequestParam(required = false) String genre) {

		List<MovieDetails> allMovie = movieService.getAllMovie(id, movieName, date, city, genre, language);
		if(allMovie.isEmpty())throw new MovieNotFoundException();
		return allMovie;

	}

	@PostMapping
	public void addMovie(@RequestBody MovieDetails movieDetails) {
		movieService.addMovie(movieDetails);
	}

	@PutMapping("/{id}")
	public void updateMovie(@PathVariable("id") Integer id, @RequestBody MovieDetails movieDetails){
		movieService.updateMovie(id, movieDetails);
	}

	//	Seat booking process
	@PostMapping("/book-seat")
	@HystrixCommand(fallbackMethod = "fallback", commandProperties = {
			@HystrixProperty(name="execution.isolation.thread.timeoutInMilliseconds", value="10")
	})
	public ResponseEntity<String> bookSeat(@RequestHeader String email, @RequestHeader String password, @RequestBody Seat seat, @RequestParam("movieId") Integer movieId,
								   @RequestParam("showDate") LocalDate showDate, @RequestParam("showTime") String showTime) {
		Customer customer = null;
		if(email.isEmpty() && password.isEmpty()){
			throw new MissingUserCredentialException();
		} else {

			if(!email.isEmpty() && !password.isEmpty()){
				customer = customerService.login(email, password);
				if (customer == null) {
					throw new UserNotFoundException();
				}
			}else{
				throw new MissingUserCredentialException();
			}
		}

		MovieDetails movieDetails = movieService.findById(movieId);
		if(movieDetails == null){
			throw new MovieNotFoundException();
		}else{
			List<Integer> i=movieService.checkAvailability(movieDetails.getMovieName(), showDate, showTime, seat.getSeatNo() );
			if(!i.isEmpty())throw new AlreadyReservedException(i);
		}

        LocalDate currentDate = LocalDate.now();
        ZoneId defaultZoneId = ZoneId.systemDefault();
        Date todayDate = Date.from(currentDate.atStartOfDay(defaultZoneId).toInstant());

        if (((showDate.isAfter(currentDate)) || (showDate.equals(currentDate)))
                && (showDate.isBefore(currentDate.plusMonths(1)) || showDate.equals(currentDate.plusMonths(1)))) {
//				Date date2 = Date.from(showDate.atStartOfDay(defaultZoneId).toInstant());
            List<Double> price = new ArrayList<Double>();
            double sum = 0;
            double p = movieDetails.getPrice();
            //modify logic for 50% or any discount
            for (String s : seat.getSeatNo()) {
                sum = sum + p;
                price.add(p);
            }
            seat.setTotal(sum);
            seat.setPrice(price);

            OrderHistory history = new OrderHistory(seat.getSeatNo(), price, sum, movieDetails.getMovieName(), currentDate, showDate, showTime,
                    customer);
            seatService.saveSeat(seat, customer, showDate, showTime);
            historyService.saveHistory(history, customer);
            List<String> seatNo1 = new ArrayList<String>();
            List<Customer> all = customerService.getAll();
            for (Customer c : all) {
                for (Seat s : c.getSeat()) {
                    for (String s1 : s.getSeatNo()) {
                        seatNo1.add(s1);
                    }

                }
            }
            movieDetails.setAvailableSeats(movieDetails.getAvailableSeats()- seat.getSeatNo().size());
            movieService.updateMovie(movieId, movieDetails);
			logger.info("Sending emails");
			notificationService.sendEmail(customer.getEmail(), "Successfully booked your tickets!", history.toString());
            return new ResponseEntity<>("Tickets Booked", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Date should be within 30 days from today!", HttpStatus.OK);

        }

    }

	//	Order history
	@GetMapping("/order-history")
	public ResponseEntity<Object> history(@RequestHeader String email, @RequestHeader String password) {
		Customer customer = null;
		if(email.isEmpty() && password.isEmpty()){
			logger.info("Credentials missing");
			return new ResponseEntity<>("Credentials missing!", HttpStatus.OK);
		} else {

			if(!email.isEmpty() && !password.isEmpty()){
				customer = customerService.login(email, password);
				if (customer == null) {
//					return Collections.emptyList();
					return new ResponseEntity<>("Invalid Credentials!", HttpStatus.OK);
				}
			}else{
				return new ResponseEntity<>("Invalid Credentials!", HttpStatus.OK);
			}
		}

		List<OrderHistory> orderHistories = historyService.getAllHistory(customer.getCId());
		return new ResponseEntity<>(orderHistories, HttpStatus.OK);

	}

	@DeleteMapping
	public ResponseEntity<String> deleteMovie(@RequestParam Integer id){
		movieService.deleteMovie(id);
		return new ResponseEntity<>("MovieDetails are removed", HttpStatus.OK);
	}

	private String fallback(){
		return "Server looks busy, please try sometime later!";
	}
}
