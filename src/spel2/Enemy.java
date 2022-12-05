package spel2;

import com.javagamemaker.javagameengine.CollisionEvent;
import com.javagamemaker.javagameengine.Scene;
import com.javagamemaker.javagameengine.components.Collider;
import com.javagamemaker.javagameengine.components.Sprite;
import com.javagamemaker.javagameengine.msc.Debug;
import com.javagamemaker.javagameengine.msc.Vector2;

import java.awt.*;

public class Enemy extends Sprite {

    public Enemy(Vector2 pos){
        Collider c = new Collider(false);
        c.setTrigger(true);
        c.setTag("enemy");
        add(c);
        animations = Level1.enemyPrefab.animations;
        c.setScale(new Vector2(50,100));
        c.updateVertices();

        setPosition(pos.subtract(new Vector2(50,0)));

        Rectangle[] die = new Rectangle[12];
        int i = 0;
        for(int y = 0;y<2;y++) {
            for(int x = 0;x<6;x++){
                Rectangle tile = new Rectangle(x * 1600 / 6, y*532 / 2, 1600 / 6, 532 / 2);
                die[i] = tile;
                        i++;
            }
        }
        loadAnimation(die, "/spel2/sprites/watersplash.png");
        setTimer(20);
    }
    int times = 0;
    @Override
    public void animationDone() {
        super.animationDone();
        if(animationIndex == 1){
            destroy();
        }
        //if(times == 1){
        //    setInverted(!isInverted());
        //    times=0;
        //    dir = dir==Vector2.right?Vector2.left:Vector2.right;
        //}
        times++;
    }
    Vector2 dir = Vector2.right;

    @Override
    protected void onTriggerEnter(CollisionEvent collisionEvent) {
        super.onTriggerEnter(collisionEvent);
        if(animationIndex == 0 && collisionEvent.getCollider2().getTag()=="bullet")
        {
            collisionEvent.getCollider2().getFirstParent().destroy();
            Scene.playSound("/spel2/sound/watersplash.wav",0.1f);
            animationIndex = 1;
            setTimer(4);
        }
    }
    int steps = 0;
    @Override
    public void update() {
        super.update();
        if(getPosition().getY() > Main.player.getPosition().getY()+500){
            destroy();
        }
        if(steps >= 200){
            setInverted(!isInverted());
            dir = dir==Vector2.right?Vector2.left:Vector2.right;
            steps=0;
        }
        translate(dir.multiply(0.5f));
        steps++;
    }
}
