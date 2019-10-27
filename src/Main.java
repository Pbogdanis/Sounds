

import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;


import javax.imageio.ImageIO;
import javax.sound.sampled.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class Main implements NativeKeyListener {
    private static Image trayImage;
    private static SystemTray sysTray;
    private static PopupMenu menu;
    private static MenuItem item1,item2,item3;
    private static TrayIcon trayIcon;
    private static String soundfile,mode;


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
            item2 = new MenuItem("Classic");
            item3 = new MenuItem("Mechanical");

            //add item to menu
            menu.add(item2);
            menu.add(item3);
            menu.add(item1);

            //Default soundfile
            soundfile = "./resources/typewriter.wav";

            //add action listener to the item in the popup menu
            item1.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    System.exit(0);
                }
            });
            item2.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    soundfile = "resources/typewriter.wav";
                    mode = "classic";
                }
            });
            item3.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    soundfile = "resources/mech_hard.wav";
                    mode = "mechanical";
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

        }
    }

    @Override
    public void nativeKeyPressed(NativeKeyEvent e){
        //check the mode selected
        /*if( mode.equals("classic") ){
            if( NativeKeyEvent.getKeyText(e.getKeyCode()).equals("Space") ) {
                soundfile = "resources/space_enter.wav";
            }
        }*/
        playSound(soundfile);
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
