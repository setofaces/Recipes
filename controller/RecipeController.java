package recipes.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import recipes.entity.Recipe;
import recipes.service.RecipeService;

import javax.validation.Valid;
import java.security.Principal;
import java.util.*;

@RestController
@RequestMapping("/api")
public class RecipeController {

    @Autowired
    RecipeService recipeService;

    @GetMapping("/recipe/{id}")
    public ResponseEntity<Recipe> getRecipe(@PathVariable int id) {
        Recipe recipe = recipeService.getRecipe(id);
        if (recipe == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(recipe, HttpStatus.OK);
    }

    @PostMapping("/recipe/new")
    public ResponseEntity<Object> addNewRecipe(@Valid @RequestBody Recipe recipe,
                                               @Autowired Principal principal) {
        recipe.setAuthor(principal.getName());
        recipeService.saveRecipe(recipe);
        Map<String, Integer> resp = new HashMap<>();
        resp.put("id", recipe.getId());
        return new ResponseEntity<>(resp, HttpStatus.OK);
    }

    @DeleteMapping("/recipe/{id}")
    public ResponseEntity<Object> deleteRecipe(@PathVariable int id,
                                               @Autowired Principal principal) {
        Recipe recipe = recipeService.getRecipe(id);
        if (recipe == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        if (!recipe.getAuthor().equalsIgnoreCase(principal.getName())) {
            return new ResponseEntity<>("wrong user", HttpStatus.FORBIDDEN);
        }
        recipeService.deleteRecipe(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/recipe/{id}")
    public ResponseEntity<Object> updateRecipe(@Valid @RequestBody Recipe recipe,
                            @PathVariable int id, @Autowired Principal principal) {
        Recipe recipeCheck = recipeService.getRecipe(id);
        if (recipeCheck == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        if (!recipeCheck.getAuthor().equalsIgnoreCase(principal.getName())) {
            return new ResponseEntity<>("wrong user", HttpStatus.FORBIDDEN);
        }
        recipe.setId(id);
        recipe.setAuthor(recipeCheck.getAuthor());
        recipe.onCreated();
        recipeService.saveRecipe(recipe);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/recipe/search/")
    public ResponseEntity<Object> searchRecipe(@RequestParam(required = false)
                                                        Map<String,String> params) {
        if (params.size() == 1) {
            Map.Entry<String,String> entry = params.entrySet().iterator().next();
            String key = entry.getKey();
            if (!entry.getKey().equalsIgnoreCase("name") &&
                    !entry.getKey().equalsIgnoreCase("category")) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
            }
            String value = entry.getValue().toLowerCase();
            List<Recipe> recipes = recipeService.getAllByKeyValue(key, value);
            return new ResponseEntity<>(Objects.requireNonNullElse(recipes, "[]"), HttpStatus.OK);
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

}
