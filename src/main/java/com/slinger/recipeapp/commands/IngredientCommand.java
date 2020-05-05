package com.slinger.recipeapp.commands;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class IngredientCommand {
    private Long id;
    private String ingredientDescription;
    private BigDecimal amount;
    private UnitOfMeasureCommand uom;
}
