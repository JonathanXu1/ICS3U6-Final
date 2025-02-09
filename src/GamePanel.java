/*
 * [GamePanel.java];
 * 
 * The main game frame, much of the logic, listening, and graphics are done here
 * 
 * Developed by: Will, Artem, Jonathan
 * 
 */ 
//Imports
/////////////////////
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.Graphics2D;
import java.awt.BasicStroke;

class GamePanel extends JPanel{
  //Debug
  private int stringLength;
  private String fps;
  private Font menuFont = new Font("Courier New", Font.PLAIN, 20);
  private double totalMem, memUsed, memPercent;
  private String debugMessage = "NULL";
  private int difficulty = 2;
  
  //Listeners
  private int [] mouseXy;
  private CustomKeyListener keyListener = new CustomKeyListener();
  private CustomMouseListener mouseListener = new CustomMouseListener();
  private boolean movementRestriction;
  
  //Images
  private Image left, leftClickedPlus, leftClickedMinus, right, rightClicked, exp, hp, hotbar,mapBorder, inventoryImage, lightningStatus, flameStatus, freezeStatus, hungry, starving,hotbarBelow;
  private final double Y_TO_X = 90.0/75.0;
  private final double INVENTORY_MOD = 110.0/75.0;
  private final double Y_TO_X_HOT = 778.0/135.0;
  private final double BOT_HEIGHT = 250.0;
  private boolean inventoryOpen =false;
  private int minButtonX;
  private int maxButtonX;
  private int minButtonY;
  private int maxButtonY;
  
  //Turn tracking
  private boolean pauseState =false;
  private int hungerCount = 0;
  
  //Sizes
  private int maxX = 0, maxY = 0;
  private int minimapFactor = 20;
  private final int TILE_SIZE= 100;
  
  //Tiling and maps
  private Tile[][] map;
  private int minimapX, minimapY, minimapArrayX, minimapArrayY; //minimapX = minimapY, may remove one later
  private boolean newFloor = true;
  private Background bg= new Background (TILE_SIZE);
  private boolean tiling = false;
  
  //Coordinate control
  private int playerStartingX, playerStartingY,playerCurrentX, playerCurrentY, playerFinishingX, playerFinishingY;
  private boolean [] blocked = new boolean [4];
  private int [] xyDirection = new int [2];
  private boolean alternateState;
  
  //Enemy control
  private Entity [][] entityMap;
  private int spawnX;
  private int spawnY;
  private int mobCap;
  private int mobCount =0;
  private int directionRand;
  private int entityArrayXMod = 0;
  private int entityArrayYMod = 0;
  private double closestPath;
  private double [] pathfinderDistance = new double [5];
  private int closestDirection;
  private int []pathfinderPriority = new int [5];
  private boolean acceptableSpawn = false;
  private int bossX;
  private int bossY;
  
  // Fire control
  private FireController playerFireController;
  private boolean collided = true;
  private int translateX = 0;
  private int translateY = 0;
  
  //Inventory and items
  private Item [][] itemMap;
  private Inventory inventory = new Inventory();
  private int xInvPixel;
  private int yInvPixel;
  private boolean itemSelected = false;
  private int minSelectX;
  private int maxSelectX;
  private int  minSelectY;
  private int maxSelectY;
  private int[] selectedItemPosition = new int [2];
  private int itemCount = 0;
  private boolean itemPickup = false;
  private int turnTransitionCounter  =0;
  private boolean turnPasser = false;
  private int itemRarity;
  private int driveNumber;
  private boolean pendingUpgrade;
  private int driveArrayX;
  private int driveArrayY;     
  private int targetX=0;
  private int targetY=0;
  private int playerLevel =1;
  private boolean anotherMap =false;
  //Attacking
  private int [] tileSelectedArray = new int [2];  
  private boolean meleeSelected  = true;
  //Game over control
  private boolean gameOver;
  private boolean weaponState = true;
  private boolean attacked;
  private int floorLevel =0;
  private int loadingCount;
  private boolean loading;
  //Constructor
  GamePanel(){
    //Adds the listeners
    this.addKeyListener(keyListener);
    this.addMouseListener(mouseListener);
    //Initializes the player
    //Initializes images
    this.left = Toolkit.getDefaultToolkit().getImage("../res/MetalL.png");
    this.leftClickedPlus = Toolkit.getDefaultToolkit().getImage("../res/MetalC+.png");
    this.leftClickedMinus = Toolkit.getDefaultToolkit().getImage("../res/MetalC-.png");
    this.right = Toolkit.getDefaultToolkit().getImage("../res/MetalR.png");
    this.rightClicked = Toolkit.getDefaultToolkit().getImage ("../res/MetalRClicked.png");
    this.exp = Toolkit.getDefaultToolkit().getImage("../res/ExpBar.png");
    this.hp = Toolkit.getDefaultToolkit().getImage("../res/HpBar.png");
    this.hotbar = Toolkit.getDefaultToolkit().getImage("../res/Hotbar.png");
    this.hotbarBelow = Toolkit.getDefaultToolkit().getImage("../res/HotbarBelow.png");
    this.mapBorder = Toolkit.getDefaultToolkit().getImage("../res/MapNoBorder.png"); //duplicate name
    this.inventoryImage = Toolkit.getDefaultToolkit().getImage("../res/Inventory.png");
    this.freezeStatus = Toolkit.getDefaultToolkit().getImage("../res/FreezeStatus.png");
    this.flameStatus = Toolkit.getDefaultToolkit().getImage("../res/FlameStatus.png");
    this.lightningStatus = Toolkit.getDefaultToolkit().getImage("../res/LightningStatus.png");
    this.hungry = Toolkit.getDefaultToolkit().getImage("../res/Hungry.png");
    this.starving = Toolkit.getDefaultToolkit().getImage("../res/Starving.png");
    //Initializes minimap size
    this.minimapX = (int)(BOT_HEIGHT);
    this.minimapY = (int)(BOT_HEIGHT);
    this.gameOver = false;

    //Sets mob cap based on settings
    mobCap = 40;
  }
  
  //Methods that are inherited from JPanel
  @Override
  /**
   *paintComponent
   *Draws all the seperate components, along with updating and moving according to all the listeners
   *@param: Graphics g
   *@return: 
   */
    public void paintComponent(Graphics g){
    super.paintComponent(g);
    //Required to have focus so that the listeners work
    this.requestFocusInWindow();
    setDoubleBuffered(true);
    if (maxX == 0){
      this.maxX= (int)this.getSize().getWidth();
      this.maxY =(int)this.getSize().getHeight();
      this.setPreferredSize(this.getSize());
      playerFireController = new FireController(maxX, maxY, maxX/2, maxY/2);
    }
    
    //Changes settings if changed
    if(difficulty== 1){ //Easy
      mobCap = 20;
    } else if(difficulty == 2){ //Medium
      mobCap = 40;
    } else if(difficulty == 3){ //Hard
      mobCap = 80;
    }
    //Checks for which entities are killed, including the player
    checkKilled(0,0);
    if (!gameOver) {
      //Draw map (background)
      drawMap(g);
      if (!(loading)){
        if ((collided)){
          updateListeners();
          determineTiling(); 
        }
      }
      if (!(mouseListener.getPressed())){
        alternateState = true;
        weaponState = true;
      }
      refreshStats();
      //Checks broken gear
      checkBroken();
      //Draws bullet sprites
      if (!(meleeSelected)){
        drawBullets (g, playerFireController);
      }
      //Checks for which entities are killed again so that dead entities cannot kill the player
      checkKilled(0,0);
      if (entityMap[playerCurrentY][playerCurrentX] instanceof Character){
        //Draws the items
        drawItems (g);
        //Draws the entities
        drawAllEntity (g);
        //Draw the health and exp
        drawBars(g);
        //Draw the game components
        drawGameComponents(g);
        //Draws the minimap
        drawMinimap(g);
        //Draw inventory
        drawInventory(g);
      }
      //Draw the debugPanel
      if (keyListener.getDebugState()){
        drawDebugPanel(g);
        g.setColor(Color.RED);
        g.fillRect(maxX/2, maxY/2, 2, 2);
      }
      this.setVisible(true);
    }else{
      System.out.println("You suck!");    
    }
    if (loadingCount>=1){
      g.setColor(new Color(0,0,0,(int)(255.0*((double)loadingCount/100.0))));
      g.fillRect(0,0,maxX,maxY);
      loadingCount--;
      keyListener.setToZero();
    }else if(loadingCount==0){
      keyListener.setToZero();
      loading = false;
    }
  }
  //End of methods that are inherited from JPanel
  /**
   *returnGameOver
   *Returns whether or not the game has ended
   *@param: 
   *@return: A boolean
   */
  public boolean returnGameOver() {
    return this.gameOver;
  }
  /**
   *drawDebugPanel 
   *Draws the debug panel, which is activated by f1
   *@param: Graphics g
   *@return: 
   */
  //Drawing methods, which were split up for legibility
  public void drawDebugPanel (Graphics g){
    //Debug panel, for personal use and testing
    g.setColor(new Color(80, 80, 80, 127)); //Translucent grey
    stringLength = ("FPS: "+fps).length();
    g.fillRect(30, 15, 12*stringLength, 20);
    stringLength = ("Memory Usage: " + String.format("%.2f", memPercent) + "% (" + String.format("%.2f", memUsed) + "MB out of " + String.format("%.2f", totalMem) + "MB)").length();
    g.fillRect(maxX - 600, 15, 12*stringLength, 20);
    stringLength = ("Mouse Click: " + " " + Integer.toString(mouseXy[0]) + "x " + Integer.toString(mouseXy[1])  + " y").length();
    g.fillRect(maxX-300, 45, 12*stringLength, 20);
    stringLength = ("Debug Message: " + debugMessage).length();
    g.fillRect(maxX-600, 75, 12*stringLength, 20);
    g.setColor(Color.WHITE);
    g.setFont (menuFont);
    g.drawString("FPS: " + fps, 30, 30);
    g.drawString("Memory Usage: " + String.format("%.2f", memPercent) + "% (" + String.format("%.2f", memUsed) + "MB out of " + String.format("%.2f", totalMem) + "MB)", maxX-600, 30);
    g.drawString("Mouse Click: " + " " + Integer.toString(mouseXy[0]) + "x " + Integer.toString(mouseXy[1]) + " y", maxX-300, 60);
    g.drawString("Debug Message: " + debugMessage, maxX-600, 90);
  }
  /**
   *drawGameComponents
   *Draws the basic game overlay (inventory, hotbar, bars for hp/exp)
   *@param: Graphics g
   *@return: 
   */
  public void drawGameComponents(Graphics g){
    //Sees if the mouse is clicking the skip turn button
    //Bottom toolbar
    g.drawImage(left,0,maxY-(int)(BOT_HEIGHT),(int)(BOT_HEIGHT*Y_TO_X),(int)(BOT_HEIGHT),this);
    if (inventoryOpen){
      g.drawImage(rightClicked,maxX-(int)(BOT_HEIGHT*Y_TO_X),maxY-(int)(BOT_HEIGHT), (int)(BOT_HEIGHT*Y_TO_X), (int)(BOT_HEIGHT),this);
    }else{
      g.drawImage(right,maxX-(int)(BOT_HEIGHT*Y_TO_X),maxY-(int)(BOT_HEIGHT), (int)(BOT_HEIGHT*Y_TO_X), (int)(BOT_HEIGHT),this);
    }
    g.drawImage(hotbarBelow,maxX/2-350,maxY-128,700,128,this);
    for (int j=0;j<6;j++){
      xInvPixel =maxX/2-350+13+(j*114);
      yInvPixel = maxY-114;
      g.setColor(new Color(54,53,53));
      if (inventory.getItem(j,3) instanceof Item){
        if ((j==0)||(j==1)||(j==2)){
          g.fillRect (xInvPixel, yInvPixel,  (int)(BOT_HEIGHT/2.0*Y_TO_X_HOT*113.0/778.0), (int)(BOT_HEIGHT/2.0*Y_TO_X_HOT*113.0/778.0));
        }
      }
      minButtonX = xInvPixel;
      maxButtonX= xInvPixel+101;
      minButtonY = yInvPixel;
      maxButtonY = yInvPixel+101;
      if (j==2){
        if (mouseListener.getPressed()){
          if((mouseListener.getMouseXy()[0] > minButtonX)&&(mouseListener.getMouseXy()[0] < maxButtonX)&&(mouseListener.getMouseXy()[1] >minButtonY)&&(mouseListener.getMouseXy()[1] <maxButtonY)&&(weaponState)){
            weaponState = false;
            meleeSelected  = true;
          }
        }
      }else if (j==0){
        if (mouseListener.getPressed()){
          if((mouseListener.getMouseXy()[0] > minButtonX)&&(mouseListener.getMouseXy()[0] < maxButtonX)&&(mouseListener.getMouseXy()[1] >minButtonY)&&(mouseListener.getMouseXy()[1] <maxButtonY)&&(weaponState)){
            weaponState = false;
            meleeSelected  = false;
          }
        }
      }
      g.setColor(new Color(255,255,255,100));
      if ((meleeSelected)&&(j==2)){
        g.fillRect (xInvPixel, yInvPixel, 105,105);
      }else if ((!(meleeSelected))&&(j==0)){
        g.fillRect (xInvPixel, yInvPixel, 105,105);
      }
      if (inventory.getItem(j,3) instanceof Item){
        inventory.drawSelectedItem(g,j,3, xInvPixel, yInvPixel, (int)(BOT_HEIGHT/2.0*Y_TO_X_HOT*113.0/778.0), (int)(BOT_HEIGHT/2.0*Y_TO_X_HOT*113.0/778.0), this);
      }
    }
    g.drawImage(hotbar,maxX/2-350,maxY-188,700,188,this);
    g.setColor(Color.WHITE);
    g.setFont (new Font("Consolas", Font.PLAIN, 12));
    g.drawString("PLAYER LEVEL:"+playerLevel, maxX/2-329, maxY-145);
    g.drawString("EXP:"+((Character)entityMap[playerCurrentY][playerCurrentX]).getXp()+"/"+((Character)entityMap[playerCurrentY][playerCurrentX]).getXpCap(), maxX/2-329, maxY-160);
    //Put in exp
    if (entityMap[playerCurrentY][playerCurrentX] instanceof Character){
      g.drawString("HEALTH:"+entityMap[playerCurrentY][playerCurrentX].getHealth()+"/"+entityMap[playerCurrentY][playerCurrentX].getHealthCap(), maxX/2-60, maxY-145);     
      g.drawString("DEFENSE:"+entityMap[playerCurrentY][playerCurrentX].getArmor(), maxX/2-60, maxY-160);
      g.drawString("HUNGER:"+((Character)(entityMap[playerCurrentY][playerCurrentX])).getHunger()+"/200", maxX/2+200, maxY-145);
      g.drawString("FLOOR LEVEL:"+(floorLevel+1), maxX/2+200, maxY-160);
    }
    //Hp and exp bars
    g.drawImage(hp,10,10, (int)(maxX*0.2),  (int)(maxX*0.2/200.0*14.0),this);
    g.drawImage(exp,10,15+ (int)(maxX*0.2/200.0*14.0),(int)(maxX*0.2), (int)(maxX*0.2/200.0*10.0),this);
    ///Draw in all the button click shading
    if (!(inventoryOpen)){
      if (mouseListener.getPressed()){
        if (!(pauseState)){
          if ((mouseListener.getMouseXy()[0] > 253)&&(mouseListener.getMouseXy()[0] < 287)&&(mouseListener.getMouseXy()[1] > maxY-240)&&(mouseListener.getMouseXy()[1] < maxY-130)){
            g.drawImage(leftClickedPlus,0,maxY-(int)(BOT_HEIGHT),(int)(BOT_HEIGHT*Y_TO_X),(int)(BOT_HEIGHT),this);
          }else if ((mouseListener.getMouseXy()[0] > 253)&&(mouseListener.getMouseXy()[0] < 287)&&(mouseListener.getMouseXy()[1] > maxY-120)&&(mouseListener.getMouseXy()[1] < maxY-10)){
            g.drawImage(leftClickedMinus,0,maxY-(int)(BOT_HEIGHT),(int)(BOT_HEIGHT*Y_TO_X),(int)(BOT_HEIGHT),this);
          }
          ///Shade in the buttons that are pressed when waiting
          minButtonX = maxX-142;
          maxButtonX= maxX-22;
          minButtonY = maxY-120;
          maxButtonY = maxY-28;
          if ((mouseListener.getMouseXy()[0] > minButtonX)&&(mouseListener.getMouseXy()[0] < maxX-22)&&(mouseListener.getMouseXy()[1] > maxY-120)&&(mouseListener.getMouseXy()[1] < maxY-28)){
            g.setColor(new Color(0, 0, 0, 100)); 
            g.fillRect(maxX-142, maxY-120, 122, 93);
          }
          //Shade buttons that are pressed when picking up
          minButtonX = maxX-(int)(BOT_HEIGHT*INVENTORY_MOD)+220;
          maxButtonX= (int)(BOT_HEIGHT*INVENTORY_MOD)-240+maxX-(int)(BOT_HEIGHT*INVENTORY_MOD)+220;
          minButtonY = maxY-(int)(BOT_HEIGHT)+23;
          maxButtonY = maxY-(int)(BOT_HEIGHT)+23+(int)(BOT_HEIGHT/2.0)-30;
          if ((mouseListener.getMouseXy()[0] > minButtonX)&&(mouseListener.getMouseXy()[0] < maxButtonX)&&(mouseListener.getMouseXy()[1] >minButtonY)&&(mouseListener.getMouseXy()[1] <maxButtonY)){
            g.setColor(new Color(0, 0, 0, 100)); 
            g.fillRect(minButtonX, minButtonY, maxButtonX-minButtonX, maxButtonY-minButtonY);
          }
          reversePixelToArray(mouseListener.getMouseXy(), false);
          if ((entityMap[playerFinishingY][playerFinishingX] instanceof Character)&&(tileSelectedArray[0]==playerFinishingX)&&(tileSelectedArray[1]==playerFinishingY)){
            bg.setOnTile();
            if (bg.getOnTile()){
              anotherMap=true;
              keyListener.setToZero();
            }
          } 
        }
      }
    }
  }
  
