package com.bookstore.main;

import com.bookstore.ui.SplashScreen;
import javax.swing.SwingUtilities;

public class MainApplication {

    public static void main(String[] args) {
        // Run UI safely on Event Dispatch Thread
        SwingUtilities.invokeLater(() -> {
            SplashScreen splash = new SplashScreen();
            splash.startLoading();
        });
    }
}
