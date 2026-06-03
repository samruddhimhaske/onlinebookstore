package com.bookstore.util;

import javax.swing.JLabel;
import javax.swing.SwingUtilities;

/**
 * Demonstrates Multithreading concept.
 */
public class MultithreadingUtil {

    // Simulates a background loading process (e.g. for Splash Screen or heavy DB operations)
    public static void simulateProcessing(int durationMs, JLabel statusLabel, Runnable onComplete) {
        Thread worker = new Thread(() -> {
            try {
                if (statusLabel != null) {
                    SwingUtilities.invokeLater(() -> statusLabel.setText("Processing..."));
                }
                Thread.sleep(durationMs);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            
            // Execute callback on Event Dispatch Thread (Safe for Swing GUI)
            if (onComplete != null) {
                SwingUtilities.invokeLater(onComplete);
            }
        });
        worker.start();
    }
}
