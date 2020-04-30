package com.slinger.recipeapp.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class UnitOfMeasure {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;

    /*
       keep commented out, don't want a relationship from uom to ingredient
       @OneToOne
       private Ingredient ingredient;

     */

}
