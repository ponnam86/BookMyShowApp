package com.sapient.movie.dto;

import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@JsonPropertyOrder({"movieName", "showOnDate", "showTime", "seat", "price", "total", "bookOnDate"})
@Table(name="order_history")
public class OrderHistory {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "history_id")
	private long hId;
	
	@ElementCollection
	@JsonProperty
	private List<String> seat;
	
	@ElementCollection
	@JsonProperty
	private List<Double> price;

	@JsonProperty
	private double total;
	
	@Column(name = "movie_name")
	@JsonProperty
	private String movieName;
	
	@Temporal(value=TemporalType.DATE)
	@Column(name = "book_on_date")
	@JsonProperty
	private LocalDate bookOnDate;
	
	@Temporal(value=TemporalType.DATE)
	@JsonProperty
	@Column(name = "show_on_date")
	private LocalDate showOnDate;
	
	@Column(name = "show_time")
	@JsonProperty
	private String showTime;
	
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Customer customer;

	public OrderHistory(List<String> seat, List<Double> price, double total, String movieName, LocalDate bookOnDate, LocalDate showOnDate, String showTime, Customer customer) {
		super();
		this.seat = seat;
		this.price = price;
		this.total = total;
		this.movieName = movieName;
		this.bookOnDate = bookOnDate;
		this.showOnDate = showOnDate;
		this.showTime = showTime;
		this.customer = customer;
	}

	@Override
	public String toString() {
		return "OrderHistory{" +
				", movieName='" + movieName + '\'' +
				", bookOnDate=" + bookOnDate +
				", showOnDate=" + showOnDate +
				", price=" + price +
				", total=" + total +

				'}';
	}
}