  public void determineTiling(){
    bg.setOnTile();
    //System.out.println (blocked [0]+" | "+blocked [1]+" | "+blocked [2]+" | "+blocked [3]);
    //Sets off tiling for the reset of movement
    if (bg.getOnTile()&&(turnTransitionCounter==10)){
      bg.setXDirection (0);
      bg.setYDirection (0);
      tiling = false;
      turnTransitionCounter=0;
      attacked = false;
    }
    //The first part of the code will determine if the player array can move given the key listeners
    if (tiling){
      keyListener.setToZero();
      //A turn is set, and it is added
      //Move all of the entities slowly
      //Moving the bg is a prerequisite for everything else to move
      turnTransitionCounter++;
      xyDirection =keyListener.getAllDirection();
      bg.setXDirection (xyDirection[0]);
      bg.setYDirection (xyDirection[1]);
      if (!(movementRestriction)){
        if (!(attacked)){
          bg.move();
        }
      }
      //Make an entity move method
      for (int i =0;i<entityMap.length;i++){
        for(int j =0;j<entityMap[0].length;j++){
          if (entityMap[i][j] instanceof Enemy){
            if (!(entityMap[i][j].getMoved())){
              if (entityMap[i][j].getTiling()==0){
                entityMap[i][j].setTileYMod (entityMap[i][j].getTileYMod()-10);
                entityMap[i][j].setMoved(true);
              }else if(entityMap[i][j].getTiling()==1){
                entityMap[i][j].setTileYMod (entityMap[i][j].getTileYMod()+10);
                entityMap[i][j].setMoved(true);
              }else if (entityMap[i][j].getTiling()==2){
                entityMap[i][j].setTileXMod (entityMap[i][j].getTileXMod()-10);
                entityMap[i][j].setMoved(true);
              }else if (entityMap[i][j].getTiling()==3){
                entityMap[i][j].setTileXMod (entityMap[i][j].getTileXMod()+10);
                entityMap[i][j].setMoved(true);
              }
            }
          }
        }
      }
      refreshMobs();
    }
  }
  /**
   *drawMap 
   *Draws the tile map, restricting what is not necessary
   *@param: Graphics g
   *@return: 
   */
  public void drawMap (Graphics g){
    //Sets the void image
    g.setColor(Color.BLACK);
    g.fillRect(0, 0, maxX, maxY);
    //System.out.println (blocked [0]+" | "+blocked [1]+" | "+blocked [2]+" | "+blocked [3]);
    //Sets off tiling for the reset of movement
    //Setting all the possible positions is the second thing that will occur
    //The tiling variables allows the user to know when a turn is occuring 
    for (int i = 0;i<map.length;i++){
      for (int j = 0;j<map[0].length;j++){
        if (map[i][j] instanceof Tile){
          if (map[i][j] instanceof DoorTile){
            if (entityMap[i][j] instanceof Entity){
              ((DoorTile)(map[i][j])).setEntityOnDoor(true);
            }else{
              ((DoorTile)(map[i][j])).setEntityOnDoor(false);
            }
          }
          if(map[i][j].getViewed()){
            map[i][j].drawTile(g, maxX/2+j*TILE_SIZE-bg.getX()-(TILE_SIZE/2)-(TILE_SIZE*playerStartingX), maxY/2+i*TILE_SIZE-bg.getY()-(TILE_SIZE/2)-(TILE_SIZE*playerStartingY), TILE_SIZE, TILE_SIZE, this, map[i][j].getFocus());
          }
        }
      }
    }
    for(int i = 0; i < map.length; i++){
      for(int j = 0; j < map[0].length; j++){
        if(map[i][j] instanceof Tile){
          map[i][j].setFocus(false);
        }
      }
    }
    //Load basic visuals last
    drawFog(playerCurrentX, playerCurrentY, 0);
  }
  /**
   *drawFog
   *Part of the previous method, with more parameters
   *@param: The int x, the int y, the int count, and the int direction
   *@return: 
   */
  public void drawFog(int x, int y, int count){
    map[y][x].setViewed();
    map[y][x].setFocus(true);
    if (entityMap[y][x] instanceof Enemy){
      ((Enemy)(entityMap[y][x])).setEnraged(true);
    }
    if(count <= 3){ //If within range
      for(int i = -1; i <= 1; i ++){
        for(int j = -1; j <= 1; j++){
          if (checkInMap(x+j,y+i)){
            if(map[y+i][x+j] instanceof Tile){
              if((map[y][x] instanceof FloorTile) || (map[y][x].getType().equals("lab")) ||(map[y][x].getType().equals("crew")) ||(map[y][x].getType().equals("capq")) ||(map[y][x].getType().equals("reactor")) && !(map[y+i][x+j] instanceof HallwayTile)){ //Avoids corner sight, in room tile
                drawFog(x+j, y+i, count+1);
              } else if(map[y][x] instanceof HallwayTile){ //In hall tile
                map[y+i][x+j].setViewed();
                map[y+i][x+j].setFocus(true);
                if((map[y+i][x+j] instanceof HallwayTile)){
                  if((i==-1) && (j==0)){ //North
                    drawFog(x+j, y+i, count, 1);
                  }else if((i==1) && (j==0)){ //South
                    drawFog(x+j, y+i, count, 2);
                  }else if((i==0) && (j==1)){ //East
                    drawFog(x+j, y+i, count, 3);
                  }else if((i==0) && (j==-1)){ //West
                    drawFog(x+j, y+i, count, 4);
                  }
                }
              }else if((map[y][x] instanceof DoorTile) && (map[playerCurrentY][playerCurrentX] instanceof DoorTile)){ //In door tile
                map[y+i][x+j].setViewed();
                map[y+i][x+j].setFocus(true);
                if((map[y+i][x+j] instanceof HallwayTile) || (map[y+i][x+j].getMinimapColor() == Color.ORANGE)){
                  if((i==-1) && (j==0)){ //North
                    drawFog(x+j, y+i, count+1, 1);
                  }else if((i==1) && (j==0)){ //South
                    drawFog(x+j, y+i, count+1, 2);
                  }else if((i==0) && (j==1)){ //East
                    drawFog(x+j, y+i, count+1, 3);
                  }else if((i==0) && (j==-1)){ //West
                    drawFog(x+j, y+i, count+1, 4);
                  }
                }
                else{
                  drawFog(x+j, y+i, count+1);
                }
              }
            }
          }
        }
      }
    }
  }
  /**
   *drawFog
   *Draws the fog to restrict viewing recursively
   *@param: The int x, the int y, and the int count
   *@return: 
   */
  public void drawFog(int x, int y, int count, int direction){
    map[y][x].setViewed();
    map[y][x].setFocus(true);
    if((count <= 2) && (map[y][x] instanceof HallwayTile)){ //If current tile white
      for(int i = -1; i <= 1; i ++){ //Draws surrounding walls
        for(int j = -1; j <= 1; j++){
          if(checkInMap(x+j, y+i)){
            if(!(map[y+i][x+j] instanceof HallwayTile) || ((Math.abs(i+j)==1) && (count <= 0))){
              drawFog(x+j, y+i, count+1, 0);
            }
          }
        }
      }
      if(direction == 1){
        drawFog(x, y-1, count+1, 1);
      } else if(direction == 2){
        drawFog(x, y+1, count+1, 2);
      } else if(direction == 3){
        drawFog(x+1, y, count+1, 3);
      } else if(direction == 4){
        drawFog(x-1, y, count+1, 4);
      }
    }
  }
  /**
   *drawMinimap
   *Draws the minimap
   *@param: Graphics g
   *@return: 
   */
  public void drawMinimap(Graphics g){
    g.setColor(Color.BLACK);
    g.fillRect(0,maxY-(int)(BOT_HEIGHT),minimapX, minimapY);
    
    //Draws minimap contents
    //Must be a double to avoid rounding errors
    double miniTileSize = ((double)minimapX)/minimapFactor;
    Color currentColor;
    for(int i = 0; i < minimapFactor; i++){
      for(int j = 0; j < minimapFactor; j++){
        //Sets the minimap based on the normal map relative to the player
        minimapArrayY = playerCurrentY + i - minimapFactor/2;
        minimapArrayX = playerCurrentX + j - minimapFactor/2;
        if (checkInMap(minimapArrayX,minimapArrayY)){ //If tiles are in view window
          if(map[minimapArrayY][minimapArrayX] != null){ //If not void tile, to remove unecessary drawing){
            if(map[minimapArrayY][minimapArrayX].getFocus()){
              if (entityMap[minimapArrayY][minimapArrayX] != null){ //If an entity is at location
                currentColor = entityMap[minimapArrayY][minimapArrayX].getMinimapColor();
              } else{
                currentColor = map[minimapArrayY][minimapArrayX].getMinimapColor();
              }
            } else{
              if (entityMap[minimapArrayY][minimapArrayX] != null && keyListener.getDebugState()){ //If an entity is at location
                currentColor = entityMap[minimapArrayY][minimapArrayX].getMinimapColor().darker().darker();
              } else{
                currentColor = map[minimapArrayY][minimapArrayX].getMinimapColor().darker().darker();
              }
            }
            g.setColor(currentColor);
            if(keyListener.getDebugState()){ //If debug state
              g.fillRect((int)Math.round(j*miniTileSize), (maxY-240)+ (int)Math.round(i*miniTileSize), (int)Math.ceil(miniTileSize), (int)Math.ceil(miniTileSize));
            }else if(map[minimapArrayY][minimapArrayX].getViewed()){ //If not debug state, to be able to see the map while testing
              if(map[minimapArrayY][minimapArrayX].getMinimapColor() == Color.LIGHT_GRAY){
                g.setColor(currentColor.darker().darker().darker());
              }
              g.fillRect((int)Math.round(j*miniTileSize), (maxY-240)+ (int)Math.round(i*miniTileSize), (int)Math.ceil(miniTileSize), (int)Math.ceil(miniTileSize));
            }
          }
        }
      }
    }
    //Draws the frame, placed last as it covers the minimap
    g.drawImage(mapBorder,0,maxY-(int)(BOT_HEIGHT),minimapX, minimapY,this);
  }
  /**
   *drawItems
   *Draws all the items on the floor
   *@param: Graphics g
   *@return: 
   */
  public void drawItems(Graphics g){
    for (int i = 0;i<itemMap.length;i++){
      for (int j = 0;j<itemMap[0].length;j++){
        if(itemMap[i][j] instanceof Item && map[i][j].getFocus()){
          itemMap[i][j].drawItem(g, maxX/2+j*TILE_SIZE-bg.getX()-(TILE_SIZE/2)-(TILE_SIZE*playerStartingX), maxY/2+i*TILE_SIZE-bg.getY()-(TILE_SIZE/2)-(TILE_SIZE*playerStartingY), TILE_SIZE, TILE_SIZE, this);
        }
      }
    }
  }
  /**
   *drawAllEntity
   *Draws all the entities
   *@param: Graphics g
   *@return: 
   */
  public void drawAllEntity(Graphics g){
    //System.out.println (playerCurrentX+ " and "+ playerCurrentY);
    for (int i = 0;i<entityMap.length;i++){
      for (int j = 0;j<entityMap[0].length;j++){
        if(entityMap[i][j] instanceof Enemy && map[i][j].getFocus()){
          //  System.out.println ("Enemy: "+i +" and " +j);
          if (entityMap[i][j].getTiling ()==0){
            entityMap[i][j].drawEntity(g, maxX/2+j*TILE_SIZE-bg.getX()-(TILE_SIZE/2)-(TILE_SIZE*playerStartingX) +(entityMap[i][j].getTileXMod()), maxY/2+(i+1)*TILE_SIZE-bg.getY()-(TILE_SIZE/2)-(TILE_SIZE*playerStartingY)+(entityMap[i][j].getTileYMod()), TILE_SIZE, TILE_SIZE,0,-1, this);
          }else if (entityMap[i][j].getTiling ()==1){
            entityMap[i][j].drawEntity(g, maxX/2+j*TILE_SIZE-bg.getX()-(TILE_SIZE/2)-(TILE_SIZE*playerStartingX) +(entityMap[i][j].getTileXMod()), maxY/2+(i-1)*TILE_SIZE-bg.getY()-(TILE_SIZE/2)-(TILE_SIZE*playerStartingY)+(entityMap[i][j].getTileYMod()), TILE_SIZE, TILE_SIZE,0,+1,this);
          }else if (entityMap[i][j].getTiling ()==2){
            entityMap[i][j].drawEntity(g, maxX/2+(j+1)*TILE_SIZE-bg.getX()-(TILE_SIZE/2)-(TILE_SIZE*playerStartingX) +(entityMap[i][j].getTileXMod()), maxY/2+i*TILE_SIZE-bg.getY()-(TILE_SIZE/2)-(TILE_SIZE*playerStartingY)+(entityMap[i][j].getTileYMod()), TILE_SIZE, TILE_SIZE,-1,0, this);
          }else if (entityMap[i][j].getTiling ()==3){
            entityMap[i][j].drawEntity(g, maxX/2+(j-1)*TILE_SIZE-bg.getX()-(TILE_SIZE/2)-(TILE_SIZE*playerStartingX) +(entityMap[i][j].getTileXMod()), maxY/2+i*TILE_SIZE-bg.getY()-(TILE_SIZE/2)-(TILE_SIZE*playerStartingY)+(entityMap[i][j].getTileYMod()), TILE_SIZE, TILE_SIZE,+1,0, this);
          }else{
            entityMap[i][j].drawEntity(g, maxX/2+j*TILE_SIZE-bg.getX()-(TILE_SIZE/2)-(TILE_SIZE*playerStartingX), maxY/2+(i)*TILE_SIZE-bg.getY()-(TILE_SIZE/2)-(TILE_SIZE*playerStartingY), TILE_SIZE, TILE_SIZE,0,0, this);
          }
        }else if (entityMap[i][j] instanceof Character){
          entityMap[i][j].drawEntity(g, maxX/2-(TILE_SIZE/2),maxY/2-(TILE_SIZE/2),TILE_SIZE, TILE_SIZE,bg.getXDirection(),bg.getYDirection(), this);
        }
      }
    }
  }
  /**
   *drawBullets
   *Draws all the bullets
   *@param: The Graphics g, and the FireController playerFireController
   *@return: 
   */
  public void drawBullets(Graphics g, FireController playerFireController){
    if (collided){
      reversePixelToArray(mouseListener.getMouseXy(), false);
      targetX = maxX/2+tileSelectedArray[0]*TILE_SIZE-bg.getX()-(TILE_SIZE/2)-(TILE_SIZE*playerStartingX)+50;
      targetY= maxY/2+tileSelectedArray[1]*TILE_SIZE-bg.getY()-(TILE_SIZE/2)-(TILE_SIZE*playerStartingY)+50;
    }
    debugMessage = Integer.toString(maxX/2-targetX) + " " + Integer.toString(maxY/2-targetY);
    playerFireController.setupProjectile(targetX, targetY, 100);
    double shootAngle = playerFireController.returnAngle();
    translateX += Math.cos(shootAngle)*10;
    translateY += Math.sin(shootAngle)*10;
    Graphics2D g2 = (Graphics2D) g;
    g2.setStroke(new BasicStroke(5));
    g.setColor(Color.RED);
    if (!(inventoryOpen)){
      if (!(tiling)){
        if(mouseListener.getPressed()){
          if(collided){
            if (checkInMap(tileSelectedArray[0],tileSelectedArray[1])){
              if (map[tileSelectedArray[1]][tileSelectedArray[0]] instanceof Tile){
                if (!((tileSelectedArray[1]==playerCurrentY)&&(tileSelectedArray[0]==playerCurrentX))){
                  if (map[tileSelectedArray[1]][tileSelectedArray[0]].getFocus()){
                    if ((inventory.getItem(0,3)) instanceof RangedWeapon){
                      if(!((mouseListener.getMouseXy()[0]>(int)(maxX/2.0-(BOT_HEIGHT/2.0*(Y_TO_X_HOT/2.0))))&&(mouseListener.getMouseXy()[0]<(int)(maxX/2.0-(BOT_HEIGHT/2.0*(Y_TO_X_HOT/2.0)))+(int)(BOT_HEIGHT/2.0*Y_TO_X_HOT))&&(mouseListener.getMouseXy()[1]>maxY-(int)(BOT_HEIGHT/2.0))&&(mouseListener.getMouseXy()[1]<maxY-(int)(BOT_HEIGHT/2.0)+maxY-(int)(BOT_HEIGHT/2.0)))){
                        ((Equipment)(inventory.getItem(0,3))).setDurability(((Equipment)(inventory.getItem(0,3))).getDurability()-1);
                        collided = false;
                        translateX = 0;
                        translateY = 0;
                        //SoundEffect.SHOOT.play();
                      }
                    }
                  }
                }
              }
            }
          }
        }
      }
    }
    int startX = maxX/2 + translateX;
    int startY = maxY/2- translateY;
    int endX = startX + (int)(Math.cos(shootAngle)*20);
    int endY = startY - (int)(Math.sin(shootAngle)*20);
    int bulletY = playerCurrentY - (maxY/2 - endY)/100;
    int bulletX = playerCurrentX +(endX-maxX/2)/100;
    if (!collided){
      g.drawLine(startX, startY, endX, endY);
      if(bulletY >= map.length || bulletY < 0 || bulletX >= map[0].length || bulletX < 0){
        collided = true;
        turnPasser = true;
      } else if(map[bulletY][bulletX] instanceof WallTile || map[bulletY][bulletX] instanceof DoorTile || entityMap[bulletY][bulletX] instanceof Enemy||map[bulletY][bulletX] instanceof ChestTile){
        if (map[bulletY][bulletX] instanceof DoorTile){
          if (!((DoorTile)(map[bulletY][bulletX])).getEntityOnDoor()){
            collided = true;
            turnPasser = true;
            if(entityMap[bulletY][bulletX] instanceof Enemy){
              playerAttack(bulletX, bulletY);
            }
          }
        }else{
          collided = true;
          turnPasser = true;
          if(entityMap[bulletY][bulletX] instanceof Enemy){
            playerAttack(bulletX, bulletY);
          }
        }
      }
    }
    //System.out.println();
  }
  /**
   *drawBars
   *Draws the hp and exp bars
   *@param: Graphics g
   *@return: 
   */
  public void drawBars(Graphics g){
    //Fill Hp, can be modified through the width
    g.setColor (new Color (69,218,215));
    int currHealth, healthCap;
    int currXp, xpCap;
    if (entityMap[playerCurrentY][playerCurrentX] == null) {
      currHealth = 0;
      healthCap = 1;
      currXp = 0;
      xpCap = 1;
      gameOver = true;
    } else {
      currHealth = entityMap[playerCurrentY][playerCurrentX].getHealth();
      healthCap = entityMap[playerCurrentY][playerCurrentX].getHealthCap();
      currXp = ((Character)entityMap[playerCurrentY][playerCurrentX]).getXp();
      xpCap = ((Character)entityMap[playerCurrentY][playerCurrentX]).getXpCap();
    }
    //System.out.print (currXp);
    if (currXp>= xpCap){
      currXp= currXp-xpCap;
      ((Character)entityMap[playerCurrentY][playerCurrentX]).setXpCap(((Character)entityMap[playerCurrentY][playerCurrentX]).getXpCap()+50);
      xpCap = ((Character)entityMap[playerCurrentY][playerCurrentX]).getXpCap();
      //Increases health and health cap by 20
      entityMap[playerCurrentY][playerCurrentX].setHealth (entityMap[playerCurrentY][playerCurrentX].getHealth()+20);
      entityMap[playerCurrentY][playerCurrentX].setHealthCap (entityMap[playerCurrentY][playerCurrentX].getHealthCap()+20);
      playerLevel++;
    }
    //debugMessage = (Integer.toString(currHealth) + " " +  Integer.toString(healthCap));
    g.fillRect (16,16,(int)((((int)(maxX*1.0/5.0))-12)*((double)currHealth)/(double)healthCap), ((int)(maxX*1.0/5.0/200.0*14.0))-12);
    //Fill Exp, can be modified through the width
    g.setColor (new Color (152,251,152));
    g.fillRect (16,21+((int)(maxX*1.0/5.0/200.0*14.0)), (int)((((int)(maxX*1.0/5.0))-12)*((double)currXp)/(double)xpCap),((int)(maxX*1.0/5.0/200.0*10.0))-12);
    //Draws status icons
    int hunger=0;
    int statusSkip=0;
    if (entityMap[playerCurrentY][playerCurrentX] instanceof Character){
      hunger = ((Character)entityMap[playerCurrentY][playerCurrentX]).getHunger();
      if (hunger <= 0){
        g.drawImage(starving,16+statusSkip,15+ (int)(maxX*0.2/200.0*14.0)+30,30,30,this);
        statusSkip=statusSkip+35;
      } else if(hunger < 100){
        g.drawImage(hungry,16+statusSkip,15+ (int)(maxX*0.2/200.0*14.0)+30,30,30,this);
        statusSkip=statusSkip+35;
      }
      if (entityMap[playerCurrentY][playerCurrentX].getLightning()){
        g.drawImage (lightningStatus,16+statusSkip,15+ (int)(maxX*0.2/200.0*14.0)+30,30,30,this);
        statusSkip=statusSkip+35;
      }
      if (entityMap[playerCurrentY][playerCurrentX].getFreeze()){
        g.drawImage (freezeStatus,16+statusSkip,15+ (int)(maxX*0.2/200.0*14.0)+30,30,30,this);
        statusSkip=statusSkip+35;
      }
      if (entityMap[playerCurrentY][playerCurrentX].getFlame()){
        g.drawImage (flameStatus,16+statusSkip,15+ (int)(maxX*0.2/200.0*14.0)+30,30,30,this);
      }
    }
    //Draws health bars for entities
    for (int i = 0; i < entityMap.length; i++) {
      for (int j = 0; j < entityMap[0].length; j++) {
        g.setColor (new Color (220,20,60));
        if (entityMap[i][j] instanceof Enemy && map[i][j].getFocus()) {
          if (entityMap[i][j].getTiling ()==0){
            g.fillRect (maxX/2+j*TILE_SIZE-bg.getX()-(TILE_SIZE/2)-(TILE_SIZE*playerStartingX) +(entityMap[i][j].getTileXMod()), maxY/2+(i+1)*TILE_SIZE-bg.getY()-(TILE_SIZE/2)-(TILE_SIZE*playerStartingY)+(entityMap[i][j].getTileYMod())-15, TILE_SIZE, 10);
            g.setColor (new Color (152,251,152));
            g.fillRect (maxX/2+j*TILE_SIZE-bg.getX()-(TILE_SIZE/2)-(TILE_SIZE*playerStartingX) +(entityMap[i][j].getTileXMod()), maxY/2+(i+1)*TILE_SIZE-bg.getY()-(TILE_SIZE/2)-(TILE_SIZE*playerStartingY)+(entityMap[i][j].getTileYMod())-15, (int)(TILE_SIZE*((double)entityMap[i][j].getHealth())/((double)entityMap[i][j].getHealthCap())), 10);
            drawStatus(g, entityMap[i][j].getTiling(),j,i);   
          }else if (entityMap[i][j].getTiling ()==1){
            g.fillRect (maxX/2+j*TILE_SIZE-bg.getX()-(TILE_SIZE/2)-(TILE_SIZE*playerStartingX) +(entityMap[i][j].getTileXMod()), maxY/2+(i-1)*TILE_SIZE-bg.getY()-(TILE_SIZE/2)-(TILE_SIZE*playerStartingY)+(entityMap[i][j].getTileYMod())-15, TILE_SIZE, 10);
            g.setColor (new Color (152,251,152));
            g.fillRect (maxX/2+j*TILE_SIZE-bg.getX()-(TILE_SIZE/2)-(TILE_SIZE*playerStartingX) +(entityMap[i][j].getTileXMod()), maxY/2+(i-1)*TILE_SIZE-bg.getY()-(TILE_SIZE/2)-(TILE_SIZE*playerStartingY)+(entityMap[i][j].getTileYMod())-15, (int)(TILE_SIZE*((double)entityMap[i][j].getHealth())/((double)entityMap[i][j].getHealthCap())), 10);
            drawStatus( g, entityMap[i][j].getTiling(),j,i);
          }else if (entityMap[i][j].getTiling ()==2){
            g.fillRect (maxX/2+(j+1)*TILE_SIZE-bg.getX()-(TILE_SIZE/2)-(TILE_SIZE*playerStartingX) +(entityMap[i][j].getTileXMod()), maxY/2+i*TILE_SIZE-bg.getY()-(TILE_SIZE/2)-(TILE_SIZE*playerStartingY)+(entityMap[i][j].getTileYMod())-15, TILE_SIZE, 10);
            g.setColor (new Color (152,251,152));
            g.fillRect (maxX/2+(j+1)*TILE_SIZE-bg.getX()-(TILE_SIZE/2)-(TILE_SIZE*playerStartingX) +(entityMap[i][j].getTileXMod()), maxY/2+i*TILE_SIZE-bg.getY()-(TILE_SIZE/2)-(TILE_SIZE*playerStartingY)+(entityMap[i][j].getTileYMod())-15, (int)(TILE_SIZE*((double)entityMap[i][j].getHealth())/((double)entityMap[i][j].getHealthCap())), 10);
            drawStatus(g, entityMap[i][j].getTiling (),j,i);   
          }else if (entityMap[i][j].getTiling ()==3){
            g.fillRect (maxX/2+(j-1)*TILE_SIZE-bg.getX()-(TILE_SIZE/2)-(TILE_SIZE*playerStartingX) +(entityMap[i][j].getTileXMod()), maxY/2+i*TILE_SIZE-bg.getY()-(TILE_SIZE/2)-(TILE_SIZE*playerStartingY)+(entityMap[i][j].getTileYMod())-15, TILE_SIZE, 10);
            g.setColor (new Color (152,251,152));
            g.fillRect (maxX/2+(j-1)*TILE_SIZE-bg.getX()-(TILE_SIZE/2)-(TILE_SIZE*playerStartingX) +(entityMap[i][j].getTileXMod()), maxY/2+i*TILE_SIZE-bg.getY()-(TILE_SIZE/2)-(TILE_SIZE*playerStartingY)+(entityMap[i][j].getTileYMod())-15, (int)(TILE_SIZE*((double)entityMap[i][j].getHealth())/((double)entityMap[i][j].getHealthCap())), 10);
            drawStatus(g, entityMap[i][j].getTiling (),j,i);   
          }else{
            g.fillRect(maxX/2+j*TILE_SIZE-bg.getX()-(TILE_SIZE/2)-(TILE_SIZE*playerStartingX), maxY/2+(i)*TILE_SIZE-bg.getY()-(TILE_SIZE/2)-(TILE_SIZE*playerStartingY)-15, TILE_SIZE, 10);
            g.setColor (new Color (152,251,152));
            g.fillRect(maxX/2+j*TILE_SIZE-bg.getX()-(TILE_SIZE/2)-(TILE_SIZE*playerStartingX), maxY/2+(i)*TILE_SIZE-bg.getY()-(TILE_SIZE/2)-(TILE_SIZE*playerStartingY)-15, (int)(TILE_SIZE*((double)entityMap[i][j].getHealth())/((double)entityMap[i][j].getHealthCap())), 10);
            drawStatus( g,entityMap[i][j].getTiling (),j,i);   
          }
        }                
      }
    }
  }
  
