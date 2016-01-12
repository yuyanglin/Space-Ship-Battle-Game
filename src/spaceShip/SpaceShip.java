package spaceShip;

import java.awt.*;
import java.util.Random;
import javax.swing.*;

public class SpaceShip {
    private boolean shooting;
    private int xPos, yPos;
    private Color clr;
    private final int WIDTH = 50, HEIGHT = 50;

    public SpaceShip(){
        Random rand = new Random();
        xPos = rand.nextInt(251);  // the range of the random integer is from 0~250
        yPos = rand.nextInt(251);
        shooting = false;
        clr = Color.red;		// assign an initial color
    }

    public SpaceShip(int posx, int posy){
        //the parameter of SpaceShip is called from the random number the constructor produce previously
        xPos = posx ;
        yPos = posy ;
        shooting = true;
        clr = Color.red;
    }
    public void setShooting(boolean isShooting){
        shooting = isShooting;
    }

    public int getxPos(){
        return xPos;
    }
    public int getyPos(){
        return yPos;
    }

    public int getHEIGHT(){
        return HEIGHT;
    }
    public int getWIDTH(){
        return WIDTH;
    }

    public void draw(Graphics g, int boundary){

        // use different color and shapes to decorate the spaceships
        g.setColor(clr);
        g.fillOval(xPos-25, yPos-25, WIDTH, HEIGHT);

        g.setColor(Color.darkGray);
        g.fillRect(xPos-10, yPos-15, WIDTH - 45, HEIGHT - 45);

        g.setColor(Color.red);
        g.fillRect(xPos+5, yPos-15, WIDTH - 40, HEIGHT - 40);

        g.setColor(Color.white);
        g.fillOval(xPos+6, yPos-14, WIDTH - 43, HEIGHT - 43);

        g.setColor(Color.green);
        g.drawLine(xPos+25, yPos+5, xPos+40, yPos-10);

        g.setColor(Color.green);
        g.drawLine(xPos+40, yPos+5, xPos+25, yPos-10);

        g.setColor(Color.black);
        g.drawArc(xPos-24, yPos-25, 100, 30, -180, 90);

        g.setColor(Color.red);
        g.drawArc(xPos-24, yPos-20, 90, 30, -180, 90);

        if(shooting){
            g.setColor(Color.red);
            g.drawLine(xPos + 25, yPos-2, boundary, yPos-2); //the laser beam will fire until the boundary
        }
    }

    public void setColor(Color color){
        clr = color;
    }

    public void move(int x,int y){
        xPos = x;
        yPos = y;
    }
}
