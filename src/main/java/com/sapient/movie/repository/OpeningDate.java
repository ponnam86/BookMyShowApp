package com.sapient.movie.repository;

import com.sapient.movie.dto.CurrentDateOperation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OpeningDate extends JpaRepository<CurrentDateOperation, Long> {

}
