package com.slinger.recipeapp.controllers;

import com.slinger.recipeapp.commands.RecipeCommand;
import com.slinger.recipeapp.services.ImageService;
import com.slinger.recipeapp.services.RecipeService;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

@Slf4j
@Controller
public class ImageController {

    private final RecipeService recipeService;
    private final ImageService imageService;

    public ImageController(RecipeService recipeService, ImageService imageService) {
        this.recipeService = recipeService;
        this.imageService = imageService;
    }

    @GetMapping("/recipe/{recipeId}/showImageForm")
    public String showUploadImageForm(@PathVariable String recipeId, Model model) {
        model.addAttribute("recipe", recipeService.findByRecipeCommandId(Long.valueOf(recipeId)));
        return "recipe/image-form";
    }

    @GetMapping("/recipe/{recipeId}/loadImage")
    public void loadImageFromDB(@PathVariable String recipeId, HttpServletResponse response) throws IOException {
        RecipeCommand recipeCommand = recipeService.findByRecipeCommandId(Long.valueOf(recipeId));

        if(recipeCommand.getImage() != null) {
            byte[] bytes = new byte[recipeCommand.getImage().length];
            int idx = 0;

            for(byte b : recipeCommand.getImage()) {
                bytes[idx++] = b;
            }

            response.setContentType("image/jpeg");
            InputStream is = new ByteArrayInputStream(bytes);
            IOUtils.copy(is, response.getOutputStream());

        }
    }

    @PostMapping("/recipe/{recipeId}/image")
    public String handleImageUpload(@PathVariable String recipeId, @RequestParam("image-file") MultipartFile file) {
        imageService.saveImage(Long.valueOf(recipeId), file);
        return "redirect:/recipe/" + recipeId + "/update";
    }

}
