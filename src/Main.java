import com.javagamemaker.javagameengine.JavaGameEngine;
import com.javagamemaker.javagameengine.Scene;
import com.javagamemaker.javagameengine.components.*;
import com.javagamemaker.javagameengine.components.shapes.Rect;
import com.javagamemaker.javagameengine.input.Input;
import com.javagamemaker.javagameengine.input.Keys;
import com.javagamemaker.javagameengine.msc.Debug;
import com.javagamemaker.javagameengine.msc.Vector2;

import javax.xml.crypto.dsig.keyinfo.PGPData;
import java.awt.*;


public class Main extends JavaGameEngine {

    public static void main(String[] args) {
        Scene scene = new Scene();
        scene.getCamera().add(new CameraMovement());
        scene.getCamera().parallax = true;
        scene.getCamera().setPosition(Vector2.zero);

        Sprite background = new Sprite();
        background.loadAnimation(new String[]{"/mountains-background-game-vector.jpg"});
        background.setScale(new Vector2(1920,900));
        background.setPosition(new Vector2(-100, -250,0));
        background.setLayer(0);
        scene.add(background);

        Collider c = new Collider(new Rect(1920,200));
        c.setPosition(new Vector2(0,415,0));
        c.setVisible(true);

        scene.add(new Tree(new Vector2(0  , -160)));
        scene.add(new Tree(new Vector2(200, -160)));
        scene.add(new Tree(new Vector2(400, -160)));
        scene.add(new Tree(new Vector2(900, -160)));

        GameObject floor = new GameObject();
        floor.setPosition(new Vector2(0,315, 50));
        floor.setScale(new Vector2(1920,500));
        Collider floorCollider = new Collider(floor.getLocalVertices());
        floorCollider.setVisible(true);
        floor.add(floorCollider);

        scene.add(floor);

        scene.add(new Player());
        setSelectedScene(scene);

        start();
    }

    static class Tree extends Sprite{

        public Tree(Vector2 pos) {
            setPosition(pos);
            getPosition().setZ(50);
            layer = 100;
            setScale(new Vector2(300,500));
            loadAnimation(new String[]{"/tree-forest-free-png.png"});
        }
    }

    static class Player extends Sprite{

        public Player(){
            Rectangle[] sprites = new Rectangle[27];
            int width = 900/7;
            int height = 600/4;
            int x = 0;
            int y = 0;
            for(int i = 0; i < 27; i++){
                Rectangle rec = new Rectangle(x*width, y*height,width,height);
                sprites[i] = rec;
                if(x == 6){
                    y++;
                    x=-1;
                }
                x++;
            }
            loadAnimation(sprites, "/playerSheet.png");
            Collider c = new Collider();
            c.setParentOffset(new Vector2(0,-30));
            add(c);
            add(new PhysicsBody(true));
            setPosition(new Vector2(0,0,100));
        }

        @Override
        public void update() {
            super.update();
            if(Input.isKeyDown(Keys.D)){
                translate(Vector2.right);
                setInverted(false);
            }
            if(Input.isKeyDown(Keys.A)){
                translate(Vector2.left);
                setInverted(true);
            }
            if(Input.isKeyPressed(Keys.SPACE)){
                ((PhysicsBody)getChild(new PhysicsBody())).addForce(Vector2.up.multiply(30));
            }
            if(Input.isKeyPressed(Keys.ESCAPE)){
                JavaGameEngine.getSelectedScene().getCamera().parallax = !JavaGameEngine.getSelectedScene().getCamera().parallax;
            }
            JavaGameEngine.getSelectedScene().getCamera().setPosition(getFirstParent().getPosition().multiply(-1));
        }
    }


}