package com.mycompany.mavenproject3;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import com.mycompany.ProductService;

public class Mavenproject3 extends JFrame implements Runnable {
    private String text;
    private int x;
    private int width;
    private BannerPanel bannerPanel;
    private JButton addProductButton;
    private ProductService service;
    private int currentId = 0;

    public Mavenproject3(ProductService service) {
        this.service = service;
        // Menu yang tersedia: Americano | Pandan Latte | Aren Latte | Matcha Frappucino | Jus Apel
        this.text = "Menu yang tersedia: ";
        for (int i = 0; i < service.getAllProducts().size(); i++) {
            var prod = service.getAllProducts().get(i);
            this.text += prod.getName();

            if (i != service.getAllProducts().size() - 1) this.text += " | ";
        }

        setTitle("WK. STI Chill");
        setSize(600, 150);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Panel teks berjalan
        bannerPanel = new BannerPanel();
        add(bannerPanel, BorderLayout.CENTER);

        // Tombol "Kelola Produk"
        JPanel bottomPanel = new JPanel();
        addProductButton = new JButton("Kelola Produk");
        bottomPanel.add(addProductButton);
        add(bottomPanel, BorderLayout.SOUTH);
        
        addProductButton.addActionListener(e -> {
            new ProductForm(service).setVisible(true);
        });

        setVisible(true);

        Thread thread1 = new Thread(() -> {
            while (true) {
                this.text = "Menu yang tersedia: ";
                for (int i = 0; i < service.getAllProducts().size(); i++) {
                    var prod = service.getAllProducts().get(i);
                    this.text += prod.getName();

                    if (i != service.getAllProducts().size() - 1) this.text += " | ";
                }

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                }
            }
        });
        thread1.start();

        Thread thread = new Thread(this);
        thread.start();
    }

    class BannerPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.setColor(Color.RED);
            g.setFont(new Font("Arial", Font.BOLD, 18));
            g.drawString(text, x, getHeight() / 2);
        }
    }

    @Override
    public void run() {
        width = getWidth();
        while (true) {
            x += 5;
            if (x > width) {
                x = -getFontMetrics(new Font("Arial", Font.BOLD, 18)).stringWidth(text);
            }
            bannerPanel.repaint();
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                break;
            }
        }
    }

    public static void main(String[] args) {
        ProductService service = new ProductService();
        service.addProduct(new Product(1, "P001", "Americano", "Coffee", 18000, 10));
        service.addProduct(new Product(2, "P002", "Pandan Latte", "Coffee", 15000, 8));

        var app = new Mavenproject3(service);
    }
}
