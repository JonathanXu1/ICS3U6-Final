import javax.swing.*;
import java.awt.*;

class Display extends JFrame{
  //Main game
  private GamePanel gamePanel;
  private int gameState= 0;
  private int maxX;
  private int maxY;
  //Menu
  private CustomButton continueButton = new CustomButton("Continue");
  private CustomButton newGameButton = new CustomButton("New");
  private CustomButton loadGameButton = new CustomButton("Load");
  private CustomButton settingsButton = new CustomButton("Settings");
  private CustomButton scoreboardButton = new CustomButton("Scoreboard");
  
  private JLabel title = new JLabel("CONCORDIA");
  private MenuPanel menuPanel;
  private MenuBGPanel menuBgPanel;
  private StartListener menuStartListener;
  
  Display(){
    super ("Concordia");
    this.setExtendedState(JFrame.MAXIMIZED_BOTH);
    this.maxX = Toolkit.getDefaultToolkit().getScreenSize().width;
    this.maxY = Toolkit.getDefaultToolkit().getScreenSize().height;
    this.setSize(maxX, maxY);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setVisible (true);
    //Creation of the basic game display
    gamePanel = new GamePanel(maxX, maxY);
      this.add (gamePanel);
    //Creation of the menu
    menuBgPanel = new MenuBGPanel(maxX, maxY);
    this.add(menuBgPanel);
    menuBgPanel.setLayout(new BorderLayout());
    menuPanel = new MenuPanel(300,100);
    menuBgPanel.add(title, BorderLayout.NORTH);
    menuBgPanel.add(menuPanel, BorderLayout.WEST);
    menuPanel.setLayout(new GridLayout(5, 1));
    menuPanel.add(continueButton);
    menuPanel.add(newGameButton);
    menuPanel.add(loadGameButton);
    menuPanel.add(settingsButton);
    menuPanel.add(scoreboardButton);
    menuStartListener = new StartListener();
    continueButton.addActionListener(menuStartListener);
    //Necessary to start with
    menuPanel.setVisible (true);
  }
  public void refreshAll(){
    if (gameState==0){ 
      menuPanel.setVisible(true);
      menuBgPanel.setVisible(true);
      title.setVisible(true);
    }else if (gameState==1){
      this.add (gamePanel);
      menuPanel.setVisible(false);
      menuBgPanel.setVisible(false);
      title.setVisible(false);
      gamePanel.setVisible(true);
      gamePanel.refresh();
    }
  }
  public void getListen (){
    if (menuStartListener.getStart()){
      gameState=1;
      menuStartListener.setStart (false);
    }
  } 
}