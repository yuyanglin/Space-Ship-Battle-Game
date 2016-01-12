package spaceShip;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

//-----------------------------------------------------------------
// Attributes
//-----------------------------------------------------------------
public class Asteroid {
    private int xPos, yPos;
    Color astColor;
    private int WIDTH = 600;
    private int HEIGHT = 600;
    private int size;
    private int minimum_size = 10;
    private int deltaX, deltaY;

    //-----------------------------------------------------------------
    // Constructors
    //-----------------------------------------------------------------

    public Asteroid(){
        Random r = new Random();
        xPos = r.nextInt(WIDTH);
        yPos = r.nextInt(HEIGHT);
        size = minimum_size + r.nextInt(HEIGHT/30);
        astColor = Color.LIGHT_GRAY;
        // the asteroids move 1~15 randomly up or down, left or right
        // if you want to generate 1~30, a = -15 + r.next(31);
        deltaX = 1 + r.nextInt(15);
        deltaY = 1 + r.nextInt(15);
    }

    public void move(int x, int y){

        if(xPos <= 0 || xPos >= WIDTH - size)
            deltaX = deltaX * -1;

        if(yPos <= 0 || yPos >= HEIGHT - size)
            deltaY = deltaY * -1;                   // change the direction if it hits the wall

        xPos = xPos+ deltaX;
        yPos = yPos+ deltaY;
    }
    public int getxPos(){
        return xPos;
    }
    public int getyPos(){
        return yPos;
    }


    public int getSize(){
        return size;
    }
    public void setSize(int c){
        size = c;
    }
    public void setWIDTH(int a){
        WIDTH = a ;
    }
    public void setHEIGHT(int b){
        HEIGHT = b ;
    }
    public int getDeltaX(){
        return deltaX;
    }
    public int getDeltaY(){
        return deltaY;
    }

    public void drawAsteroid(Graphics g){
        g.setColor(astColor);
        g.fillOval(xPos, yPos, size, size);
    }
    public boolean isHit(int xMouse, int yMouse){
        if (yMouse <= yPos + size && yMouse > yPos && xMouse < xPos )
            return true;
        else
            return false;
    }

}