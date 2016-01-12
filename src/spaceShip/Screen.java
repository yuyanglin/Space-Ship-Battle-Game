package spaceShip;

import java.awt.*;
import javax.swing.*;
import javax.swing.Timer;
import java.awt.event.*;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.util.*;

//-----------------------------------------------------------------
// Class
//-----------------------------------------------------------------
public class Screen extends JPanel implements MouseMotionListener, MouseListener, ActionListener,
        ComponentListener, KeyListener{
    // information of the Spaceship stars and the Asteroids
    private SpaceShip ship1;
    private Star[] stars;
    private ArrayList<Asteroid> ast;
    private int NUM_ASTEROIDS = 15;
    private int NUM_STARS = 100;

    // info shown on top-left corner
    private JLabel timeleft;
    private JLabel score;
    private JLabel Lives;
    private int currentSc = 0;  // currentScore
    private int currentLv = 3;  // currentLives

    // the HIT!!!
    private JLabel hit;
    // gameOver!
    private JLabel GameOver;

    // 2 Timers
    private Timer timerAst, countdown;
    private Random rand;
    private int DELAY = 100;
    private int x, y ;
    private int remainTime = 15;  // the remainTime of the game(the slower timer)

    // the size information of frame
    private int WIDTH = 1200;
    private int HEIGHT = 800;

    boolean gameOver;

    //-----------------------------------------------------------------
    // Constructors
    //-----------------------------------------------------------------
    public Screen(){
        ship1 = new SpaceShip();
        ship1.setColor(Color.blue);	// set the color of ship1 to blue
        // Create 100 stars using a loops of Array
        stars = new Star[100];        // new an array of Star called stars, which has 100 objects in it
        for (int i = 0; i < NUM_STARS; i++) {
            stars[i] = new Star();
        }

        // create an ArrayList consists of 15 Asteroids
        ast = new ArrayList<Asteroid>();
        for (int i=0; i<NUM_ASTEROIDS; i++) {
            ast.add(new Asteroid());  // new an object in the ArrayList each time
        }

        // timer of the Asteroid movement (faster)
        timerAst = new Timer(DELAY, this);
        timerAst.setActionCommand("movement");
        setPreferredSize (new Dimension(WIDTH, HEIGHT));
        timerAst.start();

        // timer of the Game (slower)
        countdown = new Timer(DELAY*10, this);
        countdown.setActionCommand("countdown");
        countdown.start();

        rand = new Random();   // new a random object for the 30% movement

        // add Listeners
        addMouseMotionListener(this);
        addMouseListener(this);
        addComponentListener(this);
        addKeyListener(this);

        // add Labels
        GameOver = new JLabel("Game Over...");
        setLayout(new GridLayout(10,5));        // set the layout so that the current info. will be on the top-left side of the Frame
        timeleft = new JLabel("Time Left: "+ remainTime);
        score = new JLabel("Score: "+ currentSc);
        Lives = new JLabel("Lives Left: "+ currentLv );
        hit = new JLabel("HIT!!!");

        // set the font of the labels
        timeleft.setFont(new Font("Serif", Font.CENTER_BASELINE, 25));
        score.setFont(new Font("Serif", Font.CENTER_BASELINE, 25));
        Lives.setFont(new Font("Serif", Font.CENTER_BASELINE, 25));

        // set the Foreground color (The color of the fonts)
        score.setForeground(Color.orange);
        Lives.setForeground(Color.orange);
        timeleft.setForeground(Color.orange);
        GameOver.setForeground(Color.green);

        //
        GameOver.setFont(new Font("Serif", Font.TRUETYPE_FONT, 90));

        // the HIT sign was shown when the Spaceship fires the Asteroids
        hit = new JLabel("HIT!!!");
        hit.setForeground(Color.red);
        hit.setFont(new Font("Serif", Font.CENTER_BASELINE, 50));

        add(timeleft);
        add(score);
        add(Lives);
    }

    public void paintComponent(Graphics g){
        // collision of two circular objects: it happens when d(AB) < r1 + r2
        for(int j = 0; j < ast.size();j++){
            int shipXcenter = ship1.getxPos(), shipYcenter = ship1.getyPos();
            int aXcenter = ast.get(j).getxPos(), aYcenter = ast.get(j).getyPos();
            int shipSize = ship1.getHEIGHT(), aSize = ast.get(j).getSize();

            if ((Math.pow(shipXcenter - aXcenter - 0.5 * aSize, 2) + Math.pow(shipYcenter -aYcenter - 0.5 * aSize, 2))
                    < Math.pow(0.5 * shipSize + 0.5 * aSize, 2)) {
                currentLv--;    // upon one collision, the lives will decrease one
                if (currentLv == 2){
                    ship1.setColor(Color.yellow);
                }
                if (currentLv == 1){
                    ship1.setColor(Color.red);
                }
                if(currentLv == 0){
                    gameIsOver();
                    countdown.setActionCommand("HAHA");
                }
                ast.remove(j);
                j--;
            }
        }

        // if there is no Asteroid left, then game over...
        if (ast.size() == 0) {
            gameIsOver();
        }
//        g.setColor(Color.RED);
//        g.setFont(new Font("Arial",Font.BOLD,50));

        timeleft.setText("Time Left: "+ remainTime);
        score.setText("Score: "+ currentSc);
        Lives.setText("Lives Left: " + currentLv);
        GameOver.setLocation(WIDTH/3,HEIGHT/2);

        super.paintComponent(g);
        setBackground(Color.black);

        for(int i=0; i<NUM_STARS; i++)
            stars[i].drawStar(g);

        for(Asteroid a : ast)
            a.drawAsteroid(g);      //ast.add(new Asteroid());
        ship1.draw(g, WIDTH);       // call function
    }

    public void actionPerformed(ActionEvent event){
        if(event.getActionCommand() == "movement") {
            int move = rand.nextInt(10);
            if(move >= 0 && move <= 2){  // implement 30% movement of the asteroids
                for(Asteroid a : ast){
                    a.move(x, y);
                    if(gameOver)
                        return;

                    repaint();
                }
            }
        }
        // the countDown Timer
        if(event.getActionCommand() == "countdown"){
            remainTime--;
            remove(hit);
            repaint();
            if(event.getActionCommand()=="HAHA" ){ // if remainTime == 0, the countdown-timer stops clicking
                return;
            }
            if(remainTime == 0){
                gameIsOver();
                countdown.setActionCommand("HAHA");
                return;
            }
        }
    }
    // a function gameIsOver
    public void gameIsOver(){
        gameOver = true;
        add(GameOver);
    }

    public static void main (String[] args)
    {
        JFrame frame = new JFrame ("Space Ships");
        frame.setLayout(new BorderLayout());
        frame.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);

        Screen panel = new Screen();
        frame.getContentPane().add(panel);

        frame.pack();
        frame.setVisible(true);

        //let the panel get the focus of keyboard
        panel.setFocusable(true);
        panel.requestFocusInWindow();
    }
    @Override
    public void mouseMoved(MouseEvent e){
        if (gameOver){
            return;
        }
        ship1.move(e.getX(),e.getY());
        repaint();
    }
    @Override
    public void mouseDragged(MouseEvent e){
        if (gameOver){
            return;
        }
        ship1.move(e.getX(),e.getY());
        ship1.setShooting(true);
        for(int i=0; i< ast.size();i++){
            if (ast.get(i).isHit(e.getX(),e.getY())){
                ast.remove(i);
                i--;
                currentSc++;
                add(hit);
            }
        }
        repaint();
    }
    @Override
    public void mouseClicked(MouseEvent e) {
    }
    @Override
    public void mouseEntered(MouseEvent e) {
        ship1.move(e.getX(),e.getY());
    }
    @Override
    public void mouseExited(MouseEvent e) {
    }
    @Override
    public void mousePressed(MouseEvent e) {
        if (gameOver){
            return;
        }
        ship1.setShooting(true);
        for(int i=0; i< ast.size();i++){
            if (ast.get(i).isHit(e.getX(),e.getY())){
                ast.remove(i);
                i--;
                currentSc++;
                add(hit);
            }
        }
        repaint();
    }
    @Override
    public void keyPressed(KeyEvent e){
        if (e.getKeyCode() == KeyEvent.VK_SPACE){
            if (gameOver){
                return;
            }
            ship1.setShooting(true);
            for(int i=0; i< ast.size();i++){
                if (ast.get(i).isHit(ship1.getxPos(),ship1.getyPos())){

                    ast.remove(i);
                    i--;
                    currentSc++;
                    add(hit);
                }
            }
            repaint();
        }
    }
    @Override
    public void keyReleased(KeyEvent e){
        if (e.getKeyCode() == KeyEvent.VK_SPACE){
            ship1.setShooting(false);
            repaint();
        }
    }
    @Override
    public void keyTyped(KeyEvent e) {
    }
    @Override
    public void mouseReleased(MouseEvent e) {
        if (gameOver){
            return;
        }
        ship1.setShooting(false);
        repaint();
    }
    @Override
    public void componentMoved(ComponentEvent e) {
    }
    @Override
    public void componentShown(ComponentEvent e) {
    }
    @Override
    public void componentHidden(ComponentEvent e) {
    }
    @Override
    public void componentResized(ComponentEvent e){
        if (gameOver){
            return;
        }
        WIDTH = (int) e.getComponent().getBounds().getWidth();
        HEIGHT = (int) e.getComponent().getBounds().getHeight();    // change the boundary as the user resized
        for(Asteroid a : ast){
            a.setWIDTH(WIDTH);
            a.setHEIGHT(HEIGHT);
        }
        for(int i=0; i<100; i++){
            stars[i] = new Star(WIDTH, HEIGHT);
        }
        repaint();
    }
}
