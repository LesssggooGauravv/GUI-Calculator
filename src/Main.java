import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

class Main extends JFrame implements ActionListener {
    static JFrame f;
    static JTextField l;

    String s0, s1, s2;

    Main() {
        s0 = s1 = s2 = "";
    }

    public static void main(String args[]) {
        f = new JFrame("Calculator");

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

        Main c = new Main();

        l = new JTextField(16);
        l.setEditable(false);
        l.setHorizontalAlignment(SwingConstants.RIGHT);

        // Buttons
        String[] btnLabels = {
                "7", "8", "9", "/",
                "4", "5", "6", "*",
                "1", "2", "3", "-",
                ".", "0", "=", "+",
                "C"
        };

        JPanel p = new JPanel(new BorderLayout());
        JPanel btnPanel = new JPanel(new GridLayout(5, 4, 5, 5)); // Grid layout with spacing

        for (String label : btnLabels) {
            JButton b = new JButton(label);
            b.addActionListener(c);
            btnPanel.add(b);
        }

        p.add(l, BorderLayout.NORTH);
        p.add(btnPanel, BorderLayout.CENTER);
        p.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Add padding

        f.add(p);
        f.setSize(300, 300);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setLocationRelativeTo(null); // Center the frame
        f.setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        String s = e.getActionCommand();

        if ((s.charAt(0) >= '0' && s.charAt(0) <= '9') || s.equals(".")) {
            if (!s1.equals(""))
                s2 += s;
            else
                s0 += s;

            l.setText(s0 + s1 + s2);
        } else if (s.equals("C")) {
            // Clear all
            s0 = s1 = s2 = "";
            l.setText("");
        } else if (s.equals("=")) {
            if (s0.isEmpty() || s1.isEmpty() || s2.isEmpty()) return;

            double te;
            try {
                switch (s1) {
                    case "+": te = Double.parseDouble(s0) + Double.parseDouble(s2); break;
                    case "-": te = Double.parseDouble(s0) - Double.parseDouble(s2); break;
                    case "*": te = Double.parseDouble(s0) * Double.parseDouble(s2); break;
                    case "/":
                        if (Double.parseDouble(s2) == 0) {
                            l.setText("Error: Divide by 0");
                            s0 = s1 = s2 = "";
                            return;
                        }
                        te = Double.parseDouble(s0) / Double.parseDouble(s2);
                        break;
                    default: return;
                }

                l.setText(s0 + s1 + s2 + "=" + te);
                s0 = Double.toString(te);
                s1 = s2 = "";
            } catch (NumberFormatException ex) {
                l.setText("Invalid Input");
                s0 = s1 = s2 = "";
            }
        } else {
            if (s0.isEmpty()) return;

            if (!s1.isEmpty() && !s2.isEmpty()) {
                actionPerformed(new ActionEvent(new JButton("="), 0, "=")); // Perform previous op first
            }
            s1 = s;
            l.setText(s0 + s1);
        }
    }
}
