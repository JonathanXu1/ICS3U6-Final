import javax.swing.JLabel;
import javax.swing.JFrame;
import java.awt.Font;
import java.awt.Toolkit;
import java.io.File;
import java.awt.GraphicsEnvironment;
import java.awt.FontFormatException;
import java.awt.Color;
import java.io.IOException;

class Display extends JFrame{
  private GamePanel gamePanel;
  //Game state controls what is shown on the screen. 0 is for the menu, 1 is for the game
  private int gameState= 0;
  private boolean addGamePanel = false;
  private int maxX, maxY;
  //Menu
  //Buttons
  private CustomButton continueButton = new CustomButton("Continue", 0);
  private CustomButton newGameButton = new CustomButton("New", 0);
  private CustomButton loadGameButton = new CustomButton("Load", 0);
  private CustomButton settingsButton = new CustomButton("Settings", 0);
  private CustomButton scoreboardButton = new CustomButton("Scoreboard", 0);
  private CustomButton quitButton = new CustomButton("Quit", 0);
  //The components of the menu
  private MenuPanel menuPanel;
  private MenuBGPanel menuBgPanel;
  private JLabel title = new JLabel("CONCORDIA");
  //Debug
  private double totalMem, memUsed;
  private int fps = 0;
  //Start Listeners
  private CustomMouseListener mouseListener = new CustomMouseListener();
  private CustomMouseListener continueButtonMouse = new CustomMouseListener();
  private CustomMouseListener newGameButtonMouse = new CustomMouseListener();
  private CustomMouseListener loadGameButtonMouse = new CustomMouseListener();
  private CustomMouseListener settingsButtonMouse = new CustomMouseListener();
  private CustomMouseListener scoreboardButtonMouse = new CustomMouseListener();
  private CustomMouseListener quitButtonMouse = new CustomMouseListener();  
  //Game logic
  private Tile[][] map;
  private int playerStartingX;
  private int playerStartingY;
  //Fonts
  Font customTitle, customHeader;
  
  Display(){
    super ("Concordia");
    this.setExtendedState(JFrame.MAXIMIZED_BOTH);
    this.maxX = Toolkit.getDefaultToolkit().getScreenSize().width;
    this.maxY = Toolkit.getDefaultToolkit().getScreenSize().height;
    this.setSize(maxX, maxY);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setFocusable(true);
    
    //Adds fonts
    try {
      customTitle = Font.createFont(Font.TRUETYPE_FONT, new File("../res/fonts/spaceage.ttf")).deriveFont(80f);
      customHeader = Font.createFont(Font.TRUETYPE_FONT, new File("../res/fonts/spaceage.ttf")).deriveFont(32f);
    } catch (IOException e) {
      e.printStackTrace();
    } catch(FontFormatException e) {
      e.printStackTrace();
    }
  
    //Adds keylistener object
    
    //Creation of the basic game display
    gamePanel = new GamePanel();
    gamePanel.addMouseListener(mouseListener);
    
    //Creation of the menu
    menuBgPanel = new MenuBGPanel(maxX, maxY);
    
    //Title
    title.setFont(customTitle);
    title.setForeground(Color.WHITE);
    title.setBounds(50, -50, 800, 300);
    
    //Menu panel and buttons
    menuPanel = new MenuPanel(50, maxY/2, 300, 220);
    menuPanel.setFont(customHeader);
    
    continueButton.addMouseListener(continueButtonMouse);
    newGameButton.addMouseListener(newGameButtonMouse);
    loadGameButton.addMouseListener(loadGameButtonMouse);
    settingsButton.addMouseListener(settingsButtonMouse);
    scoreboardButton.addMouseListener(scoreboardButtonMouse);
    quitButton.addMouseListener(quitButtonMouse);
    
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
    this.setVisible (true);
  }
  
  public void refreshAll(){
    //Menu state
    //Setting content area is more effective than setting opacity
    continueButton.setContentAreaFilled(false);
    newGameButton.setContentAreaFilled(false);
    loadGameButton.setContentAreaFilled(false);
    settingsButton.setContentAreaFilled(false);
    scoreboardButton.setContentAreaFilled(false);
    quitButton.setContentAreaFilled(false);
    if (gameState==0){
      if(continueButtonMouse.getHover()){
        continueButton.setContentAreaFilled(true);
      } else if(newGameButtonMouse.getHover()){
        newGameButton.setContentAreaFilled(true);
      } else if(loadGameButtonMouse.getHover()){
        loadGameButton.setContentAreaFilled(true);
      } else if(settingsButtonMouse.getHover()){
        settingsButton.setContentAreaFilled(true);
      } else if(scoreboardButtonMouse.getHover()){
        scoreboardButton.setContentAreaFilled(true);
      } else if(quitButtonMouse.getHover()){
        quitButton.setContentAreaFilled(true);
      }//Custy and redundant might have to remove
      menuPanel.add(continueButton);
      menuPanel.add(newGameButton);
      menuPanel.add(loadGameButton);
      menuPanel.add(settingsButton);
      menuPanel.add(scoreboardButton);
      menuPanel.add(quitButton);
      menuBgPanel.add(menuPanel);
      menuBgPanel.add(title);
      menuBgPanel.refresh();
      // Main game state
    }else if (gameState==1){
      gamePanel.setDebugInfo(fps, totalMem, memUsed);
      if (gamePanel.getNewFloor()){
        gamePanel.setNewFloor(false);
        gamePanel.createMap (map, playerStartingX, playerStartingY);
      }
      //Only refreshes once, keep this seperate from the line above
      if (addGamePanel){
        addGamePanel = false;
        this.add(gamePanel);
        gamePanel.setVisible (true);
        menuPanel.setVisible(false);
        menuBgPanel.setVisible(false);
        title.setVisible(false);
      }
      gamePanel.refresh();
    } else if (gameState == 2){
    } else if (gameState == 3) {
      System.exit(0);
    }
  }
  //Determines if the game has begun
  public void getListen (){
    if (continueButtonMouse.getPressed()){
      gameState=1;
      addGamePanel =true;
    } else if (newGameButtonMouse.getPressed()){
      gameState=1;
      addGamePanel =true;
    } else if (loadGameButtonMouse.getPressed()){
    } else if (settingsButtonMouse.getPressed()){
    } else if (scoreboardButtonMouse.getPressed()){
    } else if (quitButtonMouse.getPressed()){
      gameState = 3;
    } else if (gamePanel.returnGameOver()) {
      gameState = 3;
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
  public void setPlayerLocation (int playerStartingX, int playerStartingY){
    this.playerStartingX = playerStartingX;
    this.playerStartingY = playerStartingY;
  }
}