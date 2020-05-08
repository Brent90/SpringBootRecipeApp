package com.slinger.recipeapp.services;

import com.slinger.recipeapp.commands.IngredientCommand;
import com.slinger.recipeapp.converters.IngredientCommandToIngredient;
import com.slinger.recipeapp.converters.IngredientToIngredientCommand;
import com.slinger.recipeapp.converters.UnitOfMeasureCommandToUnitOfMeasure;
import com.slinger.recipeapp.converters.UnitOfMeasureToUnitOfMeasureCommand;
import com.slinger.recipeapp.domain.Ingredient;
import com.slinger.recipeapp.domain.Recipe;
import com.slinger.recipeapp.repositories.IngredientRepository;
import com.slinger.recipeapp.repositories.RecipeRepository;
import com.slinger.recipeapp.repositories.UnitOfMeasureRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class IngredientServiceImplTest {

    private final IngredientToIngredientCommand ingredientToIngredientCommand;
    private final IngredientCommandToIngredient ingredientCommandToIngredient;
    private final UnitOfMeasureCommandToUnitOfMeasure uomConverter;

    @Mock
    RecipeRepository recipeRepository;

    @Mock
    UnitOfMeasureRepository unitOfMeasureRepository;

    @Mock
    IngredientRepository ingredientRepository;

    @Mock
    IngredientService ingredientService;

    //init converters
    public IngredientServiceImplTest() {
        this.ingredientToIngredientCommand = new IngredientToIngredientCommand(new UnitOfMeasureToUnitOfMeasureCommand());
        this.ingredientCommandToIngredient = new IngredientCommandToIngredient(new UnitOfMeasureCommandToUnitOfMeasure());
        uomConverter = new UnitOfMeasureCommandToUnitOfMeasure();

    }

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        ingredientService = new IngredientServiceImpl(ingredientToIngredientCommand, ingredientCommandToIngredient, ingredientRepository, recipeRepository, unitOfMeasureRepository, uomConverter);

    }

    @Test
    void findIngredientByRecipeIdAndIngredientId() {
        //given
        Recipe recipe = new Recipe();
        recipe.setId(10L);

        Ingredient ingredient1 = new Ingredient();
        ingredient1.setId(1L);
        recipe.addIngredient(ingredient1);

        Ingredient ingredient2 = new Ingredient();
        ingredient2.setId(2L);
        recipe.addIngredient(ingredient2);

        Optional<Recipe> recipeOptional = Optional.of(recipe);

        when(recipeRepository.findById(anyLong())).thenReturn(recipeOptional);

        //when
        IngredientCommand ingredientCommand = ingredientService.findIngredientByRecipeIdAndIngredientId(10L, 1L);

        //then
        assertNotNull(ingredientCommand);
        assertEquals(1L, ingredientCommand.getId());
        assertEquals(10L, ingredientCommand.getRecipeId());
        verify(recipeRepository, times(1)).findById(anyLong());

    }


}
