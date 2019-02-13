/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JButton;
import javax.swing.JFrame;

/**
 * GUI for the Open Rail Sim project
 * @author mrp15ndu
 */
public final class OpenRailSimGUIOld extends JFrame {
    private static final Dimension DEFAULT_DIMENSION = new Dimension(1000, 600);
    private static final Color DEFAULT_COLOUR = new Color(0,0,0,1);
    
    private JButton B;
    
    public OpenRailSimGUIOld() {
        // Configure frame
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setMinimumSize(DEFAULT_DIMENSION);
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        
        //Add components to the frame
        this.addComponents();
    }
    
    private void addComponents() {
//        this.setSize(1000, 600);
        this.setLayout(null);
        
        this.B = new JButton("Click here!");
        this.B.setBounds(100, 100, 200, 100);
        
        this.add(B);
    }
}
