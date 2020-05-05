package com.slinger.recipeapp.controllers;

import com.slinger.recipeapp.commands.RecipeCommand;
import com.slinger.recipeapp.converters.CategoryToCategoryCommand;
import com.slinger.recipeapp.repositories.CategoryRepository;
import com.slinger.recipeapp.services.RecipeService;
import com.slinger.recipeapp.services.UnitOfMeasureService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.junit.jupiter.api.Assertions.*;

class RecipeControllerTest {
    @Mock
    RecipeService recipeService;
    @Mock
    CategoryRepository categoryRepository;
    @Mock
    UnitOfMeasureService unitOfMeasureService;
    @Mock
    CategoryToCategoryCommand categoryToCategoryCommand;

    @InjectMocks
    RecipeController recipeController;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(recipeController).build();
    }

    @Test
    void showRecipeById() throws Exception{
        RecipeCommand recipeCommand = new RecipeCommand();
        recipeCommand.setId(1L);

        when(recipeService.findByRecipeCommandId(anyLong())).thenReturn(recipeCommand);

        mockMvc.perform(get("/recipe/2/show"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("recipe"))
                .andExpect(view().name("recipe/show"));

        assertNotNull(recipeCommand);
        verify(recipeService, times(1)).findByRecipeCommandId(anyLong());
    }

    @Test
    void showRecipeForm() throws Exception{
        mockMvc.perform(get("/recipe/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/recipe-form"))
                .andExpect(model().attributeExists("recipe"))
                .andExpect(model().attributeExists("ingredient"))
                .andExpect(model().attributeExists("categories"))
                .andExpect(model().attributeExists("uomList"));

        verify(categoryRepository, times(1)).findAll();
        verify(unitOfMeasureService, times(1)).listAllUom();
    }
}