  /**
   *findBlocked
   *Finds whats blocked so that the array can be updated
   *@param: The int j, and the int i
   *@return: 
   */
  //Map manipulation
  //The first method blocks off all impossible paths so that it the player does not clip into walls
  public void findBlocked(int j, int i){
    //Restrictions are placed before the start of the each if so that array out of bounds is avoided
    blocked[0] = false;
    blocked[1] = false;
    blocked[2] = false;
    blocked[3] = false;
    if (i-1>=0){
      if ((entityMap[i][j] instanceof Enemy)&&(map[i-1][j] instanceof DoorTile)){
        if(!(((Enemy)(entityMap[i][j])).getEnraged())){
          blocked[0] = true;
        }
      }
      if (!(map[i-1][j]  instanceof WalkableTile)||(entityMap[i-1][j] instanceof Entity)){
        blocked[0] = true;
      }
    }else{
      blocked[0] = true;
    }
    if (i+1<map.length){
      if ((entityMap[i][j] instanceof Enemy)&&(map[i+1][j] instanceof DoorTile)){
        if(!(((Enemy)(entityMap[i][j])).getEnraged())){
          blocked[1] = true;
        }
      }
      if (!(map[i+1][j]  instanceof WalkableTile)|| (entityMap[i+1][j] instanceof Entity)){
        blocked[1] = true;
      }
    }else{
      blocked[1] = true;
    }
    if (j-1>=0){
      if ((entityMap[i][j] instanceof Enemy)&&(map[i][j-1] instanceof DoorTile)){
        if(!(((Enemy)(entityMap[i][j])).getEnraged())){
          blocked[2] = true;
        }
      }
      if (!(map[i][j-1]  instanceof WalkableTile)|| (entityMap[i][j-1] instanceof Entity)){
        blocked[2] = true;
      }
    }else{
      blocked[2] = true;
    }
    if (j+1<map[0].length){
      if ((entityMap[i][j] instanceof Enemy)&&(map[i][j+1] instanceof DoorTile)){
        if(!(((Enemy)(entityMap[i][j])).getEnraged())){
          blocked[3] = true;
        }
      }
      if (!(map[i][j+1]  instanceof WalkableTile)|| (entityMap[i][j+1] instanceof Entity)){
        blocked[3] = true;
      }
    }else{
      blocked[3] = true;
    }
  }
  /**
   *drawInventory
   *Inventory is drawn
   *@param: Graphics g
   *@return: 
   */
  public void drawInventory(Graphics g){
    if (inventoryOpen){
      g.setColor(new Color(0, 0, 0, 200)); 
      g.fillRect (0,0,maxX,maxY);
      g.drawImage (inventoryImage,maxX/2-400,maxY/2-300,800,600,this);
      //
      //Testing code
      for (int i=0;i<4;i++){
        for (int j=0;j<6;j++){
          //Sets all the dimensions for each tile, and the click area for each one
          xInvPixel=maxX/2-377+128*j;
          minSelectX = maxX/2-377+128*j;
          maxSelectX=maxX/2-377+128*j+113;
          if (i<3){
            yInvPixel=maxY/2-278+128*i;
            minSelectY = maxY/2-278+128*i;
            maxSelectY = maxY/2-278+128*i+113;
          }else{
            yInvPixel=maxY/2+164;
            minSelectY =maxY/2+164;
            maxSelectY =maxY/2+277;
          }
          //Nothing is selected, looking at each one to swap something
          if (pendingUpgrade){
            inventory.writePending(g, driveArrayX,driveArrayY,maxX/2-385,maxY/2+110);
            if  ((inventory.getItem(j,i) instanceof Equipment)){
              if ((mouseListener.getMouseXy()[0] >minSelectX)&&(mouseListener.getMouseXy()[0] < maxSelectX)&&(mouseListener.getMouseXy()[1] >minSelectY)&&(mouseListener.getMouseXy()[1] < maxSelectY)&&(mouseListener.getPressed())&&(alternateState)){
                alternateState = false;
                pendingUpgrade = false;
                inventory.setItem(j,i,(((Drive)(inventory.getItem(driveArrayX,driveArrayY))).upgrade(inventory.getItem(j,i))));
                inventory.setItem(driveArrayX,driveArrayY, null);
              }
            }else if ((j==driveArrayX)&&(i==driveArrayY)){
              if ((mouseListener.getMouseXy()[0] >minSelectX)&&(mouseListener.getMouseXy()[0] < maxSelectX)&&(mouseListener.getMouseXy()[1] >minSelectY)&&(mouseListener.getMouseXy()[1] < maxSelectY)&&(mouseListener.getPressed())&&(alternateState)){
                alternateState = false;
                pendingUpgrade = false;
                //Cancelling the upgrade does not cose a turn
              }
            }
          }else{
            if (!(itemSelected)){
              if  ((inventory.getItem(j,i) instanceof Item)){
                if ((mouseListener.getMouseXy()[0] >minSelectX)&&(mouseListener.getMouseXy()[0] < maxSelectX)&&(mouseListener.getMouseXy()[1] >minSelectY)&&(mouseListener.getMouseXy()[1] < maxSelectY)&&(mouseListener.getPressed())&&(alternateState)){
                  alternateState = false;
                  inventory.setSelected(j,i, true);
                  selectedItemPosition[0]=j;
                  selectedItemPosition[1]=i;
                  itemSelected = true;
                }
              }
            }
            //The item can be swapped with any space
            if (itemSelected){
              inventory.writeStats(g, selectedItemPosition[0],selectedItemPosition[1],maxX/2-385,maxY/2+110);
              if ((mouseListener.getMouseXy()[0] >minSelectX)&&(mouseListener.getMouseXy()[0] < maxSelectX)&&(mouseListener.getMouseXy()[1] >minSelectY)&&(mouseListener.getMouseXy()[1] < maxSelectY)&&(mouseListener.getPressed())&&(alternateState)){
                alternateState = false;
                inventory.setSelected(selectedItemPosition[0],selectedItemPosition[1], false);
                itemSelected = false;
                if (((j==selectedItemPosition[0])&&(i==selectedItemPosition[1]))){
                  if (inventory.getItem(selectedItemPosition[0],selectedItemPosition[1]) instanceof Drive){
                    inventory.setSelected(selectedItemPosition[0],selectedItemPosition[1], false);
                    itemSelected = false;
                    pendingUpgrade = true;
                    driveArrayX=j;
                    driveArrayY=i;  
                  }
                  if (inventory.getItem(selectedItemPosition[0],selectedItemPosition[1]) instanceof MedKit){
                    inventory.setSelected(selectedItemPosition[0],selectedItemPosition[1], false);
                    itemSelected = false;
                    entityMap[playerCurrentY][playerCurrentX] = ((MedKit)(inventory.getItem(selectedItemPosition[0],selectedItemPosition[1]))).heal(((Character)(entityMap[playerCurrentY][playerCurrentX])));
                    inventory.setItem(selectedItemPosition[0],selectedItemPosition[1], null);
                  }               
                  if (inventory.getItem(selectedItemPosition[0],selectedItemPosition[1]) instanceof Food){
                    inventory.setSelected(selectedItemPosition[0],selectedItemPosition[1], false);
                    itemSelected = false;
                    entityMap[playerCurrentY][playerCurrentX] = ((Food)(inventory.getItem(selectedItemPosition[0],selectedItemPosition[1]))).restoreHunger(((Character)(entityMap[playerCurrentY][playerCurrentX])));
                    inventory.setItem(selectedItemPosition[0],selectedItemPosition[1], null);
                  }
                }else if ((j==0)&&(i==3)&&(!(inventory.getItem(selectedItemPosition[0],selectedItemPosition[1]) instanceof RangedWeapon))){
                  inventory.setSelected(selectedItemPosition[0],selectedItemPosition[1], false);
                  itemSelected = false;
                }else if ((j==1)&&(i==3)&&(!(inventory.getItem(selectedItemPosition[0],selectedItemPosition[1]) instanceof Armor))){
                  inventory.setSelected(selectedItemPosition[0],selectedItemPosition[1], false);
                  itemSelected = false;
                }else if ((j==2)&&(i==3)&&(!(inventory.getItem(selectedItemPosition[0],selectedItemPosition[1]) instanceof MeleeWeapon))){
                  inventory.setSelected(selectedItemPosition[0],selectedItemPosition[1], false);
                  itemSelected = false;
                }else{
                  if (!(inventory.getItem(j,i) instanceof Item)){
                    inventory.swap(j,i,selectedItemPosition[0],selectedItemPosition[1]);
                  }else{
                    if ((selectedItemPosition[0]==0)&&(selectedItemPosition[1]==3)&&(!(inventory.getItem(j,i) instanceof RangedWeapon))){
                      inventory.setSelected(selectedItemPosition[0],selectedItemPosition[1], false);
                      itemSelected = false;
                    }else if ((selectedItemPosition[0]==1)&&(selectedItemPosition[1]==3)&&(!(inventory.getItem(j,i) instanceof Armor))){
                      inventory.setSelected(selectedItemPosition[0],selectedItemPosition[1], false);
                      itemSelected = false;
                    }else if ((selectedItemPosition[0]==2)&&(selectedItemPosition[1]==3)&&(!(inventory.getItem(j,i) instanceof MeleeWeapon))){
                      inventory.setSelected(selectedItemPosition[0],selectedItemPosition[1], false);
                      itemSelected = false;
                    }else{
                      inventory.swap(j,i,selectedItemPosition[0],selectedItemPosition[1]);
                    }
                  }
                }
              }else if ((!((mouseListener.getMouseXy()[0] >maxX/2-400)&&(mouseListener.getMouseXy()[0] <maxX/2+400)&&(mouseListener.getMouseXy()[1] >maxY/2-300)&&(mouseListener.getMouseXy()[1] <maxY/2+300)))&&(mouseListener.getPressed())&&(alternateState)){
                alternateState = false;
                itemSelected = false;
                //Create an ERROR sound effect!
                if (!(itemMap[playerCurrentY][playerCurrentX]instanceof Item)){
                  inventory.setSelected(selectedItemPosition[0],selectedItemPosition[1], false);
                  itemMap[playerCurrentY][playerCurrentX] =  inventory.getItem(selectedItemPosition[0],selectedItemPosition[1]);
                  inventory.setItem(selectedItemPosition[0],selectedItemPosition[1], null);
                  inventoryOpen = false;
                  pauseState=false;
                }
                //No turn passes when dropping items, but turns will pass when picking them up
              }
            }
          }
          if (inventory.getItem(j,i) instanceof Item){
            g.setColor(new Color(54,53,53));
            if (((j==0)&&(i==3))||((j==1)&&(i==3))||((j==2)&&(i==3))){
              g.fillRect (xInvPixel, yInvPixel, 113, 113);
            }
            inventory.drawSelectedItem(g,j,i, xInvPixel, yInvPixel, 113, 113, this);
          }
        }
      }
    }else{
      if (inventory.getItem(selectedItemPosition[0],selectedItemPosition[1]) instanceof Item){
        inventory.setSelected(selectedItemPosition[0],selectedItemPosition[1], false);
      }
      itemSelected = false;
      pendingUpgrade = false;
    }
  }
  /**
   *initial
   *Sets up the map
   *@param: The Tile [][]map, the int playerStartingX, the int playerStartingY, the int playerFinishingX, and the int playerFinishingY
   *@return: 
   */
  //Sets up the map so that setting the floor is easier as well
  public void initial(Tile [][]map, int playerStartingX, int playerStartingY, int playerFinishingX, int playerFinishingY){
    //Initializes player locations
    this.map = map;
    this.entityMap = new Entity [map.length][map[0].length];
    this.itemMap = new Item [map.length][map[0].length];
    this.playerStartingX = playerStartingX;
    this.playerStartingY =playerStartingY;
    this.playerFinishingX = playerFinishingX;
    this.playerFinishingY =playerFinishingY;
    playerCurrentX = playerStartingX;
    playerCurrentY = playerStartingY;
    entityMap[playerStartingY][playerStartingX]= new Character(100,100,0,false,false,false,Color.BLUE);
    //Creates all the items on the floor
    spawnItems();
    //Sets inventory default items
    inventory.setItem(1,3,new SpaceSuit (50));
    inventory.setItem(2,3,new Wrench (50));
  }
  /**
   *createMap
   *Creates a tile map for loading new levels
   *@param: The Tile [][]map, the int playerStartingX, the int playerStartingY, the int playerFinishingX, and the int playerFinishingY
   *@return: 
   */
  public void createMap(Tile [][]map, int playerStartingX, int playerStartingY, int playerFinishingX, int playerFinishingY){
    Entity tempCharacter = entityMap[this.playerCurrentY][this.playerCurrentX];
    bg.setX(0);
    bg.setY(0);
    bg.setXDirection(0);
    bg.setYDirection(0);
    this.map = map;
    for (int i =0;i<map.length;i++){
      for(int j =0;j<map[0].length;j++){
        entityMap[i][j]= null;
        itemMap[i][j]= null;
      }
    }
    floorLevel++;
    this.playerStartingX = playerStartingX;
    this.playerStartingY =playerStartingY;
    this.playerCurrentX = playerStartingX;
    this.playerCurrentY = playerStartingY;
    this.playerFinishingX = playerFinishingX;
    this.playerFinishingY =playerFinishingY;
    entityMap[playerStartingY][playerStartingX] = tempCharacter;
    loading= true;
    loadingCount=100;
    if (floorLevel!=4){
      spawnItems();
    }else{
      entityMap[bossY][bossX] = new Boss (100,100,50,false,false,false,Color.RED, true);
    }
  }
  
