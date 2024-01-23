package com.sapient.movie.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Seat {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "seat_id")
	@JsonIgnore
	private long sId;
	
	@ElementCollection
	private List<String> seatNo;
	
	@ElementCollection
	@JsonIgnore
	private List<Double> price;
	
	@Column(name="total")
	@JsonIgnore
	private double total;
	
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JsonIgnore
	private Customer customer;
	
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JsonIgnore
	private CurrentDateOperation operation;



	/*@Override
	public String toString() {
		return "Seat [sId=" + sId + ", seatNo=" + seatNo + ", price=" + price + ", total=" + total + ", customer="
				+ customer + ", operation=" + operation + "]";
	}*/

		
	
}
