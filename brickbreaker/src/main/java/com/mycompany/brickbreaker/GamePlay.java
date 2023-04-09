/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.brickbreaker;


import javax.swing.JPanel; 
import javax.swing.Timer; 
import java.awt.Graphics2D; 
import java.awt.Rectangle; 
import java.awt.Graphics; 
import java.awt.Color; 
import java.awt.Font; 
import java.awt.event.ActionEvent; 
import java.awt.event.ActionListener; 
import java.awt.event.KeyEvent; 
import java.awt.event.KeyListener ; 


/**
 *
 * @author Admin
 */

public class GamePlay extends JPanel implements KeyListener, ActionListener {
    
    private boolean play = false;
    private int score = 0;
    private int totalbricks = 21;
    private Timer Timer;
    private int delay = 8;
    private int playerX = 310;
    private int ballposX = 120;
    private int ballposY = 350;
    private int ballXdir = -1;
    private int ballYdir = -2;
    private MapGenerator map;

    public GamePlay() {
        map = new MapGenerator(3, 7);
        addKeyListener(this);
        setFocusable(true);  // setFocusable() is actually a method from the Component class in Swing. It lets the component (in your case, JPanel which extends Component) have the power of getting focused. Its based on GPU
        setFocusTraversalKeysEnabled(false); //focus traversal keys (TAB key, SHIFT+TAB, etc.) are allowed to be used when the current Component has focus 
        Timer = new Timer(delay, this);
        Timer.start();
    }
    
     public void paint(Graphics g) {
        g.setColor(Color.black);      
        g.fillRect(1, 1, 692, 592);     // background color

        map.draw((Graphics2D) g);      // bricks

        g.setColor(Color.yellow);
        g.fillRect(0, 0, 3, 592);
        g.fillRect(0, 0, 692, 3);
        g.fillRect(681, 0, 3, 592);     // yellow borders

        g.setColor(Color.white);
        g.setFont(new Font("serif", Font.BOLD, 25));
        g.drawString("" + score, 590, 30);         // score    

        g.setColor(Color.yellow);
        g.fillRect(playerX, 550, 100, 8);    // slider

        g.setColor(Color.GREEN);
        g.fillOval(ballposX, ballposY, 20, 20);        // ball

        if (ballposY > 570) {           // ball falls down
            play = false;
            ballXdir = 0;
            ballYdir = 0;
            g.setColor(Color.red);
            g.setFont(new Font("serif", Font.BOLD, 30));
            g.drawString("    Game Over Score: " + score, 190, 300);

            g.setFont(new Font("serif", Font.BOLD, 30));
            g.drawString("   Press Enter to Restart", 190, 340) ;
                    
        }
        
        if(totalbricks == 0){          // all bricks detroyed
            play = false;
            ballYdir = -2;                
            ballXdir = -1;                 // reset ball position
            g.setColor(Color.red);
            g.setFont(new Font("serif",Font.BOLD,30));
            g.drawString("    Game Over: "+score,190,300);

            g.setFont(new Font("serif", Font.BOLD, 30));
            g.drawString("   Press Enter to Restart", 190, 340);

        }

        g.dispose();         // remove from memory when game ends

    }

    @Override
    public void actionPerformed(ActionEvent e) {  // actionPerformed is an in-built func for ActionEvent, we are overriding it 
        Timer.start();

        if (play) {
            if (new Rectangle(ballposX, ballposY, 20, 20).intersects(new Rectangle(playerX, 550, 100, 8))) {
                ballYdir = -ballYdir;       // when ball touches slider direction of ball is reversed
            }

            A:          // used to reference a loop just below it
            for (int i = 0; i < map.map.length; i++) {
                for (int j = 0; j < map.map[0].length; j++) {
                    if (map.map[i][j] > 0) {
                        int brickX = j * map.bricksWidth + 80;
                        int brickY = i * map.bricksHeight + 50;   // brick size
                        int bricksWidth = map.bricksWidth;
                        int bricksHeight = map.bricksHeight;

                        Rectangle rect = new Rectangle(brickX, brickY, bricksWidth, bricksHeight);
                        Rectangle ballrect = new Rectangle(ballposX, ballposY, 20, 20);
                        Rectangle brickrect = rect;   // reference variable(simiiar to temp variable for head)

                        if (ballrect.intersects(brickrect)) {
                            map.setBricksValue(0, i, j);  // making value of array map[i][j] = 0 of MapGeneraor class
                            totalbricks--;
                            score += 5;
                            
                            if (ballposX + 19 <= brickrect.x || ballposX + 1 >= brickrect.x + bricksWidth) { // change x-direc of ball if it hits only 1 brick 
                                ballXdir = -ballXdir;
                            } else {      // change y-direc of ball if it touches 2 bricks
                                ballYdir = -ballYdir;
                            }
                            break A;  
                        }
                    }

                }
            }


            ballposX += ballXdir;
            ballposY += ballYdir;        // moving the ball acc. to the direc
            
            if (ballposX < 0) {          // left border
                ballXdir = -ballXdir;
            }
            if (ballposY < 0) {          // top border
                ballYdir = -ballYdir;
            }
            if (ballposX > 670) {        // right border
                ballXdir = -ballXdir;
            }
        }
        repaint();
    }

    @Override
    public void keyTyped(KeyEvent e) {    // in-built fucn of extended or implemented class have to be given a body

       }


    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {  // checking if right key is pressed
            if (playerX >= 600) {      // max right position for slider
                playerX = 600;
            } else {
                moveRight();
            }
        }
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {   // checking if left key is pressed
            if (playerX < 10) {
                playerX = 10;
            } else {
                moveLeft();
            }
        }

        if (e.getKeyCode() == KeyEvent.VK_ENTER) {   // checking if enter key is pressed
            if (!play) {
                ballposX = 120;              // re-setting values
                ballposY = 350;
                ballXdir = -1;
                ballYdir = -2;
                score = 0;
                playerX = 310;
                totalbricks = 21;
                map = new MapGenerator(3, 7);

                repaint();
            }
        }


        }

        public void moveRight ()
        {
            play = true;
            playerX += 20;
        }
        public void moveLeft ()
        {
            play = true;
            playerX -= 20;
        }
           
}