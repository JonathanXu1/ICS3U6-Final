//Moving on a player smoothly on the screen

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*; 
import java.util.Scanner;
import java.awt.image.*;
import javax.imageio.*;

/*An Example demonstrating player movement using keylisteners to have smooth movement
* 
* @Author Mangat
*/

//This class is used to start the program and manage the windows
class SmoothMoves { 

  public static void main(String[] args) { 
    GameWindow game= new GameWindow();  
  }

}

//This class represents the game window
class GameWindow extends JFrame { 
  
  //Window constructor
  public GameWindow() { 
   setTitle("Simple Game Loop Example");
   //setSize(1280,1024);  // set the size of my window to 400 by 400 pixels
   setResizable(true);  // set my window to allow the user to resize it
   setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  // set the window up to end the program when closed
   getContentPane().add( new GamePanel());
   pack(); //makes the frame fit the contents
   setVisible(true);
  }
  


// An inner class representing the panel on which the game takes place
static class GamePanel extends JPanel implements KeyListener{
  
  
  Player player;
  
  //constructor
  public GamePanel() { 
    setPreferredSize(new Dimension(1024,768));
    addKeyListener(this);
    setFocusable(true);
    requestFocusInWindow();
    
   
    player = new Player();
    player.x=25;
    player.y=25;
  }
  
  
  public void paintComponent(Graphics g) { 
    super.paintComponent(g); //required to ensure the panel si correctly redrawn
    //update the content
    //draw the screen
    player.move();
    player.draw(g);
    //request a repaint
    repaint();
  }

  public void keyTyped(KeyEvent e) {      
  
  }
  
  public void keyPressed(KeyEvent e) {
    if(e.getKeyChar() == 'a' ){    //Good time to use a Switch statement
      player.xdirection=-1;
    } else if(e.getKeyChar() == 's' ){
      player.ydirection=1;
    } else if(e.getKeyChar() == 'd' ){
      player.xdirection=1;
    } else if(e.getKeyChar() == 'w' ){
      player.ydirection=-1;
    }  //note - would be better to make player class and pass in map, test movement in there
  }
  
  public void keyReleased(KeyEvent e) {      
    if(e.getKeyChar() == 'a' ){    //Good time to use a Switch statement
      player.xdirection=0;
    } else if(e.getKeyChar() == 's' ){
      player.ydirection=0;
    } else if(e.getKeyChar() == 'd' ){
      player.xdirection=0;
    } else if(e.getKeyChar() == 'w' ){
      player.ydirection=0;
    }  //note - would be better to make player class and pass in map, test movement in there
  }  
  
}

}


  
 class Player { 
     int x, y; 
     int xdirection,ydirection;
     
   BufferedImage sprite;

      
   public Player() { 
     loadSprites();
     //int currentSprite=0;
     this.x=500;
     this.y=768/2;
     this.xdirection=0;
     this.ydirection=0;
   }
   
   public void loadSprites() { 
     try {
     sprite = ImageIO.read(new File("sprite.png"));

     } catch(Exception e) { System.out.println("error loading sprite");};
   }
 
   public void draw(Graphics g) { 
     g.drawImage(sprite,x,y,null);

   }
 
   public void move() { 
     this.x=this.x+this.xdirection;
     this.y=this.y+this.ydirection;
   }
   
 }