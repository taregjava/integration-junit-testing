package com.halfacode.integration;

import com.halfacode.entity.Movie;
import com.halfacode.reppository.MovieRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MovieIntegrationTest {

    @LocalServerPort
    private int port;

    private String baseUrl= "http://localhost";
    private Movie avatarMovie;
    private Movie titanic;
    private static RestTemplate restTemplate;
    @Autowired
    private MovieRepository movieRepository;

    @BeforeAll
    public static void init(){
        restTemplate= new RestTemplate();
    }

    @BeforeEach
    public void beforeSetup(){
        baseUrl= baseUrl + ":" +port + "/movies";
       avatarMovie= new Movie();
        avatarMovie.setMovieName("Avatar");
        avatarMovie.setGenera("Action");
        avatarMovie.setReleaseDate(LocalDate.of(2000, Month.APRIL,22));

        titanic= new Movie();
        titanic.setMovieName("titanic");
        titanic.setGenera("Romantic");
        titanic.setReleaseDate(LocalDate.of(2000, Month.APRIL,22));
        avatarMovie = movieRepository.save(avatarMovie);

        titanic = movieRepository.save(titanic);
    }
    @AfterEach
    public void afterSetup(){
        movieRepository.deleteAll();
    }
    @Test
    void shouldCreateMovieTest(){
        Movie avatarMovie= new Movie();
        avatarMovie.setMovieName("Avatar");
        avatarMovie.setGenera("Action");
        avatarMovie.setReleaseDate(LocalDate.of(2000, Month.APRIL,22));

        Movie newMovie=  restTemplate.postForObject(baseUrl,avatarMovie, Movie.class);

        assertNotNull(newMovie);
        assertThat(newMovie.getId()).isNotNull();
    }
    @Test
    void shouldFetchMovieTest(){

       // restTemplate.postForObject(baseUrl,avatarMovie, Movie.class);
       // restTemplate.postForObject(baseUrl,titanic, Movie.class);
        List<Movie> listMovie=  restTemplate.getForObject(baseUrl, List.class);

      //  assertNotNull(listMovie);
        assertThat(listMovie.size()).isEqualTo(2);
    }

    @Test
    void shouldFetchOneMovieTest(){
       //avatarMovie=  restTemplate.postForObject(baseUrl,avatarMovie, Movie.class);
       // titanic= restTemplate.postForObject(baseUrl,titanic, Movie.class);

        Movie existingmoving = restTemplate.getForObject(baseUrl+"/"+avatarMovie.getId(),Movie.class);

        assertNotNull(existingmoving);
        assertEquals("Avatar", existingmoving.getMovieName());
    }
    @Test
    void shouldDeleteMoveTest(){

        //avatarMovie=  restTemplate.postForObject(baseUrl,avatarMovie, Movie.class);
        //titanic= restTemplate.postForObject(baseUrl,titanic, Movie.class);

        restTemplate.delete(baseUrl+"/"+avatarMovie.getId());
        int count = movieRepository.findAll().size();
        assertEquals(1,count);
    }

    @Test
    void shouldUpdateMoveTest(){

       // avatarMovie=  restTemplate.postForObject(baseUrl,avatarMovie, Movie.class);
       // titanic= restTemplate.postForObject(baseUrl,titanic, Movie.class);

        avatarMovie.setGenera("Fantacy");
        restTemplate.put(baseUrl+"/{id}", avatarMovie, avatarMovie.getId());
        Movie existingmoving=  restTemplate.getForObject(baseUrl+"/"+avatarMovie.getId(), Movie.class);

        assertNotNull(existingmoving);

        assertEquals("Fantacy", existingmoving.getGenera());
    }
}
