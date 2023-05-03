package org.tasks.First;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ScreenInputForm extends JFrame implements ActionListener {
    public JTextField textField;
    public JButton button;
    public Label console;

    public ScreenInputForm() {
        this(new JTextField(10), new JButton("Process"));
    }

    public ScreenInputForm(JTextField textField, JButton button) {
        super("Input Form");

        this.textField = textField;
        this.button = button;

        button.addActionListener(this);

        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());
        panel.add(textField);
        panel.add(button);


        setContentPane(panel);
        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == button) {
            String input = textField.getText();
            console = new Label();
            console.setText("Input: " + input);
            try {
                int num = Integer.parseInt(input);
                System.out.println("Input: " + num);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid input: " + input, "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public static void main(String[] args) {
        ScreenInputForm form = new ScreenInputForm();
    }
}
