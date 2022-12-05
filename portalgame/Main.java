package TestGames.portalgame;

import com.javagamemaker.javagameengine.JavaGameEngine;
import com.javagamemaker.javagameengine.Scene;
import com.javagamemaker.javagameengine.components.CameraMovement;
import com.javagamemaker.javagameengine.components.Collider;
import com.javagamemaker.javagameengine.components.shapes.Rect;
import com.javagamemaker.javagameengine.msc.Vector2;
import TestGames.Player;

public class Main extends JavaGameEngine {


    public static void main(String[] args){
        Scene level1 = new Scene();
        level1.getCamera().add(new CameraMovement());
        /*GameObject thing = new GameObject();
        PhysicsBody p = new PhysicsBody();
        p.velocity = new Vector2(0,2);
        thing.add(p);
        Collider c  = new Collider();
        c.setTrigger(true);
        thing.add(c);

        level1.add(thing);*/
        Player p = new Player();
        Collider c = ((Collider)p.getChild(new Collider()));
        c.setLocalVertices(new Rect(100,100));
        c.setTag("asd");
        c.setTrigger(true);
        level1.add(p);

        level1.add(new Portal(new Vector2(-200,-200),new Vector2(0,200)));
        setSelectedScene(level1);

        start();

    }

}
