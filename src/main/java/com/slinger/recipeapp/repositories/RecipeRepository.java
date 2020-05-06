package com.slinger.recipeapp.repositories;

import com.slinger.recipeapp.domain.Recipe;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Set;

public interface RecipeRepository extends CrudRepository<Recipe, Long> {
    List<Recipe> findByOrderByDescriptionAsc();
}
