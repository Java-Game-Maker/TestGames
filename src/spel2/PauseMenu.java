package spel2;

import com.javagamemaker.javagameengine.JavaGameEngine;
import com.javagamemaker.javagameengine.components.Component;
import com.javagamemaker.javagameengine.msc.Debug;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.*;

public class PauseMenu extends JPanel {

    Btn continueBtn = new Btn("Continue");
    Btn quit = new  Btn("Quit");
    Btn reset = new Btn("Restart");
    JSlider volume = new JSlider();
    JLabel volumeValue = new JLabel("0");
    JCheckBox useLight = new JCheckBox("Use light");

    public PauseMenu(){
        JPanel THIS = this;
        setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
        setSize(500,500);
        setLocation(0,0);

        volume.setValue((int) (JavaGameEngine.masterVolume*100));
        volumeValue.setText(String.valueOf((JavaGameEngine.masterVolume*100)));
        volume.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent changeEvent) {
                volumeValue.setText(String.valueOf(volume.getValue()));
                Main.masterVolume = volume.getValue()/100f;
            }
        });

        JPanel volumePanel = new JPanel();
        volumePanel.add(volume);
        volumePanel.add(volumeValue);

        add(volumePanel);
        useLight.setSelected(Main.getSelectedScene().useLight);
        useLight.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                Main.getSelectedScene().useLight = useLight.isSelected();
            }
        });
        add(useLight);
        continueBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                Main.gameWorld.remove(THIS);
            }
        });

        add(continueBtn);
        reset.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                Main.player.paused = null;
                Main.gameWorld.remove(THIS);
                Main.setSelectedScene(new Level1());
            }
        });
        add(reset);
        quit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                Main.player.paused = null;
                Main.gameWorld.remove(THIS);
                Main.setSelectedScene(new Splashscreen());
            }
        });
        add(quit);

        PauseMenu pauseMenu = this;
        requestFocus();
        grabFocus();

        addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseExited(MouseEvent mouseEvent) {
                Main.player.paused = null;
                Main.gameWorld.remove(pauseMenu);
                super.mouseExited(mouseEvent);
            }
        });
    }
    static class Btn extends JButton{
        public Btn(String name){
            super(name);
            setFont(new Font("Verdana",Font.BOLD,32));
        }
    }

}
