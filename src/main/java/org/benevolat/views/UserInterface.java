package org.benevolat.views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class UserInterface extends JFrame implements ActionListener{
    private CardLayout windowChoice;
    private Container cPane;

    public UserInterface() {
        super("Bénévolat");

        setSize(800,400);
        WindowListener l = new WindowAdapter() {
            public void windowClosing(WindowEvent e){

                System.exit(0);
            }
        };

        addWindowListener(l);

        this.cPane = this.getContentPane();

        this.windowChoice = new CardLayout();
        cPane.setLayout(windowChoice);

        FirstWindow fw = new FirstWindow(50, 200, this);
        fw.setBounds(0, 0, this.getWidth(), this.getHeight());

        LogInScreen log = new LogInScreen(50, 200, this);
        log.setBounds(0, 0, this.getWidth(), this.getHeight());

        SignInScreen sg = new SignInScreen(50, 200, this);
        sg.setBounds(0, 0, this.getWidth(), this.getHeight());

        cPane.add(fw);
        cPane.add(log);
        cPane.add(sg);

        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "login" -> {this.windowChoice.next(this.cPane);}
            case "signin" -> {this.windowChoice.last(this.cPane);}
            case "firstwindow" -> {this.windowChoice.first(this.cPane);}
        }
    }

    //TODO A mettre dans un controleur
    public static void main(String [] args){
        JFrame frame = new UserInterface();
    }
}
