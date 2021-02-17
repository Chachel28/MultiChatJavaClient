package net.juanxxiii.view;

import javax.swing.*;
import java.io.IOException;
import java.net.Socket;

public class VentanaPrincipal {

    private JTextField textField1;
    private JButton enviarButton;
    private JButton conectarseButton;
    private JScrollPane jPanelMessage;
    private JScrollPane jPanelUsers;
    public JPanel panelMain;
    private boolean online = false;

    public VentanaPrincipal(){
            textField1.setEnabled(false);
            enviarButton.setEnabled(false);
            conectarseButton.addActionListener(e -> {
                if(!online){
                    String username = JOptionPane.showInputDialog(panelMain, "¿Cuál es tu nombre de usuario?", null);
                    comprobarNombre(username);
                }else{
                    JOptionPane.showMessageDialog(panelMain, "¿Estás seguro de que deseas desconectarte?");
                    online=false;
                    textField1.setEnabled(false);
                    enviarButton.setEnabled(false);
                    conectarseButton.setText("Conectarse");
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
            textField1.setEnabled(true);
            enviarButton.setEnabled(true);
            conectarseButton.setText("Desconectarse");
            try {
                Socket client = new Socket("localhost", 9090);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
