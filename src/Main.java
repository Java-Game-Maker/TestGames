import com.javagamemaker.javagameengine.JavaGameEngine;
import com.javagamemaker.javagameengine.Scene;
import com.javagamemaker.javagameengine.components.Collider;
import com.javagamemaker.javagameengine.components.GameObject;
import com.javagamemaker.javagameengine.components.PhysicsBody;
import com.javagamemaker.javagameengine.msc.Vector2;


public class Main extends JavaGameEngine {

    public static void main(String[] args) {
        Scene scene = new Scene();

        for(int i = 0; i < 100;i++){
            GameObject gameObject = new GameObject();
            gameObject.add(new PhysicsBody(true));
            gameObject.add(new Collider());
            gameObject.setPosition(new Vector2((i)*100-500,i*100));
            gameObject.updateVertices();
            scene.add(gameObject);
        }
        setSelectedScene(scene);

        Scene scene1 = new Scene();
        GameObject parent = new GameObject();
        for(int i = 0; i < 100;i++){
            GameObject gameObject = new GameObject();
            gameObject.add(new PhysicsBody(true));
            //gameObject.add(new Collider());
            //gameObject.setParentOffset(new Vector2(i*100+50,1));
            parent.add(gameObject);
        }
        scene1.add(parent);
        setSelectedScene(scene);

        start();
    }

}
