package pharmacy.system;

import javax.swing.*;
import java.awt.*;

public class AdminDashboard extends JFrame {
    private int userId;
    private String userName;
    private String userRole;
    
    public AdminDashboard(int userId, String userName, String userRole) {
        this.userId = userId;
        this.userName = userName;
        this.userRole = userRole;
        
        setTitle("Admin Dashboard - Pharmacy Management System");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        JLabel titleLabel = new JLabel("Admin Dashboard");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(titleLabel);
        mainPanel.add(Box.createVerticalStrut(20));
        
        JLabel userInfoLabel = new JLabel("Welcome, " + userName + " (" + userRole + ")");
        userInfoLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        userInfoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(userInfoLabel);
        mainPanel.add(Box.createVerticalStrut(30));
        
        JButton manageUsersBtn = new JButton("Manage Users");
        JButton viewAllMedicinesBtn = new JButton("View All Medicines");
        JButton viewAllBillsBtn = new JButton("View All Bills");
        JButton exitBtn = new JButton("Exit");
        
        manageUsersBtn.addActionListener(e -> new ManageUsers(userId));
        viewAllMedicinesBtn.addActionListener(e -> new ViewAllMedicines(userId));
        viewAllBillsBtn.addActionListener(e -> new ViewAllBills(userId));
        exitBtn.addActionListener(e -> {
            this.dispose();
            new Login();
        });
        
        JPanel buttonPanel = new JPanel(new GridLayout(4, 1, 10, 10));
        buttonPanel.add(manageUsersBtn);
        buttonPanel.add(viewAllMedicinesBtn);
        buttonPanel.add(viewAllBillsBtn);
        buttonPanel.add(exitBtn);
        
        buttonPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(buttonPanel);
        
        add(mainPanel);
        setVisible(true);
    }
}
