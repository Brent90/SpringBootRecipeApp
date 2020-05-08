package com.slinger.recipeapp.controllers;

import com.slinger.recipeapp.commands.IngredientCommand;
import com.slinger.recipeapp.commands.RecipeCommand;
import com.slinger.recipeapp.services.IngredientService;
import com.slinger.recipeapp.services.RecipeService;
import com.slinger.recipeapp.services.UnitOfMeasureService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


class IngredientControllerTest {

    @Mock
    RecipeService recipeService;

    @Mock
    IngredientService ingredientService;

    @Mock
    UnitOfMeasureService unitOfMeasureService;

    @InjectMocks
    IngredientController controller;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void listIngredients() throws Exception {
        RecipeCommand recipeCommand = new RecipeCommand();
        recipeCommand.setId(1L);

        when(recipeService.findByRecipeCommandId(anyLong())).thenReturn(recipeCommand);

        mockMvc.perform(get("/recipe/1/ingredients"))
                .andExpect(model().attributeExists("recipe"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/ingredients/list-ingredients"));

        verify(recipeService, times(1)).findByRecipeCommandId(anyLong());
    }

    @Test
    void showIngredient() throws Exception{
        IngredientCommand ingredientCommand = new IngredientCommand();
        ingredientCommand.setId(1L);
        RecipeCommand recipeCommand = new RecipeCommand();
        recipeCommand.setId(2L);

        when(recipeService.findByRecipeCommandId(anyLong())).thenReturn(recipeCommand);
        when(ingredientService.findIngredientByRecipeIdAndIngredientId(anyLong(), anyLong())).thenReturn(ingredientCommand);

        assertNotNull(ingredientCommand);
        assertNotNull(recipeCommand);

        mockMvc.perform(get("/recipe/1/ingredient/2/show"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("recipe"))
                .andExpect(model().attributeExists("ingredient"))
                .andExpect(view().name("recipe/ingredients/show"));

    }

    @Test
    void showIngredientForm() throws Exception {
        mockMvc.perform(get("/recipe/1/ingredient/3/update"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("uomList"))
                .andExpect(view().name("recipe/ingredients/ingredient-form"));

        verify(ingredientService, times(1)).findIngredientByRecipeIdAndIngredientId(anyLong(), anyLong());
        verify(recipeService, times(1)).findByRecipeCommandId(anyLong());
        verify(unitOfMeasureService, times(1)).listAllUom();
    }

    @Test
    void saveOrUpdateIngredient() throws Exception {
        IngredientCommand ingredientCommand = new IngredientCommand();
        ingredientCommand.setId(1L);
        ingredientCommand.setRecipeId(2L);

        when(ingredientService.saveOrUpdateIngredientCommand(any(IngredientCommand.class))).thenReturn(ingredientCommand);

        mockMvc.perform(post("/recipe/1/ingredient"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/recipe/2/ingredient/1/show"));

    }

    @Test
    void addNewIngredient() throws Exception {
        RecipeCommand recipeCommand = new RecipeCommand();
        recipeCommand.setId(1L);

        when(recipeService.findByRecipeCommandId(anyLong())).thenReturn(recipeCommand);
        when(unitOfMeasureService.listAllUom()).thenReturn(new HashSet<>());

        mockMvc.perform(get("/recipe/1/ingredient"))
                .andExpect(model().attributeExists("ingredient"))
                .andExpect(model().attributeExists("uomList"))
                .andExpect(view().name("recipe/ingredients/ingredient-form"));
    }
}
