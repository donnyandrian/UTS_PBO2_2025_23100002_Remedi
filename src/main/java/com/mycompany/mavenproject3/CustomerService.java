package com.mycompany.mavenproject3;

import java.util.ArrayList;
import java.util.List;

public class CustomerService {
    private final List<Customer> customers = new ArrayList<>();
    private final List<DataChangeListener> listeners = new ArrayList<>();
    private int currentId = 0;

    public CustomerService() {
    }

    public int getCurrentId() {
        return currentId;
    }
    public int getNextId() {
        return currentId + 1;
    }

    public List<Customer> getAllCustomers() {
        return customers;
    }

    public void addCustomer(Customer item) {
        currentId = item.getId();
        customers.add(item);
        fireDataChangeListener("ADD");
    }

    public void updateCustomer(int id, Customer item) {
        customers.set(id, item);
        fireDataChangeListener("UPDATE");
    }

    public void updateCustomerSpent(int id, double totalSpent) {
        customers.get(id).addTotalSpent(totalSpent);
        fireDataChangeListener("UPDATE");
    }

    public void deleteCustomer(int id) {
        customers.remove(id);
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
