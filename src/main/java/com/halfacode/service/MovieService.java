package com.halfacode.service;

import com.halfacode.entity.Movie;
import com.halfacode.reppository.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MovieService {


    private final MovieRepository movieRepository;

    public Movie save(Movie movie){
        return movieRepository.save(movie);
    }
    public List<Movie> getAllMoves(){
        return movieRepository.findAll();
    }
  public Movie getMoveById(Long id){
        return movieRepository.findById(id).orElseThrow(() -> new RuntimeException("Movie not found for "+id));

  }

  public Movie updateMovie(Movie movie, Long id){
        Movie existMove= movieRepository.findById(id).get();
      existMove.setGenera(movie.getGenera());
      existMove.setMovieName(movie.getMovieName());
      existMove.setReleaseDate(movie.getReleaseDate());
      return movieRepository.save(existMove);
  }
  public void  deleteMovie(Long id){
        Movie existingMovie = movieRepository.findById(id).get();
        movieRepository.delete(existingMovie);

  }
}
