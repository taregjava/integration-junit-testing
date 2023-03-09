package com.halfacode.controllerTest;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.halfacode.entity.Movie;
import com.halfacode.service.MovieService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//import static org.springframework.http.RequestEntity.post;
import static org.hamcrest.Matchers.is;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;


@WebMvcTest
public class MovieControllerTest {


    @MockBean
    private MovieService movieService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;
    private Movie avatarMovie;
    private Movie titanic;
    @BeforeEach
    void init(){
        avatarMovie= new Movie();
        avatarMovie.setId(1L);
        avatarMovie.setMovieName("Avatar");
        avatarMovie.setGenera("Action");
        avatarMovie.setReleaseDate(LocalDate.of(2000, Month.APRIL,22));

        titanic= new Movie();
        titanic.setId(2L);
        titanic.setMovieName("titanic");
        titanic.setGenera("Action");
        titanic.setReleaseDate(LocalDate.of(2000, Month.APRIL,22));
    }
    @Test
    void shouldCreateNewMovie() throws Exception {
        when(movieService.save(any(Movie.class))).thenReturn(avatarMovie);
        mockMvc.perform(post("/movies")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(avatarMovie)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.movieName", is(avatarMovie.getMovieName())))
                .andExpect(jsonPath("$.genera", is(avatarMovie.getGenera())))
                .andExpect(jsonPath("$.releaseDate", is(avatarMovie.getReleaseDate().toString())));

    }
    @Test
    void shouldFetchAllMovies() throws Exception {

        List<Movie> list= new ArrayList<>();
        list.add(avatarMovie);
        list.add(titanic);

        when(movieService.getAllMoves()).thenReturn(list);
        this.mockMvc.perform(get("/movies"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()",is(list.size())));
    }

   @Test
    void shouldFetchOneMovieById() throws Exception {
       when(movieService.getMoveById(anyLong())).thenReturn(avatarMovie);

       this.mockMvc.perform(get("/movies/{id}", 1L))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.movieName", is(avatarMovie.getMovieName())))
               .andExpect(jsonPath("$.genera", is(avatarMovie.getGenera())));

   }
    @Test
    void shouldDeleteMovieById() throws Exception {

        doNothing().when(movieService).deleteMovie(anyLong());

        this.mockMvc.perform(delete("/movies/{id}", 1L))
                .andExpect(status().isNoContent());

    }
    @Test
    void shouldUpdateMovieById() throws Exception {

        when(movieService.updateMovie(any(Movie.class), anyLong())).thenReturn(avatarMovie);

        this.mockMvc.perform(put("/movies/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(avatarMovie)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.movieName", is(avatarMovie.getMovieName())))
                .andExpect(jsonPath("$.genera", is(avatarMovie.getGenera())));

    }

}
