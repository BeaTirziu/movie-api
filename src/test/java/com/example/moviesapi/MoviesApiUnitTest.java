package com.example.moviesapi;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.example.moviesapi.controller.MoviesApiController;
import com.example.moviesapi.dto.MovieRequestDTO;
import com.example.moviesapi.dto.MovieResponseDTO;
import com.example.moviesapi.service.MoviesApiService;

public class MoviesApiUnitTest {

    // We test the controller
    @InjectMocks
    private MoviesApiController movieController;

    @Mock
    private MoviesApiService movieService;

    @Test
    public void testSearchMovies() {
        // Create a test MovieRequestDTO
        MovieRequestDTO request = new MovieRequestDTO(/* set parameters */);

        // Mock data and behavior for the MovieService
        when(movieService.searchMovies(any())).thenReturn(Arrays.asList(/* mock movie data */));

        // Call the controller method
        ResponseEntity<List<MovieResponseDTO>> response = movieController.searchMovies(request);

        // Assertions based on the mock data
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotEmpty();
        // Additional assertions...
    }
}
