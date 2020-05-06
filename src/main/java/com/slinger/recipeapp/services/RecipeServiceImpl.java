package com.slinger.recipeapp.services;

import com.slinger.recipeapp.commands.IngredientCommand;
import com.slinger.recipeapp.commands.RecipeCommand;
import com.slinger.recipeapp.converters.RecipeCommandToRecipe;
import com.slinger.recipeapp.converters.RecipeToRecipeCommand;
import com.slinger.recipeapp.domain.Recipe;
import com.slinger.recipeapp.repositories.RecipeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Slf4j
@Service
public class RecipeServiceImpl implements RecipeService {

    private final RecipeRepository recipeRepository;
    private final RecipeCommandToRecipe recipeCommandToRecipe;
    private final RecipeToRecipeCommand recipeToRecipeCommand;

    public RecipeServiceImpl(RecipeRepository recipeRepository, RecipeCommandToRecipe recipeCommandToRecipe, RecipeToRecipeCommand recipeToRecipeCommand) {
        this.recipeRepository = recipeRepository;

        this.recipeCommandToRecipe = recipeCommandToRecipe;
        this.recipeToRecipeCommand = recipeToRecipeCommand;
    }

    @Override
    public Set<Recipe> getRecipes() {
        Set<Recipe> recipes = new HashSet<>();
        recipeRepository.findAll().iterator().forEachRemaining(recipes::add);
        return recipes;
    }

    @Override
    public Recipe findById(Long recipeId) {
        Optional<Recipe> recipeOptional = recipeRepository.findById(recipeId);

        if(!recipeOptional.isPresent()) {
            //todo implement better error handling
            throw new RuntimeException("No recipe with id " + recipeId + " was found");
        }

        return recipeOptional.get();
    }

    @Override
    @Transactional
    public RecipeCommand saveRecipeCommand(RecipeCommand recipeCommand) {
        Recipe recipeHolder = recipeCommandToRecipe.convert(recipeCommand);
        Recipe savedRecipe = recipeRepository.save(recipeHolder);
        log.debug("Saved recipe " + savedRecipe.getId());
        return recipeToRecipeCommand.convert(savedRecipe);
    }

    @Override
    @Transactional
    public RecipeCommand findByRecipeCommandId(Long recipeId) {
        return recipeToRecipeCommand.convert(findById(recipeId));
    }

    @Override
    @Transactional
    public RecipeCommand saveIngredientToRecipe(RecipeCommand recipeCommand, IngredientCommand ingredientCommand) {
        recipeCommand.getIngredients().add(ingredientCommand);
        recipeCommand.getIngredients().forEach(temp -> temp.setRecipeId(recipeCommand.getId()));
        Recipe savedRecipe = recipeRepository.save(recipeCommandToRecipe.convert(recipeCommand));
        return recipeToRecipeCommand.convert(savedRecipe);
    }

    @Override
    public List<Recipe> getRecipesSorted() {
        return recipeRepository.findByOrderByDescriptionAsc();
    }

    @Override
    public void deleteRecipeById(Long recipeId) {
        Optional<Recipe> optionalRecipe = recipeRepository.findById(recipeId);

        if(!optionalRecipe.isPresent()) {
            throw new RuntimeException("No recipe found with id " + recipeId);
        }

        recipeRepository.deleteById(recipeId);
        log.debug("Deleted recipe with id: " + recipeId);
    }
}
