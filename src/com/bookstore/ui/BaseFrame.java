package com.bookstore.ui;

import javax.swing.*;
import java.awt.*;

/**
 * Demonstrates inheritance in UI - All other frames will extend this BaseFrame.
 */
public class BaseFrame extends JFrame {
    
    // Phase 2 Aesthetic Color Palette Upgrade
    public static final Color PRIMARY_COLOR = new Color(30, 61, 89); // Deep Navy Blue
    public static final Color SECONDARY_COLOR = new Color(255, 110, 64); // Vibrant Coral
    public static final Color BACKGROUND_COLOR = new Color(245, 240, 225); // Cream / Off-White
    public static final Color TEXT_COLOR = new Color(33, 37, 41); // Slate Dark
    public static final Color ACCENT_COLOR = new Color(67, 160, 71); // Clean Green
    
    // Upgraded standard fonts
    public static final Font TITLE_FONT = new Font("Verdana", Font.BOLD, 26);
    public static final Font LABEL_FONT = new Font("Tahoma", Font.PLAIN, 15);
    public static final Font BUTTON_FONT = new Font("Trebuchet MS", Font.BOLD, 15);

    public BaseFrame(String title) {
        super(title);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        getContentPane().setBackground(BACKGROUND_COLOR);
    }

    protected void setCenterScreen(int width, int height) {
        setSize(width, height);
        setLocationRelativeTo(null); // Centers the frame
    }

    // Utility to create styled buttons
    protected JButton createStyledButton(String text, Color background) {
        JButton button = new JButton(text);
        button.setFont(BUTTON_FONT);
        button.setBackground(background);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setOpaque(true);
        button.setBorderPainted(false);
        return button;
    }

    // Utility to create styled text fields
    protected JTextField createStyledTextField() {
        JTextField textField = new JTextField();
        textField.setFont(LABEL_FONT);
        textField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(189, 195, 199), 1),
            BorderFactory.createEmptyBorder(8, 10, 8, 10)
        ));
        return textField;
    }
}
