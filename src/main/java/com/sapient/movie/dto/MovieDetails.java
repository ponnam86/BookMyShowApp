package com.sapient.movie.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter@Setter
@Table(name = "movie_details")
public class MovieDetails {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonIgnore
	@Column(name="movie_id")
	private long movieId;

	@Column(nullable = false)
	private String movieName;

	@Column
	private String image;

	@Column
	private String movieDetails;
	@Column
	private String genre;
	@Column
	private String city;
	@Column
	private String language;
	@Column
	private String location;
	@Column
	private int totalSeats;
	@Column
	private int availableSeats;

	@Column
	private LocalDate showDate;

	@Column
	private double price;

	@ElementCollection
	private List<String> showTime;

	@Override
	public String toString() {
		return "MovieDetails{" +
				"movieId=" + movieId +
				", movieName='" + movieName + '\'' +
				", image='" + image + '\'' +
				", movieDetails='" + movieDetails + '\'' +
				", genre='" + genre + '\'' +
				", city='" + city + '\'' +
				", language='" + language + '\'' +
				", location='" + location + '\'' +
				", totalSeats=" + totalSeats +
				", availableSeats=" + availableSeats +
				", showDate=" + showDate +
				", showTime=" + showTime +
				", price=" + price +
				'}';
	}
}
