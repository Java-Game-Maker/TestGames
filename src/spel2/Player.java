package spel2;

import com.javagamemaker.javagameengine.CollisionEvent;
import com.javagamemaker.javagameengine.JavaGameEngine;
import com.javagamemaker.javagameengine.Scene;
import com.javagamemaker.javagameengine.components.Collider;
import com.javagamemaker.javagameengine.components.GameObject;
import com.javagamemaker.javagameengine.components.PhysicsBody;
import com.javagamemaker.javagameengine.components.Sprite;
import com.javagamemaker.javagameengine.components.lights.Light;
import com.javagamemaker.javagameengine.components.lights.LightManager;
import com.javagamemaker.javagameengine.components.shapes.Circle;
import com.javagamemaker.javagameengine.components.shapes.Rect;
import com.javagamemaker.javagameengine.input.Input;
import com.javagamemaker.javagameengine.input.Keys;
import com.javagamemaker.javagameengine.msc.Debug;
import com.javagamemaker.javagameengine.msc.Vector2;

import javax.swing.*;
import java.awt.*;

public class Player extends Sprite {
    PhysicsBody physicsBody = new PhysicsBody(true);
    float force = 2;

    GameObject object = new GameObject();
    public Player(){
        layer = 200; //above every one
    }
    float coins = 10;
    PauseMenu paused;
    @Override
    public void start() {
        super.start();

        object.setScale(new Vector2(50,10));
        object.setParentOffset(new Vector2(50,0));

        add(object);
        loadAnimation(new String[]{"/spel2/sprites/cookie.png"});
        loadAnimation(new String[]{"/spel2/sprites/cookie2.png"});
        add(physicsBody);
        Collider c = new Collider(false);
        c.setTag("player");
        c.setLocalVertices(new Circle(50,50));
        Light l = new Light();
        l.setRadius(600);
        //LightManager.opacity = 0.5f;
        add(l);
        add(c);
    }

    @Override
    public void updateSecond() {
        super.updateSecond();
    }

    @Override
    public void update() {
        super.update();
        Level1.coinsLabel.setText("Coins: "+coins);
        Main.heightLabel.setText("Score: "+-(int)getPosition().getY()/100);

        if(Input.isKeyDown(Keys.A)){
            if(physicsBody.velocity.getX() > -5)
                physicsBody.addForce(Vector2.left.multiply(force));
        }

        if(Input.isKeyDown(Keys.D)){
            if(physicsBody.velocity.getX() < 5)
                physicsBody.addForce(Vector2.right.multiply(force));
        }


        // wrap player
        float width = Main.getSelectedScene().getWidth();
        if(getPosition().getX() < -width/2)
        {
            translate(new Vector2(width,0));
        }
        if(getPosition().getX() > width/2)
        {
            translate(new Vector2(-width,0));
        }

        if(Input.isKeyDown(Keys.SPACE)){
            if(Math.round(physicsBody.velocity.getY())==0){
                Scene.playSound("/spel2/sound/jump.wav");
                physicsBody.addForce(Vector2.up.multiply(5*15f));
            }
        }
        if(Input.isMousePressed(Keys.LEFTCLICK) && coins > 0){
            Vector2 startPos = object.getPosition().add(object.getPosition().lookAt(Input.getMousePosition()).multiply(50));
            Main.getSelectedScene().instantiate(new Bullet(startPos,Vector2.getDirection(object.getPosition().lookAtDouble(Input.getMousePosition()))));
            coins -=5;
        }
        if(Input.isKeyPressed(Keys.ESCAPE)){
            if(paused!=null){
                Main.gameWorld.remove(paused);
                paused = null;
            }
            else{
               paused = new PauseMenu();
               Main.gameWorld.add(paused,1);
            }
        }
        if(paused!=null){
            paused.setLocation((int) (JavaGameEngine.getWindowSize().getX()/2-250), (int) (JavaGameEngine.getWindowSize().getY()/2-250));
        }

        // rotate gun to mouse
        object.rotateTo(object.getPosition().lookAtDouble(Input.getMousePosition()),new Vector2(-20,0));
        //camera follow player y pos
    }

    @Override
    protected void onTriggerEnter(CollisionEvent collisionEvent) {
        super.onTriggerEnter(collisionEvent);

        if(collisionEvent.getCollider2().getTag() == "enemy" && ((Enemy)collisionEvent.getCollider2().getParent()).animationIndex == 0){
            Main.player.paused = null;
            Main.setSelectedScene(new Splashscreen());
        }
    }

    @Override
    public void render(Graphics2D g) {
        super.render(g);
        //drawn mouse aim
        g.setColor(Color.green);
        g.drawOval((int) (Input.getMousePosition().getX()-25), (int) (Input.getMousePosition().getY()-25),50,50);
    }

    @Override
    public void onCollisionEnter(CollisionEvent collisionEvent) {
        super.onCollisionEnter(collisionEvent);
        //standing animation
        animationIndex = 1;
    }

    @Override
    public void onCollisionLeft() {
        super.onCollisionLeft();
        // floating animation
        animationIndex = 0;
    }
}
