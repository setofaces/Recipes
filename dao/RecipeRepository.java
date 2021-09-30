package recipes.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import recipes.entity.Recipe;

import java.util.List;
import java.util.Optional;

public interface RecipeRepository extends JpaRepository<Recipe, Integer> {

    Optional<List<Recipe>> findAllByCategoryIgnoreCaseOrderByDateDesc(String category);
    Optional<List<Recipe>> findAllByNameContainingIgnoreCaseOrderByDateDesc(String name);
}
