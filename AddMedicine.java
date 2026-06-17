package pharmacy.system;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.text.SimpleDateFormat;

public class AddMedicine extends JFrame {
    private int userId;
    private JTextField nameField;
    private JTextField companyField;
    private JTextField categoryField;
    private JTextField quantityField;
    private JTextField priceField;
    private JTextField expiryDateField;
    private JTextField batchField;
    private JButton addButton;
    private JButton backButton;
    
    public AddMedicine(int userId) {
        this.userId = userId;
        setTitle("Add Medicine - Pharmacy Management System");
        setSize(500, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        JPanel inputPanel = new JPanel(new GridLayout(7, 2, 10, 10));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        inputPanel.add(new JLabel("Medicine Name:"));
        nameField = new JTextField();
        inputPanel.add(nameField);
        
        inputPanel.add(new JLabel("Company Name:"));
        companyField = new JTextField();
        inputPanel.add(companyField);
        
        inputPanel.add(new JLabel("Category:"));
        categoryField = new JTextField();
        inputPanel.add(categoryField);
        
        inputPanel.add(new JLabel("Quantity:"));
        quantityField = new JTextField();
        inputPanel.add(quantityField);
        
        inputPanel.add(new JLabel("Price per Unit:"));
        priceField = new JTextField();
        inputPanel.add(priceField);
        
        inputPanel.add(new JLabel("Expiry Date (YYYY-MM-DD):"));
        expiryDateField = new JTextField();
        inputPanel.add(expiryDateField);
        
        inputPanel.add(new JLabel("Batch Number:"));
        batchField = new JTextField();
        inputPanel.add(batchField);
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        addButton = new JButton("Add Medicine");
        backButton = new JButton("Back");
        
        buttonPanel.add(addButton);
        buttonPanel.add(backButton);
        
        addButton.addActionListener(e -> addMedicine());
        backButton.addActionListener(e -> {
            this.dispose();
            new PharmacistDashboard(userId, "Pharmacist", "Pharmacist");
        });
        
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        mainPanel.add(inputPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        add(mainPanel);
        setVisible(true);
    }
    
    private void addMedicine() {
        String medicineName = nameField.getText();
        String companyName = companyField.getText();
        String category = categoryField.getText();
        String quantity = quantityField.getText();
        String price = priceField.getText();
        String expiryDate = expiryDateField.getText();
        String batchNumber = batchField.getText();
        
        if (medicineName.isEmpty() || companyName.isEmpty() || quantity.isEmpty() || price.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill all required fields!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        try {
            Connection conn = DatabaseConnection.getConnection();
            String sql = "INSERT INTO medicines (medicine_name, company_name, category, quantity, price_per_unit, expiry_date, batch_number) VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, medicineName);
            pst.setString(2, companyName);
            pst.setString(3, category);
            pst.setInt(4, Integer.parseInt(quantity));
            pst.setDouble(5, Double.parseDouble(price));
            pst.setDate(6, expiryDate.isEmpty() ? null : new Date(java.sql.Date.valueOf(expiryDate)));
            pst.setString(7, batchNumber);
            
            int result = pst.executeUpdate();
            
            if (result > 0) {
                JOptionPane.showMessageDialog(this, "Medicine added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                nameField.setText("");
                companyField.setText("");
                categoryField.setText("");
                quantityField.setText("");
                priceField.setText("");
                expiryDateField.setText("");
                batchField.setText("");
            }
            
            conn.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error adding medicine: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Invalid input format: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
