import javax.swing.*;
import java.awt.*;
import java.io.FileInputStream;

class Display extends JFrame{
  //Main game
  private GamePanel gamePanel;
  private int gameState= 0;
  private boolean debugState = false;
  private int maxX, maxY;
  private double totalMem, memUsed;
  //Menu
  private CustomButton continueButton = new CustomButton("Continue");
  private CustomButton newGameButton = new CustomButton("New");
  private CustomButton loadGameButton = new CustomButton("Load");
  private CustomButton settingsButton = new CustomButton("Settings");
  private CustomButton scoreboardButton = new CustomButton("Scoreboard");
  
  private MenuPanel menuPanel;
  private MenuBGPanel menuBgPanel;  
  private JLabel title = new JLabel("CONCORDIA");
  //Debug Panel
  private DebugPanel debugPanel=  new DebugPanel (200,200);
  private JLabel frames;
  private int fps = 0;
  //Start Listener
  private StartListener menuStartListener;
  private CustomKeyListener keyListener;
  
  Display(){
    super ("Concordia");
    this.setExtendedState(JFrame.MAXIMIZED_BOTH);
    this.maxX = Toolkit.getDefaultToolkit().getScreenSize().width;
    this.maxY = Toolkit.getDefaultToolkit().getScreenSize().height;
    this.setSize(maxX, maxY);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setFocusable(true);
    //Creates keylistener object
    keyListener = new CustomKeyListener();
    this.addKeyListener(keyListener);
    
    //Creation of the basic game display
    gamePanel = new GamePanel(maxX, maxY);
    //Creation of the menu
    menuBgPanel = new MenuBGPanel(maxX, maxY);
    this.add(menuBgPanel);
    menuBgPanel.setLayout(new BorderLayout());
    menuPanel = new MenuPanel(300,100);
    title.setFont(new Font("sansserif", Font.BOLD, 72));
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
    menuBgPanel.setVisible (true);
    menuPanel.setVisible (true);
    title.setVisible(true);
    this.setVisible (true);
  }
  
  public void refreshAll(){
    if (gameState==0){ 
      menuPanel.setVisible(true);
      menuBgPanel.setVisible(true);
      title.setVisible(true);
    }else if (gameState==1){
      if (keyListener.getDebugState()){
        gamePanel.setDebugState(true, fps, totalMem, memUsed);
      }
      else{
        gamePanel.setDebugState(false, fps, totalMem, memUsed);
      }
      this.add (gamePanel);
      menuPanel.setVisible(false);
      menuBgPanel.setVisible(false);
      title.setVisible(false);
      gamePanel.refresh();
    }
  }
  
  public void getListen (){
    if (menuStartListener.getStart()){
      gameState=1;
      menuStartListener.setStart (false);
    }
  }
  public void setfps(int fps){
    this.fps = fps;
  }
  public void setMem(double totalMem, double memUsed){
    this.totalMem = totalMem;
    this.memUsed = memUsed;
  }
}