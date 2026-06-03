package com.bookstore.ui;

import com.bookstore.service.AuthService;
import javax.swing.*;
import java.awt.*;

public class RegistrationFrame extends BaseFrame {

    private JTextField nameField, emailField, phoneField, addressField;
    private JPasswordField passField;
    private AuthService authService;

    public RegistrationFrame() {
        super("Register - Online Book Store");
        authService = new AuthService();
        setCenterScreen(450, 600);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(BACKGROUND_COLOR);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        JLabel header = new JLabel("Create an Account");
        header.setFont(TITLE_FONT);
        header.setForeground(PRIMARY_COLOR);
        header.setAlignmentX(Component.CENTER_ALIGNMENT);

        nameField = new JTextField();
        emailField = new JTextField();
        phoneField = new JTextField();
        addressField = new JTextField();
        passField = new JPasswordField();

        setFieldSize(nameField, emailField, phoneField, addressField, passField);

        JButton registerBtn = createStyledButton("REGISTER", ACCENT_COLOR);
        registerBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JButton backBtn = createStyledButton("Back to Login", Color.GRAY);
        backBtn.setAlignmentX(Component.CENTER_ALIGNMENT);

        registerBtn.addActionListener(e -> attemptRegister());
        backBtn.addActionListener(e -> {
            dispose();
            new LoginFrame().setVisible(true);
        });

        mainPanel.add(header);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        addLabelAndField(mainPanel, "Full Name", nameField);
        addLabelAndField(mainPanel, "Email Address", emailField);
        addLabelAndField(mainPanel, "Password", passField);
        addLabelAndField(mainPanel, "Phone Number", phoneField);
        addLabelAndField(mainPanel, "Address", addressField);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        mainPanel.add(registerBtn);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        mainPanel.add(backBtn);

        add(mainPanel);
    }

    private void setFieldSize(JTextField... fields) {
        for (JTextField f : fields) {
            f.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));
        }
    }

    private void addLabelAndField(JPanel panel, String labelText, JTextField field) {
        JLabel label = new JLabel(labelText);
        label.setFont(LABEL_FONT);
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(label);
        panel.add(Box.createRigidArea(new Dimension(0, 5)));
        panel.add(field);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
    }

    private void attemptRegister() {
        try {
            boolean success = authService.register(
                nameField.getText(), emailField.getText(), 
                new String(passField.getPassword()), 
                phoneField.getText(), addressField.getText(), "USER"
            );
            if (success) {
                JOptionPane.showMessageDialog(this, "Registration Successful! Please login.");
                dispose();
                new LoginFrame().setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, "Registration Failed.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Input Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
