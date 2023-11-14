package org.benevolat.views;

import javax.swing.*;
import java.awt.event.*;


public class UserInterface extends JFrame {
    public UserInterface() {
        super("Bénévolat");

        WindowListener l = new WindowAdapter() {
            public void windowClosing(WindowEvent e){

                System.exit(0);
            }
        };

        addWindowListener(l);
        setSize(800,400);

        FirstWindow fw = new FirstWindow(50, 200, this);
        fw.setBounds(0, 0, this.getWidth(), this.getHeight());
        this.add(fw);

        setLayout(null);
        setVisible(true);

    }

    public void changePanel(JPanel panel) {
        this.remove(0);
        panel.setBounds(0, 0, this.getWidth(), this.getHeight());
        this.add(panel);

        setLayout(null);
        setVisible(true);
    }




    //TODO A mettre dans un controleur
    public static void main(String [] args){
        JFrame frame = new UserInterface();
    }
}
