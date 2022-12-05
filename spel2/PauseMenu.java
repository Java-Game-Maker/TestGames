package spel2;

import com.javagamemaker.javagameengine.JavaGameEngine;
import com.javagamemaker.javagameengine.components.Component;
import com.javagamemaker.javagameengine.msc.Debug;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class PauseMenu extends JPanel {

    Btn continueBtn = new Btn("Continue");
    Btn quit = new  Btn("Quit");
    Btn reset = new Btn("Restart");
    JSlider volume = new JSlider();
    JLabel volumeValue = new JLabel("0");

    public PauseMenu(){
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

        add(continueBtn);
        add(reset);
        add(quit);

        PauseMenu pauseMenu = this;
        requestFocus();
        grabFocus();

        addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseExited(MouseEvent mouseEvent) {
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
