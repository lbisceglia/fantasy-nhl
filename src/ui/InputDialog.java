package ui;

import javax.swing.*;

public class InputDialog {

    public String getUserInput(String msg) {
        String m = JOptionPane.showInputDialog(msg);
        return m;
    }
}
