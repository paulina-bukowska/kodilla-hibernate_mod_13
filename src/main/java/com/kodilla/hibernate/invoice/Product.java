package com.kodilla.hibernate.invoice;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Product {
    private int id;
    private String name;

    public Product() {
    }

    public Product(String name) {
        this.name = name;
    }

    @Id
    @GeneratedValue
    @Column(name = "ID", nullable = false, unique = true)
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    private void setId(int id) {
        this.id = id;
    }

    private void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Product)) return false;

        Product product = (Product) o;

        return name != null ? name.equals(product.name) : product.name == null;
    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }

    @Override
    public String toString() {
        return name;
    }
}
