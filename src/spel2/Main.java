package spel2;

import com.formdev.flatlaf.FlatDarkLaf;
import com.javagamemaker.javagameengine.JavaGameEngine;
import com.javagamemaker.javagameengine.components.lights.LightManager;
import com.javagamemaker.javagameengine.msc.Debug;
import com.javagamemaker.javagameengine.msc.Vector2;

import javax.sound.sampled.*;
import javax.swing.*;
import java.io.IOException;
import java.io.InputStream;

/**TODO
 * count height
 * save milk and height
 * generate more enemies by height
 * make floor bigger based on the height
 */
public class Main extends JavaGameEngine {
    public static Player player;
    public static void main(String[] args){
        Debug.showWhere=false;
        player = new Player();
        size = new Vector2(600,1000);
        player.setPosition(new Vector2(0,-200));
        g.setY(g.getY()*2);
        setSelectedScene(new Level1());
        //setSelectedScene(new Splashscreen());

        try {
            UIManager.setLookAndFeel(new FlatDarkLaf());
        } catch (Exception e) {
            e.printStackTrace();
        }

        start();
    }


}
