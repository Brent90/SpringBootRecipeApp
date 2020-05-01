package com.slinger.recipeapp.controllers;

import com.slinger.recipeapp.domain.Recipe;
import com.slinger.recipeapp.repositories.RecipeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.HashSet;
import java.util.Set;

@Slf4j
@Controller
public class IndexController {

    private final RecipeRepository recipeRepository;

    public IndexController(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    @GetMapping({"", "/", "/index", "index.html"})
    public String showIndexPage() {
        Set<Recipe> recipes = new HashSet<>();
        recipeRepository.findAll().iterator().forEachRemaining(recipes::add);

        log.debug(recipeRepository.findById(1L).get().getDescription());


        return "index";
    }

}
