package recipes.service;

import recipes.entity.Recipe;

import java.util.List;

public interface RecipeService {

    public List<Recipe> getAllRecipes();

    void deleteRecipe(int id);

    public void saveRecipe(Recipe recipe);

    public Recipe getRecipe(int id);

    List<Recipe> getAllByKeyValue(String key, String value);
}
