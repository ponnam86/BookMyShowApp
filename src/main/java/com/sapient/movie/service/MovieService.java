package com.sapient.movie.service;

import com.sapient.movie.controller.MovieController;
import com.sapient.movie.repository.MovieRepo;
import com.sapient.movie.dto.MovieDetails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class MovieService{

    @Autowired
    private MovieRepo movieRepo;

    Logger logger = LoggerFactory.getLogger(MovieService.class);

    public MovieDetails findById(Integer id){
        return movieRepo.findById(Long.valueOf(id)).orElseThrow(() -> new IllegalArgumentException("Movie not found"));
    }
    public List<MovieDetails> getAllMovie(Integer id, String movieName, LocalDate date, String city, String genre, String language) {
        logger.info("Fetching movie details");
        if (id == null && movieName == null && date == null && city == null && genre == null && language == null) {
            return movieRepo.findAll();
        } else {
            return filterMovies(id, movieName, date, city, genre, language);
        }

    }

    public void addMovie(MovieDetails movieDetails) {
        movieRepo.save(movieDetails);
    }

    public void updateMovie(Integer id, MovieDetails request) {
        MovieDetails movie = movieRepo.findById(Long.valueOf(id)).orElseThrow(() -> new IllegalArgumentException("Invalid movie id:"+id));

        movie.setMovieDetails(request.getMovieDetails());
        movie.setCity(request.getCity());
        movie.setGenre(request.getGenre());
        movie.setMovieName(request.getMovieName());
        movie.setAvailableSeats(request.getAvailableSeats());
        movie.setLanguage(request.getLanguage());
        movie.setLocation(request.getLocation());
        movie.setImage(request.getImage());
        movie.setShowDate(request.getShowDate());
        movie.setShowTime(request.getShowTime());
        movie.setTotalSeats(request.getTotalSeats());
        movie.setPrice(request.getPrice());
        movieRepo.save(movie);

    }

    public List<MovieDetails> filterMovies(Integer id, String movieName, LocalDate date, String city, String genre, String language) {
        List<MovieDetails> movies = movieRepo.findAll();
        List<MovieDetails> filteredMovies = new ArrayList<>();

        for (MovieDetails movie : movies) {
            boolean match = true;
            if (id != null && movie.getMovieId()!=id) {
                match = false;
            }
            if (movieName != null && !movie.getMovieName().toLowerCase().contains(movieName.toLowerCase())) {
                match = false;
            }
            if (date != null && !movie.getShowDate().isEqual(date)) {
                match = false;
            }
            if (city != null && !movie.getCity().toLowerCase().contains(city.toLowerCase())) {
                match = false;
            }
            if (genre != null && !movie.getGenre().toLowerCase().contains(genre.toLowerCase())) {
                match = false;
            }
            if (language != null && !movie.getLanguage().toLowerCase().contains(language.toLowerCase())) {
                match = false;
            }
            if (match) {
                filteredMovies.add(movie);
            }
        }

        return filteredMovies;
    }

    public void deleteMovie(Integer id) {
        movieRepo.deleteById(Long.valueOf(id));
    }

    public List<Integer> checkAvailability(String name, LocalDate showDate, String showTime, List<String> seatNo) {
        logger.info("Checking availibility");
        // option 1
        /*List<Integer> hid = movieRepo.getHistory(name, showDate, showTime);
        if(!hid.isEmpty()){
            return  movieRepo.getSeat(hid);
        }*/
        //option 2
        return movieRepo.checkAvailability(name, showDate, showTime, seatNo);
    }
}
