package com.bookstore.ui;

import com.bookstore.dao.UserDAO;
import com.bookstore.model.User;
import com.bookstore.service.AuthService;

import javax.swing.*;
import java.awt.*;

public class UserProfileDialog extends JDialog {

    private JTextField nameField, phoneField, addressField;
    private JLabel emailLabel;

    public UserProfileDialog(JFrame parent) {
        super(parent, "My Profile", true);
        setSize(400, 350);
        setLocationRelativeTo(parent);
        
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        mainPanel.setBackground(BaseFrame.BACKGROUND_COLOR);

        JLabel header = new JLabel("<html><h2 style='color: " + getHex(BaseFrame.PRIMARY_COLOR) + "'>Edit Profile</h2></html>");
        header.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        User u = AuthService.currentUser;
        
        emailLabel = new JLabel("<html><b>Email:</b> " + u.getEmail() + " <i>(Cannot be changed)</i></html>");
        emailLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        nameField = new JTextField(u.getName());
        phoneField = new JTextField(u.getPhone());
        addressField = new JTextField(u.getAddress());

        JButton saveBtn = new JButton("Save Changes");
        saveBtn.setBackground(BaseFrame.ACCENT_COLOR);
        saveBtn.setForeground(Color.WHITE);
        saveBtn.setFont(BaseFrame.BUTTON_FONT);
        saveBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        saveBtn.setAlignmentX(Component.CENTER_ALIGNMENT);

        saveBtn.addActionListener(e -> {
            u.setName(nameField.getText());
            u.setPhone(phoneField.getText());
            u.setAddress(addressField.getText());
            
            boolean updated = new UserDAO().updateUserProfile(u);
            if (updated) {
                JOptionPane.showMessageDialog(this, "Profile Updated Successfully!");
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Failed to update profile.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        mainPanel.add(header);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        mainPanel.add(emailLabel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        
        addLabelAndField(mainPanel, "Full Name", nameField);
        addLabelAndField(mainPanel, "Phone Number", phoneField);
        addLabelAndField(mainPanel, "Shipping Address", addressField);
        
        mainPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        mainPanel.add(saveBtn);
        
        add(mainPanel);
    }

    private void addLabelAndField(JPanel panel, String labelText, JTextField field) {
        JLabel label = new JLabel(labelText);
        label.setFont(BaseFrame.LABEL_FONT);
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        field.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        panel.add(label);
        panel.add(Box.createRigidArea(new Dimension(0, 5)));
        panel.add(field);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
    }
    
    private String getHex(Color color) {
        return "#" + Integer.toHexString(color.getRGB()).substring(2);
    }
}
