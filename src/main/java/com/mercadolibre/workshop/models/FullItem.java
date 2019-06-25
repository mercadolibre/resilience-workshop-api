package com.mercadolibre.workshop.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class FullItem {
    @JsonProperty("id")
    private String id;

    @JsonProperty("title")
    private String title;

    @JsonProperty("category")
    private Category category;

    public static FullItem from(Item item, Category category) {
        FullItem fullItem = new FullItem();
        fullItem.id = item.getId();
        fullItem.title = item.getTitle();
        fullItem.category = category;

        return fullItem;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    @JsonIgnore
    public boolean isComplete() {
        return category.isComplete();
    }
}
