This is a project created with a goal to develop a backend system for a movie ticket booking platform that allows users to search for movies, book tickets, make payment and view booking history. The project is built using Java and Spring Boot framework.

Features
Browse Movies: Users can view a list of available movies.

Search and Filter: Users can search for movies based on title, date, location, genre and movie ID.

Movie Details: Users can view detailed information about a specific movie, including title, director, description, genre, date, location, total seats, available seats, and price of ticket.

Booking Tickets: Users can book tickets for a movie by specifying the movie ID, quantity of tickets, and total price.

Booking History: Users can view their booking history, which includes details of movies they have booked, such as title, director, description, genre, date, location, booked tickets, and total price.

Add/Delete/Update Movies: Admin users can add new movies, delete existing movies, and update movie information.

Technologies
Java, Spring Boot, Spring Data JPA, Postman, MySQL Database and OpenAPI

API Endpoints
GET /movie: Get a list of movies.

GET /movie/{id}: Get detailed information about a specific movie.

GET /movie?genre=Drama: Get a list of movies based on genre.

GET /movie?date=2024-01-22: Get a list of movies based on date.

GET /movie?movieName="avatar": Get a list of movies based on title.

GET /movie?location="pvr": Get a list of movies based on location.

GET /movie/order-history: Get booking history.

POST /movie: Add a new movie.

POST /movie/book-seat?movieId=91&&showDate=2024-01-22&&showTime="06:00 pm": Create a new booking.

DELETE /movie/{movieId}: Delete a movie.

PUT /movie/{movieId}: Update movie information.
