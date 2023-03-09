package com.halfacode;

import com.halfacode.entity.Movie;
import com.halfacode.reppository.MovieRepository;
import com.halfacode.service.MovieService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;


import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MovieServiceTest {

    @InjectMocks
    private MovieService movieService;
    @Mock
    private MovieRepository movieRepository;

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
    @DisplayName("should save the movie object to database")
    void save(){

        when(movieRepository.save(any(Movie.class))).thenReturn(avatarMovie);
        Movie newMovie=movieService.save(avatarMovie);

        assertNotNull(newMovie);
        assertThat(newMovie.getMovieName()).isEqualTo("Avatar");
    }
    @Test
    @DisplayName("should return lest of movie of size 2")
    void getAllMovie(){
        List<Movie>  list= new ArrayList<>();
        list.add(avatarMovie);
        list.add(titanic);
        when(movieRepository.findAll()).thenReturn(list);
        List<Movie> listMovie= movieService.getAllMoves();
        assertEquals(2, listMovie.size());
        assertNotNull(listMovie);
    }
    @Test
    void getMovieById(){
        when(movieRepository.findById(anyLong())).thenReturn(Optional.of(avatarMovie));

        Movie existMovie=movieService.getMoveById(1L);

        assertNotNull(existMovie);
        assertThat(existMovie.getId()).isEqualTo(1L);
    }
    @Test
    void getMovieByIdForException(){

        when(movieRepository.findById(1L)).thenReturn(Optional.of(avatarMovie));

        assertThrows(RuntimeException.class,() ->{
            movieService.getMoveById(2L);
        });
    }
    @Test
    void updateMovie(){
        when(movieRepository.findById(anyLong())).thenReturn(Optional.of(avatarMovie));
        when(movieRepository.save(any(Movie.class))).thenReturn(avatarMovie);

        avatarMovie.setGenera("Fantacy");
        Movie updateMovie = movieService.updateMovie(avatarMovie, 1L);
        assertNotNull(updateMovie);
        assertEquals("Fantacy", updateMovie.getGenera());

    }
    @Test
    void deleteMovie(){
        when(movieRepository.findById(anyLong())).thenReturn(Optional.of(avatarMovie));
        doNothing().when(movieRepository).delete(any(Movie.class));

         movieService.deleteMovie( 1L);
         verify(movieRepository, times(1)).delete(avatarMovie);


    }
}
