package com.slinger.recipeapp.mockdata;

import com.slinger.recipeapp.domain.*;
import com.slinger.recipeapp.repositories.CategoryRepository;
import com.slinger.recipeapp.repositories.RecipeRepository;
import com.slinger.recipeapp.repositories.UnitOfMeasureRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Component
public class MockDataLoader implements CommandLineRunner {

    final RecipeRepository recipeRepository;
    final UnitOfMeasureRepository unitOfMeasureRepository;
    final CategoryRepository categoryRepository;

    public MockDataLoader(RecipeRepository recipeRepository, UnitOfMeasureRepository unitOfMeasureRepository, CategoryRepository categoryRepository) {
        this.recipeRepository = recipeRepository;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        log.debug("Loading data...");
        recipeRepository.saveAll(getRecipes());
    }

    private List<Recipe> getRecipes() {

        List<Recipe> recipes = new ArrayList<>();

        //get UOMs
        Optional<UnitOfMeasure> eachUomOptional = unitOfMeasureRepository.findByDescription("Each");

        if(!eachUomOptional.isPresent()){
            throw new RuntimeException("Expected UOM Not Found");
        }

        Optional<UnitOfMeasure> tableSpoonUomOptional = unitOfMeasureRepository.findByDescription("Tablespoon");

        if(!tableSpoonUomOptional.isPresent()){
            throw new RuntimeException("Expected UOM Not Found");
        }

        Optional<UnitOfMeasure> teaSpoonUomOptional = unitOfMeasureRepository.findByDescription("Teaspoon");

        if(!teaSpoonUomOptional.isPresent()){
            throw new RuntimeException("Expected UOM Not Found");
        }

        Optional<UnitOfMeasure> dashUomOptional = unitOfMeasureRepository.findByDescription("Dash");

        if(!dashUomOptional.isPresent()){
            throw new RuntimeException("Expected UOM Not Found");
        }

        Optional<UnitOfMeasure> pintUomOptional = unitOfMeasureRepository.findByDescription("Pint");

        if(!pintUomOptional.isPresent()){
            throw new RuntimeException("Expected UOM Not Found");
        }

        Optional<UnitOfMeasure> cupsUomOptional = unitOfMeasureRepository.findByDescription("Cup");

        if(!cupsUomOptional.isPresent()){
            throw new RuntimeException("Expected UOM Not Found");
        }

        Optional<UnitOfMeasure> poundUomOptional = unitOfMeasureRepository.findByDescription("Pound");

        if(!poundUomOptional.isPresent()) {
            throw new RuntimeException("Expected UOM Not Found");
        }

        Optional<UnitOfMeasure> ounceUomOptional = unitOfMeasureRepository.findByDescription("Ounce");

        if(!ounceUomOptional.isPresent()) {
            throw new RuntimeException("Expected UOM Not Found");
        }

        //get optionals
        UnitOfMeasure eachUom = eachUomOptional.get();
        UnitOfMeasure tableSpoonUom = tableSpoonUomOptional.get();
        UnitOfMeasure teaSpoonUom = tableSpoonUomOptional.get();
        UnitOfMeasure dashUom = dashUomOptional.get();
        UnitOfMeasure pintUom = pintUomOptional.get();
        UnitOfMeasure cupsUom = cupsUomOptional.get();
        UnitOfMeasure poundUom = poundUomOptional.get();
        UnitOfMeasure ounceUom = ounceUomOptional.get();

        //get Categories
        Optional<Category> americanCategoryOptional = categoryRepository.findByDescription("American");

        if(!americanCategoryOptional.isPresent()){
            throw new RuntimeException("Expected Category Not Found");
        }

        Optional<Category> mexicanCategoryOptional = categoryRepository.findByDescription("Mexican");

        if(!mexicanCategoryOptional.isPresent()){
            throw new RuntimeException("Expected Category Not Found");
        }

        Optional<Category> italianCategoryOptional = categoryRepository.findByDescription("Italian");

        if(!italianCategoryOptional.isPresent()) {
            throw new RuntimeException("Expected Category Not Found");
        }

        Optional<Category> frenchCategoryOptional = categoryRepository.findByDescription("French");

        if(!frenchCategoryOptional.isPresent()) {
            throw new RuntimeException("Expected Category Not Found");
        }

        Category americanCategory = americanCategoryOptional.get();
        Category mexicanCategory = mexicanCategoryOptional.get();
        Category italianCategory = italianCategoryOptional.get();
        Category frenchCategory = frenchCategoryOptional.get();


        //////////////////////// START OF RECIPE: Blue Corn Chicken Nachos /////////////////////////////////
        Recipe chickenNachos = new Recipe();

        chickenNachos.setDescription("Blue Corn Chicken Nachos");
        chickenNachos.setPrepTime(10);
        chickenNachos.setCookTime(20);
        chickenNachos.setDifficulty(Difficulty.EASY);
        chickenNachos.setServings(4);
        chickenNachos.setSource("simplyrecipes");
        chickenNachos.setUrl("https://www.simplyrecipes.com/recipes/blue_corn_chicken_nachos/");
        chickenNachos.getCategories().add(mexicanCategory);

        chickenNachos.setDirections("1 Poach the chicken breast: Place chicken breast(s) in a medium saucepan and cover with 2 inches of water. Add the salt. Cover. Bring to a boil. Turn off the heat. Let sit covered for 10 minutes in the hot salted water to gently poach. Drain. Remove from pan.\n" +
                "\n" +
                "2 Preheat oven to 350°F.\n" +
                "\n" +
                "3 Make the cheese sauce: If you have a double boiler, use it. Otherwise put an inch of water in a small pot and place a medium metal bowl on top of it to create a double boiler effect.\n" +
                "\n" +
                "Put the shredded cheese in the bowl and toss to coat with the cornstarch. Pour in the heavy cream and milk.\n" +
                "\n" +
                "Turn on the heat and bring the water in the pot to a boil. Slowly heat up the milk cream cheese mixture until eventually the cheese melts, stirring occasionally until smooth.\n" +
                "\n" +
                "4 Heat tortilla chips in oven: While the cheese is heating, spread the tortilla chips out in a large baking sheet and put in the oven at 350°F. Heat for 5 minutes. Then remove from the oven.\n" +
                "\n" +
                "5 Cut the cooked chicken into 1/4 to 1/2-inch cubes.\n" +
                "\n" +
                "6 Top tortilla chips with cheese sauce, chicken, and salsa: When the cheese sauce is melty and smooth, put the heated tortilla chips on a platter and pour the cheese sauce over it. Top with cooked chicken and pico de gallo tomato salsa.\n" +
                "\n");


        Notes chickenNachoNotes = new Notes();
        chickenNachoNotes.setRecipeNotes("Taking a tip from my other favorite nacho recipe—Mexican street corn nachos—rather than baking the chips with the cheese on them, I’m making the cheese sauce separately.\n" +
                "\n" +
                "This way the chips have a better chance at staying somewhat firm and crunchy when you eat the nachos. Otherwise they tend to bake into a limp mess.\n" +
                "\n" +
                "Making a cheese sauce is easy to do with monterey jack cheese. Jack melts beautifully with cream and doesn’t tend to separate like cheddar can.\n" +
                "\n" +
                "Happy Fourth!");

        chickenNachos.setNotes(chickenNachoNotes);

        chickenNachos.addIngredient(new Ingredient("skinless chicken breast", new BigDecimal(".5"), poundUom));
        chickenNachos.addIngredient(new Ingredient("salt", new BigDecimal(1), teaSpoonUom));
        chickenNachos.addIngredient(new Ingredient("blue corn tortilla chips", new BigDecimal(14), ounceUom));
        chickenNachos.addIngredient(new Ingredient(" pepper jack or monterey jack ", new BigDecimal(1), poundUom));
        chickenNachos.addIngredient(new Ingredient("cornstarch", new BigDecimal(1), tableSpoonUom));
        chickenNachos.addIngredient(new Ingredient("heavy cream", new BigDecimal(".5"), cupsUom));
        chickenNachos.addIngredient(new Ingredient("milk", new BigDecimal(".5"), cupsUom));
        chickenNachos.addIngredient(new Ingredient("fresh tomato salsa", new BigDecimal(1), cupsUom));
        chickenNachos.addIngredient(new Ingredient("fresh cilantro", new BigDecimal(".5"), cupsUom));

        recipes.add(chickenNachos);

        //////////////////////// END OF RECIPE: Blue Corn Chicken Nachos /////////////////////////////////



        //////////////////////// START OF RECIPE: Blueberry Buttermilk Pancakes //////////////////////////
        Recipe buttermilkPancakes = new Recipe();

        buttermilkPancakes.setDescription("Blueberry Buttermilk Pancakes");
        buttermilkPancakes.setPrepTime(5);
        buttermilkPancakes.setCookTime(20);
        buttermilkPancakes.setDifficulty(Difficulty.EASY);
        buttermilkPancakes.setServings(3);
        buttermilkPancakes.setSource("simplyrecipes");
        buttermilkPancakes.setUrl("https://www.simplyrecipes.com/recipes/blueberry_buttermilk_pancakes/");
        buttermilkPancakes.getCategories().add(americanCategory);
        buttermilkPancakes.getCategories().add(frenchCategory);

        buttermilkPancakes.setDirections("1 Make pancake batter: Whisk together the dry ingredients in a large bowl.\n" +
                "\n" +
                "In a separate bowl, whisk the eggs, then whisk in the milk, and buttermilk.\n" +
                "\n" +
                "Pour the wet ingredients into the dry ingredients and combine, using a wooden spoon. Mix only until the batter just comes together.\n" +
                "\n" +
                "Stir in the melted butter. Do not over-mix! The mixture should be a little lumpy. Lumpy is good. A lumpy batter makes fluffy pancakes.\n" +
                "\n" +
                "At this point you can either gently fold in the blueberries, or wait until you pour the batter onto the griddle, and then place the blueberries into the surface of the pancake batter. Placing the blueberries into the pancakes while they are cooking will help keep them from bleeding.\n" +
                "\n" +
                "2 Ladle batter in hot pan: Heat a griddle or large pan on medium to medium high heat. (A large cast iron pan works great for cooking pancakes.)\n" +
                "\n" +
                "Oil the pan with either a little butter or vegetable oil.\n" +
                "\n" +
                "Ladle the pancake batter onto the griddle to the desired size (a quarter-cup measure works well for this), anywhere from 4 to 6 inches wide.\n" +
                "\n" +
                "If you haven't already added the blueberries to the batter, you can place several in each pancake while it cooks.\n" +
                "\n" +
                "3 When you see air bubbles in the center, flip the pancakes over: When air bubbles start to bubble up to the surface at the center of the pancakes (about 2-3 minutes), use a flat metal spatula to flip them over.\n" +
                "\n" +
                "After a minute, peek under one for doneness. When golden or darker golden brown, they are done.\n" +
                "\n" +
                "Note that cooking the second side takes only about half as long as the first side. And the second side doesn't brown as evenly as the first side.\n" +
                "\n" +
                "Continue to make the batches of pancakes, putting a little oil or butter on the pan before each batch so the pancakes don't stick.\n" +
                "\n" +
                "Serve immediately. Serve with butter, maple syrup, and extra blueberries.\n" +
                "\n");

        Notes buttermilkPancakeNotes = new Notes();
        buttermilkPancakeNotes.setRecipeNotes("A final tip. Audrey was all of ten years old when she showed me how she keeps the blueberries from bleeding into the batter and turning it purple. She likes to add them individually to each pancake once the batter is poured out onto the griddle. This way you can space them out well and they don’t turn your blueberry pancakes blue.");

        buttermilkPancakes.setNotes(buttermilkPancakeNotes);

        buttermilkPancakes.addIngredient(new Ingredient("purpose flour", new BigDecimal(2), cupsUom));
        buttermilkPancakes.addIngredient(new Ingredient("salt", new BigDecimal(".5"), teaSpoonUom));
        buttermilkPancakes.addIngredient(new Ingredient("baking powder", new BigDecimal(".5"), teaSpoonUom));
        buttermilkPancakes.addIngredient(new Ingredient("baking soda", new BigDecimal(".5"), teaSpoonUom));
        buttermilkPancakes.addIngredient(new Ingredient("sugar", new BigDecimal(2), tableSpoonUom));
        buttermilkPancakes.addIngredient(new Ingredient("large eggs", new BigDecimal(2), eachUom));
        buttermilkPancakes.addIngredient(new Ingredient("buttermilk", new BigDecimal(".5"), cupsUom));
        buttermilkPancakes.addIngredient(new Ingredient("milk", new BigDecimal(1), cupsUom));
        buttermilkPancakes.addIngredient(new Ingredient("blueberries", new BigDecimal(1), cupsUom));

        recipes.add(buttermilkPancakes);

        //////////////////////// END OF RECIPE: Blueberry Buttermilk Pancakes //////////////////////////


        //////////////////////// START OF RECIPE: FRESH BASIL PESTO  ///////////////////////////////////
        Recipe basilPesto = new Recipe();
        basilPesto.setDescription("Fresh Basil Pesto");
        basilPesto.setPrepTime(15);
        basilPesto.setCookTime(0);
        basilPesto.setDifficulty(Difficulty.MEDIUM);
        basilPesto.setServings(1);
        basilPesto.setSource("simplyrecipes");
        basilPesto.setUrl("https://www.simplyrecipes.com/recipes/fresh_basil_pesto/");
        basilPesto.getCategories().add(italianCategory);

        basilPesto.setDirections("1 Pulse basil and pine nuts in a food processor: Place the basil leaves and pine nuts into the bowl of a food processor and pulse a several times.\n" +
                "\n" +
                "2: Add the garlic and cheese: Add the garlic and Parmesan or Romano cheese and pulse several times more. Scrape down the sides of the food processor with a rubber spatula.\n" +
                "\n" +
                "3 Stream in the olive oil: While the food processor is running, slowly add the olive oil in a steady small stream. Adding the olive oil slowly, while the processor is running, will help it emulsify and help keep the olive oil from separating. Occasionally stop to scrape down the sides of the food processor.\n" +
                "\n" +
                "4 Stir in salt and freshly ground black pepper, add more to taste.\n" +
                "\n" +
                "Toss with pasta for a quick sauce, dollop over baked potatoes, or spread onto crackers or toasted slices of bread.");

        Notes basilPestoNotes = new Notes();
        basilPestoNotes.setRecipeNotes("If you want to freeze the pesto you make, omit the cheese (it doesn’t freeze well). Line an ice cube tray with plastic wrap, and fill each pocket with the pesto. Freeze and then remove from the ice tray and store in a freezer bag. When you want to use, defrost and add in grated Parmesan or Romano.");

        basilPesto.setNotes(basilPestoNotes);

        basilPesto.addIngredient(new Ingredient("fresh basil leaves", new BigDecimal(2), cupsUom));
        basilPesto.addIngredient(new Ingredient("grated romano", new BigDecimal(".5"), cupsUom));
        basilPesto.addIngredient(new Ingredient("extra virgin olive oil", new BigDecimal(".5"), cupsUom));
        basilPesto.addIngredient(new Ingredient("pine nuts", new BigDecimal(".5"), cupsUom));
        basilPesto.addIngredient(new Ingredient("minced garlic cloves", new BigDecimal(3), teaSpoonUom));
        basilPesto.addIngredient(new Ingredient("salt", new BigDecimal(1), teaSpoonUom));
        basilPesto.addIngredient(new Ingredient("pepper", new BigDecimal(1), teaSpoonUom));

        recipes.add(basilPesto);

        //////////////////////// END OF RECIPE: FRESH BASIL PESTO  ///////////////////////////////////



        return recipes;
    }

}
