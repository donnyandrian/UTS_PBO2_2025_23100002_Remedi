package com.mycompany.mavenproject3;

import java.util.ArrayList;
import java.util.List;

public class ProductService {
    private final List<Product> products = new ArrayList<>();
    private final List<DataChangeListener> listeners = new ArrayList<>();
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
        fireDataChangeListener("ADD");
    }

    public void updateProduct(int id, Product item) {
        products.set(id, item);
        fireDataChangeListener("UPDATE");
    }

    public void deleteProduct(int id) {
        products.remove(id);
        fireDataChangeListener("DELETE");
    }

    public void addDataChangeListener(DataChangeListener listener) {
        listeners.add(listener);
    }

    public void removeDataChangeListener(DataChangeListener listener) {
        listeners.remove(listener);
    }

    private void fireDataChangeListener(String operation) {
        DataChangeEvent event = new DataChangeEvent(operation);
        for (DataChangeListener listener : listeners) {
            listener.onDataChanged(event);
        }
    }
}
