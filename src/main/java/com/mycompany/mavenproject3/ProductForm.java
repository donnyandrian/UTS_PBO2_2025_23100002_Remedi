/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.mavenproject3;

/**
 *
 * @author ASUS
 */
import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;
import javax.swing.table.DefaultTableModel;

import com.mycompany.ProductService;

public class ProductForm extends JFrame {
    private JTable drinkTable;
    private DefaultTableModel tableModel;
    private JTextField codeField;
    private JTextField nameField;
    private JComboBox<String> categoryField;
    private JTextField priceField;
    private JTextField stockField;
    private JButton saveButton;

    private ProductService service;

    public ProductForm(ProductService service) {
        this.service = service;
        setTitle("WK. Cuan | Stok Barang");
        setSize(1000, 450);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        
        // Panel form pemesanan
        JPanel formPanel = new JPanel();
        formPanel.add(new JLabel("Kode Barang"));
        codeField = new JTextField();
        codeField.setPreferredSize(new Dimension(60, 24));
        formPanel.add(codeField);
        
        formPanel.add(new JLabel("Nama Barang:"));
        nameField = new JTextField();
        nameField.setPreferredSize(new Dimension(60, 24));
        formPanel.add(nameField);
        
        formPanel.add(new JLabel("Kategori:"));
        categoryField = new JComboBox<>(new String[]{"Coffee", "Dairy", "Juice", "Soda", "Tea"});
        formPanel.add(categoryField);
        
        formPanel.add(new JLabel("Harga Jual:"));
        priceField = new JTextField();
        priceField.setPreferredSize(new Dimension(60, 24));
        formPanel.add(priceField);
        
        formPanel.add(new JLabel("Stok Tersedia:"));
        stockField = new JTextField();
        stockField.setPreferredSize(new Dimension(60, 24));
        formPanel.add(stockField);
        
        saveButton = new JButton("Simpan");
        JButton updateButton = new JButton("Perbarui");
        JButton deleteButton = new JButton("Hapus");

        saveButton.addActionListener((e) -> addProductData());
        updateButton.addActionListener((e) -> updateProductData());
        deleteButton.addActionListener((e) -> deleteProductData());

        formPanel.add(saveButton);
        formPanel.add(updateButton);
        formPanel.add(deleteButton);
        
        tableModel = new DefaultTableModel(new String[]{"Kode", "Nama", "Kategori", "Harga Jual", "Stok"}, 0);
        drinkTable = new JTable(tableModel);

        add(new JScrollPane(drinkTable), BorderLayout.CENTER);
        add(formPanel, BorderLayout.SOUTH);

        loadProductData();
    }

    private void loadProductData() {
        tableModel.setRowCount(0);
        for (Product product : service.getAllProducts()) {
            tableModel.addRow(new Object[]{
                product.getCode(), product.getName(), product.getCategory(), product.getPrice(), product.getStock()
            });
        }
    }

    private void addProductData() {
        try {
            String code = codeField.getText();
            String name = nameField.getText();
            String category = (String)categoryField.getSelectedItem();
            String priceText = priceField.getText();
            String stockText = stockField.getText();
            
            if (code.isBlank() || name.isBlank() || priceText.isBlank() || stockText.isBlank()) {
                JOptionPane.showMessageDialog(this, "Semua data harus diisi!");
                return;
            }

            double price = Double.parseDouble(priceText);
            int stock = Integer.parseInt(stockText);

            int nextId = service.getNextId();

            service.addProduct(new Product(nextId, code, name, category, price, stock));

            loadProductData();
        } catch(NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Terdapat kesalahan:\n" + ex);
        }
    }

    private void updateProductData() {
        try {
            int index = drinkTable.getSelectedRow();

            if (index == -1) {
                JOptionPane.showMessageDialog(this, "Pilih satu data yang ingin diubah");
                return;
            }
            
            String code = codeField.getText();
            String name = nameField.getText();
            String category = (String)categoryField.getSelectedItem();
            String priceText = priceField.getText();
            String stockText = stockField.getText();
            
            if (code.isBlank() || name.isBlank() || priceText.isBlank() || stockText.isBlank()) {
                JOptionPane.showMessageDialog(this, "Semua data harus diisi!");
                return;
            }
            
            double price = Double.parseDouble(priceText);
            int stock = Integer.parseInt(stockText);

            service.updateProduct(index, new Product(service.getNextId(), code, name, category, price, stock));

            loadProductData();
        } catch(NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Terdapat kesalahan:\n" + ex);
        }
    }

    private void deleteProductData() {
        try {
            int index = drinkTable.getSelectedRow();
            if (index == -1) {
                JOptionPane.showMessageDialog(this, "Pilih satu data yang ingin dihapus");
                return;
            }

            service.deleteProduct(index);

            loadProductData();
        } catch(NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Terdapat kesalahan:\n" + ex);
        }
    }
}