  //Getters and setters
  //There is no getter for the following, as it only needs to be accessed from this class
  //Sets all the information for the debug panel
  /**
   *setDebugInfo
   *The debug info is refreshed
   *@param: The int fps, the double totalMem, and the double memUsed
   *@return: 
   */
  public void setDebugInfo(int fps, double totalMem, double memUsed, int difficulty){
    this.fps = Integer.toString(fps);
    this.totalMem = totalMem;
    this.memUsed = memUsed;
    memPercent = (memUsed/totalMem)*100;
    this.difficulty = difficulty;
  }
  //Retrieves whether or not it is a new floor
  /**
   *getNewFloor
   *Retrieves whether or not it is a new floor
   *@param: 
   *@return: A boolean
   */
  public boolean getNewFloor(){
    return (newFloor);
  }
  /**
   *setNewFloor
   *Sends back if the floor is new or not; this is useful for understanding what code to run
   *@param: boolean newFloor
   *@return: 
   */
  //Sends back if the floor is new or not; this is useful for understanding what code to run
  public void setNewFloor(boolean newFloor){
    this.newFloor = newFloor;
  }
  /**
   *passTurn 
   *Passes the turn so that the game is turn based
   *@param: 
   *@return: 
   */
  public void passTurn (){
    //Updates hunger and health
    hungerCount ++;
    int hunger = ((Character)entityMap[playerCurrentY][playerCurrentX]).getHunger();
    debugMessage = Integer.toString(hunger);
    int tempHealth = entityMap[playerCurrentY][playerCurrentX].getHealth();
    if(hungerCount >= 5){
      ((Character)entityMap[playerCurrentY][playerCurrentX]).setHunger(hunger-1);
      if(hunger <= 0){
        entityMap[playerCurrentY][playerCurrentX].setHealth(tempHealth-1);
      } else if(hunger < 100){
        //There is just a hunger warning
      } else if(tempHealth < entityMap[playerCurrentY][playerCurrentX].getHealthCap()) {
        entityMap[playerCurrentY][playerCurrentX].setHealth(tempHealth+1);
      }
      hungerCount = 0;
    }
    
    for (int i =0;i<entityMap.length;i++){
      for(int j =0;j<entityMap[0].length;j++){
        if (entityMap[i][j] instanceof Enemy){
          entityMap[i][j].setTiling (4);
        }
      }
    }
    //5 % chance to spawn
    //Spawning method, this is the first thing that will occur
    if (((int)(Math.random()*100)<5)&&(mobCount<mobCap)&&(floorLevel!=4)){
      //Resets the spawn
      while(!(acceptableSpawn)){
        spawnX =(int)(Math.random()*entityMap[0].length);
        spawnY =(int)(Math.random()*entityMap.length);      
        acceptableSpawn = true;
        if ((entityMap[spawnY][spawnX] instanceof Entity)||(!(map[spawnY][spawnX] instanceof FloorTile))||((playerCurrentX<spawnX+10)&&(playerCurrentX>spawnX-10)&&(playerCurrentY<spawnY+10)&&(playerCurrentY>spawnY-10))){
          acceptableSpawn = false;
        }
      }
      acceptableSpawn = false;
      
      //Cannot spawn 20 blocks in radius beside the character
      mobCount++;
      //Defense and health will both increase as the player progresses
      //Though roombas have less hp and defense, they have the ability to inflict status
      //Roombas also have slightly more attack
      int tempRand = (int)(Math.random()*4);
      if (tempRand==0){
        entityMap[spawnY][spawnX] = new FlameRoomba (10+5*floorLevel,10+5*floorLevel,2*floorLevel,false,false,false,Color.MAGENTA, false);
      }else if (tempRand==1){
        entityMap[spawnY][spawnX] = new LightningRoomba (10+5*floorLevel,10+5*floorLevel,2*floorLevel,false,false,false,Color.MAGENTA, false);
      }else if (tempRand==2){
        entityMap[spawnY][spawnX] = new FreezeRoomba (10+5*floorLevel,10+5*floorLevel,2*floorLevel,false,false,false,Color.MAGENTA, false);
      }else{
        entityMap[spawnY][spawnX] = new Brute (15+5*floorLevel,15+5*floorLevel,3*floorLevel,false,false,false,Color.MAGENTA, false);
      }
    }
    //Damages whatever is burned
    for (int i =0;i<entityMap.length;i++){
      for(int j =0;j<entityMap[0].length;j++){
        if (entityMap[i][j] instanceof Entity){
          if (entityMap[i][j].getFlame()){
            //Deals 5 damage, ignores defense
            entityMap[i][j].setHealth(entityMap[i][j].getHealth()-5);
            if (((int)(Math.random()*100)<25)){
              entityMap[i][j].setFlame(false);
            }
          }else if(entityMap[i][j].getFreeze()){
            if (((int)(Math.random()*100)<25)){
              entityMap[i][j].setFreeze(false);
            }
            if (entityMap[i][j] instanceof Roomba){
              entityMap[i][j].setArmor(2*floorLevel);
            }else if (entityMap[i][j] instanceof Brute){
              entityMap[i][j].setArmor(3*floorLevel);
            }
          }else if (entityMap[i][j].getLightning()){
            if ((int)(Math.random()*100)<25){
              entityMap[i][j].setLightning(false);
            }
          }
        }
      }
    }
    //Kills whatever dies from burning
    checkKilled(0,0);
    findBlocked (playerCurrentX, playerCurrentY);
    //Set all array postion
    for (int i =0;i<entityMap.length;i++){
      for(int j =0;j<entityMap[0].length;j++){
        if (entityMap[i][j] instanceof Character){
          if ((entityMap[playerCurrentY][playerCurrentX].getLightning())){
          }else if(attacked){
          }else if((!(blocked[0])&&(keyListener.getAllDirection()[1]<0))||(!(blocked[1])&&(keyListener.getAllDirection()[1]>0))||(!(blocked[2])&&(keyListener.getAllDirection()[0]<0))||(!(blocked[3])&&(keyListener.getAllDirection()[0]>0))){
            movementRestriction = false;
            if ((!(entityMap[i][j].getMoved()))){
              //Sets the position on the map directly
              if (keyListener.getAllDirection()[0]<0){
                playerCurrentX= playerCurrentX -1;
                entityMap[playerCurrentY][playerCurrentX]= entityMap[i][j];
                entityMap[i][j] =null;
                entityMap[playerCurrentY][playerCurrentX].setMoved(true);
              }else if (keyListener.getAllDirection()[0]>0){
                playerCurrentX =playerCurrentX+1;
                entityMap[playerCurrentY][playerCurrentX]= entityMap[i][j];
                entityMap[i][j] =null;
                entityMap[playerCurrentY][playerCurrentX].setMoved(true);
              }
              if (keyListener.getAllDirection()[1]<0){
                playerCurrentY =playerCurrentY-1;
                entityMap[playerCurrentY][playerCurrentX]= entityMap[i][j];
                entityMap[i][j] =null;
                entityMap[playerCurrentY][playerCurrentX].setMoved(true);
              }else if (keyListener.getAllDirection()[1]>0){
                playerCurrentY =playerCurrentY+1;
                entityMap[playerCurrentY][playerCurrentX]= entityMap[i][j];
                entityMap[i][j] =null;
                entityMap[playerCurrentY][playerCurrentX].setMoved(true);
              }else{
                entityMap[playerCurrentY][playerCurrentX].setMoved(true);
              }
            }
            ///Possibly make tiling true;
          }else{
            movementRestriction = true;
          }
        }
      }
    }
    for (int i =0;i<entityMap.length;i++){
      for(int j =0;j<entityMap[0].length;j++){
        if (entityMap[i][j] instanceof Entity){
          entityMap[i][j].setTileXMod(0);
          entityMap[i][j].setTileYMod(0);
          if (!(entityMap[i][j].getMoved())){
            //Finding the blocked for entity
            findBlocked (j,i);
            if (entityMap[i][j] instanceof Enemy){
              if (entityMap[i][j].getLightning()){
                entityMap[i][j].setTiling (4);
                entityMap[i][j].setMoved(true);
                entityArrayXMod = 0;
                entityArrayYMod = 0;
                if ((int)(Math.random()*100)<25){
                  entityMap[i][j].setLightning(false);
                }
              }else{
                if (((Enemy)(entityMap[i][j])).getEnraged()){
                  for (int k=0;k<4;k++){
                    if (blocked[k]){
                      pathfinderPriority[k]=100;
                    }else{
                      pathfinderPriority [k]=1;
                    }
                  }
                  pathfinderPriority [4]=1;
                  for (int k=0;k<5;k++){
                    if (pathfinderPriority[k]==1){
                      if (k==0){
                        pathfinderDistance[k] = Math.sqrt(Math.pow(j-playerCurrentX,2.0)+Math.pow((i-1)-playerCurrentY,2.0));
                      }else if (k==1){
                        pathfinderDistance[k] = Math.sqrt(Math.pow(j-playerCurrentX,2.0)+Math.pow((i+1)-playerCurrentY,2.0));
                      }else if (k==2){
                        pathfinderDistance[k] = Math.sqrt(Math.pow((j-1)-playerCurrentX,2.0)+Math.pow(i-playerCurrentY,2.0));
                      }else if (k==3){
                        pathfinderDistance[k] = Math.sqrt(Math.pow((j+1)-playerCurrentX,2.0)+Math.pow(i-playerCurrentY,2.0));
                      }else if (k==4){
                        pathfinderDistance[4] = Math.sqrt(Math.pow(j-playerCurrentX,2.0)+Math.pow(i-playerCurrentY,2.0));
                      }
                    }else{
                      pathfinderDistance [k]=100;
                    }
                  }
                  //Resets the closest path
                  closestPath =1000;
                  for (int k=0;k<5;k++){
                    if (pathfinderDistance[k]<closestPath){
                      closestPath = pathfinderDistance[k];
                      //You might want to randomize the closestDirection by setting it as an array
                      closestDirection = k;
                    }
                  }
                  directionRand = closestDirection;
                  if (((int)(Math.abs(playerCurrentX-j)+(int)(Math.abs(playerCurrentY-i))))==1){
                    directionRand=4;
                  }
                  if ((blocked[0])&&(blocked[1])&&(blocked[2])&&(blocked[3])){
                    directionRand=4;
                  }
                }else{
                  if ((blocked[0])&&(blocked[1])&&(blocked[2])&&(blocked[3])){
                    directionRand=4;
                  }else{
                    do{
                      directionRand=((int)(Math.random()*4));
                    }while (blocked[directionRand]);
                  }
                  if ((!((Enemy)(entityMap[i][j])).getEnraged())&&(((int)(Math.random()*2))==1)){
                    //50% chance for them to not move if aggro'd
                    directionRand  = 4;
                  }
                }
                entityMap[i][j].setTiling (directionRand);
                entityMap[i][j].setMoved(true);
                entityArrayXMod = 0;
                entityArrayYMod = 0;
                if (directionRand==0){
                  entityArrayYMod = -1;
                }else if (directionRand==1){
                  entityArrayYMod = 1;
                }else if (directionRand==2){
                  entityArrayXMod = -1;
                }else if(directionRand==3){
                  entityArrayXMod = 1;
                }
                if (directionRand != 4){
                  entityMap[i+entityArrayYMod][j+entityArrayXMod] = entityMap[i][j];
                  //Not sure about setting it to null, look at if there is a better method
                  entityMap[i][j] =null;
                } else {
                  tempHealth = entityMap[playerCurrentY][playerCurrentX].getHealth();
                  int damage;
                  if (entityMap[i][j] instanceof Boss){
                    damage= 25;
                  }else{
                    damage= 5+5*floorLevel;
                  }
                  //Should increase by 7 every level
                  int tempArmor=entityMap[playerCurrentY][playerCurrentX].getArmor();
                  if (entityMap[playerCurrentY][playerCurrentX].getFreeze()){
                    entityMap[playerCurrentY][playerCurrentX].setArmor(0);
                  }
                  if (entityMap[playerCurrentY][playerCurrentX].getArmor()>=damage){
                    damage =(int)(Math.ceil(((double)(damage))*0.2));
                  }else{
                    damage =damage-tempArmor;
                  }
                  if ((i + 1 == playerCurrentY && j == playerCurrentX)||(i == playerCurrentY && j - 1 == playerCurrentX)|| (i == playerCurrentY && j + 1 == playerCurrentX)|| (i - 1 == playerCurrentY && j == playerCurrentX)) {
                    entityMap[playerCurrentY][playerCurrentX].setHealth(tempHealth - damage);
                    ((Equipment)(inventory.getItem(1,3))).setDurability(((Equipment)(inventory.getItem(1,3))).getDurability()-1);
                    if (entityMap[i][j] instanceof FlameRoomba){
                      if (((int)(Math.random()*100)<10)){
                        if (inventory.getItem(1,3) instanceof Armor){
                          if (((int)(Math.random()*100))<((Armor)(inventory.getItem(1,3))).getFlameDefense()){
                          }else{
                            entityMap[playerCurrentY][playerCurrentX].setFlame(true);  
                          }
                        }else{
                          entityMap[playerCurrentY][playerCurrentX].setFlame(true);
                        }
                      }
                    }else if (entityMap[i][j] instanceof FreezeRoomba){
                      if (((int)(Math.random()*100)<10)){
                        if (inventory.getItem(1,3) instanceof Armor){
                          if (((int)(Math.random()*100))<((Armor)(inventory.getItem(1,3))).getFlameDefense()){
                          }else{
                            entityMap[playerCurrentY][playerCurrentX].setFreeze(true);  
                          }
                        }else{
                          entityMap[playerCurrentY][playerCurrentX].setFreeze(true);
                        }
                      }
                    }else if (entityMap[i][j] instanceof LightningRoomba){
                      if (((int)(Math.random()*100)<10)){
                        if (inventory.getItem(1,3) instanceof Armor){
                          if (((int)(Math.random()*100))<((Armor)(inventory.getItem(1,3))).getFlameDefense()){
                          }else{
                            entityMap[playerCurrentY][playerCurrentX].setLightning(true);  
                          }
                        }else{
                          entityMap[playerCurrentY][playerCurrentX].setLightning(true);
                        }
                      }
                    }
                  }
                }
              }
            }
          }
        }
      }
    }
    refreshMobs();
    //Picks up an item if the item pickup was set to true
    if (itemPickup){
      for (int i=0;i<3;i++){
        for (int j=0;j<6;j++){
          if (!(inventory.getItem(j,i) instanceof Item)){
            inventory.setItem(j,i,itemMap[playerCurrentY][playerCurrentX]);
            itemMap[playerCurrentY][playerCurrentX] =null;
          }
        }
      }
      itemPickup = false;
    }
  }
  /**
   *refreshMobs
   *Refreshes all the mobs, so that they can move again
   *@param: 
   *@return: 
   */
  public void refreshMobs(){
    for (int i =0;i<entityMap.length;i++){
      for(int j =0;j<entityMap[0].length;j++){
        if (entityMap[i][j] instanceof Entity){
          entityMap[i][j].setMoved(false);
        }
      }
    }
    for (int k= 0;k<4;k++){
      pathfinderDistance[k]=0;
    }
  }
  /**
   *reversePixelToArray 
   *Reverses the pixel position to the array position
   *@param: The int [] xyPixel, and the boolean melee
   *@return: 
   */
  public void reversePixelToArray (int [] xyPixel, boolean melee){
    if (!(melee)){
      if (xyPixel[0]<maxX/2-50){
        tileSelectedArray[0]=playerCurrentX-(int)(Math.ceil(-((xyPixel[0]-(maxX/2-50))/100.0)));
      }else if (xyPixel[0]>maxX/2+50){
        tileSelectedArray[0]=playerCurrentX+(int)(Math.ceil((xyPixel[0]-(maxX/2+50))/100.0));
      }else{
        tileSelectedArray[0] = playerCurrentX;      
        //The person is clicking the character, the value for the x is the same as the playerCurrentX
      }
      if (xyPixel[1]<maxY/2-50){
        tileSelectedArray[1]=playerCurrentY-(int)(Math.ceil(-((xyPixel[1]-(maxY/2-50))/100.0)));
      }else if (xyPixel[1]>maxY/2+50){
        tileSelectedArray[1]=playerCurrentY+(int)(Math.ceil((xyPixel[1]-(maxY/2+50))/100.0));
      }else{
        tileSelectedArray[1] = playerCurrentY;
      }
      if ((Math.abs(tileSelectedArray[1])==1)&&(Math.abs(tileSelectedArray[0])==1)){
        tileSelectedArray[0] =0;
        tileSelectedArray[1] =0;
      }
    }
  }
  /**
   *checkKilled
   *Checks what is killed
   *@param: The int arrayX, and the int arrayY
   *@return: 
   */
  
