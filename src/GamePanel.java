import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

class GamePanel extends JPanel{
  //Debug
  private int stringLength;
  private String fps;
  private Font menuFont = new Font("Courier New", Font.PLAIN, 20);
  private int [] mouseXy;
  private double totalMem, memUsed, memPercent;
  private String debugMessage = "NULL";
  
  //Listeners
  private boolean mouseClicked;
  private CustomKeyListener keyListener = new CustomKeyListener();
  private CustomMouseListener mouseListener = new CustomMouseListener();
  
  //Images
  private Image left, leftClickedPlus, leftClickedMinus, right, middle, exp, hp, hotbar,mapBorder;
  private final double Y_TO_X = 110.0/75.0;
  private final double Y_TO_X_HOT = 208.0/75.0;
  private final double BOT_HEIGHT = 250.0;
  
  //Sizes
  private int maxX= 0, maxY= 0;
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
  private int MOB_CAP = 10;
  private int mobCount =0;
  private int directionRand;
  private int entityArrayXMod = 0;
  private int entityArrayYMod = 0;
  
  // Fire control
  private int[] fireTarget;
  
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
    this.middle = Toolkit.getDefaultToolkit().getImage("../res/MetalM.png");
    this.exp = Toolkit.getDefaultToolkit().getImage("../res/ExpBar.png");
    this.hp = Toolkit.getDefaultToolkit().getImage("../res/HpBar.png");
    this.hotbar = Toolkit.getDefaultToolkit().getImage("../res/Hotbar.png");
    this.mapBorder = Toolkit.getDefaultToolkit().getImage("../res/MapNoBorder.png"); //duplicate name
    //Initializes minimap size
    this.minimapX = (int)(BOT_HEIGHT);
    this.minimapY = (int)(BOT_HEIGHT);
  }
  
  //Methods that are inherited from JPanel
  @Override
  public void paintComponent(Graphics g){
    super.paintComponent(g);
    //Required to have focus so that the listeners work
    this.requestFocusInWindow();
    setDoubleBuffered(true);
    if (maxX==0){
      this.maxX= (int)this.getSize().getWidth();
      this.maxY =(int)this.getSize().getHeight();
      this.setPreferredSize(this.getSize());
    }
    //Update the listeners
    mouseXy = mouseListener.getMouseXy();
    mouseClicked = mouseListener.getClicked();
    //Draw map (background)
    drawMap(g);
    //Draws the entities
    drawEntity (g);
    //Draw the game components
    drawGameComponents(g);
    //Draws the minimap
    drawMinimap(g);
    //Draw the health and exp
    drawBars(g);
    //Draw the debugPanel
    if (keyListener.getDebugState()){
      drawDebugPanel(g);
    }
    this.setVisible(true);
    
  }
  public void refresh(){
    this.repaint();
  }
  //End of methods that are inherited from JPanel
  
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
    //Bottom toolbar
    g.drawImage(left,0,maxY-(int)(BOT_HEIGHT),(int)(BOT_HEIGHT*Y_TO_X),(int)(BOT_HEIGHT),this);
    if (mouseListener.getAlternateButton()){
      if ((mouseListener.getMouseXy()[0] > 253)&&(mouseListener.getMouseXy()[0] < 287)&&(mouseListener.getMouseXy()[1] > maxY-240)&&(mouseListener.getMouseXy()[1] < maxY-130)){
        g.drawImage(leftClickedPlus,0,maxY-(int)(BOT_HEIGHT),(int)(BOT_HEIGHT*Y_TO_X),(int)(BOT_HEIGHT),this);
      }else if ((mouseListener.getMouseXy()[0] > 253)&&(mouseListener.getMouseXy()[0] < 287)&&(mouseListener.getMouseXy()[1] > maxY-120)&&(mouseListener.getMouseXy()[1] < maxY-10)){
        g.drawImage(leftClickedMinus,0,maxY-(int)(BOT_HEIGHT),(int)(BOT_HEIGHT*Y_TO_X),(int)(BOT_HEIGHT),this);
      }
    }
    g.drawImage(middle,(int)(BOT_HEIGHT*Y_TO_X),maxY-(int)(BOT_HEIGHT),maxX -(int)(2.0*BOT_HEIGHT*Y_TO_X)+5, (int)(BOT_HEIGHT),this);
    g.drawImage(right,maxX-(int)(BOT_HEIGHT*Y_TO_X),maxY-(int)(BOT_HEIGHT), (int)(BOT_HEIGHT*Y_TO_X), (int)(BOT_HEIGHT),this);
    g.drawImage(hotbar,(int)(maxX/2.0-(BOT_HEIGHT*(Y_TO_X_HOT/2.0))),maxY-(int)(BOT_HEIGHT),(int)(BOT_HEIGHT*Y_TO_X_HOT), (int)(BOT_HEIGHT),this);
    //Hp and exp bars
    g.drawImage(hp,10,10, ((int)(maxX*0.2)),  ((int)(maxX*0.2/200.0*14.0)),this);
    g.drawImage(exp,10,15+ ((int)(maxX*0.2/200.0*14.0)),((int)(maxX*0.2)), ((int)(maxX*0.2/200.0*10.0)),this);
  }
  public void drawMap (Graphics g){
    //Sets the void image
    g.setColor(Color.BLACK);
    g.fillRect(0, 0, maxX, maxY);
    bg.setOnTile();
    //5 % chance to spawn
    //Spawning method, this is the first thing that will occur
    if (((int)(Math.random()*100)<5)&&(mobCount<MOB_CAP)){
      //Resets the spawn
      spawnX = 0;
      spawnY = 0;
      do{
        spawnX =(int)(Math.random()*entityMap[0].length);
        spawnY =(int)(Math.random()*entityMap.length);
      }while(!(entityMap[spawnY][spawnX] instanceof Entity)&&(!(map[spawnY][spawnX] instanceof FloorTile)));
      mobCount++;
      entityMap[spawnY][spawnX] = new Enemy (100,100,1,1,0,Color.MAGENTA, false);
    }
    findBlocked (playerCurrentX, playerCurrentY);
    //System.out.println (blocked [0]+" | "+blocked [1]+" | "+blocked [2]+" | "+blocked [3]);
    //Sets off tiling for the reset of movement
    if (bg.getOnTile()){
      tiling = false;
    }
    //Setting all the possible positions is the second thing that will occur
    if (!(tiling)){
      keyListener.setAllDirection();
      if ((!(blocked[0])&&(keyListener.getAllDirection()[1]<0))||(!(blocked[1])&&(keyListener.getAllDirection()[1]>0))||(!(blocked[2])&&(keyListener.getAllDirection()[0]<0))||(!(blocked[3])&&(keyListener.getAllDirection()[0]>0))){  
        //Set all array postion
        for (int i =0;i<entityMap.length;i++){
          for(int j =0;j<entityMap[0].length;j++){
            if (entityMap[i][j] instanceof Character){
              if (!(entityMap[i][j].getMoved())){
                //Sets the position on the map directly
                if (keyListener.getAllDirection()[0]<0){
                  playerCurrentX= playerCurrentX -1;
                  tiling =true;
                  entityMap[playerCurrentY][playerCurrentX]= entityMap[i][j];
                  entityMap[i][j] =null;
                  entityMap[playerCurrentY][playerCurrentX].setMoved(true);
                }else if (keyListener.getAllDirection()[0]>0){
                  playerCurrentX =playerCurrentX+1;
                  tiling =true;
                  entityMap[playerCurrentY][playerCurrentX]= entityMap[i][j];
                  entityMap[i][j] =null;
                  entityMap[playerCurrentY][playerCurrentX].setMoved(true);
                }
                if (keyListener.getAllDirection()[1]<0){
                  playerCurrentY =playerCurrentY-1;
                  tiling =true;
                  entityMap[playerCurrentY][playerCurrentX]= entityMap[i][j];
                  entityMap[i][j] =null;
                  entityMap[playerCurrentY][playerCurrentX].setMoved(true);
                }else if (keyListener.getAllDirection()[1]>0){
                  playerCurrentY =playerCurrentY+1;
                  tiling =true;
                  entityMap[playerCurrentY][playerCurrentX]= entityMap[i][j];
                  entityMap[i][j] =null;
                  entityMap[playerCurrentY][playerCurrentX].setMoved(true);
                }
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
                if (entityMap[i][j] instanceof Enemy){
                  //Finding the blocked for entity
                  findBlocked (j,i);
                  if ((blocked[0])&&(blocked[1])&&(blocked[2])&&(blocked[3])){
                    directionRand = 4;
                  }else{
                    do{
                      directionRand=((int)(Math.random()*4));
                    }while (blocked[directionRand]);
                    if ((!((Enemy)(entityMap[i][j])).getEnraged())&&(((int)(Math.random()*2))==1)){
                      //50% chance for them to not move if aggro'd
                      directionRand  =4;
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
                  if (directionRand!=4){
                    entityMap[i+entityArrayYMod][j+entityArrayXMod] =entityMap[i][j];
                    //Not sure about setting it to null, look at if there is a better method
                    entityMap[i][j] =null;
                  }
                }
              }
            }
          }
        }
      }
      ///Place in a refresh method later on
      for (int i =0;i<entityMap.length;i++){
        for(int j =0;j<entityMap[0].length;j++){
          if (entityMap[i][j] instanceof Entity){
            entityMap[i][j].setMoved(false);
          }
        }
      }
      //May add this back later if necessary
      //player.setArrayY(playerStartingY+bg.getY()/TILE_SIZE);
      //  player.setArrayX(playerStartingX+bg.getX()/TILE_SIZE);
      drawFog(playerCurrentX, playerCurrentY, 0);
    }
    //The tiling variables allows the user to know when a turn is occuring 
    if (tiling){
      //Move all of the entities slowly
      //Moving the bg is a prerequisite for everything else to move
      xyDirection =keyListener.getAllDirection();
      bg.setXDirection (xyDirection[0]);
      bg.setYDirection (xyDirection[1]);
      bg.move();
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
      for (int i =0;i<entityMap.length;i++){
        for(int j =0;j<entityMap[0].length;j++){
          if (entityMap[i][j] instanceof Entity){
            entityMap[i][j].setMoved(false);
          }
          if(map[i][j] instanceof Tile){
            map[i][j].setFocus(false);
          }
        }
      }
    }
    for (int i = 0;i<map.length;i++){
      for (int j = 0;j<map[0].length;j++){
        //Getting the x and y for the background allow the ability to have smooth movement when going from one tile to the next
        if (map[i][j] instanceof Tile){
          if(map[i][j].getViewed()){
            //Restricts the map so that the array will not go out of bounds
            if (((maxX/2+j*TILE_SIZE-bg.getX()-(TILE_SIZE/2)-(TILE_SIZE*playerStartingX))>-TILE_SIZE*2)&&((maxX/2+j*TILE_SIZE-bg.getX()-(TILE_SIZE/2)-(TILE_SIZE*playerStartingX))<maxX+TILE_SIZE*2)&&((maxY/2+i*TILE_SIZE-bg.getY()-(TILE_SIZE/2)-(TILE_SIZE*playerStartingY))>-TILE_SIZE*2)&&((maxY/2+i*TILE_SIZE-bg.getY()-(TILE_SIZE/2)-(TILE_SIZE*playerStartingY))<maxY+TILE_SIZE*2)){
              map[i][j].drawTile(g, maxX/2+j*TILE_SIZE-bg.getX()-(TILE_SIZE/2)-(TILE_SIZE*playerStartingX), maxY/2+i*TILE_SIZE-bg.getY()-(TILE_SIZE/2)-(TILE_SIZE*playerStartingY), TILE_SIZE, TILE_SIZE, this);
            }
          }
        }
      }
    }
    
    //Load basic visuals last
    drawFog(playerCurrentX, playerCurrentY, 0);
  }
  
  public void drawFog(int x, int y, int count){
    if (map[y][x] instanceof Tile){
    map[y][x].setViewed();
    //May need later
    /*
     if(count <= 2){
     if(map[y][x].getMinimapColor() == Color.GREEN){
     for(int i = -1; i <= 1; i ++){
     for(int j = -1; j <= 1; j++){
     if (map[y+i][x+j] instanceof Tile){
     if(map[y+i][x+j].getMinimapColor() == Color.GREEN || map[y+i][x+j].getMinimapColor() == Color.LIGHT_GRAY || map[y+i][x+j].getMinimapColor() == Color.RED){
     drawFog(x+j, y+i, count+1);
     }
     }
     }
     }
     } else if (map[y][x].getMinimapColor() == Color.WHITE){
     for(int i = -1; i <= 1; i ++){
     for(int j = -1; j <= 1; j++){
     if (map[y+i][x+j] instanceof Tile){
     if(map[y+i][x+j].getMinimapColor() == Color.WHITE || map[y+i][x+j].getMinimapColor() == Color.DARK_GRAY || map[y+i][x+j].getMinimapColor() == Color.RED){
     drawFog(x+j, y+i, count+1);
     }
     */
    map[y][x].setFocus(true);
    }
    if(count <= 3){ //If within range
      for(int i = -1; i <= 1; i ++){
        for(int j = -1; j <= 1; j++){
          if ((y+i>=0)&&(y+i<map.length)&&(x+j>=0)&&(x+j<map[0].length)){
            if(map[y+i][x+j] instanceof Tile){
              if(map[y][x].getMinimapColor() == Color.GREEN && map[y+i][x+j].getMinimapColor() != Color.WHITE){ //Avoids corner sight
                drawFog(x+j, y+i, count+1);
              } else if(map[y][x].getMinimapColor() == Color.WHITE && map[y+i][x+j].getMinimapColor() != Color.GREEN){
                drawFog(x+j, y+i, count+1);
              } else if(map[y][x].getMinimapColor() == Color.RED && map[playerCurrentY][playerCurrentX].getMinimapColor() == Color.RED && map[y+i][x+j].getMinimapColor() != Color.RED){
                drawFog(x+j, y+i, count+1);
              }
            }
          }
        }
      }
    }
  }
  /*
   for(int i = -4; i < 5; i++){
   for(int j = -4; j < 5; j++){
   //Sets the view range in a circular shape
   if (!((Math.abs(j-i)==8)||(Math.abs(j-i)==7)||(Math.abs(j-i)==6)||(Math.abs(i+j)==8)||(Math.abs(i+j)==7)||(Math.abs(i+j)==6))){
   if((y+i>=0)&&(y+i<map.length)&&(x+j>=0)&&(x+j<map[0].length)){
   if(map[player.getArrayY()][player.getArrayX()].getMinimapColor() == Color.GREEN){
   if(map[y+i][x+j].getMinimapColor() == Color.GREEN || map[y+i][x+j].getMinimapColor() == Color.LIGHT_GRAY || map[y+i][x+j].getMinimapColor() == Color.RED){
   map[y + i][x + j].setViewed();
   }
   } else if(map[player.getArrayY()][player.getArrayX()].getMinimapColor() == Color.WHITE){
   if(map[y+i][x+j].getMinimapColor() == Color.WHITE || map[y+i][x+j].getMinimapColor() == Color.DARK_GRAY || map[y+i][x+j].getMinimapColor() == Color.RED){
   map[y + i][x + j].setViewed();
   }
   } else{
   map[y + i][x + j].setViewed();
   }
   }
   }
   }
   } */
  
  public void drawMinimap(Graphics g){
    g.setColor(Color.BLACK);
    g.fillRect(0,maxY-(int)(BOT_HEIGHT),minimapX, minimapY);    
    //User clicks zoom in and out buttons
    if((mouseListener.getMouseXy()[0] > 253)&&(mouseListener.getMouseXy()[0] < 287)&&(mouseListener.getMouseXy()[1] > maxY-240)&&(mouseListener.getMouseXy()[1] < maxY-130)&&(mouseListener.getClicked())&&(minimapFactor > 20)){ //Clicked on top button
      mouseListener.setClicked (false);
      minimapFactor -= 10;
    } else if ((mouseListener.getMouseXy()[0] > 253)&&(mouseListener.getMouseXy()[0] < 287)&&(mouseListener.getMouseXy()[1] > maxY-120)&&(mouseListener.getMouseXy()[1] < maxY-10)&&(mouseListener.getClicked())&&(minimapFactor < 100)){ //Clicked on bottom button
      mouseListener.setClicked (false);
      minimapFactor += 10;
    }
    debugMessage = "Minimap factor: " + Integer.toString(minimapFactor);
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
  public void drawEntity(Graphics g){
   
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
  public void drawBars(Graphics g){
    //Fill Hp, can be modified through the width
    g.setColor (new Color (69,218,215));
    g.fillRect (16,16, ((int)(maxX*1.0/5.0))-12, ((int)(maxX*1.0/5.0/200.0*14.0))-12);
    //Fill Exp, can be modified through the width
    g.setColor (new Color (152,251,152));
    g.fillRect (16,21+((int)(maxX*1.0/5.0/200.0*14.0)), ((int)(maxX*1.0/5.0))-12,((int)(maxX*1.0/5.0/200.0*10.0))-12);
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
    if (i+1<=map.length){
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
    if (j+1<=map[0].length){
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
  //Sets up the map so that setting the floor is easier as well
  public void createMap(Tile [][]map, int playerStartingX, int playerStartingY){
    //Initializes player locations
    this.map = map;
    this.entityMap = new Entity [map.length][map[0].length];
    this.playerStartingX = playerStartingX;
    this.playerStartingY =playerStartingY;
    playerCurrentX = playerStartingX;
    playerCurrentY = playerStartingY;
    entityMap[playerStartingX][playerStartingY]= new Character(100,100,1,1,0,Color.BLUE);
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
}