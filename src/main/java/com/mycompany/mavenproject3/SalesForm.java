package com.mycompany.mavenproject3;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class SalesForm extends JFrame {
    @SuppressWarnings("unused")
    private ProductService service;

    private JComboBox<String> itemField;
    private JComboBox<String> customerField;
    private JTextField qtyField;
    private JTextField priceField;

    public SalesForm(ProductService service, CustomerService customerService) {
        this.service = service;

        setTitle("WK. Cuan | Form Penjualan");
        setSize(450, 250);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        var productsCount = service.getAllProducts().size();
        if (productsCount <= 0) {
            JOptionPane.showMessageDialog(null,
                    "Tidak ada produk yang terdaftar untuk dijual! Silahkan tambahkan produk terlebih dahulu.");
            dispose();
            return;
        }

        String[] items = new String[service.getAllProducts().size()];
        for (int i = 0; i < items.length; i++) {
            items[i] = service.getAllProducts().get(i).getName();
        }
        itemField = new JComboBox<>(items);

        String[] customers = new String[customerService.getAllCustomers().size()];
        for (int i = 0; i < customers.length; i++) {
            customers[i] = customerService.getAllCustomers().get(i).getName();
        }
        customerField = new JComboBox<>(customers);

        qtyField = new JTextField();
        qtyField.setPreferredSize(new Dimension(60, 24));
        priceField = new JTextField();
        priceField.setPreferredSize(new Dimension(60, 24));

        JPanel labelPanel = new JPanel();

        Product defItem = service.getAllProducts().get(0);

        JLabel stocksText = new JLabel(Integer.toString(defItem.getStock()));
        priceField.setText(Double.toString(defItem.getPrice()));

        labelPanel.setLayout(new GridLayout(5, 2));
        labelPanel.add(new JLabel("Pelanggan:"));
        labelPanel.add(customerField);
        labelPanel.add(new JLabel("Barang:"));
        labelPanel.add(itemField);
        labelPanel.add(new JLabel("Stok tersedia:"));
        labelPanel.add(stocksText);
        labelPanel.add(new JLabel("Harga jual:"));
        labelPanel.add(priceField);
        labelPanel.add(new JLabel("Qty:"));
        labelPanel.add(qtyField);

        itemField.addActionListener((event) -> {
            int index = itemField.getSelectedIndex();
            Product prod = service.getAllProducts().get(index);
            
            stocksText.setText(Integer.toString(prod.getStock()));
            priceField.setText(Double.toString(prod.getPrice()));
        });

        JButton processButton = new JButton("Proses");
        processButton.addActionListener(e -> {
            int qty = Integer.parseInt(qtyField.getText());
            double price = Double.parseDouble(priceField.getText());

            int index = itemField.getSelectedIndex();
            int customerIndex = customerField.getSelectedIndex();
            Product prod = service.getAllProducts().get(index);

            int finalStock = prod.getStock() - qty;
            if (finalStock < 0) {
                JOptionPane.showMessageDialog(this,
                        "Stock tidak cukup. Jumlah yang tersedia: " + Integer.toString(prod.getStock()));
                return;
            }

            service.updateProduct(index, new Product(prod.getId(), prod.getCode(), prod.getName(), prod.getCategory(),
                    price, finalStock));

            customerService.updateCustomerSpent(customerIndex, price * qty);

            stocksText.setText(Integer.toString(finalStock));
            JOptionPane.showMessageDialog(this, "Berhasil di jual!");
        });

        add(labelPanel, BorderLayout.CENTER);
        add(processButton, BorderLayout.SOUTH);

        setVisible(true);
    }
}
