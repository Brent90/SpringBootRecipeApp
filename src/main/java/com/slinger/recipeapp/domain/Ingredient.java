package com.slinger.recipeapp.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Ingredient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String ingredientDescription;
    private BigDecimal amount;

    @OneToOne(fetch = FetchType.EAGER) // load all uoms
    private UnitOfMeasure uom;

    @ManyToOne// do not apply cascade to this... if you delete an ingredient doesn't make sense to delete a recipe
    Recipe recipe;

    public Ingredient(String ingredientDescription, BigDecimal amount, UnitOfMeasure uom) {
        this.ingredientDescription = ingredientDescription;
        this.amount = amount;
        this.uom = uom;
    }


}
