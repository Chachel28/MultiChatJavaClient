package net.juanxxiii.view;

import javax.swing.*;

public class VentanaPrincipal {

    private JTextField textField1;
    private JButton enviarButton;
    private JButton conectarseButton;
    private JScrollPane jPanelMessage;
    private JScrollPane jPanelUsers;
    public JPanel panelMain;
    private boolean online = false;

    public VentanaPrincipal(){
        conectarseButton.addActionListener(e -> {
            String username = JOptionPane.showInputDialog(panelMain, "¿Cuál es tu nombre de usuario?", null);
            comprobarNombre(username);
            if(!online){
                textField1.setEnabled(false);
                enviarButton.setEnabled(false);
            }else{
                textField1.setEnabled(true);
                enviarButton.setEnabled(true);
            }
        });
    }

    public void comprobarNombre(String username){
        if (username.equals("")){
            JOptionPane.showMessageDialog(panelMain, "El campo nombre no puede estar vacío.");
            online = false;
        }else if(username.contains(" ")){
            JOptionPane.showMessageDialog(panelMain, "El campo nombre tener espacios en blanco.");
            online = false;
        }else{
            online = true;
        }
    }
}
