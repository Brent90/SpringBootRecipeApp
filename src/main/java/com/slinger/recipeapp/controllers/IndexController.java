package com.slinger.recipeapp.controllers;

import com.slinger.recipeapp.domain.Recipe;
import com.slinger.recipeapp.repositories.RecipeRepository;
import com.slinger.recipeapp.services.RecipeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.HashSet;
import java.util.Set;

@Slf4j
@Controller
public class IndexController {

    private final RecipeService recipeService;

    public IndexController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @GetMapping({"", "/", "/index", "index.html"})
    public String showIndexPage() {
        Set<Recipe> recipes = new HashSet<>();

        recipeService.getRecipes().stream().forEach(recipe -> {
            log.debug(recipe.getDescription());
        });

        return "index";
    }

}