  // Checks if any entities have died
  public void checkKilled(int arrayX, int arrayY){
    if ((arrayX==0)&&(arrayY==0)){
      // runs a for through the entire entity map
      for (int i=0;i<entityMap.length;i++){
        for(int j=0;j<entityMap[0].length;j++){
          if (entityMap[i][j] instanceof Entity){
            // If there is something in the array cell, its health is checked
            
            if (entityMap[i][j].getHealth()<=0){ // if it's dead, it's removed
              if (entityMap[i][j] instanceof Character) {
                gameOver = true; // if player dies game is over
              } else {
                mobCount--; // if mob dies than xp and drops are generated.
                dropItems(j, i, 0);
                ((Character)entityMap[playerCurrentY][playerCurrentX]).changeXp(10);
              }
              entityMap[i][j] = null; 
            }
          }
        }
      }
    }else{
      if (entityMap[arrayY][arrayX].getHealth()<=0){
        entityMap[arrayY][arrayX] = null;
        mobCount--;
        dropItems(arrayX,arrayY,0);
        ((Character)entityMap[playerCurrentY][playerCurrentX]).changeXp(10);
      }
    }
  }
  /**
   *updateListeners
   *Updates all the listeners for the turn passer 
   *@param: 
   *@return: 
   */
  public void updateListeners(){
    mouseXy = mouseListener.getMouseXy();
    //If the game is not paused
    if (!(inventoryOpen)){
      //Outside of tiling, as it can be used at any time
      if((mouseListener.getMouseXy()[0] > 253)&&(mouseListener.getMouseXy()[0] < 287)&&(mouseListener.getMouseXy()[1] > maxY-240)&&(mouseListener.getMouseXy()[1] < maxY-130)&&(mouseListener.getPressed())&&(alternateState)&&(minimapFactor > 20)){ //Clicked on top button
        alternateState = false;
        minimapFactor -= 10;
      }else if ((mouseListener.getMouseXy()[0] > 253)&&(mouseListener.getMouseXy()[0] < 287)&&(mouseListener.getMouseXy()[1] > maxY-120)&&(mouseListener.getMouseXy()[1] < maxY-10)&&(mouseListener.getPressed())&&(alternateState)&&(minimapFactor < 100)){ //Clicked on bottom button
        alternateState = false;
        minimapFactor += 10;
      }
      if (!(tiling)){
        keyListener.setAllDirection();
        if (mouseListener.getPressed()&&(alternateState)){
          alternateState = false;
          minButtonX = maxX-142;
          maxButtonX= maxX-22;
          minButtonY = maxY-120;
          maxButtonY = maxY-28;
          //This is for skipping a turn
          if (((mouseListener.getMouseXy()[0] > minButtonX)&&(mouseListener.getMouseXy()[0] < maxButtonX)&&(mouseListener.getMouseXy()[1] > minButtonY)&&(mouseListener.getMouseXy()[1] < maxButtonY))){
            turnPasser = true;
          }
          //This is for opening the inventory
          minButtonX = maxX-(int)(BOT_HEIGHT*INVENTORY_MOD)+85;
          maxButtonX= (int)(BOT_HEIGHT*INVENTORY_MOD)-240+maxX-(int)(BOT_HEIGHT*INVENTORY_MOD)+85;
          minButtonY = maxY-(int)(BOT_HEIGHT)+20;
          maxButtonY = maxY-(int)(BOT_HEIGHT)+20+(int)(BOT_HEIGHT/2.0)-30;
          if ((mouseListener.getMouseXy()[0] >minButtonX)&&(mouseListener.getMouseXy()[0] < maxButtonX)&&(mouseListener.getMouseXy()[1] >minButtonY)&&(mouseListener.getMouseXy()[1] < maxButtonY)&&(!(inventoryOpen))){
            inventoryOpen = true;
          }
          minButtonX = maxX-(int)(BOT_HEIGHT*INVENTORY_MOD)+220;
          maxButtonX= (int)(BOT_HEIGHT*INVENTORY_MOD)-240+maxX-(int)(BOT_HEIGHT*INVENTORY_MOD)+220;
          minButtonY = maxY-(int)(BOT_HEIGHT)+20;
          maxButtonY = maxY-(int)(BOT_HEIGHT)+20+(int)(BOT_HEIGHT/2.0)-30;
          if ((mouseListener.getMouseXy()[0] >minButtonX)&&(mouseListener.getMouseXy()[0] < maxButtonX)&&(mouseListener.getMouseXy()[1] >minButtonY)&&(mouseListener.getMouseXy()[1] < maxButtonY)){
            if (itemMap[playerCurrentY][playerCurrentX] instanceof Item){
              itemPickup = true;
              turnPasser = true;
            }
          }
          ///Melee attack
          reversePixelToArray(mouseListener.getMouseXy(), false);
          if (checkInMap(tileSelectedArray[0], tileSelectedArray[1])){
            if (entityMap[tileSelectedArray[1]][tileSelectedArray[0]] instanceof Enemy){
              if ((Math.abs(tileSelectedArray[0]-playerCurrentX)+Math.abs((tileSelectedArray[1]-playerCurrentY)))==1){
                if (meleeSelected){
                  turnPasser = true;
                  playerAttack(tileSelectedArray[0],tileSelectedArray[1]);
                }
              }
            }
          }
          if (checkInMap(tileSelectedArray[0], tileSelectedArray[1])){
            if (map[tileSelectedArray[1]][tileSelectedArray[0]] instanceof ChestTile){
              if ((Math.abs(tileSelectedArray[0]-playerCurrentX)+Math.abs((tileSelectedArray[1]-playerCurrentY)))==1){
                if (!((ChestTile)(map[tileSelectedArray[1]][tileSelectedArray[0]])).getOpen()){
                  ((ChestTile)(map[tileSelectedArray[1]][tileSelectedArray[0]])).setOpen();
                  if (map[tileSelectedArray[1]][tileSelectedArray[0]].getType().equals("chest")){
                    dropItems (playerCurrentX, playerCurrentY,2);
                  }else{
                    dropItems (playerCurrentX, playerCurrentY,1);
                  }
                }
              }
            }
          }
        }
        findBlocked (playerCurrentX, playerCurrentY);
        if ((!(blocked[0])&&(keyListener.getAllDirection()[1]<0))||(!(blocked[1])&&(keyListener.getAllDirection()[1]>0))||(!(blocked[2])&&(keyListener.getAllDirection()[0]<0))||(!(blocked[3])&&(keyListener.getAllDirection()[0]>0))){  
          turnPasser = true;
        }
      }
      //User clicks zoom in and out buttons
    }else{
      minButtonX = maxX-(int)(BOT_HEIGHT*INVENTORY_MOD)+85;
      maxButtonX= (int)(BOT_HEIGHT*INVENTORY_MOD)-240+maxX-(int)(BOT_HEIGHT*INVENTORY_MOD)+85;
      minButtonY = maxY-(int)(BOT_HEIGHT)+20;
      maxButtonY = maxY-(int)(BOT_HEIGHT)+20+(int)(BOT_HEIGHT/2.0)-30;
      if ((mouseListener.getMouseXy()[0] >minButtonX)&&(mouseListener.getMouseXy()[0] < maxButtonX)&&(mouseListener.getMouseXy()[1] >minButtonY)&&(mouseListener.getMouseXy()[1] < maxButtonY)&&(alternateState)&&(mouseListener.getPressed())&&(inventoryOpen)){
        inventoryOpen = false;
        alternateState = false;
        pauseState =false;
      }else if  ((!(itemSelected))&&(!((mouseListener.getMouseXy()[0] >maxX/2-400)&&(mouseListener.getMouseXy()[0] <maxX/2+400)&&(mouseListener.getMouseXy()[1] >maxY/2-300)&&(mouseListener.getMouseXy()[1] <maxY/2+300)))&&(mouseListener.getPressed())&&(alternateState)){
        inventoryOpen = false;
        alternateState = false;
        pauseState =false;
      }
    }
    if (turnPasser){
      turnPasser = false;
      tiling = true;
      passTurn();
    }
  }
  /**
   *spawnItems
   *Spawns the items at the start of a new floor
   *@param: 
   *@return: 
   */
  public void spawnItems(){
    //First initialize a random number of weapons and armors to spawn across the map
    int meleeWeaponCap =((5-(int)(Math.sqrt(((int)(Math.random()*9))))));
    int rangedWeaponCap =((5-(int)(Math.sqrt(((int)(Math.random()*9))))));
    int armorCap =((7-(int)(Math.sqrt(((int)(Math.random()*49))))));
    int driveCap =((5-(int)(Math.sqrt(((int)(Math.random()*9))))));
    //medicineCap =((4-(int)(Math.sqrt(((int)(Math.random()*9))))));
    int medicineCap =((7-(int)(Math.sqrt(((int)(Math.random()*49))))));
    int foodCap =(5-((int)(Math.random()*3)));
    //Resets the spawn
    spawnX = 0;
    spawnY = 0;
    while(itemCount<meleeWeaponCap){
      do{
        spawnX =(int)(Math.random()*itemMap[0].length);
        spawnY =(int)(Math.random()*itemMap.length);
      }while(!(itemMap[spawnY][spawnX] instanceof Item)&&(!(map[spawnY][spawnX] instanceof FloorTile)));
      itemCount++;
      randomizeMeleeWeapon(spawnX, spawnY);
    }
    itemCount=0;
    while(itemCount<rangedWeaponCap){
      do{
        spawnX =(int)(Math.random()*itemMap[0].length);
        spawnY =(int)(Math.random()*itemMap.length);
      }while(!(itemMap[spawnY][spawnX] instanceof Item)&&(!(map[spawnY][spawnX] instanceof FloorTile)));
      itemCount++;
      randomizeRangedWeapon(spawnX, spawnY);
    }
    itemCount=0;
    while(itemCount<armorCap){
      do{
        spawnX =(int)(Math.random()*itemMap[0].length);
        spawnY =(int)(Math.random()*itemMap.length);
      }while(!(itemMap[spawnY][spawnX] instanceof Item)&&(!(map[spawnY][spawnX] instanceof FloorTile)));
      itemCount++;
      randomizeArmor(spawnX, spawnY);
    }
    itemCount=0;
    
    while (itemCount<driveCap){
      do{
        spawnX =(int)(Math.random()*itemMap[0].length);
        spawnY =(int)(Math.random()*itemMap.length);
      }while(!(itemMap[spawnY][spawnX] instanceof Item)&&(!(map[spawnY][spawnX] instanceof FloorTile)));
      itemCount++;
      randomizeDrive(spawnX, spawnY);
    }
    itemCount=0;
    while(itemCount<medicineCap){
      do{
        spawnX =(int)(Math.random()*itemMap[0].length);
        spawnY =(int)(Math.random()*itemMap.length);
      }while(!(itemMap[spawnY][spawnX] instanceof Item)&&(!(map[spawnY][spawnX] instanceof FloorTile)));
      itemCount++;
      itemMap[spawnY][spawnX] = new MedKit();
    }
    itemCount=0;
    while(itemCount<foodCap){
      do{
        spawnX =(int)(Math.random()*itemMap[0].length);
        spawnY =(int)(Math.random()*itemMap.length);
      }while(!(itemMap[spawnY][spawnX] instanceof Item)&&(!(map[spawnY][spawnX] instanceof FloorTile)));
      itemCount++;
      itemMap[spawnY][spawnX] = new Food();
    }
  }
  /**
   *randomizeMeleeWeapon
   *Randomizes the possible melee weapons
   *@param: The int spawnX, and the int spawnY
   *@return: 
   */
  public void randomizeMeleeWeapon(int spawnX, int spawnY){
    
    itemRarity = ((5-(int)(Math.sqrt(((int)(Math.random()*16))))));
    if (itemRarity==5){
      itemMap[spawnY][spawnX] = new PlasmaRapier(50);
    }else if (itemRarity==4){
      itemMap[spawnY][spawnX] = new GammaHammer(50); 
    }else if (itemRarity==3){
      itemMap[spawnY][spawnX] = new KineticMace(50);
    }else{
      itemMap[spawnY][spawnX] = new EnergySword(50);
    }
  }
  /**
   *randomizeArmor
   *Randomizes the possible armors
   *@param: The int spawnX, and the int spawnY
   *@return: 
   */
  public void randomizeArmor(int spawnX, int spawnY){
    itemRarity = ((5-(int)(Math.sqrt(((int)(Math.random()*16))))));
    if (itemRarity==5){
      itemMap[spawnY][spawnX] = new IridiumExoskeleton(50);
    }else if (itemRarity==4){
      itemMap[spawnY][spawnX] = new ProximityArmor(50); 
    }else if (itemRarity==3){
      itemMap[spawnY][spawnX] = new EnergySuit(50);
    }else{
      itemMap[spawnY][spawnX] = new AssaultVest(50);
    }
  }
  /**
   *randomizeRangedWeapon
   *Randomizes the possible ranged
   *@param: The int spawnX, and the int spawnY
   *@return: 
   */
  public void randomizeRangedWeapon(int spawnX, int spawnY){
    itemRarity = ((5-(int)(Math.sqrt(((int)(Math.random()*16))))));
    if (itemRarity==5){
      itemMap[spawnY][spawnX] = new SolarScorcher(50);
    }else if (itemRarity==4){
      itemMap[spawnY][spawnX] = new PulseRailgun(50); 
    }else if (itemRarity==3){
      itemMap[spawnY][spawnX] = new PlasmaRifle(50);
    }else{
      itemMap[spawnY][spawnX] = new LaserPistol(50);
    }
  }
  /**
   *randomizeDrive
   *Creates a random drive
   *@param: The int spawnX, and the int spawnY
   *@return: 
   */
  public void randomizeDrive(int spawnX, int spawnY){
    driveNumber =(int)(Math.random()*5);
    if (driveNumber==0){
      itemMap[spawnY][spawnX] = new FreezeDrive();
    }else if (driveNumber==1){
      itemMap[spawnY][spawnX] = new FlameDrive();
    }else if (driveNumber==2){
      itemMap[spawnY][spawnX] = new LightningDrive();
    }else if (driveNumber==3){
      itemMap[spawnY][spawnX] = new SteelDrive();
    }else{
      itemMap[spawnY][spawnX] = new PowerDrive();      
    }
  }
  /**
   *playerAttack
   *Attacks the enemies
   *@param: The int targetX, and the int targetY
   *@return: 
   */
  public void playerAttack(int targetX, int targetY){
    attacked = true;
    if (checkInMap(targetX, targetY)){
      if (meleeSelected){
        if ((inventory.getItem(2,3)) instanceof MeleeWeapon){
          ((Equipment)(inventory.getItem(2,3))).setDurability(((Equipment)(inventory.getItem(2,3))).getDurability()-1);
          if ((int)(Math.random()*100)<((Weapon)(inventory.getItem(2,3))).getFreezeChance()){
            if (!(entityMap[targetY][targetX].getFreeze())){
              entityMap[targetY][targetX].setFreeze(true);
              //Armor becomes weaker
              entityMap[targetY][targetX].setArmor(0);
            }
          }
          if ((int)(Math.random()*100)<((Weapon)(inventory.getItem(2,3))).getFlameChance()){
            if (!(entityMap[targetY][targetX].getFlame())){
              entityMap[targetY][targetX].setFlame(true);
              //Draw a little icon above the heads of the enemies with the status
            }
          }
          if ((int)(Math.random()*100)<((Weapon)(inventory.getItem(2,3))).getLightningChance()){
            if (!(entityMap[targetY][targetX].getLightning())){
              entityMap[targetY][targetX].setLightning(true);
            }
          }
          //Defense will block up to %80 of damage
          int damage;
          if (entityMap[targetY][targetX].getArmor()>=((double)(((Weapon)(inventory.getItem(2,3))).getDamage()))*0.8){
            damage =(int)((Math.ceil((((double)((Weapon)(inventory.getItem(2,3))).getDamage()))*0.2)));
          }else{
            damage = (((Weapon)(inventory.getItem(2,3))).getDamage()-entityMap[targetY][targetX].getArmor());     
          }
          entityMap[targetY][targetX].setHealth(entityMap[targetY][targetX].getHealth()-damage);
        }
      }else if (!(meleeSelected)){
        if ((inventory.getItem(0,3)) instanceof RangedWeapon){
          ((Equipment)(inventory.getItem(0,3))).setDurability(((Equipment)(inventory.getItem(0,3))).getDurability()-1);
          if ((int)(Math.random()*100)<((Weapon)(inventory.getItem(0,3))).getFreezeChance()){
            if (!(entityMap[targetY][targetX].getFreeze())){
              entityMap[targetY][targetX].setFreeze(true);
              //Armor becomes weaker
              entityMap[targetY][targetX].setArmor(0);
            }
          }
          if ((int)(Math.random()*100)<((Weapon)(inventory.getItem(0,3))).getFlameChance()){
            if (!(entityMap[targetY][targetX].getFlame())){
              entityMap[targetY][targetX].setFlame(true);
              //Draw a little icon above the heads of the enemies with the status
            }
          }
          if ((int)(Math.random()*100)<((Weapon)(inventory.getItem(0,3))).getLightningChance()){
            if (!(entityMap[targetY][targetX].getLightning())){
              entityMap[targetY][targetX].setLightning(true);
            }
          }
          //Defense will block up to %80 of damage
          int damage;
          if (entityMap[targetY][targetX].getArmor()>=((double)(((Weapon)(inventory.getItem(0,3))).getDamage()))*0.8){
            damage =(int)((Math.ceil((((double)((Weapon)(inventory.getItem(0,3))).getDamage()))*0.2)));
          }else{
            damage = (((Weapon)(inventory.getItem(0,3))).getDamage()-entityMap[targetY][targetX].getArmor());     
          }
          entityMap[targetY][targetX].setHealth(entityMap[targetY][targetX].getHealth()-damage);
        }
      }
      //Checks if the entity has been killed, so that it will not be able to attack afterwards
      checkKilled(targetX, targetY);
    }
  }
  /**
   *checkBroken
   *Breaks items
   *@param: 
   *@return: 
   */
  public void checkBroken(){
    for (int i=0;i<4;i++){
      for (int j=0;j<6;j++){
        if (inventory.getItem(j,i) instanceof Equipment){
          if (((Equipment)(inventory.getItem(j,i))).getDurability()<=0){
            inventory.setItem(j,i,null);
          }
        }
      }
    }
  }
  /**
   *dropItems
   *Drops items upon death or chests
   *@param: The int arrayX, the int arrayY, and the int situation
   *@return: 
   */
  public void dropItems(int arrayX, int arrayY, int situation){
    int itemNum;
    int chance;
    if (situation==0){
      //Mob drops
      chance = 15;
    }else if (situation==1){
      //Safes with items
      chance = 50;
    }else{
      //Chests with items
      chance =100;
    }
    if (!(itemMap[arrayY][arrayX] instanceof Item)){
      //5% chance to spawn an item
      //Maybe up?
      if ((int)(Math.random()*100)<chance){
        itemNum = (int)(Math.random()*5);
        if (itemNum==0){
          randomizeDrive(arrayX, arrayY);
        }else if(itemNum==1){
          randomizeArmor(arrayX, arrayY);
        }else if(itemNum==2){
          randomizeMeleeWeapon(arrayX, arrayY);
        }else if (itemNum==3){
          randomizeRangedWeapon(arrayX, arrayY);
        }else if (itemNum==4){
          itemMap[spawnY][spawnX] = new MedKit();
        }
      }
    }
  }
  /**
   *refreshStats
   *Refreshes the armor and the defense
   *@param: 
   *@return: 
   */
  public void refreshStats(){
    if (inventory.getItem(1,3) instanceof Armor){
      entityMap[playerCurrentY][playerCurrentX].setArmor(((Armor)(inventory.getItem(1,3))).getDefense());
    }else{
      entityMap[playerCurrentY][playerCurrentX].setArmor(0);
    }
  }
  /**
   *drawStatus
   *Draws the status effects
   *@param: The Graphics g, the int entityDirection, the int j, and the int i
   *@return: 
   */
  public void drawStatus(Graphics g, int entityDirection, int j, int i){
    int statusSkip = 0;
    int iMod=0;
    int jMod=0;
    if (entityDirection==0){
      iMod = 1;
    }else if(entityDirection==1){
      iMod=-1;
    }else if(entityDirection==2){
      jMod=1;
    }else if(entityDirection==3){
      jMod=-1;
    }
    if (entityMap[i][j].getLightning()){
      g.drawImage (lightningStatus,statusSkip+maxX/2+(j+jMod)*TILE_SIZE-bg.getX()-(TILE_SIZE/2)-(TILE_SIZE*playerStartingX) +(entityMap[i][j].getTileXMod()), maxY/2+(i+iMod)*TILE_SIZE-bg.getY()-(TILE_SIZE/2)-(TILE_SIZE*playerStartingY)+(entityMap[i][j].getTileYMod())-45, 30, 30,this);
      statusSkip=statusSkip+35;
    }
    if (entityMap[i][j].getFreeze()){
      g.drawImage (freezeStatus,statusSkip+maxX/2+(j+jMod)*TILE_SIZE-bg.getX()-(TILE_SIZE/2)-(TILE_SIZE*playerStartingX) +(entityMap[i][j].getTileXMod()), maxY/2+(i+iMod)*TILE_SIZE-bg.getY()-(TILE_SIZE/2)-(TILE_SIZE*playerStartingY)+(entityMap[i][j].getTileYMod())-45, 30, 30,this);      
      statusSkip=statusSkip+35;
    }
    if (entityMap[i][j].getFlame()){
      g.drawImage (flameStatus,statusSkip+maxX/2+(j+jMod)*TILE_SIZE-bg.getX()-(TILE_SIZE/2)-(TILE_SIZE*playerStartingX) +(entityMap[i][j].getTileXMod()), maxY/2+(i+iMod)*TILE_SIZE-bg.getY()-(TILE_SIZE/2)-(TILE_SIZE*playerStartingY)+(entityMap[i][j].getTileYMod())-45, 30, 30,this);     
    }
  }
  /**
   *checkInMap
   *Checks if the coordinates are in map
   *@param: The int x, and the int y
   *@return: A boolean
   */
  public boolean checkInMap(int x, int y){
    if ((x>=0)&&(x<map[0].length)&&(y>=0)&&(y<map.length)){
      return (true);
    }else{
      return (false);
    }
  }
  /**
   *getAnotherMap
   *Returns whether or not a new map is required
   *@param: 
   *@return: A boolean
   */
  public boolean getAnotherMap(){
    if (anotherMap){
      anotherMap = false;
      return (true);
    }else{
      return (false);
    }
  }
  /**
   *getFloor
   *Gets the floor level
   *@param: 
   *@return: An int
   */
  public int getFloor(){
    return (floorLevel);
  }
  /**
   *setBoss
   * Sets the boss position
   *@param: The int bossX, and the int bossY
   *@return: 
   */
  public void setBoss(int bossX, int bossY){
    this.bossX = bossX;
    this.bossY = bossY;
  }
}