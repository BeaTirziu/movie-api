package com.example.moviesapi.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record MovieResponseDTO(Integer page, List<MovieResultDTO> results, Integer total_pages) {
}