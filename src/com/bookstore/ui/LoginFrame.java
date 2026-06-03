package com.bookstore.ui;

import com.bookstore.service.AuthService;
import javax.swing.*;
import java.awt.*;

public class LoginFrame extends BaseFrame {

    private JTextField emailField;
    private JPasswordField passwordField;
    private AuthService authService;

    public LoginFrame() {
        super("Login - Online Book Store");
        authService = new AuthService();
        setCenterScreen(400, 500);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(BACKGROUND_COLOR);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));

        // Header
        JLabel header = new JLabel("Welcome Back!");
        header.setFont(TITLE_FONT);
        header.setForeground(PRIMARY_COLOR);
        header.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Form Fields
        emailField = new JTextField();
        emailField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        passwordField = new JPasswordField();
        passwordField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));

        // Buttons
        JButton loginBtn = createStyledButton("LOGIN", PRIMARY_COLOR);
        loginBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JButton registerBtn = createStyledButton("Create Account", SECONDARY_COLOR);
        registerBtn.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Add action listeners
        loginBtn.addActionListener(e -> attemptLogin());
        registerBtn.addActionListener(e -> {
            dispose();
            new RegistrationFrame().setVisible(true);
        });

        // Assemble
        mainPanel.add(header);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 30)));
        addLeftAlignedLabel(mainPanel, "Email Address");
        mainPanel.add(emailField);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        addLeftAlignedLabel(mainPanel, "Password");
        mainPanel.add(passwordField);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 30)));
        mainPanel.add(loginBtn);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        mainPanel.add(registerBtn);

        add(mainPanel);
    }
    
    private void addLeftAlignedLabel(JPanel panel, String text) {
        JLabel label = new JLabel(text);
        label.setFont(LABEL_FONT);
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(label);
        panel.add(Box.createRigidArea(new Dimension(0, 5)));
    }

    private void attemptLogin() {
        String email = emailField.getText();
        String pass = new String(passwordField.getPassword());
        
        try {
            if (authService.login(email, pass)) {
                JOptionPane.showMessageDialog(this, "Login Successful!");
                dispose();
                if ("ADMIN".equals(AuthService.currentUser.getRole())) {
                    new AdminDashboardFrame().setVisible(true);
                } else {
                    new UserDashboardFrame().setVisible(true);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Invalid credentials.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
