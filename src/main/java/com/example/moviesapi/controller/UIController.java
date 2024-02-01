package com.example.moviesapi.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.moviesapi.dto.MovieRequestDTO;

@Controller
public class UIController {

    @GetMapping("/showForm")
    public String showForm(Model model) {
        model.addAttribute("movies", new MovieRequestDTO());
        return "movies";
    }
}
