import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class MainForm {
    private JComboBox filesBox;
    private JButton applyBtn;
    public JPanel panelMain;
    public JLabel usingLabel;
    public static JFrame frame;

    public MainForm() {
        applyBtn.addActionListener(actionEvent -> {
            String comboSelection = filesBox.getSelectedItem().toString();
            switch (comboSelection){
                case "Mechanical":
                    Main.soundfile = "resources/mech_hard.wav";
                    Main.mode = "Mechanical";
                    usingLabel.setText(Main.mode);
                    JOptionPane.showMessageDialog(null,"Mechanical sound ON");
                    break;
                case "Classic Typewriter":
                    Main.soundfile = "resources/typewriter.wav";
                    Main.mode = "Classic";
                    usingLabel.setText(Main.mode.toString());
                    JOptionPane.showMessageDialog(null,"Classic sound ON");
                    break;
                case "Futuristic":
                    Main.soundfile = "resources/futuristic.wav";
                    Main.mode = "Futuristic";
                    usingLabel.setText(Main.mode.toString());
                    JOptionPane.showMessageDialog(null,"Futuristic sound ON");
                    break;
            }
        });
    }
    public static void showGUI(){

        frame = new JFrame("Sounds");
        frame.setContentPane(new MainForm().panelMain);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(250, 150));
        frame.setResizable(false);
        ImageIcon icon = new ImageIcon(Main.trayImage);
        frame.setIconImage(icon.getImage());
        frame.pack();
        if (!frame.isVisible()){
            frame.setVisible(true);
        }
    }
    public static void hideGUI(){
        frame.setVisible(false);
    }
}
