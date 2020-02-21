package ua.polischuk.model.entity;

import java.util.HashMap;
import java.util.Map;

public enum Category {
    PROGRAMMING("PROGRAMMING"), MATH("MATH"), PHYSICS("PHYSICS"), HISTORY("HISTORY");

    private String enteredCategory;

    Category(String enteredCategory) {
        this.enteredCategory = enteredCategory;
    }

    public String getEnteredCategory() {
        return enteredCategory;
    }


    private static final Map<String, Category> CATEGORY_MAP = new HashMap<>();

    static {
        for(Category category: values()){
            CATEGORY_MAP.put(category.getEnteredCategory(), category);
        }
    }

    public static Category getCategoryByString( String enteredCategory){
        return CATEGORY_MAP.get(enteredCategory);
    }
}
