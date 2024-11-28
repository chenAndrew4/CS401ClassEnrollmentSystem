package client.gui;

import client.gui.dashboard.AdminDashboardGUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class AdminManageUsersGUI {
    private JPanel manageUsersPanel;

    public AdminManageUsersGUI(AdminDashboardGUI adminDashboardGUI) {
        manageUsersPanel = new JPanel();
        JLabel titleLabel = new JLabel("Admin Manage Users Panel");
        manageUsersPanel.add(titleLabel);
        
        
        JPanel goBackButtonPanel = new JPanel();

        JButton goBackButton = new JButton("Go Back");

        titleLabel.setBounds(50, 50, 150, 20);
        goBackButton.setBounds(50, 100, 95, 30);
        
        goBackButton.addActionListener(new ActionListener(){
        	public void actionPerformed(ActionEvent e) {
        		adminDashboardGUI.goBackToMainOptions();
        	}
        });
        
        goBackButtonPanel.add(goBackButton);
        
        manageUsersPanel.add(goBackButtonPanel);
        
        
        
    }
   
    public JPanel getPanel() {
        return manageUsersPanel;
    }
}
