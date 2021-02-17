package net.juanxxiii.controller;

import net.juanxxiii.view.VentanaPrincipal;

import javax.swing.*;

public class Controller {
    public static void main(String[] args) {
        JFrame jFrame = new JFrame("App");
        jFrame.setContentPane(new VentanaPrincipal().panelMain);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.pack();
        jFrame.setVisible(true);
        jFrame.setBounds(300,150, 500, 500);
    }
}
