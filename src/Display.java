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
  private CustomButton continueButton = new CustomButton("Continue", 0);
  private CustomButton newGameButton = new CustomButton("New", 0);
  private CustomButton loadGameButton = new CustomButton("Load", 0);
  private CustomButton settingsButton = new CustomButton("Settings", 0);
  private CustomButton scoreboardButton = new CustomButton("Scoreboard", 0);
  private CustomButton quitButton = new CustomButton("Quit", 0);
  private MenuPanel menuPanel;
  private MenuBGPanel menuBgPanel;  
  private JLabel title = new JLabel("CONCORDIA");
  //Debug
  private double totalMem, memUsed;
  private int fps = 0;
  //Start Listeners
  private StartListener menuStartListener, menuQuitListener;
  private CustomKeyListener keyListener = new CustomKeyListener();
  private CustomMouseListener mouseListener = new CustomMouseListener();
  private CustomMouseListener continueButtonMouse = new CustomMouseListener();
  private CustomMouseListener newGameButtonMouse = new CustomMouseListener();
  private CustomMouseListener loadGameButtonMouse = new CustomMouseListener();
  private CustomMouseListener settingsButtonMouse = new CustomMouseListener();
  private CustomMouseListener scoreboardButtonMouse = new CustomMouseListener();
  private CustomMouseListener quitButtonMouse = new CustomMouseListener();
  
  //Game logic
  private Tile[][] map;
  private int characterX;
  private int characterY;
  
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
    menuStartListener = new StartListener();
    menuQuitListener = new StartListener();
    menuBgPanel = new MenuBGPanel(maxX, maxY);
        
    //Menu panel and buttons
    menuPanel = new MenuPanel(50, maxY/2, 300, 220);
    title.setFont(new Font("sansserif", Font.BOLD, 72));
    title.setBounds(10, 10, 500, 300);
    continueButton.addMouseListener(continueButtonMouse);
    newGameButton.addMouseListener(newGameButtonMouse);
    loadGameButton.addMouseListener(loadGameButtonMouse);
    settingsButton.addMouseListener(settingsButtonMouse);
    scoreboardButton.addMouseListener(scoreboardButtonMouse);
    quitButton.addMouseListener(quitButtonMouse);
    continueButton.addActionListener(menuStartListener);
    quitButton.addActionListener(menuQuitListener);
    
    //Adds everything
    menuPanel.add(continueButton);
    menuPanel.add(newGameButton);
    menuPanel.add(loadGameButton);
    menuPanel.add(settingsButton);
    menuPanel.add(scoreboardButton);
    menuPanel.add(quitButton);
    menuBgPanel.add(menuPanel);
    menuBgPanel.add(title);
    this.add(menuBgPanel);
    
    //Necessary to start with
    this.setVisible (true);
    this.getContentPane().setBackground(Color.BLACK);
  }
  
  public void refreshAll(){
    mouseXy = mouseListener.getMouseXy();
    //Menu state
    if (gameState==0){
      continueButton.setOpaque(true);
      newGameButton.setOpaque(true);
      loadGameButton.setOpaque(true);
      settingsButton.setOpaque(true);
      scoreboardButton.setOpaque(true);
      quitButton.setOpaque(true);
      
      if(continueButtonMouse.getHover()){
        continueButton.setOpaque(false);
      } else if(newGameButtonMouse.getHover()){
        newGameButton.setOpaque(false);
      } else if(loadGameButtonMouse.getHover()){
        loadGameButton.setOpaque(false);
      } else if(settingsButtonMouse.getHover()){
        settingsButton.setOpaque(false);
      } else if(scoreboardButtonMouse.getHover()){
        scoreboardButton.setOpaque(false);
      } else if(quitButtonMouse.getHover()){
        quitButton.setOpaque(false);
      }
      // Main game state
    }else if (gameState==1){
      if (keyListener.getDebugState()){
        gamePanel.setDebugInfo(true, fps, totalMem, memUsed, mouseListener);
      }else{
        gamePanel.setDebugInfo(false, fps, totalMem, memUsed, mouseListener);
      }
      if (gamePanel.getNewFloor()){
        gamePanel.setNewFloor(false);
        gamePanel.createMap (map, characterX, characterY);
      }
      keyListener.setAllDirection();
      this.add(gamePanel);
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
    } else if (menuQuitListener.getStart()){
      System.exit(0);
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
  public void setSetPlayerLocation (int characterX, int characterY){
    this.characterX = characterX;
    this.characterY = characterY;
  }
}