package com.slinger.recipeapp.controllers;

import com.slinger.recipeapp.services.RecipeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class ImageController {

    private final RecipeService recipeService;

    public ImageController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @GetMapping("/recipe/{recipeId}/image")
    public String showUploadImageForm(@PathVariable String recipeId, Model model) {
        model.addAttribute("recipe", recipeService.findByRecipeCommandId(Long.valueOf(recipeId)));
        return "recipe/image-form";
    }

}
