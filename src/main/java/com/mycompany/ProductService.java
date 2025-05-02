package com.mycompany;

import java.util.ArrayList;
import java.util.List;

import com.mycompany.mavenproject3.Product;

public class ProductService {
    private List<Product> products = new ArrayList<>();
    private int currentId = 0;

    public ProductService() {
    }

    public int getCurrentId() {
        return currentId;
    }
    public int getNextId() {
        return currentId + 1;
    }

    public List<Product> getAllProducts() {
        return products;
    }

    public void addProduct(Product item) {
        currentId = item.getId();
        products.add(item);
    }

    public void updateProduct(int id, Product item) {
        products.set(id, item);
    }

    public void deleteProduct(int id) {
        products.remove(id);
    }
}
