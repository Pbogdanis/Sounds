

import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;


import javax.imageio.ImageIO;
import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;

public class Main implements NativeKeyListener {
    public static Image trayImage;
    private static SystemTray sysTray;
    private static PopupMenu menu;
    private static MenuItem item1,item2,item3;
    private static TrayIcon trayIcon;
    public static String soundfile,mode;


    public static void main(String[] args){

        try {
            GlobalScreen.registerNativeHook();
        } catch (NativeHookException e) {
            e.printStackTrace();
        }
        GlobalScreen.addNativeKeyListener(new Main());

        if (SystemTray.isSupported()) {
            sysTray = SystemTray.getSystemTray();
            try {
                Image image = ImageIO.read(new FileInputStream("resources/g1048.png"));
                trayImage  = image;
            } catch (IOException e) {
                e.printStackTrace();
            }
            //create popupmenu
            menu = new PopupMenu();

            //create item
            item1 = new MenuItem("Exit");
            item2 = new MenuItem("Show GUI");
            item3 = new MenuItem("Hide GUI");


            //add item to menu
            menu.add(item2);
            menu.add(item3);
            menu.add(item1);

            //Default soundfile
            soundfile = "./resources/mech_hard.wav";
            mode = "Mechanical";

            //add action listener to the item in the popup menu
            item1.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    System.exit(0);
                }
            });
            item2.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    SwingUtilities.invokeLater(new Runnable() {
                        public void run() {
                            MainForm.showGUI();
                        }
                    });
                }
            });
            item3.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    SwingUtilities.invokeLater(new Runnable() {
                        public void run() {
                            MainForm.hideGUI();
                        }
                    });
                }
            });

            //create system tray icon.
            trayIcon = new TrayIcon(trayImage, "Keyboard Sounds", menu);
            trayIcon.setImageAutoSize(true);
            //add the tray icon to the system tray.
            try {
                sysTray.add(trayIcon);
            }
            catch(AWTException e) {
                System.out.println(e.getMessage());
            }

            //Show GUI Form
            SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    MainForm.showGUI();
                }
            });
        }
    }

    @Override
    public void nativeKeyPressed(NativeKeyEvent e){
        if(e.getKeyCode() == NativeKeyEvent.VC_ENTER && mode.equals("Classic")){
            playSound("resources/bell.wav");
        }
        else {
            //check the mode selected
            playSound(soundfile);
        }
        System.out.println("Pressed : " + NativeKeyEvent.getKeyText(e.getKeyCode()));
    }
    @Override
    public void nativeKeyReleased(NativeKeyEvent e){

    }
    @Override
    public void nativeKeyTyped(NativeKeyEvent e){

    }
    void playSound(String soundFile) {
        File f = new File(soundFile);
        AudioInputStream audioIn = null;
        try {
            audioIn = AudioSystem.getAudioInputStream(f.toURI().toURL());
        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Clip clip = null;
        try {
            clip = AudioSystem.getClip();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }
        try {
            clip.open(audioIn);
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        clip.start();
    }


}
