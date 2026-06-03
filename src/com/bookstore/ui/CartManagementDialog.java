package com.bookstore.ui;

import com.bookstore.model.CartItem;
import com.bookstore.service.BillingService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class CartManagementDialog extends JDialog {

    private List<CartItem> cart;
    private BillingService billingService;
    private DefaultTableModel model;
    private JLabel totalLabel;
    private Runnable onCheckout;
    private Runnable onCartUpdated;

    public CartManagementDialog(JFrame parent, List<CartItem> cart, BillingService billingService, Runnable onCheckout, Runnable onCartUpdated) {
        super(parent, "Manage Cart", true);
        this.cart = cart;
        this.billingService = billingService;
        this.onCheckout = onCheckout;
        this.onCartUpdated = onCartUpdated;
        
        setSize(700, 450);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());

        JLabel header = new JLabel("<html><h2 style='color:#FF6E40;'>Your Shopping Cart</h2></html>");
        header.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        add(header, BorderLayout.NORTH);

        String[] cols = {"Book Title", "Unit Price", "Quantity", "Total"};
        model = new DefaultTableModel(cols, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        JTable table = new JTable(model);
        table.setRowHeight(30);
        table.getTableHeader().setFont(BaseFrame.BUTTON_FONT);
        add(new JScrollPane(table), BorderLayout.CENTER);

        refreshTable();

        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
        rightPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        totalLabel = new JLabel();
        totalLabel.setFont(BaseFrame.TITLE_FONT);
        updateTotals();

        JButton incBtn = new JButton("Increase Qty");
        JButton decBtn = new JButton("Decrease Qty");
        JButton removeBtn = new JButton("Remove Item");
        JButton clearBtn = new JButton("Clear Cart");
        JButton checkoutBtn = new JButton("Buy Now - Proceed to Checkout");

        checkoutBtn.setBackground(Color.decode("#1E3D59"));
        checkoutBtn.setForeground(Color.WHITE);
        checkoutBtn.setFont(BaseFrame.BUTTON_FONT);

        rightPanel.add(totalLabel);
        rightPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        rightPanel.add(incBtn);
        rightPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        rightPanel.add(decBtn);
        rightPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        rightPanel.add(removeBtn);
        rightPanel.add(Box.createRigidArea(new Dimension(0, 25)));
        rightPanel.add(clearBtn);
        rightPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        rightPanel.add(checkoutBtn);

        add(rightPanel, BorderLayout.EAST);

        // Actions
        incBtn.addActionListener(e -> updateQty(table, 1));
        decBtn.addActionListener(e -> updateQty(table, -1));
        removeBtn.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row != -1) {
                cart.remove(row);
                refreshTable();
                updateTotals();
                onCartUpdated.run();
            }
        });
        clearBtn.addActionListener(e -> {
            cart.clear();
            refreshTable();
            updateTotals();
            onCartUpdated.run();
        });
        
        checkoutBtn.addActionListener(e -> {
            dispose();
            onCheckout.run();
        });
    }

    private void updateQty(JTable table, int delta) {
        int row = table.getSelectedRow();
        if (row != -1) {
            CartItem item = cart.get(row);
            int newQty = item.getQuantity() + delta;
            if (newQty > 0 && newQty <= item.getBook().getStock()) {
                item.setQuantity(newQty);
                refreshTable();
                updateTotals();
                table.setRowSelectionInterval(row, row);
            } else if (newQty > item.getBook().getStock()) {
                JOptionPane.showMessageDialog(this, "Cannot exceed available stock!");
            }
        }
    }

    private void refreshTable() {
        model.setRowCount(0);
        for (CartItem it : cart) {
            model.addRow(new Object[]{
                it.getBook().getTitle(),
                "₹" + it.getBook().getPrice(),
                it.getQuantity(),
                "₹" + (it.getBook().getPrice() * it.getQuantity())
            });
        }
    }

    private void updateTotals() {
        double subTotal = billingService.calculateSubTotal(cart);
        totalLabel.setText("<html>Total: <br/><span style='color:green;'>₹" + String.format("%.2f", billingService.calculateFinalTotal(subTotal)) + "</span></html>");
    }
}
