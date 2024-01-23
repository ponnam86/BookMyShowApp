package com.sapient.movie.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "customer")
public class Customer {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "customer_id")
	@JsonIgnore
	private long cId;
	
	@Column(nullable = false)
	@JsonProperty
	@NotEmpty(message = "Username is can not blank")
	private String name;
	@Column(nullable = false, unique = true)
	@JsonProperty
	@NotEmpty(message = "email is can not blank")
	@Email(message = "Email should be valid")
	private String email;
	@Column(nullable = false)
	@NotEmpty(message = "password is can not blank")
	private String password;

	@JsonIgnore
	@OneToMany(mappedBy = "customer", fetch = FetchType.EAGER)
	private List<Seat> seat;

	@JsonIgnore
	@OneToMany(mappedBy = "customer", fetch = FetchType.EAGER)
	private List<OrderHistory> history;


	@Override
	public String toString() {
		return "Customer{" +
				", name='" + name + '\'' +
				", email='" + email + '\'' +
				", password='" + password + '\'' +
				'}';
	}
}
