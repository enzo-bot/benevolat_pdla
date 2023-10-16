package org.benevolat.views;
import javax.swing.*;

public class InputField extends JPanel{
//    private String title;
//    private JLabel nom_label;
    private final JTextField NAME_FIELD;

    public InputField(String title, int x, int y, int width, int height, boolean hidden) {
        super();
//        this.title = title;

        this.setBounds(x, y, width, height);

        JLabel nom_label = new JLabel(title);
        nom_label.setBounds(0,0,width/2,height);

        // Hypothèse : 15px par caractère
        if (!hidden) {
            NAME_FIELD = new JTextField(width/30);
        } else {
            NAME_FIELD = new JPasswordField(width/30);
        }
        NAME_FIELD.setBounds(width/2,0,width/2,height);

        this.add(nom_label);
        this.add(NAME_FIELD);
    }

    public String getText() {
        return this.NAME_FIELD.getText();
    }
}
