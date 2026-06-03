package com.bookstore.ui;

import com.bookstore.util.MultithreadingUtil;
import javax.swing.*;
import java.awt.*;

public class SplashScreen extends BaseFrame {

    private JProgressBar progressBar;
    private JLabel statusLabel;

    public SplashScreen() {
        super("Loading System...");
        setCenterScreen(500, 300);
        setUndecorated(true); // Remove window borders for splash

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(PRIMARY_COLOR);
        panel.setBorder(BorderFactory.createLineBorder(Color.WHITE, 3));

        // Title
        JLabel titleLabel = new JLabel("Online Book Store", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 36));
        titleLabel.setForeground(Color.WHITE);
        panel.add(titleLabel, BorderLayout.CENTER);

        // Progress Panel
        JPanel progressPanel = new JPanel(new BorderLayout());
        progressPanel.setOpaque(false);

        statusLabel = new JLabel("Initializing modules...", SwingConstants.CENTER);
        statusLabel.setForeground(Color.WHITE);
        statusLabel.setFont(LABEL_FONT);
        progressPanel.add(statusLabel, BorderLayout.NORTH);

        progressBar = new JProgressBar(0, 100);
        progressBar.setForeground(ACCENT_COLOR);
        progressBar.setBackground(Color.WHITE);
        progressBar.setPreferredSize(new Dimension(500, 15));
        progressBar.setBorderPainted(false);
        progressPanel.add(progressBar, BorderLayout.SOUTH);

        panel.add(progressPanel, BorderLayout.SOUTH);
        add(panel);
    }

    public void startLoading() {
        setVisible(true);

        // Multithreading demonstration
        new Thread(() -> {
            try {
                for (int i = 0; i <= 100; i += 2) {
                    Thread.sleep(30); // Simulate heavy loading
                    final int progress = i;
                    SwingUtilities.invokeLater(() -> {
                        progressBar.setValue(progress);
                        if (progress == 30) statusLabel.setText("Connecting to Database...");
                        if (progress == 60) statusLabel.setText("Loading UI Components...");
                        if (progress == 90) statusLabel.setText("Starting Application...");
                    });
                }
                
                SwingUtilities.invokeLater(() -> {
                    dispose();
                    new LoginFrame().setVisible(true);
                });

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }
}
