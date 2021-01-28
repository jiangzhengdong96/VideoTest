package com.example.buttontest.entity;

import java.io.Serializable;

public class CategoryEntity implements Serializable {
    /**
     * categoryId : 1
     * categoryName : 游戏
     */

    private Integer categoryId;
    private String categoryName;

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}