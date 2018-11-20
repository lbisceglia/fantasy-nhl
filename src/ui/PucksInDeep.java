package ui;

import javax.swing.*;
import java.awt.*;

public class PucksInDeep extends JFrame {
    JPanel p = new JPanel();
    JTextField t = new JTextField("Hey bud");
    JTextArea ta = new JTextArea("Ey" + "\n" + "Wanna go crush a dart");
    JButton add = new JButton("Add");
    JButton delete = new JButton("Delete");
    JButton view = new JButton("View");
    JButton quit = new JButton("Quit");
    JButton save = new JButton("Save");

    public static void main(String[] args) {
        new PucksInDeep();
    }

    public PucksInDeep() {
        super("Pucks In Deep");

        // Modelled after: https://alvinalexander.com/java/jframe-size-example-screen
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        int height = screenSize.height * 2 / 3;
        int width = screenSize.width * 2 / 3;

        this.setPreferredSize(new Dimension(width, height));
        setResizable(true);

        setVisible(true);

        setDefaultCloseOperation(EXIT_ON_CLOSE);

        p.add(add);
        p.add(delete);
        p.add(view);
        p.add(quit);
        p.add(save);

        p.add(t);
        p.add(ta);

        add(p);
    }
}
