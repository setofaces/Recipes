package recipes.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;


import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Entity
@Table(name = "recipes")
public class Recipe {

    @JsonIgnore
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "name")
    @NotEmpty
    @NotBlank
    @NotNull
    private String name;
    @Column(name = "category")
    @NotEmpty
    @NotBlank
    @NotNull
    private String category;
    @Column(name = "date")
    private LocalDateTime date;
    @Column(name = "description")
    @NotEmpty
    @NotBlank
    @NotNull
    private String description;
    @Column(name = "ingredients")
    @Size(min = 1)
    @NotNull
    private String[] ingredients;
    @Column(name = "directions")
    @Size(min = 1)
    @NotNull
    private String[] directions;
    @JsonIgnore
    @Column(name = "author")
    private String author;

    @PrePersist
    public void onCreated() {
        this.date = LocalDateTime.now();
    }

    public Recipe() {
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String[] getIngredients() {
        return ingredients;
    }

    public void setIngredients(String[] ingredients) {
        this.ingredients = ingredients;
    }

    public String[] getDirections() {
        return directions;
    }

    public void setDirections(String[] directions) {
        this.directions = directions;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
