package com.mycompany.mavenproject3;

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
import javax.swing.table.DefaultTableModel;

public class CustomerForm extends JFrame {
    private final JTable customerTable;
    private final DefaultTableModel tableModel;
    private final JTextField codeField;
    private final JTextField nameField;
    private final JComboBox<String> statusField;
    private final JTextField phoneField;
    private final JTextField birthField;
    private final JButton saveButton;

    private final CustomerService service;

    public CustomerForm(CustomerService service) {
        this.service = service;
        setTitle("WK. Cuan | Daftar Pelanggan (Member)");
        setSize(1000, 450);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        
        // Panel form pemesanan
        JPanel formPanel = new JPanel();
        formPanel.add(new JLabel("Kode Pelanggan"));
        codeField = new JTextField();
        codeField.setPreferredSize(new Dimension(60, 24));
        formPanel.add(codeField);
        
        formPanel.add(new JLabel("Nama Pelanggan:"));
        nameField = new JTextField();
        nameField.setPreferredSize(new Dimension(60, 24));
        formPanel.add(nameField);
        
        formPanel.add(new JLabel("No. HP:"));
        phoneField = new JTextField();
        phoneField.setPreferredSize(new Dimension(60, 24));
        formPanel.add(phoneField);
        
        formPanel.add(new JLabel("Tanggal Lahir:"));
        birthField = new JTextField();
        birthField.setPreferredSize(new Dimension(60, 24));
        formPanel.add(birthField);
        
        formPanel.add(new JLabel("Status:"));
        statusField = new JComboBox<>(new String[]{"Aktif", "Tidak Aktif"});
        formPanel.add(statusField);
        
        saveButton = new JButton("Simpan");
        JButton updateButton = new JButton("Perbarui");
        JButton deleteButton = new JButton("Hapus");

        saveButton.addActionListener((e) -> addCustomerData());
        updateButton.addActionListener((e) -> updateCustomerData());
        deleteButton.addActionListener((e) -> deleteCustomerData());

        formPanel.add(saveButton);
        formPanel.add(updateButton);
        formPanel.add(deleteButton);
        
        tableModel = new DefaultTableModel(new String[]{"Kode", "Nama", "No. HP", "Tanggal Lahir", "Status", "Total Belanja"}, 0);
        customerTable = new JTable(tableModel);

        add(new JScrollPane(customerTable), BorderLayout.CENTER);
        add(formPanel, BorderLayout.SOUTH);

        this.service.addDataChangeListener(e -> loadCustomerData());

        loadCustomerData();
    }

    private void loadCustomerData() {
        tableModel.setRowCount(0);
        for (Customer customer : service.getAllCustomers()) {
            tableModel.addRow(new Object[]{
                customer.getCode(), customer.getName(), customer.getPhone(), customer.getBirth(), customer.getStatus(), customer.getTotalSpent()
            });
        }
    }

    private void addCustomerData() {
        try {
            String code = codeField.getText();
            String name = nameField.getText();
            String phoneText = phoneField.getText();
            String birthText = birthField.getText();
            String status = (String)statusField.getSelectedItem();
            
            if (code.isBlank() || name.isBlank() || phoneText.isBlank() || birthText.isBlank()) {
                JOptionPane.showMessageDialog(this, "Semua data harus diisi!");
                return;
            }

            int nextId = service.getNextId();

            service.addCustomer(new Customer(nextId, code, name, phoneText, birthText, status, 0));
        } catch(NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Terdapat kesalahan:\n" + ex);
        }
    }

    private void updateCustomerData() {
        try {
            int index = customerTable.getSelectedRow();

            if (index == -1) {
                JOptionPane.showMessageDialog(this, "Pilih satu data yang ingin diubah");
                return;
            }
            
            String code = codeField.getText();
            String name = nameField.getText();
            String status = (String)statusField.getSelectedItem();
            String phoneText = phoneField.getText();
            String birthText = birthField.getText();
            
            if (code.isBlank() || name.isBlank() || phoneText.isBlank() || birthText.isBlank()) {
                JOptionPane.showMessageDialog(this, "Semua data harus diisi!");
                return;
            }
            
            var customer = service.getAllCustomers().get(index);
            service.updateCustomer(index, new Customer(customer.getId(), code, name, phoneText, birthText, status, customer.getTotalSpent()));
        } catch(NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Terdapat kesalahan:\n" + ex);
        }
    }

    private void deleteCustomerData() {
        try {
            int index = customerTable.getSelectedRow();
            if (index == -1) {
                JOptionPane.showMessageDialog(this, "Pilih satu data yang ingin dihapus");
                return;
            }

            service.deleteCustomer(index);
        } catch(NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Terdapat kesalahan:\n" + ex);
        }
    }
}
