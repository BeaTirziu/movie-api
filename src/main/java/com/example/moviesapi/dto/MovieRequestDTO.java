package com.example.moviesapi.dto;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

// The annotation ensures that null fields are excluded from the serialized JSON
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MovieRequestDTO {

    @Size(max = 50, message = "Genre specification must be less than or equal to 50 characters!")
    @Getter
    @Setter
    private String genre; // You can predefine a set of existing genres and check if genre is valid

    @Size(max = 100, message = "Actor name must be less than or equal to 100 characters!")
    @Getter
    @Setter
    private String actor;

    @Min(1888)
    @Max(2024) // Try do determine the current year at compile time and specify here as
               // constant
    @Getter
    @Setter
    private Integer year;

    @Min(1)
    @Max(10)
    @Getter
    @Setter
    private Integer pages = 1;

    // The following code ensures that a request can be made only with the above
    // parameters and no other
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<>();

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        // Capture additional properties
        additionalProperties.put(name, value);
    }

    public Map<String, Object> getAdditionalProperties() {
        return additionalProperties;
    }

    public void validate() {
        // Check if there are any additional properties
        if (!additionalProperties.isEmpty()) {
            throw new IllegalArgumentException("Unknown parameter(s) in the request: " + additionalProperties.keySet());
        }
    }
}
