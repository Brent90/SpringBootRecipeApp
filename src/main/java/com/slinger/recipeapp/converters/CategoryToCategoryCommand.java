package com.slinger.recipeapp.converters;

import com.slinger.recipeapp.commands.CategoryCommand;
import com.slinger.recipeapp.domain.Category;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class CategoryToCategoryCommand implements Converter<Category, CategoryCommand> {

    @Nullable
    @Synchronized
    @Override
    public CategoryCommand convert(Category category) {

        if(category == null) {
            return null;
        }

        final CategoryCommand categoryCommand = new CategoryCommand();
        categoryCommand.setId(category.getId());
        categoryCommand.setDescription(category.getDescription());

        return categoryCommand;
    }
}
