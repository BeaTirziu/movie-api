package com.example.moviesapi;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.moviesapi.dto.MovieRequestDTO;
import com.example.moviesapi.dto.MovieResponseDTO;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MoviesApiIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void testSearchMovies() {
        // Create a test MovieRequestDTO
        MovieRequestDTO request = new MovieRequestDTO(/* set parameters */);

        // Make an HTTP request to the endpoint
        ResponseEntity<List<MovieResponseDTO>> response = restTemplate.exchange(
                "/movies", HttpMethod.GET, new HttpEntity<>(request),
                new ParameterizedTypeReference<List<MovieResponseDTO>>() {
                });

        // Assertions based on the expected behavior
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotEmpty();
        // Additional assertions...
    }
}
