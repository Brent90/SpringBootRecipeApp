package com.slinger.recipeapp.services;

import com.slinger.recipeapp.commands.RecipeCommand;
import com.slinger.recipeapp.converters.RecipeCommandToRecipe;
import com.slinger.recipeapp.converters.RecipeToRecipeCommand;
import com.slinger.recipeapp.domain.Recipe;
import com.slinger.recipeapp.repositories.RecipeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RecipeServiceImplTest {

    @Mock
    RecipeRepository recipeRepository;
    @Mock
    RecipeToRecipeCommand recipeToRecipeCommand;
    @Mock
    RecipeCommandToRecipe recipeCommandToRecipe;

    RecipeServiceImpl recipeService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        recipeService = new RecipeServiceImpl(recipeRepository, recipeCommandToRecipe, recipeToRecipeCommand);
    }

    @Test
    void getRecipes() {
        //given
        Set<Recipe> recipes = new HashSet<>();

        Recipe recipe1 = new Recipe();
        recipe1.setId(1L);
        recipes.add(recipe1);

        Recipe recipe2 = new Recipe();
        recipe2.setId(2L);
        recipes.add(recipe2);

        when(recipeRepository.findAll()).thenReturn(recipes);

        //when
        Set<Recipe> recipeResults = recipeService.getRecipes();

        //then
        assertEquals(recipes.size(), recipeResults.size());
        assertNotNull(recipeResults);
        verify(recipeRepository, times(1)).findAll();

    }

    @Test
    void findById() {
        Recipe recipe = new Recipe();
        recipe.setId(10L);
        Optional<Recipe> recipeOptional = Optional.of(recipe);

        when(recipeRepository.findById(anyLong())).thenReturn(recipeOptional);

        Recipe foundRecipe = recipeService.findById(10L);

        assertNotNull(foundRecipe);
        verify(recipeRepository, times(1)).findById(anyLong());
    }




}
