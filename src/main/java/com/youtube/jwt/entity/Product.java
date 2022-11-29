package com.youtube.jwt.entity;

import com.sun.istack.NotNull;

import javax.persistence.*;

@Entity
@Table(name = "product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "name")
//    @NotNull(message = "Name is mandatory")
//    @NotBlank(message = "Name is mandatory")
    private String name;

    @Column(name = "Category")
//    @NotNull(message = "Category is mandatory")
//    @NotBlank(message = "Category is mandatory")
    private String Category;

    @Column(name = "qty")
//    @Min(value = 0, message = "Value must be positive")
    private int qty;

    public Product() {
    }
    public int getId() {
        return id;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
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

    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
    }
}
