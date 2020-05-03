package com.slinger.recipeapp.controllers;

import com.slinger.recipeapp.commands.IngredientCommand;
import com.slinger.recipeapp.commands.RecipeCommand;
import com.slinger.recipeapp.repositories.CategoryRepository;
import com.slinger.recipeapp.services.RecipeService;
import com.slinger.recipeapp.services.UnitOfMeasureService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
public class RecipeController {

    private final RecipeService recipeService;
    private final CategoryRepository categoryRepository;
    private final UnitOfMeasureService unitOfMeasureService;

    public RecipeController(RecipeService recipeService, CategoryRepository categoryRepository, UnitOfMeasureService unitOfMeasureService) {
        this.recipeService = recipeService;
        this.categoryRepository = categoryRepository;
        this.unitOfMeasureService = unitOfMeasureService;
    }

    @GetMapping("/recipe/{recipeId}/show")
    public String showRecipeById(@PathVariable String recipeId, Model model) {
        model.addAttribute("recipe", recipeService.findById(Long.valueOf(recipeId)));
        return "recipe/show";
    }

    @GetMapping("/recipe/new")
    public String newRecipe(Model model) {
        model.addAttribute("recipe", new RecipeCommand());
        model.addAttribute("ingredient", new IngredientCommand());
        model.addAttribute("categories", categoryRepository.findAll());
        model.addAttribute("uomList", unitOfMeasureService.listAllUom());

        return "recipe/recipe-form";
    }

}
