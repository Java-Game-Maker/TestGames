package spel2;

import com.javagamemaker.javagameengine.components.Collider;
import com.javagamemaker.javagameengine.components.Sprite;
import com.javagamemaker.javagameengine.components.shapes.Rect;
import com.javagamemaker.javagameengine.msc.Debug;
import com.javagamemaker.javagameengine.msc.Random;
import com.javagamemaker.javagameengine.msc.Vector2;

public class Ground extends Sprite {
    public Ground(float width,Vector2 pos){
        loadAnimation(new String[]{"/spel2/sprites/groundtileStone.png"});
        setScale(new Vector2(width,50));
        setPosition(pos);

    }
    public boolean generateEnemy(){
       if(new Random().nextInt(5)==1){
           Enemy e = new Enemy(getPosition().add(new Vector2(0,-80)));
           Main.getSelectedScene().instantiate(e);
           return true;
       }
       return false;
    }
    public void respawn(){
        // set new pos and maybe generate an enemy
        float diff = 1000;
        setPosition(new Vector2(new Random().nextFloat(-100,300),getPosition().getY()-diff/2-1000));
        if(generateEnemy()){
            setScale(new Vector2(300,50));
        }
        else{
            setScale(new Vector2(100,50));
        }
    }
    @Override
    public void update() {
        super.update();
        // if the player is above add a collider
        // else destroy it if it exists
        if(Main.player.getPosition().getY()+90 < getPosition().getY() && getChild(new Collider())==null){
            Collider c =new Collider(false);
            c.setLocalVertices(new Rect(getScale()));
            c.updateVertices();
            add(c);
        }
        else if(Main.player.getPosition().getY()+50 > getPosition().getY() && getChild(new Collider())!=null){
            getChild(new Collider()).destroy();
        }
        // check if the ground should respawn
        if(getPosition().getY() > Main.player.getPosition().getY()+1000){
            respawn();
        }
    }
}
