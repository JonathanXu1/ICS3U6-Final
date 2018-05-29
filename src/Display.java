import javax.swing.JLabel;
import javax.swing.JFrame;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Toolkit;

class Display extends JFrame{
  //Main game stats
  private GamePanel gamePanel;
  private int gameState= 0;
  private int maxX, maxY;
  private int[] mouseXy;
  
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
  private double totalMem, memUsed;
  private int fps = 0;
  //Start Listeners
  private StartListener menuStartListener;
  private CustomKeyListener keyListener = new CustomKeyListener();
  private CustomMouseListener mouseListener = new CustomMouseListener();
  //Game logic
  private Tile[][] map;
  
  Display(){
    super ("Concordia");
    this.setExtendedState(JFrame.MAXIMIZED_BOTH);
    this.maxX = Toolkit.getDefaultToolkit().getScreenSize().width;
    this.maxY = Toolkit.getDefaultToolkit().getScreenSize().height;
    this.setSize(maxX, maxY);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setFocusable(true);
    //Adds keylistener object
    this.addKeyListener(keyListener);
    //Creation of the basic game display
    gamePanel = new GamePanel();
    gamePanel.addMouseListener(mouseListener);
    //Creation of the menu
    menuBgPanel = new MenuBGPanel(maxX, maxY);
    menuBgPanel.setLayout(null);
    this.add(menuBgPanel);
    menuPanel = new MenuPanel(300,100);
    //menuPanel.setOpaque(false);
    menuPanel.setBounds(200, 200, 200, 300);
    title.setFont(new Font("sansserif", Font.BOLD, 72));
    title.setLocation(10,10);
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
    this.getContentPane().setBackground(Color.BLACK);
  }
  
  public void refreshAll(){
    mouseXy = mouseListener.getMouseXy();
    //Menu state
    if (gameState==0){ 
      menuPanel.setVisible(true);
      menuBgPanel.setVisible(true);
      title.setVisible(true);
      // Main game state
    }else if (gameState==1){
      if (keyListener.getDebugState()){
        gamePanel.setDebugInfo(true, fps, totalMem, memUsed, mouseXy);
      }else{
        gamePanel.setDebugInfo(false, fps, totalMem, memUsed, mouseXy);
      }
      if (gamePanel.getNewFloor()){
        gamePanel.setNewFloor(false);
        gamePanel.createMap (map);
      }
      keyListener.setAllDirection();
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
  public void setMap(Tile[][] map){
    this.map = map;
  }
  public void setFps(int fps){
    this.fps = fps;
  }
  public void setMem(double totalMem, double memUsed){
    this.totalMem = totalMem;
    this.memUsed = memUsed;
  }
}