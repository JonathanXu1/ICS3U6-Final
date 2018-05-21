import javax.swing.*;
import java.awt.*;

class Display extends JFrame{
  private DisplayPanel mediumPanel;
  private DisplayPanel botPanel;
  private DisplayPanel rightPanel;
  private DisplayPanel gamePanel;
  private DisplayPanel mapPanel;
  private DisplayPanel hpPanel;
  private DisplayPanel expPanel;
  private DisplayPanel accessoryPanel;
  private int polygon =0;
  private int maxX;
  private int maxY;
  Display(){
    super ("Concordia");
    //this.setExtendedState(JFrame.MAXIMIZED_BOTH);
    this.maxX= 1500;
    this.maxY = 1000;
    this.setSize(maxX+15, maxY);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
    polygon =1;
    botPanel = new DisplayPanel(maxX, (int)(maxY*2.0/10.0), Color.GRAY, polygon);
    polygon =0;
    gamePanel = new DisplayPanel(maxX, maxY, Color.WHITE, polygon);
    this.setLayout(new BorderLayout());
    this.add (botPanel, BorderLayout.SOUTH);
    this.add (gamePanel, BorderLayout.NORTH);
    gamePanel.setLayout(new BorderLayout());
    polygon =2;
    mapPanel = new DisplayPanel((int)(maxX*1.0/5.0),(int)(maxX*1.0/5.0), Color.GRAY, polygon);
    polygon =0;
    rightPanel = new DisplayPanel((int)(maxX*1.5/5.0),(int)(maxY*1.0/20.0), Color.WHITE, polygon);
    gamePanel.add (mapPanel, BorderLayout.EAST);
    gamePanel.add (rightPanel, BorderLayout.WEST);
    rightPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
    polygon =3;
    hpPanel = new DisplayPanel((int)(maxX*1.5/5.0),(int)(maxY*1.0/20.0), Color.RED, polygon);
    polygon =4;
    expPanel = new DisplayPanel((int)(maxX*1.0/4.0),(int)(maxY*1.0/30.0), new Color (  152,251,152), polygon);
    polygon =0;
    rightPanel.add (hpPanel);
    rightPanel.add (expPanel);
    this.setVisible(true);
  }
  public void start(){
    gamePanel.animate();
    botPanel.animate();
    mapPanel.animate();
  }
}