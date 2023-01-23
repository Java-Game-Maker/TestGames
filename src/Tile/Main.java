package Tile;

import com.javagamemaker.javagameengine.JavaGameEngine;
import com.javagamemaker.javagameengine.components.CameraMovement;
import com.javagamemaker.javagameengine.components.Component;
import com.javagamemaker.javagameengine.components.GameObject;
import com.javagamemaker.javagameengine.components.Tile;
import com.javagamemaker.javagameengine.components.TileMap;
import com.javagamemaker.javagameengine.components.shapes.Circle;
import com.javagamemaker.javagameengine.input.Input;
import com.javagamemaker.javagameengine.input.Keys;
import com.javagamemaker.javagameengine.msc.Vector2;

import java.awt.*;
import java.util.ArrayList;

public class Main extends JavaGameEngine {
    ArrayList<Tile> fallenTiles = new ArrayList<>();
    public static void main(String[] args){
        TileMap t = new TileMap();
        t.tileWidth = 50;


        t.add(new Pacman());

        setSelectedScene(t);
        //t.getCamera().setScale(new Vector2(0.1f,0.1f));
        start();
    }
    static class Pacman extends Tile {
        int ticks = 0;
        public Pacman(){
            setColor(Color.RED);
            Tile t = new Tile();
            Tile t1 = new Tile();
            Tile t2 = new Tile();
            t.setParentOffset(new Vector2(1.0f,0));
            t1.setParentOffset(new Vector2(-1.0f,0));
            t2.setParentOffset(new Vector2(0,-1.0f));
            add(t);
            add(t1);
            add(t2);
        }

        @Override
        public void updateMili() {
            super.updateSecond();
            if(ticks%8==0){
                translate(Vector2.down);
            }
            ticks++;
        }

        @Override
        public void update() {
            super.update();
            if(Input.isKeyPressed(Keys.SPACE))
                rotate((float) (90/JavaGameEngine.deltaTime));
            if(Input.isKeyPressed(Keys.D)) translate(Vector2.right);
            if(Input.isKeyPressed(Keys.A)) translate(Vector2.left);
            updateVertices();
            for(Component c : getChildren())
                c.updateVertices();

            if(getPosition().getY() >= 500){

            }
        }
    }
}
