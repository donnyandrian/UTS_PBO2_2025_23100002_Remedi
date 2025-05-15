package com.mycompany.mavenproject3;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class SalesForm extends JFrame {
    private ProductService service;

    private JComboBox<String> itemField;
    private JTextField qtyField;
    private JTextField priceField;

    public SalesForm(ProductService service) {
        this.service = service;

        setTitle("WK. Cuan | Form Penjualan");
        setSize(450, 200);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        String[] items = new String[service.getAllProducts().size()];
        for (int i = 0; i < items.length; i++) {
            items[i] = service.getAllProducts().get(i).getName();
        }
        itemField = new JComboBox<>(items);

        qtyField = new JTextField();
        qtyField.setPreferredSize(new Dimension(60, 24));
        priceField = new JTextField();
        priceField.setPreferredSize(new Dimension(60, 24));

        JPanel labelPanel = new JPanel();

        Product defItem = service.getAllProducts().get(0);

        JLabel stocksText = new JLabel(Integer.toString(defItem.getStock()));
        priceField.setText(Double.toString(defItem.getPrice()));

        labelPanel.setLayout(new GridLayout(4, 2));
        labelPanel.add(new JLabel("Barang:"));
        labelPanel.add(itemField);
        labelPanel.add(new JLabel("Stok tersedia:"));
        labelPanel.add(stocksText);
        labelPanel.add(new JLabel("Harga jual:"));
        labelPanel.add(priceField);
        labelPanel.add(new JLabel("Qty:"));
        labelPanel.add(qtyField);

        itemField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                int index = itemField.getSelectedIndex();
                Product prod = service.getAllProducts().get(index);

                stocksText.setText(Integer.toString(prod.getStock()));
                priceField.setText(Double.toString(prod.getPrice()));
            }
        });

        JButton processButton = new JButton("Proses");
        processButton.addActionListener(e -> {
            int qty = Integer.parseInt(qtyField.getText());

            int index = itemField.getSelectedIndex();
            Product prod = service.getAllProducts().get(index);

            int finalStock = prod.getStock() - qty;
            if (finalStock < 0) {
                JOptionPane.showMessageDialog(this,
                        "Stock tidak cukup. Jumlah yang tersedia: " + Integer.toString(prod.getStock()));
                return;
            }

            service.updateProduct(index, new Product(prod.getId(), prod.getCode(), prod.getName(), prod.getCategory(),
                    prod.getPrice(), finalStock));

            stocksText.setText(Integer.toString(finalStock));
            JOptionPane.showMessageDialog(this, "Berhasil di jual!");
        });

        add(labelPanel, BorderLayout.CENTER);
        add(processButton, BorderLayout.SOUTH);
    }
}
