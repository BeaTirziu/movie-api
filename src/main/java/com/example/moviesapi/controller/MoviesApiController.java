package com.example.moviesapi.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.moviesapi.dto.MovieRequestDTO;
import com.example.moviesapi.dto.MovieResponseDTO;
import com.example.moviesapi.service.MoviesApiService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@Tag(name = "Movie API", description = "Endpoints for Movie Operations")
public class MoviesApiController {

    private final MoviesApiService moviesApiService;

    public MoviesApiController(MoviesApiService moviesApiService) {
        this.moviesApiService = moviesApiService;
    }

    // The @Valid annotation triggers the validation process of the request body
    // before processing the request.
    @PostMapping("/movies")
    @Operation(summary = "Filter movies by genre, actor and/or year. If none specified, list all movies. Additionally indicate the number of pages to display via the pages argument. The default is set to one.")
    @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(mediaType = "text/plain"))
    public ResponseEntity<List<MovieResponseDTO>> searchMovies(@RequestBody @Valid MovieRequestDTO request) {
        request.validate();
        return ResponseEntity.ok(moviesApiService.searchMovies(request));
    }
}
