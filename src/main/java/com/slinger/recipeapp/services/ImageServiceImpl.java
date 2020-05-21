package com.slinger.recipeapp.services;

import com.slinger.recipeapp.commands.RecipeCommand;
import com.slinger.recipeapp.domain.Recipe;
import com.slinger.recipeapp.repositories.RecipeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Service
public class ImageServiceImpl implements ImageService {

    private final RecipeRepository recipeRepository;

    public ImageServiceImpl(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    @Override
    @Transactional
    public void saveImage(Long recipeId, MultipartFile file) {
        Optional<Recipe> optionalRecipe = recipeRepository.findById(recipeId);

        if(!optionalRecipe.isPresent()) {
            throw new RuntimeException("Recipe not found with id " + recipeId);
        }

        Recipe recipe = optionalRecipe.get();

        try {
            Byte[] bytes = new Byte[file.getBytes().length];
            int idx = 0;

            for(Byte b : file.getBytes()) {
                bytes[idx++] = b;
            }

            recipe.setImage(bytes);
            recipeRepository.save(recipe);

        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
