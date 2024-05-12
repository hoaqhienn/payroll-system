package ui;

import javax.swing.*;

import java.awt.Image;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @author HIEN
 * MÀN HÌNH SPLASH
 */
public class frmSplashScreen {

    private JFrame splashFrame;

    public frmSplashScreen() {
        splashFrame = new JFrame();
        ImageIcon splashImage = new ImageIcon(".\\src\\main\\resources\\img\\img.png");
        Image scaledImage = splashImage.getImage().getScaledInstance(300, 400, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);
        JLabel splashLabel = new JLabel(scaledIcon);
        splashFrame.add(splashLabel);
        splashFrame.setUndecorated(true);
        splashFrame.setSize(300,400);
        splashFrame.setLocationRelativeTo(null);
        splashFrame.setVisible(true);

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                splashFrame.dispose();
                new frmMainFrame().setVisible(true);
            }
        }, 300);
    }
}
