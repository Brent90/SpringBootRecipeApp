package com.slinger.recipeapp.controllers;

import com.slinger.recipeapp.commands.IngredientCommand;
import com.slinger.recipeapp.commands.RecipeCommand;
import com.slinger.recipeapp.commands.UnitOfMeasureCommand;
import com.slinger.recipeapp.converters.CategoryToCategoryCommand;
import com.slinger.recipeapp.domain.Category;
import com.slinger.recipeapp.repositories.CategoryRepository;
import com.slinger.recipeapp.services.RecipeService;
import com.slinger.recipeapp.services.UnitOfMeasureService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.HashSet;
import java.util.Set;

@Slf4j
@Controller
public class RecipeController {

    private final RecipeService recipeService;
    private final CategoryRepository categoryRepository;
    private final UnitOfMeasureService unitOfMeasureService;
    private final CategoryToCategoryCommand categoryToCategoryCommand;
    private Set<IngredientCommand> ingredientCommandSet = new HashSet<>();

    public RecipeController(RecipeService recipeService, CategoryRepository categoryRepository, UnitOfMeasureService unitOfMeasureService, CategoryToCategoryCommand categoryToCategoryCommand) {
        this.recipeService = recipeService;
        this.categoryRepository = categoryRepository;
        this.unitOfMeasureService = unitOfMeasureService;
        this.categoryToCategoryCommand = categoryToCategoryCommand;
    }

    @GetMapping("/recipe/{recipeId}/show")
    public String showRecipeById(@PathVariable String recipeId, Model model) {
        model.addAttribute("recipe", recipeService.findByRecipeCommandId(Long.valueOf(recipeId)));
        return "recipe/show";
    }

    @GetMapping("/recipe/new")
    public String showRecipeForm(Model model) {
        model.addAttribute("recipe", new RecipeCommand());
        model.addAttribute("ingredient", new IngredientCommand());
        model.addAttribute("categories", categoryRepository.findAll());
        model.addAttribute("uomList", unitOfMeasureService.listAllUom());

        return "recipe/recipe-form";
    }

    @PostMapping(value = "/recipe")  //todo this method needs work, not saving ingredient right
    public String processNewRecipe(@ModelAttribute("recipe") RecipeCommand recipeCommand,
                                   @ModelAttribute("ingredient") IngredientCommand ingredientCommand,
                                   @RequestParam(value = "checkedCategory", required = false) Set<Category> categorySet
                                   ) {

        if(categorySet != null ) {
            for(Category category : categorySet) {
                recipeCommand.getCategories().add(categoryToCategoryCommand.convert(category));
            }
        }

        for(IngredientCommand temp : ingredientCommandSet) {  //todo work on this loop, clean it up if possible
            IngredientCommand result = new IngredientCommand();
            result.setIngredientDescription(temp.getIngredientDescription());
            result.setAmount(temp.getAmount());

            UnitOfMeasureCommand uom = new UnitOfMeasureCommand();
            uom.setDescription(temp.getUom().getDescription());

            UnitOfMeasureCommand savedUom = unitOfMeasureService.saveUnitOfMeasureCommand(uom);

            result.setUom(savedUom);

            recipeCommand.getIngredients().add(result);
        }


        RecipeCommand savedRecipeCommand = recipeService.saveRecipeCommand(recipeCommand);


        return "redirect:/recipe/" + savedRecipeCommand.getId() + "/show";
    }


    @PostMapping(value = "/saveIngredients")
    @ResponseStatus(value = HttpStatus.OK)
    public @ResponseBody void handleAjaxIngredientPost(@RequestBody Set<IngredientCommand> object) {

        ingredientCommandSet.clear();

        for(IngredientCommand ingredientCommand : object) {
            ingredientCommandSet.add(ingredientCommand);
        }
    }



}
