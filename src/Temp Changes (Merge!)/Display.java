import javax.swing.*;
import java.awt.*;

class Display extends JFrame{
  private DisplayGamePanel mediumPanel;
  private DisplayGamePanel botPanel;
  private DisplayGamePanel rightPanel;
  private GamePanel gamePanel;
  private DisplayGamePanel mapPanel;
  private DisplayGamePanel hpPanel;
  private DisplayGamePanel expPanel;
  private DisplayGamePanel accessoryPanel;
  private int draw =0;
  private int maxX;
  private int maxY;
  Display(){
    super ("Concordia");
    //this.setExtendedState(JFrame.MAXIMIZED_BOTH);
    this.maxX= 1500;
    this.maxY = 1000;
    this.setSize(maxX+15, maxY);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
    draw =1;
    botPanel = new DisplayGamePanel(maxX, (int)(maxY*3.0/10.0), Color.GRAY, draw);
    draw =0;
    gamePanel = new GamePanel(maxX, maxY, Color.BLACK);
    this.setLayout(new BorderLayout());
    this.add (botPanel, BorderLayout.SOUTH);
    this.add (gamePanel, BorderLayout.NORTH);
    gamePanel.setLayout(new BorderLayout());
    draw =2;
    mapPanel = new DisplayGamePanel((int)(maxX*1.0/5.0),(int)(maxX*1.0/5.0), Color.GRAY, draw);
    draw =-1;
    rightPanel = new DisplayGamePanel((int)(maxX*1.7/5.0),(int)(maxY*1.0/20.0), Color.WHITE, draw);
    draw =0;
    gamePanel.add (mapPanel, BorderLayout.EAST);
    gamePanel.add (rightPanel, BorderLayout.WEST);
    rightPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 10));
    draw =3;
    hpPanel = new DisplayGamePanel((int)(maxX*1.5/5.0),(int)(maxX*1.5/5.0/200.0*14.0), new Color (69,218,215), draw);
    draw =4;
    expPanel = new DisplayGamePanel((int)(maxX*1.5/5.0),(int)(maxX*1.5/5.0/200.0*10.0), new Color (152,251,152), draw);
    draw =0;
    rightPanel.add (hpPanel);
    rightPanel.add (expPanel);
    this.setVisible(true);
  }
  public void start(){
    gamePanel.animate();
  }
}