package pharmacy.system;

import javax.swing.*;
import java.awt.*;

public class PharmacistDashboard extends JFrame {
    private int userId;
    private String userName;
    private String userRole;
    
    public PharmacistDashboard(int userId, String userName, String userRole) {
        this.userId = userId;
        this.userName = userName;
        this.userRole = userRole;
        
        setTitle("Pharmacist Dashboard - Pharmacy Management System");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        JLabel titleLabel = new JLabel("Pharmacist Dashboard");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(titleLabel);
        mainPanel.add(Box.createVerticalStrut(20));
        
        JLabel userInfoLabel = new JLabel("Welcome, " + userName + " (" + userRole + ")");
        userInfoLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        userInfoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(userInfoLabel);
        mainPanel.add(Box.createVerticalStrut(30));
        
        JButton addMedicineBtn = new JButton("Add Medicine");
        JButton sellMedicineBtn = new JButton("Sell Medicine");
        JButton viewMedicinesBtn = new JButton("View Medicines");
        JButton viewBillsBtn = new JButton("View My Bills");
        JButton exitBtn = new JButton("Exit");
        
        addMedicineBtn.addActionListener(e -> new AddMedicine(userId));
        sellMedicineBtn.addActionListener(e -> new SellMedicine(userId));
        viewMedicinesBtn.addActionListener(e -> new ViewMedicines(userId));
        viewBillsBtn.addActionListener(e -> new ViewMyBills(userId));
        exitBtn.addActionListener(e -> {
            this.dispose();
            new Login();
        });
        
        JPanel buttonPanel = new JPanel(new GridLayout(5, 1, 10, 10));
        buttonPanel.add(addMedicineBtn);
        buttonPanel.add(sellMedicineBtn);
        buttonPanel.add(viewMedicinesBtn);
        buttonPanel.add(viewBillsBtn);
        buttonPanel.add(exitBtn);
        
        buttonPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(buttonPanel);
        
        add(mainPanel);
        setVisible(true);
    }
}
