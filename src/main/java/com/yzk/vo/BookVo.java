package com.yzk.vo;

public class BookVo {

    private Integer id;
    private String name;
    private String description;
    private String author;
    private String publis;
    private String category;
    private String categoryname;
    private Integer Inventory;

    public BookVo() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPublis() {
        return publis;
    }

    public void setPublis(String publis) {
        this.publis = publis;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCategoryname() {
        return categoryname;
    }

    public void setCategoryname(String categoryname) {
        this.categoryname = categoryname;
    }

    public Integer getInventory() {
        return Inventory;
    }

    public void setInventory(Integer inventory) {
        Inventory = inventory;
    }

    public BookVo(Integer id, String name, String description, String author, String publis, String category, String categoryname, Integer inventory) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.author = author;
        this.publis = publis;
        this.category = category;
        this.categoryname = categoryname;
        Inventory = inventory;
    }
    @Override
    public String toString() {
        return "BookVo{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", author='" + author + '\'' +
                ", publis='" + publis + '\'' +
                ", category='" + category + '\'' +
                ", categoryname='" + categoryname + '\'' +
                ", Inventory=" + Inventory +
                '}';
    }

}
