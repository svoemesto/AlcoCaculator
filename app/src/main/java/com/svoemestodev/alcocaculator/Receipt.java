package com.svoemestodev.alcocaculator;

import java.io.Serializable;
import java.util.List;

public class Receipt implements Serializable {
    private String name;
    private Solution result;
    private List<Solution> ingredients;

    public Receipt(String name, Solution result, List<Solution> ingredients) {
        this.name = name;
        this.result = result;
        this.ingredients = ingredients;
    }

    public Receipt() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Solution getResult() {
        return result;
    }

    public void setResult(Solution result) {
        this.result = result;
    }

    public List<Solution> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<Solution> ingredients) {
        this.ingredients = ingredients;
    }
}
