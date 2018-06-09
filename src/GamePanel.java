//BUGS TO FIX
//Hitting enemies can make you pass through them
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
  
  //Listeners
  private boolean mouseReleased;
  private int [] mouseXy;
  private CustomKeyListener keyListener = new CustomKeyListener();
  private CustomMouseListener mouseListener = new CustomMouseListener();
  private boolean movementRestriction;
  
  //Images
  private Image left, leftClickedPlus, leftClickedMinus, right, rightClicked, exp, hp, hotbar,mapBorder, inventoryImage;
  private final double Y_TO_X = 90.0/75.0;
  private final double INVENTORY_MOD = 110.0/75.0;
  private final double INV_Y_TO_X = 160.0/212.0;
  private final double Y_TO_X_HOT = 778.0/135.0;
  private final double BOT_HEIGHT = 250.0;
  private boolean inventoryOpen =false;
  private int minButtonX;
  private int maxButtonX;
  private int minButtonY;
  private int maxButtonY;
  private boolean plusPressed;
  private boolean minusPressed;
  
  //Turn tracking
  private boolean turnStart = false;
  private int turnCount =0;
  private boolean pauseState =false;
  
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
  private int playerStartingX, playerStartingY;
  private int playerCurrentX, playerCurrentY;
  private boolean [] blocked = new boolean [4];
  private int [] xyDirection = new int [2];
  
  //Enemy control
  private Entity [][] entityMap;
  private int spawnX;
  private int spawnY;
  private int MOB_CAP = 40;
  private int mobCount =0;
  private int directionRand;
  private int entityArrayXMod = 0;
  private int entityArrayYMod = 0;
  private double closestPath;
  private double [] pathfinderDistance = new double [5];
  private int closestDirection;
  private int []pathfinderPriority = new int [5];
  private boolean acceptableSpawn = false;
  
  // Fire control
  private int[] fireTarget;
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
  private int ITEM_CAP = 10;
  private boolean itemPickup = false;
  private int turnTransitionCounter  =0;
  private boolean turnPasser = false;
  
  //Attacking
  private int [] tileSelectedArray = new int [2];  
  
  //Game over control
  private boolean gameOver;
  
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
    this.mapBorder = Toolkit.getDefaultToolkit().getImage("../res/MapNoBorder.png"); //duplicate name
    this.inventoryImage = Toolkit.getDefaultToolkit().getImage("../res/Inventory.png");
    //Initializes minimap size
    this.minimapX = (int)(BOT_HEIGHT);
    this.minimapY = (int)(BOT_HEIGHT);
    this.gameOver = false;
  }
  
  //Methods that are inherited from JPanel
  @Override
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
    
    //Checks for which entities are killed, including the player
    checkKilled();
    
    if (!gameOver) {
      //Draw map (background)
      drawMap(g);
      updateListeners();
      determineTiling();
      
      //Draws the items
      drawItems (g);
      //Draws the entities
      drawAllEntity (g);
      //Draws bullet sprites
      drawBullets (g, playerFireController);
      //Draw the game components
      drawGameComponents(g);
      //Draws the minimap
      drawMinimap(g);
      //Draw the health and exp
      drawBars(g);
      //Draw inventory
      drawInventory(g);
      //Draw the debugPanel
      if (keyListener.getDebugState()){
        drawDebugPanel(g);
        g.setColor(Color.RED);
        g.fillRect(maxX/2, maxY/2, 2, 2);
      }
      this.setVisible(true);
    } else {
      System.out.println("You suck!");    
    }
  }
    
  public void refresh(){
    this.repaint();
  }
  //End of methods that are inherited from JPanel
  
  public boolean returnGameOver() {
    return this.gameOver;
  }
  
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
  public void drawGameComponents(Graphics g){
    //Sees if the mouse is clicking the skip turn button
    //Bottom toolbar
    g.drawImage(left,0,maxY-(int)(BOT_HEIGHT),(int)(BOT_HEIGHT*Y_TO_X),(int)(BOT_HEIGHT),this);
    if (inventoryOpen){
      g.drawImage(rightClicked,maxX-(int)(BOT_HEIGHT*Y_TO_X),maxY-(int)(BOT_HEIGHT), (int)(BOT_HEIGHT*Y_TO_X), (int)(BOT_HEIGHT),this);
    }else{
      g.drawImage(right,maxX-(int)(BOT_HEIGHT*Y_TO_X),maxY-(int)(BOT_HEIGHT), (int)(BOT_HEIGHT*Y_TO_X), (int)(BOT_HEIGHT),this);
    }
    g.drawImage(hotbar,(int)(maxX/2.0-(BOT_HEIGHT/2.0*(Y_TO_X_HOT/2.0))),maxY-(int)(BOT_HEIGHT/2.0),(int)(BOT_HEIGHT/2.0*Y_TO_X_HOT), (int)(BOT_HEIGHT/2.0),this);
    for (int j=0;j<6;j++){
      if (inventory.getItem(j,3) instanceof Item){
        xInvPixel =(int)(maxX/2.0-(BOT_HEIGHT/2.0*(Y_TO_X_HOT/2.0))+((BOT_HEIGHT/2.0*Y_TO_X_HOT)*12.0/778.0)+(j*BOT_HEIGHT/2.0*Y_TO_X_HOT*128.0/778.0));
        yInvPixel = (int)(maxY-(int)(BOT_HEIGHT/2.0)+((int)(BOT_HEIGHT/2.0)*11.0/135.0))-1;
        inventory.drawSelectedItem(g,j,3, xInvPixel, yInvPixel, (int)(BOT_HEIGHT/2.0*Y_TO_X_HOT*113.0/778.0), (int)(BOT_HEIGHT/2.0*Y_TO_X_HOT*113.0/778.0), this);
      }
    }
    //Hp and exp bars
    g.drawImage(hp,10,10, ((int)(maxX*0.2)),  ((int)(maxX*0.2/200.0*14.0)),this);
    g.drawImage(exp,10,15+ ((int)(maxX*0.2/200.0*14.0)),((int)(maxX*0.2)), ((int)(maxX*0.2/200.0*10.0)),this);
    ///Draw in all the button click shading
    if (!(inventoryOpen)){
      if (mouseListener.getAlternateButton()){
        if (!(pauseState)){
          if ((mouseListener.getMouseXy()[0] > 253)&&(mouseListener.getMouseXy()[0] < 287)&&(mouseListener.getMouseXy()[1] > maxY-240)&&(mouseListener.getMouseXy()[1] < maxY-130)){
            g.drawImage(leftClickedPlus,0,maxY-(int)(BOT_HEIGHT),(int)(BOT_HEIGHT*Y_TO_X),(int)(BOT_HEIGHT),this);
          }else if ((mouseListener.getMouseXy()[0] > 253)&&(mouseListener.getMouseXy()[0] < 287)&&(mouseListener.getMouseXy()[1] > maxY-120)&&(mouseListener.getMouseXy()[1] < maxY-10)){
            g.drawImage(leftClickedMinus,0,maxY-(int)(BOT_HEIGHT),(int)(BOT_HEIGHT*Y_TO_X),(int)(BOT_HEIGHT),this);
          }
          ///Shade in the buttons that are pressed when pausing
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
        } 
      }
    }
  }
  public void determineTiling(){
    bg.setOnTile();
    //System.out.println (blocked [0]+" | "+blocked [1]+" | "+blocked [2]+" | "+blocked [3]);
    //Sets off tiling for the reset of movement
    if (bg.getOnTile()&&(turnTransitionCounter==10)){
      tiling = false;
      turnTransitionCounter=0;
    }
    //The first part of the code will determine if the player array can move given the key listeners
    if (tiling){
      //A turn is set, and it is added
      //Move all of the entities slowly
      //Moving the bg is a prerequisite for everything else to move
      turnTransitionCounter++;
      xyDirection =keyListener.getAllDirection();
      bg.setXDirection (xyDirection[0]);
      bg.setYDirection (xyDirection[1]);
      if (!(movementRestriction)){
        bg.move();
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
  
  public void drawFog(int x, int y, int count){
    if (map[y][x] instanceof Tile){
      map[y][x].setViewed();
      map[y][x].setFocus(true);
      if (entityMap[y][x] instanceof Enemy){
        ((Enemy)(entityMap[y][x])).setEnraged(true);
      }
    }
    if(count <= 3){ //If within range
      for(int i = -1; i <= 1; i ++){
        for(int j = -1; j <= 1; j++){
          if ((y+i>=0)&&(y+i<map.length)&&(x+j>=0)&&(x+j<map[0].length)){
            if(map[y+i][x+j] instanceof Tile){
              if((map[y][x].getMinimapColor() == Color.GREEN) || (map[y][x].getMinimapColor() == Color.YELLOW) && map[y+i][x+j].getMinimapColor() != Color.WHITE){ //Avoids corner sight, in room tile
                drawFog(x+j, y+i, count+1);
              } else if(map[y][x].getMinimapColor() == Color.WHITE){ //In hall/airlock tile
                map[y+i][x+j].setViewed();
                map[y+i][x+j].setFocus(true);
                if((map[y+i][x+j].getMinimapColor() == Color.WHITE)){
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
              } else if(map[y][x].getMinimapColor() == Color.CYAN && map[y+i][x+j].getMinimapColor() != Color.WHITE){ //Avoids corner sight, in chest room tile
                drawFog(x+j, y+i, count+1);
              } else if((map[y][x] instanceof DoorTile) && (map[playerCurrentY][playerCurrentX] instanceof DoorTile) && !(map[y+i][x+j] instanceof DoorTile)){ //In door tile
                map[y+i][x+j].setViewed();
                map[y+i][x+j].setFocus(true);
                if((map[y+i][x+j].getMinimapColor() == Color.WHITE) || (map[y+i][x+j].getMinimapColor() == Color.ORANGE)){
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
  public void drawFog(int x, int y, int count, int direction){
    if((y>=0) && (y<map.length) && (x>=0) && (x<map[0].length)){
      if (map[y][x] instanceof Tile){
        map[y][x].setViewed();
        map[y][x].setFocus(true);
      }
      if((count <= 2) && (map[y][x].getMinimapColor() == Color.WHITE)){
        for(int i = -1; i <= 1; i ++){
          for(int j = -1; j <= 1; j++){
            if((y+i>=0) && (y+i<map.length) && (x+j>=0) && (x+j<map[0].length)){
              if((map[y+i][x+j].getMinimapColor() != Color.WHITE) || ((Math.abs(i+j)==1) && (count <= 0))){
                map[y+i][x+j].setViewed();
                map[y+i][x+j].setFocus(true);
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
  }
  
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
        if ((minimapArrayY>0)&&(minimapArrayY<map.length)&&(minimapArrayX>0)&&(minimapArrayX<map[0].length)){ //If tiles are in view window
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
  public void drawItems(Graphics g){
    for (int i = 0;i<itemMap.length;i++){
      for (int j = 0;j<itemMap[0].length;j++){
        if(itemMap[i][j] instanceof Item){
          itemMap[i][j].drawItem(g, maxX/2+j*TILE_SIZE-bg.getX()-(TILE_SIZE/2)-(TILE_SIZE*playerStartingX), maxY/2+i*TILE_SIZE-bg.getY()-(TILE_SIZE/2)-(TILE_SIZE*playerStartingY), TILE_SIZE, TILE_SIZE, this);
        }
      }
    }
  }
  public void drawAllEntity(Graphics g){
    //System.out.println (playerCurrentX+ " and "+ playerCurrentY);
    for (int i = 0;i<entityMap.length;i++){
      for (int j = 0;j<entityMap[0].length;j++){
        if(entityMap[i][j] instanceof Enemy){
          //  System.out.println ("Enemy: "+i +" and " +j);
          if (entityMap[i][j].getTiling ()==0){
            entityMap[i][j].drawEntity(g, maxX/2+j*TILE_SIZE-bg.getX()-(TILE_SIZE/2)-(TILE_SIZE*playerStartingX) +(entityMap[i][j].getTileXMod()), maxY/2+(i+1)*TILE_SIZE-bg.getY()-(TILE_SIZE/2)-(TILE_SIZE*playerStartingY)+(entityMap[i][j].getTileYMod()), TILE_SIZE, TILE_SIZE, this);
          }else if (entityMap[i][j].getTiling ()==1){
            entityMap[i][j].drawEntity(g, maxX/2+j*TILE_SIZE-bg.getX()-(TILE_SIZE/2)-(TILE_SIZE*playerStartingX) +(entityMap[i][j].getTileXMod()), maxY/2+(i-1)*TILE_SIZE-bg.getY()-(TILE_SIZE/2)-(TILE_SIZE*playerStartingY)+(entityMap[i][j].getTileYMod()), TILE_SIZE, TILE_SIZE, this);
          }else if (entityMap[i][j].getTiling ()==2){
            entityMap[i][j].drawEntity(g, maxX/2+(j+1)*TILE_SIZE-bg.getX()-(TILE_SIZE/2)-(TILE_SIZE*playerStartingX) +(entityMap[i][j].getTileXMod()), maxY/2+i*TILE_SIZE-bg.getY()-(TILE_SIZE/2)-(TILE_SIZE*playerStartingY)+(entityMap[i][j].getTileYMod()), TILE_SIZE, TILE_SIZE, this);
          }else if (entityMap[i][j].getTiling ()==3){
            entityMap[i][j].drawEntity(g, maxX/2+(j-1)*TILE_SIZE-bg.getX()-(TILE_SIZE/2)-(TILE_SIZE*playerStartingX) +(entityMap[i][j].getTileXMod()), maxY/2+i*TILE_SIZE-bg.getY()-(TILE_SIZE/2)-(TILE_SIZE*playerStartingY)+(entityMap[i][j].getTileYMod()), TILE_SIZE, TILE_SIZE, this);
          }else{
            entityMap[i][j].drawEntity(g, maxX/2+j*TILE_SIZE-bg.getX()-(TILE_SIZE/2)-(TILE_SIZE*playerStartingX), maxY/2+(i)*TILE_SIZE-bg.getY()-(TILE_SIZE/2)-(TILE_SIZE*playerStartingY), TILE_SIZE, TILE_SIZE, this);
          }
        }else if (entityMap[i][j] instanceof Character){
          entityMap[i][j].drawEntity(g, maxX/2-(TILE_SIZE/2),maxY/2-(TILE_SIZE/2),TILE_SIZE, TILE_SIZE, this);
        }
      }
    }
  }
  
  public void drawBullets(Graphics g, FireController playerFireController){
    playerFireController.setupProjectile(mouseListener.getMouseXy()[0], mouseListener.getMouseXy()[1], 100);
    double shootAngle = playerFireController.returnAngle();
    translateX += Math.cos(shootAngle)*10;
    translateY += Math.sin(shootAngle)*10;
    debugMessage = Double.toString(Math.toDegrees(shootAngle));
    Graphics2D g2 = (Graphics2D) g;
    g2.setStroke(new BasicStroke(5));
    g.setColor(Color.RED);
    if(!mouseListener.getReleased()){
      if(collided){
        collided = false;
        translateX = 0;
        translateY = 0;
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
      if(map[bulletY][bulletX] instanceof WallTile || map[bulletY][bulletX] instanceof DoorTile || entityMap[bulletY][bulletX] instanceof Enemy){
        collided = true;
      }
    }
    //System.out.println();
  }
  
  public void drawBars(Graphics g){
    //Fill Hp, can be modified through the width
    g.setColor (new Color (69,218,215));
    
    int currHealth, healthCap;
    
    if (entityMap[playerCurrentY][playerCurrentX] == null) {
      
      currHealth = 0;
      healthCap = 1;
      gameOver = true;
        
    } else {
      
      currHealth = entityMap[playerCurrentY][playerCurrentX].getHealth();
      healthCap = entityMap[playerCurrentY][playerCurrentX].getCap();
    
    }
    debugMessage = (Integer.toString(currHealth) + " " +  Integer.toString(healthCap));
    
    g.fillRect (16,16,(int)((((int)(maxX*1.0/5.0))-12)*((double)currHealth)/(double)healthCap), ((int)(maxX*1.0/5.0/200.0*14.0))-12);
    //Fill Exp, can be modified through the width
    g.setColor (new Color (152,251,152));
    g.fillRect (16,21+((int)(maxX*1.0/5.0/200.0*14.0)), ((int)(maxX*1.0/5.0))-12,((int)(maxX*1.0/5.0/200.0*10.0))-12);
    
    for (int i = 0; i < entityMap.length; i++) {
      for (int j = 0; j < entityMap[0].length; j++) {
        if (entityMap[i][j] instanceof Enemy) {
          
        
        }                
      }
    }
  }
  
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
          if (!(itemSelected)){
            if  ((inventory.getItem(j,i) instanceof Item)){
              if ((mouseListener.getMouseXy()[0] >minSelectX)&&(mouseListener.getMouseXy()[0] < maxSelectX)&&(mouseListener.getMouseXy()[1] >minSelectY)&&(mouseListener.getMouseXy()[1] < maxSelectY)&&(mouseListener.getReleased())){
                mouseListener.setReleased(false);
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
            if ((mouseListener.getMouseXy()[0] >minSelectX)&&(mouseListener.getMouseXy()[0] < maxSelectX)&&(mouseListener.getMouseXy()[1] >minSelectY)&&(mouseListener.getMouseXy()[1] < maxSelectY)&&(mouseListener.getReleased())){
              mouseListener.setReleased(false);
              inventory.setSelected(selectedItemPosition[0],selectedItemPosition[1], false);
              itemSelected = false;
              if (((j==selectedItemPosition[0])&&(i==selectedItemPosition[1]))){
                //Nothing will occur, rewrite this section of code
                //However, this statement must be first
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
                inventory.swap(j,i,selectedItemPosition[0],selectedItemPosition[1]);
              }
            }else if ((!((mouseListener.getMouseXy()[0] >maxX/2-400)&&(mouseListener.getMouseXy()[0] <maxX/2+400)&&(mouseListener.getMouseXy()[1] >maxY/2-300)&&(mouseListener.getMouseXy()[1] <maxY/2+300)))&&(mouseListener.getReleased())){
              mouseListener.setReleased(false);
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
          if (inventory.getItem(j,i) instanceof Item){
            inventory.drawSelectedItem(g,j,i, xInvPixel, yInvPixel, 113, 113, this);
          }
        }
      }
    }else{
      if (inventory.getItem(selectedItemPosition[0],selectedItemPosition[1]) instanceof Item){
        inventory.setSelected(selectedItemPosition[0],selectedItemPosition[1], false);
        itemSelected = false;
      }
    }
  }
  //Sets up the map so that setting the floor is easier as well
  public void createMap(Tile [][]map, int playerStartingX, int playerStartingY){
    //Initializes player locations
    this.map = map;
    this.entityMap = new Entity [map.length][map[0].length];
    this.itemMap = new Item [map.length][map[0].length];
    this.playerStartingX = playerStartingX;
    this.playerStartingY =playerStartingY;
    playerCurrentX = playerStartingX;
    playerCurrentY = playerStartingY;
    entityMap[playerStartingY][playerStartingX]= new Character(100,100,1,1,0,Color.BLUE);
    //Creates all the items on the floor
    while (itemCount<ITEM_CAP){
      //Resets the spawn
      spawnX = 0;
      spawnY = 0;
      do{
        spawnX =(int)(Math.random()*entityMap[0].length);
        spawnY =(int)(Math.random()*entityMap.length);
      }while(!(itemMap[spawnY][spawnX] instanceof Item)&&(!(map[spawnY][spawnX] instanceof FloorTile)));
      itemCount++;
      //For now, just spawn armor
      //    int randomItemNumber;
      itemMap[spawnY][spawnX] = new AssaultVest (100);  
    }
    inventory.setItem(1,3,new SpaceSuit (100));
  }
  
  //Getters and setters
  //There is no getter for the following, as it only needs to be accessed from this class
  //Sets all the information for the debug panel
  public void setDebugInfo(int fps, double totalMem, double memUsed){
    this.fps = Integer.toString(fps);
    this.totalMem = totalMem;
    this.memUsed = memUsed;
    memPercent = (memUsed/totalMem)*100;
  }
  //Retrieves whether or not it is a new floor
  public boolean getNewFloor(){
    return (newFloor);
  }
  //Sends back if the floor is new or not; this is useful for understanding what code to run
  public void setNewFloor(boolean newFloor){
    this.newFloor = newFloor;
  }
  public void passTurn (){
    for (int i =0;i<entityMap.length;i++){
      for(int j =0;j<entityMap[0].length;j++){
        if (entityMap[i][j] instanceof Enemy){
          entityMap[i][j].setTiling (4);
        }
      }
    }
    //5 % chance to spawn
    //Spawning method, this is the first thing that will occur
    if (((int)(Math.random()*100)<10)&&(mobCount<MOB_CAP)){
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
      entityMap[spawnY][spawnX] = new Enemy (100,100,1,1,0,Color.MAGENTA, false);
    }
    findBlocked (playerCurrentX, playerCurrentY);
    //Set all array postion
    for (int i =0;i<entityMap.length;i++){
      for(int j =0;j<entityMap[0].length;j++){
        if (entityMap[i][j] instanceof Character){
          if((!(blocked[0])&&(keyListener.getAllDirection()[1]<0))||(!(blocked[1])&&(keyListener.getAllDirection()[1]>0))||(!(blocked[2])&&(keyListener.getAllDirection()[0]<0))||(!(blocked[3])&&(keyListener.getAllDirection()[0]>0))){
            movementRestriction = false;
            if (!(entityMap[i][j].getMoved())){
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
              ///Possibly make tiling true;
            }
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
              if ((blocked[0])&&(blocked[1])&&(blocked[2])&&(blocked[3])){
                directionRand = 4;
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
                  //THE CODE BELOW AY BE UNECESSARY
                  if (((int)(Math.abs(playerCurrentX-j)+(int)(Math.abs(playerCurrentY-i))))==1){
                    directionRand=4;
                  }
                }else{
                  do{
                    directionRand=((int)(Math.random()*4));
                  }while (blocked[directionRand]);
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
                  
                  int tempHealth = entityMap[playerCurrentY][playerCurrentX].getHealth();
                  
                  if (i + 1 == playerCurrentY && j == playerCurrentX) {
                    entityMap[playerCurrentY][playerCurrentX].setHealth(tempHealth - 10);
                  }
                  if (i == playerCurrentY && j + 1 == playerCurrentX) {
                    entityMap[playerCurrentY][playerCurrentX].setHealth(tempHealth - 10);
                  }
                  if (i - 1 == playerCurrentY && j == playerCurrentX) {
                    entityMap[playerCurrentY][playerCurrentX].setHealth(tempHealth - 10);
                  }
                  if (i == playerCurrentY && j - 1 == playerCurrentX) {
                    entityMap[playerCurrentY][playerCurrentX].setHealth(tempHealth - 10);
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
  public void reversePixelToArray (int [] xyPixel){
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
  }
  public void checkKilled(){
    for (int i=0;i<entityMap.length;i++){
      for(int j=0;j<entityMap[0].length;j++){
        if (entityMap[i][j] instanceof Entity){
          if (entityMap[i][j].getHealth()<=0){
            entityMap[i][j] = null;
            if (entityMap[i][j] instanceof Character) {
              gameOver = true;
            } else {
              mobCount--;
            }
          }
        }
      }
    }
  }
  public void updateListeners(){
    mouseXy = mouseListener.getMouseXy();
    mouseReleased = mouseListener.getReleased();
    //If the game is not paused
    if (!(inventoryOpen)){
      //Outside of tiling, as it can be used at any time
      if((mouseListener.getMouseXy()[0] > 253)&&(mouseListener.getMouseXy()[0] < 287)&&(mouseListener.getMouseXy()[1] > maxY-240)&&(mouseListener.getMouseXy()[1] < maxY-130)&&(mouseListener.getReleased())&&(minimapFactor > 20)){ //Clicked on top button
        mouseListener.setReleased (false);
        minimapFactor -= 10;
      }else if ((mouseListener.getMouseXy()[0] > 253)&&(mouseListener.getMouseXy()[0] < 287)&&(mouseListener.getMouseXy()[1] > maxY-120)&&(mouseListener.getMouseXy()[1] < maxY-10)&&(mouseListener.getReleased())&&(minimapFactor < 100)){ //Clicked on bottom button
        mouseListener.setReleased (false);
        minimapFactor += 10;
      }
      //Wait a turn button
      if (!(tiling)){
        keyListener.setAllDirection();
        if (mouseListener.getReleased()){
          mouseListener.setReleased (false);
          //This is for waiting a turn
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
          reversePixelToArray(mouseListener.getMouseXy());
          ///Attack enemies (2 hit kill)
          if (((int)(Math.abs(playerCurrentX-tileSelectedArray[0])+(int)(Math.abs(playerCurrentY-tileSelectedArray[1]))))==1){
            if (entityMap[tileSelectedArray[1]][tileSelectedArray[0]] instanceof Enemy){
              System.out.print ("w");
              entityMap[tileSelectedArray[1]][tileSelectedArray[0]].setHealth(entityMap[tileSelectedArray[1]][tileSelectedArray[0]].getHealth()-50);
              turnPasser = true;
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
      if ((mouseListener.getMouseXy()[0] >minButtonX)&&(mouseListener.getMouseXy()[0] < maxButtonX)&&(mouseListener.getMouseXy()[1] >minButtonY)&&(mouseListener.getMouseXy()[1] < maxButtonY)&&(mouseListener.getReleased())&&(inventoryOpen)){
        mouseListener.setReleased (false);
        inventoryOpen = false;
        pauseState =false;
      }
    }
    if (turnPasser){
      turnPasser = false;
      tiling = true;
      passTurn();
      turnCount++;
    }
  }
}