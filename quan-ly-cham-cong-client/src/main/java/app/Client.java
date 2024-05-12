package app;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import com.formdev.flatlaf.themes.FlatMacLightLaf;

import controller.Controller;
import ui.frmLogin;

public class Client {
    public static void main(String[] args) throws MalformedURLException, RemoteException, NotBoundException, ClassNotFoundException {
    	if (Controller.connectServer()) {
    		SwingUtilities.invokeLater(new Runnable() {
    			public void run() {
    				try {
    					UIManager.setLookAndFeel(new FlatMacLightLaf());
    				} catch (Exception e) {
    					e.printStackTrace();
    				}
    				new frmLogin().setVisible(true);
    			}
    		});
    	}
    }
}
