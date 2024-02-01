package com.example.moviesapi.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.example.moviesapi.dto.MovieRequestDTO;
import com.example.moviesapi.dto.MovieResponseDTO;
import com.example.moviesapi.util.RateLimiter;

@Service
public class MoviesApiService {

    // Define constant strings
    private static final String API_KEY_PARAM = "api_key";
    private static final String PAGE_PARAM = "page";
    private static final String GENRE_PARAM = "with_genres";
    private static final String ACTOR_PARAM = "with_cast";
    private static final String YEAR_PARAM = "primary_release_year";

    private final RestTemplate restTemplate;
    private final RateLimiter rateLimiter;

    private Integer curPageToDisplay = 1; // Page index starts from 1 instead of 0
    private Integer numPagesToDisplay = 1;
    private Integer numPagesDisplayed = 0;
    private Integer numPagesTotal = 1;

    private static final Logger log = LoggerFactory.getLogger(MoviesApiService.class);

    @Value("${movie.api.url}")
    private String apiUrl;

    @Value("${movie.api.key}")
    private String apiKey;

    public MoviesApiService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
        this.rateLimiter = new RateLimiter();
    }

    public List<MovieResponseDTO> searchMovies(MovieRequestDTO movieRequest) {
        List<MovieResponseDTO> movieResponses = new ArrayList<>();
        numPagesToDisplay = movieRequest.getPages();

        while (curPageToDisplay <= numPagesToDisplay && curPageToDisplay <= numPagesTotal) {
            if (rateLimiter.tryConsume()) {
                // UriComponentsBuilder automatically encodes special characters, ensuring that
                // the URI remains consistent and adheres to web standards.
                UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString(apiUrl)
                        .queryParam(API_KEY_PARAM, apiKey)
                        .queryParam(PAGE_PARAM, curPageToDisplay)
                        .queryParamIfPresent(GENRE_PARAM, Optional.ofNullable(movieRequest.getGenre()))
                        .queryParamIfPresent(ACTOR_PARAM, Optional.ofNullable(movieRequest.getActor()))
                        .queryParamIfPresent(YEAR_PARAM, Optional.ofNullable(movieRequest.getYear()));

                // Add API key to headers
                HttpHeaders headers = new HttpHeaders();
                headers.set("Authorization", "Bearer " + apiKey);
                HttpEntity<Object> entity = new HttpEntity<>(headers);

                // Make HTTP GET request with headers and parse JSON response
                ResponseEntity<MovieResponseDTO> responseEntity = restTemplate.exchange(uriBuilder.toUriString(),
                        HttpMethod.GET, entity, MovieResponseDTO.class);

                MovieResponseDTO movieResponse = responseEntity.getBody();
                if (responseEntity.getStatusCode().is2xxSuccessful() && movieResponse != null) {
                    curPageToDisplay++;
                    numPagesDisplayed++;
                    numPagesTotal = movieResponse.total_pages();
                    movieResponses.add(movieResponse);
                } else {
                    log.info(
                            "If the response is erroneous, the exception is caught and handled in the centralized advice handler. The response might be NULL depending on the API architecture!");
                }
            } else {
                // If the rate limit is exceeded, you can sleep for a short duration before
                // retrying. Retrying can also be done programmatically, but is not recommended
                // for POST methods.
                log.info("Rate limiting exceeded. Wait before posting a new request!");
            }
        }
        log.info("Displayed Pages: " + numPagesDisplayed + "/" + numPagesTotal);
        return movieResponses;
    }
}
