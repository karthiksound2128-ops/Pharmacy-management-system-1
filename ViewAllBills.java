package pharmacy.system;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class ViewAllBills extends JFrame {
    private int userId;
    private JTable billsTable;
    
    public ViewAllBills(int userId) {
        this.userId = userId;
        setTitle("View All Bills - Pharmacy Management System");
        setSize(1000, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        JButton backButton = new JButton("Back to Dashboard");
        backButton.addActionListener(e -> {
            this.dispose();
            new AdminDashboard(userId, "Admin", "Admin");
        });
        
        Object[] columnNames = {"Bill ID", "Bill Number", "Customer ID", "User ID", "Total Amount", "Discount", "Final Amount", "Payment Method", "Bill Date"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        billsTable = new JTable(model);
        
        JScrollPane tableScrollPane = new JScrollPane(billsTable);
        
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        mainPanel.add(backButton, BorderLayout.NORTH);
        mainPanel.add(tableScrollPane, BorderLayout.CENTER);
        
        add(mainPanel);
        loadBills();
        setVisible(true);
    }
    
    private void loadBills() {
        try {
            Connection conn = DatabaseConnection.getConnection();
            String sql = "SELECT * FROM bills";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            
            DefaultTableModel model = (DefaultTableModel) billsTable.getModel();
            model.setRowCount(0);
            
            while (rs.next()) {
                Object[] row = {
                    rs.getInt("bill_id"),
                    rs.getString("bill_number"),
                    rs.getInt("customer_id"),
                    rs.getInt("user_id"),
                    rs.getDouble("total_amount"),
                    rs.getDouble("discount"),
                    rs.getDouble("final_amount"),
                    rs.getString("payment_method"),
                    rs.getTimestamp("bill_date")
                };
                model.addRow(row);
            }
            
            conn.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
