package com.bookstore.ui;

import com.bookstore.dao.OrderDAO;
import com.bookstore.model.Order;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class OrderHistoryDialog extends JDialog {

    public OrderHistoryDialog(JFrame parent, int userId) {
        super(parent, "My Order History", true);
        setSize(700, 400);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());

        JLabel header = new JLabel("<html><h2 style='color:#1e3d59;'>Your Past Purchases</h2></html>");
        header.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(header, BorderLayout.NORTH);

        String[] columns = {"Order ID", "Date", "Tax Paid", "Total Amount", "Status"};
        DefaultTableModel model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        
        List<Order> orders = new OrderDAO().getOrdersByUser(userId);
        if (orders.isEmpty()) {
            model.addRow(new Object[]{"No orders found", "-", "-", "-", "-"});
        } else {
            for (Order o : orders) {
                String deliveryInfo = o.getDeliveryDays() > 0 ? " (Arriving in " + o.getDeliveryDays() + " days)" : "";
                model.addRow(new Object[]{o.getOrderId(), o.getOrderDate(), "₹" + o.getTaxAmount(), "₹" + o.getTotalAmount(), o.getStatus() + deliveryInfo});
            }
        }
        
        JTable table = new JTable(model);
        // HTML Styling inside JTable header just for fun standard
        table.getTableHeader().setFont(BaseFrame.BUTTON_FONT);
        table.setRowHeight(25);
        add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel btnPanel = new JPanel();
        JButton closeBtn = new JButton("Close");
        closeBtn.addActionListener(e -> dispose());
        btnPanel.add(closeBtn);
        add(btnPanel, BorderLayout.SOUTH);
    }
}
