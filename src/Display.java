import javax.swing.JLabel;
import javax.swing.JFrame;
import java.awt.Font;
import java.awt.Toolkit;
import java.io.File;
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
  private MenuPanel menuPanel;
  private MenuBGPanel menuBgPanel;
  private JLabel mainTitle = new JLabel("CONCORDIA");
  private JLabel settingsTitle = new JLabel("Settings");
  private CustomMouseListener mouseListener = new CustomMouseListener();
  //private CustomMouseListener continueButtonMouse = new CustomMouseListener();
  private CustomMouseListener newGameButtonMouse = new CustomMouseListener();
  //private CustomMouseListener loadGameButtonMouse = new CustomMouseListener();
  private CustomMouseListener settingsButtonMouse = new CustomMouseListener();
  //private CustomMouseListener scoreboardButtonMouse = new CustomMouseListener();
  private CustomMouseListener quitButtonMouse = new CustomMouseListener();  
  //private CustomButton continueButton = new CustomButton("Continue",continueButtonMouse);
  private CustomButton newGameButton = new CustomButton("New",newGameButtonMouse);
  //private CustomButton loadGameButton = new CustomButton("Load",loadGameButtonMouse);
  private CustomButton settingsButton = new CustomButton("Settings",settingsButtonMouse);
  //private CustomButton scoreboardButton = new CustomButton("Scoreboard",scoreboardButtonMouse);
  private CustomButton quitButton = new CustomButton("Quit",quitButtonMouse);
  //Settings panel
  private SettingsPanel settingsPanel;
  private CustomMouseListener backButtonMouse = new CustomMouseListener();
  private CustomButton backButton = new CustomButton("Back",backButtonMouse);
  //Debug
  private double totalMem, memUsed;
  private int fps = 0;
  //Game logic
  private Tile[][] map;
  private int playerStartingX, playerStartingY, playerFinishingX,playerFinishingY, bossX, bossY;
  //Fonts
  Font customTitle;
  
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
    } catch (IOException e) {
      e.printStackTrace();
    } catch(FontFormatException e) {
      e.printStackTrace();
    }
  
    //Adds keylistener object
    
    //Creation of the menu
    menuBgPanel = new MenuBGPanel(maxX, maxY);
    mainTitle.setFont(customTitle);
    mainTitle.setForeground(Color.WHITE);
    mainTitle.setBounds(50, -50, 800, 300);
    
    menuPanel = new MenuPanel(50, maxY/2, 450, 220);
    //menuPanel.add(continueButton);
    menuPanel.add(newGameButton);
    //menuPanel.add(loadGameButton);
    menuPanel.add(settingsButton);
    //menuPanel.add(scoreboardButton);
    menuPanel.add(quitButton);
    menuBgPanel.add(menuPanel);
    menuBgPanel.add(mainTitle);
    
    //Creation of the settings panel
    settingsPanel = new SettingsPanel(maxX, maxY);    
    backButton.setBounds(maxX/2-100, maxY - 200, 220, 50);
    settingsPanel.add(backButton);
    settingsPanel.add(settingsTitle);
    
    //Creation of the basic game display
    gamePanel = new GamePanel();
    gamePanel.addMouseListener(mouseListener);
    
    this.add(menuBgPanel);
    this.setVisible (true);
  }
  
  public void refreshAll(){
    if (gameState==0){
      //Menu state
      //Setting content area is more effective than setting opacity
      //continueButton.updateStyle(0);
      newGameButton.updateStyle(0);
      //loadGameButton.updateStyle(0);
      settingsButton.updateStyle(0);
      //scoreboardButton.updateStyle(0);
      quitButton.updateStyle(0);
      /*
      if(continueButtonMouse.getHover()){
        continueButton.updateStyle(1);
      } else*/ if(newGameButtonMouse.getHover()){
        newGameButton.updateStyle(1);
      } /*else if(loadGameButtonMouse.getHover()){
        loadGameButton.updateStyle(1);
      } */else if(settingsButtonMouse.getHover()){
        settingsButton.updateStyle(1);
      } /*else if(scoreboardButtonMouse.getHover()){
        scoreboardButton.updateStyle(1);
      } */else if(quitButtonMouse.getHover()){
        quitButton.updateStyle(1);
      }//Custy and redundant might have to remove
      menuBgPanel.refresh();
      // Main game state
    }else if (gameState==1){ //New game
      gamePanel.setDebugInfo(fps, totalMem, memUsed, getSettings()[1]);
      if (gamePanel.getNewFloor()){
        gamePanel.setNewFloor(false);
        gamePanel.initial (map, playerStartingX, playerStartingY, playerFinishingX, playerFinishingY);
      }
      //Only refreshes once, keep this seperate from the line above
      if (addGamePanel){
        addGamePanel = false;
        this.add(gamePanel);
        closeAll();
        gamePanel.setVisible (true);
      }
      gamePanel.repaint();
    } /*else if (gameState == 2){ //continue
      
    } else if (gameState == 3){ //Load
    } */else if (gameState == 4){ //Settings
      settingsPanel.repaint();
    } /*else if (gameState == 5){ //Scoreboard
    } */else if (gameState == 6){ //Quit
      System.exit(0);
    }
  }
  //Determines if the game has begun
  public void getListen (){
    /*if (continueButtonMouse.getPressed()){
      gameState = 2
    } else */if (newGameButtonMouse.getPressed()){
      gameState=1;
      addGamePanel =true;
    } /*else if (loadGameButtonMouse.getPressed()){
      gameState=3;
    } */else if (settingsButtonMouse.getPressed()){
      gameState=4;
      this.add(settingsPanel);
      closeAll();
      settingsPanel.setVisible (true);
      settingsTitle.setVisible(true);
      backButton.setVisible(true);
    } /*else if (scoreboardButtonMouse.getPressed()){
      gameState=5;
    } */else if (quitButtonMouse.getPressed()){
      gameState = 6;
    } else if (backButtonMouse.getPressed()){
      gameState = 0;
      closeAll();
      menuBgPanel.setVisible (true);
    } else if (gamePanel.returnGameOver()) {
      gameState = 6;
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
  public void setPlayerLocation (int playerStartingX, int playerStartingY, int playerFinishingX, int playerFinishingY){
    this.playerStartingX = playerStartingX;
    this.playerStartingY = playerStartingY;
    this.playerFinishingX = playerFinishingX;
    this.playerFinishingY = playerFinishingY;
  }
  public void setBossLocation (int bossX, int bossY){
    this.bossX = bossX;
    this.bossY=bossY;
  }
  public void closeAll(){
    gamePanel.setVisible (false);
    menuBgPanel.setVisible(false);
    settingsPanel.setVisible(false);
  }
  public GamePanel getPanel(){
    return(gamePanel);
  }
  public boolean getNewMap(){
    if (gamePanel.getAnotherMap()){
      return (true);
    }else{
      return (false);
    }
  }
  public void setGameMap(){
    gamePanel.createMap(map,playerStartingX,playerStartingY,playerFinishingX,playerFinishingY);
  }
  
  public int[] getSettings(){
    return settingsPanel.getSettings();
  }
  
  public void setBossCoords(){
    gamePanel.setBoss(bossX,bossY);
  }
  
  public int getLevel(){
    return (gamePanel.getFloor());
  }
}