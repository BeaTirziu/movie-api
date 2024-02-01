package com.example.moviesapi.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record MovieResultDTO(String title, String overview, String release_date, Float popularity) {
}
