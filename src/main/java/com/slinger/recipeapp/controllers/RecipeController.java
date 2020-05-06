package com.slinger.recipeapp.controllers;

import com.slinger.recipeapp.commands.CategoryCommand;
import com.slinger.recipeapp.commands.IngredientCommand;
import com.slinger.recipeapp.commands.RecipeCommand;
import com.slinger.recipeapp.commands.UnitOfMeasureCommand;
import com.slinger.recipeapp.converters.CategoryToCategoryCommand;
import com.slinger.recipeapp.domain.Category;
import com.slinger.recipeapp.repositories.CategoryRepository;
import com.slinger.recipeapp.services.CategoryService;
import com.slinger.recipeapp.services.IngredientService;
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
    private final IngredientService ingredientService;
    private final Set<IngredientCommand> ingredientCommandSet;
    private final CategoryService categoryService; //todo

    public RecipeController(RecipeService recipeService, CategoryRepository categoryRepository, UnitOfMeasureService unitOfMeasureService, CategoryToCategoryCommand categoryToCategoryCommand, IngredientService ingredientService, CategoryService categoryService) {
        this.recipeService = recipeService;
        this.categoryRepository = categoryRepository;
        this.unitOfMeasureService = unitOfMeasureService;
        this.categoryToCategoryCommand = categoryToCategoryCommand;
        this.ingredientService = ingredientService;
        this.categoryService = categoryService;
        ingredientCommandSet = new HashSet<>();
    }

    @GetMapping("/recipe/{recipeId}/show")
    public String showRecipeById(@PathVariable String recipeId, Model model) {
        RecipeCommand recipeCommand = recipeService.findByRecipeCommandId(Long.valueOf(recipeId));
        model.addAttribute("recipe", recipeCommand);

        return "recipe/show";
    }

    @GetMapping("/recipe/new")
    public String showRecipeForm(Model model) {
        model.addAttribute("recipe", new RecipeCommand());
        model.addAttribute("ingredient", new IngredientCommand());
        model.addAttribute("categories", categoryRepository.findAll());
        model.addAttribute("uomList", unitOfMeasureService.listAllUom());
        model.addAttribute("checkedCategories", new HashSet<>());

        return "recipe/recipe-form";
    }

    @GetMapping("/recipe/{recipeId}/update")
    public String updateRecipe(@PathVariable String recipeId, Model model) {
        RecipeCommand recipeCommand = recipeService.findByRecipeCommandId(Long.valueOf(recipeId));

        model.addAttribute("recipe", recipeCommand);
        model.addAttribute("ingredient", new IngredientCommand());
        model.addAttribute("uomList", unitOfMeasureService.listAllUom());


//        used for check boxes
        Set<String> checked = new HashSet<>();
        for(CategoryCommand cat : recipeCommand.getCategories()) {
            checked.add(cat.getDescription());
        }

        model.addAttribute("checkedCategories", checked); //todo
        System.out.println(categoryService.findDescriptions());
        model.addAttribute("categories", categoryService.findAll()); //todo

        return "recipe/recipe-form";
    }


    @PostMapping(value = "/recipe")
    public String saveOrUpdateRecipe(@ModelAttribute("recipe") RecipeCommand recipeCommand,
                                   @ModelAttribute("ingredient") IngredientCommand ingredientCommand,
                                   @RequestParam(value = "checkedCategory", required = false) Set<Category> categorySet) {

        log.debug("saving or updating recipe ");

        if(categorySet != null ) {
            for(Category category : categorySet) {
                recipeCommand.getCategories().add(categoryToCategoryCommand.convert(category));
            }
        }

        RecipeCommand savedRecipeCommand = recipeService.saveRecipeCommand(recipeCommand);

        for(IngredientCommand temp : ingredientCommandSet) {
            recipeService.saveIngredientToRecipe(savedRecipeCommand, temp);
        }

        return "redirect:/recipe/" + savedRecipeCommand.getId() + "/show";
    }


    @PostMapping(value = "/saveIngredients")
    @ResponseStatus(value = HttpStatus.OK)
    public @ResponseBody void handleAjaxIngredientPost(@RequestBody Set<IngredientCommand> object) {
        log.debug("Handling ajax post");

        ingredientCommandSet.clear();

        for(IngredientCommand ingredientCommand : object) {
            //save uom to get id
            UnitOfMeasureCommand savedUOM = unitOfMeasureService.saveUnitOfMeasureCommand(ingredientCommand.getUom());
            ingredientCommand.setUom(savedUOM);

            //save ingredient to get id
            IngredientCommand savedIngredientCommand = ingredientService.saveIngredientCommand(ingredientCommand);
            ingredientCommandSet.add(savedIngredientCommand);
        }

    }







}
