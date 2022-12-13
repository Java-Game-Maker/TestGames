package spel2;

import com.javagamemaker.javagameengine.JavaGameEngine;
import com.javagamemaker.javagameengine.Scene;
import com.javagamemaker.javagameengine.components.CameraMovement;
import com.javagamemaker.javagameengine.components.PhysicsBody;
import com.javagamemaker.javagameengine.components.Sprite;
import com.javagamemaker.javagameengine.components.lights.LightManager;
import com.javagamemaker.javagameengine.input.Input;
import com.javagamemaker.javagameengine.msc.Debug;
import com.javagamemaker.javagameengine.msc.Random;
import com.javagamemaker.javagameengine.msc.Vector2;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Level1 extends Scene {
    public static JLabel coinsLabel = new JLabel("Coins: 0");
    public static Sprite enemyPrefab = new Sprite();
    public static Sprite coinPrefab = new Sprite();
    public static Sprite bulletPrefab = new Sprite();
    public Level1(){

        enemyPrefab.loadAnimation(new String[]{
                "/spel2/sprites/enemy/pixil-frame-0.png",
                "/spel2/sprites/enemy/pixil-frame-1.png",
                "/spel2/sprites/enemy/pixil-frame-2.png",
                "/spel2/sprites/enemy/pixil-frame-3.png",
                "/spel2/sprites/enemy/pixil-frame-4.png",
        });

        Rectangle[] die = new Rectangle[12];
        int i = 0;
        for(int y = 0;y<2;y++) {
            for(int x = 0;x<6;x++){
                Rectangle tile = new Rectangle(x * 1600 / 6, y*532 / 2, 1600 / 6, 532 / 2);
                die[i] = tile;
                i++;
            }
        }
        enemyPrefab.loadAnimation(die, "/spel2/sprites/watersplash.png");

        coinPrefab.loadAnimation(new String[]{"/spel2/sprites/milk.png"});
        bulletPrefab.loadAnimation(new String[]{"/spel2/sprites/cookie.png"});



        Main.player = new Player();
        Main.player.setPosition(new Vector2(0,-200));
        useLight = true;

        // ui
        LightManager.opacity = 0.99f;
        setBackground(new Color(50,50,50));
        JavaGameEngine.masterVolume = 1f;

        //playSound("/spel2/sound/ambience.wav");
        playSound("/spel2/sound/theme.wav");

        coinsLabel.setFont(new Font("Verdana",Font.BOLD,32));
        coinsLabel.setForeground(Color.WHITE);
        coinsLabel.setLocation(100,210);
        coinsLabel.setSize(1000,100);
        add(coinsLabel);
        Main.heightLabel.setFont(new Font("Verdana",Font.BOLD,32));
        Main.heightLabel.setForeground(Color.WHITE);
        Main.heightLabel.setLocation(100,100);
        Main.heightLabel.setSize(1000,100);
        add(Main.heightLabel);
    }
    @Override
    public void start() {
       getCamera().setPosition(new Vector2(getCamera().getPosition().getX()*2,JavaGameEngine.getWindowSize().getY()));
       Ground startGround = new Ground(JavaGameEngine.getWindowSize().getX(),new Vector2(0,0)){
           // this ground should not respawn
           @Override
           public void respawn() {}
       };

       add(startGround);

       add(new Ground(200,new Vector2(-100,-200)));
       add(new Ground(200,new Vector2(0,-400)));
       add(new Ground(200,new Vector2(100,-600)));

       add(new Ground(200,new Vector2(-100,-800)){
           // when this ground respawn we add a coin chunk as well
           @Override
           public void respawn() {
               super.respawn();
               switch (new Random().nextInt(3)){
                   case 0:
                       instantiate(new CoinChunk(CoinChunk.box,getPosition().removeX().add(new Vector2(-300,-1500))));
                       break;
                   case 1:
                       instantiate(new CoinChunk(CoinChunk.spiral,getPosition().removeX().add(new Vector2(-300,-1500))));
                       break;
                   case 2:
                       instantiate(new CoinChunk(CoinChunk.pipe,getPosition().removeX().add(new Vector2(-300,-1500))));
                       break;
               }
           }
       });

       add(new Ground(200,new Vector2(0,-1000)));
       add(new Ground(200,new Vector2(100,-1200)));
       add(new Ground(200,new Vector2(200,-1400)));
       add(new Ground(200,new Vector2(-200,-1600)));
       add(new Ground(200,new Vector2(0,-1800)));
       add(new Ground(200,new Vector2(-300,-2000)));

       add(new CoinChunk(CoinChunk.pipe,new Vector2(-300,-1300)));
        add(new Enemy(new Vector2(300,10)));
       add(Main.player);
       super.start();
    }
    // camera speed
    float speed = 0.3f;
    @Override
    public void update() {
        super.update();
        //make the camera move upwards
        getCamera().translate(Vector2.down.multiply(speed));
        speed+=0.00001f;

        //make the camera follow player when player leaves the camera view
        float h = JavaGameEngine.getWindowSize().getY()/3; // 1/3 of screen height
        if(Main.player.getPosition().getY() < -getCamera().getPosition().getY()+h){
            getCamera().getPosition().setY(-Main.player.getPosition().getY()+h);
        }
        // if player is bellow camera view
        if(Main.player.getPosition().getY() > -getCamera().getPosition().getY()+50+JavaGameEngine.getWindowSize().getY() ){
            Main.player.paused = null;
            Main.setSelectedScene(new Splashscreen());
        }

    }
}
