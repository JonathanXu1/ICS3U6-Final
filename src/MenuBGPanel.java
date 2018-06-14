//Add custom font to buttons
//Have better button highlight effects
//Add ships
//Allign pixels to bg

/////////////////////
import javax.swing.JPanel;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.Random;
import javax.swing.Timer;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/////////////////////
class MenuBGPanel extends JPanel{
  private int xVal;
  private int yVal;
  private double pixelX, pixelY;
  private Image bg = Toolkit.getDefaultToolkit().getImage("../res/Bg.png");
  private Image station1 = Toolkit.getDefaultToolkit().getImage("../res/Station1.png");
  private Image station2 = Toolkit.getDefaultToolkit().getImage("../res/Station2.png");
  private Image ship1 = Toolkit.getDefaultToolkit().getImage("../res/Ship1.png");
  private Image ship2 = Toolkit.getDefaultToolkit().getImage("../res/Ship2.png");
  private Star[][] stars = new Star[100][200];
  private int stationState = 1;
  private int ship1X, ship1Y, ship1Wait;
  private int ship2X, ship2Y, ship2Wait;
  private int ship1Count = 0;
  private int ship2Count = 0;
  private boolean ship1Drawn = false;
  private boolean ship2Drawn = false;
  private boolean ship1FirstDrawn = false;
  private boolean ship2FirstDrawn = false;
  
  Random rand = new Random();
  
  MenuBGPanel(int xVal, int yVal){
    this.setFocusable(true);
    this.xVal = xVal;
    this.yVal = yVal;
    Dimension panelSize= new Dimension (xVal, yVal);
    this.setPreferredSize(panelSize);
    this.setLayout(null);
    
    pixelX = xVal/200;
    pixelY = yVal/100;
    
    ship1Wait = rand.nextInt(500) + 300; //1 to 5 seconds
    ship2Wait = rand.nextInt(500) + 300; //1 to 5 seconds
    
    ActionListener drawStars = new ActionListener() {
      /**
       *actionPerformed
       *Changes the stars
       *@param: ActionEvent evt
       *@return: 
       */
      public void actionPerformed(ActionEvent evt) {
        int starCount = 2; //# stars
        for (int i = 0; i <= starCount; i ++){
          int randX = rand.nextInt(200);
          int randY = rand.nextInt(100);
          if(stars[randY][randX] == null){
            stars[randY][randX] = new Star();
          }
        }
        for (int i = 0; i < stars.length; i ++){
          for (int j = 0; j < stars[0].length; j++){
            if(stars[i][j] != null){
              if(!stars[i][j].getDied()){
                stars[i][j].updateColor();
              }
            }
          }
        }
      }
    };
    new Timer(100, drawStars).start();
    
    ActionListener drawStation = new ActionListener() {
      /**
       *actionPerformed
       *Action listener is for the station
       *@param: ActionEvent evt
       *@return: 
       */
      public void actionPerformed(ActionEvent evt) {
        if(stationState == 1){
          stationState = 2;
        }else if(stationState == 2){
          stationState = 1;
        } 
      }
    };
    new Timer(2000, drawStation).start();
  }
  
  @Override
  /**
   *paintComponent
   *Does all the painting
   *@param: Graphics g
   *@return: 
   */
    public void paintComponent(Graphics g){
    super.paintComponent(g);
//Draws space gradient
    g.drawImage(bg,0,0,xVal,yVal,this);
//Draws stars
    for (int i = 0; i < stars.length; i ++){
      for (int j = 0; j < stars[0].length; j++){
        if(stars[i][j] != null){
          if(!stars[i][j].getDied()){
            g.setColor(stars[i][j].getColor());
            g.fillRect((int)(j*pixelX), (int)(i*pixelY), (int)(pixelX), (int)(pixelY));
          } else {
            stars[i][j] = null;
          }
        }
      }
    }
//Draws Ship1
    ship1Count ++;
    if(!(ship1Drawn) && (ship1Count >= ship1Wait) || (!ship1FirstDrawn) )
      ship1Y = rand.nextInt(500) + 50 ; //50 to 550
      ship1Drawn = true;
      if(!ship1FirstDrawn){
        ship1X = rand.nextInt(xVal/2) + 800;
        ship1FirstDrawn = true;
      } else {
        ship1X = xVal;
      }
  if((ship1Drawn) && (ship1X+(int)(16*pixelX)<= 1)){
    ship1Drawn = false;
      ship1Wait = rand.nextInt(500) + 300;
      ship1Count = 0;
    } else if (ship1Drawn){
      ship1X --;
      g.drawImage(ship1,ship1X,ship1Y,(int)(16*pixelX),(int)(5*pixelY),this);
    }
//System.out.println(Integer.toString(ship1Count) + " " + Integer.toString(ship1Wait));
    
//Draws Station
    if(stationState == 1){
      g.drawImage(station1,0,0,xVal,yVal,this);
    } else if(stationState == 2){
      g.drawImage(station2,0,0,xVal,yVal,this);
    }
    
//Draws Ship2
    ship2Count ++;
    if(!(ship2Drawn) && (ship2Count >= ship2Wait) || (!ship2FirstDrawn) ){
      ship2Y = rand.nextInt(yVal - 300) + 50 ; //50 to 550
      ship2Drawn = true;
      if(!ship2FirstDrawn){
        ship2X = rand.nextInt(300);
        ship2FirstDrawn = true;
      } else {
        ship2X = xVal;
      }
    }
    if((ship2Drawn) && (ship2X+(int)(25*pixelX)<= 1)){
      ship2Drawn = false;
      ship2Wait = rand.nextInt(500) + 300;
      ship2Count = 0;
    } else if (ship2Drawn){
      ship2X -= 2;
      g.drawImage(ship2,ship2X,ship2Y,(int)(25*pixelX),(int)(8*pixelY),this);
    }
  }
  /**
   *refresh
   *Repaints the menu
   *@param: 
   *@return: 
   */
  public void refresh(){
    this.repaint();
  }
}
