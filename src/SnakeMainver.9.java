//import java.applet.*;
//import java.awt.*;
//import java.awt.event.*;
//import java.util.ArrayList;
//import javax.swing.Timer;
//
///**
// * 
// * @author Aaron Leong
// * @version 0.9 June 28 2013
// * @see javax.swing.Timer
// * @param serailVersionUID  Default serialVersion UID
// * @param DRAW_GRID         Determinds whether grid is drawn for game or not
// * @param UNIT              Size of one Unit on the gameboard, can be scaled
// * @param WIDTH             Width of the gameboard
// * @param HEIGHT            Height of the gameboard
// * @param gameSpeed         Current speed of the game, increases with score
// * @param score             Players current score (amt of pellets eaten)
// * @param snakeSpace        Current occupied spaces by snake (not Head)
// * @param player            Head piece user controls with arrow keys
// * @param clearX            X coordinate of space to turn from snake to blank
// * @param clearY            Y coordinate of space to turn from snake to blank
// * @param gameOver          Determines whether game has ended or not
// * 
// * SnakeMain
// * Runs snake game applet. Score increases by UNIT every time a pellet is eaten
// * game ends when player hits a wall or any part of the snake with the
// * controlled Head piece. To change the scale of the game change UNIT of both
// * SnakeMain.java and Piece.java. To change dimensions of board change
// * HEIGHT and WIDTH of both SnakeMain.java and Piece.java as well.
// * Upon game over, game can be replayed.  
// */
//public class SnakeMain extends Applet implements ActionListener
//{
//  private static final long serialVersionUID = 1L;  // Default serialVersionUID
//  final boolean DRAW_GRID = false;
//  final private int UNIT = 10; // Amt of units each square takes up
//  final private int WIDTH = UNIT*50, HEIGHT = UNIT*30;
//  
//  int gameSpeed = 70;  // speed of the game, TODO change later to increment on score
//  int score;
//  ArrayList<Space> snakeSpace = new ArrayList<Space>();  // Array list of space types
//  Head player;
//  Pellet pellet;
//  int clearX;    // x Coord to clear
//  int clearY;    // y Coord to clear
//  boolean gameOver = false;
//  long currentTime;
//  
//  // Double buffer
//  Image offscreen;
//  Graphics bufferGraphics;
//  
//  
//  // TODO add sounds
//  // TODO add score interface
//  
//  public void init()
//  {  
//    System.gc();   // Unneeded? TODO
//    setSize(WIDTH+1,HEIGHT+1);
//    setVisible(true);
//    score = 0;
//    player = new Head();
//    pellet = new Pellet();
//    
//    // Create offscreen image to draw on
//    offscreen = createImage(WIDTH+1, HEIGHT+1);
//    bufferGraphics = offscreen.getGraphics();
//  }
//  
//  public void start()
//  {
//    // set current time in milliseconds    
//    Timer time = new Timer(gameSpeed, this);
//    time.start();
//    while(gameOver==false){} // executes actions performed while timed
//    time.stop();
//    
//    repaint();  // repaint to show final score
//  }
//  
//  public void reset()
//  {
//    System.gc();   // Unneeded? TODO
//    setSize(WIDTH+1,HEIGHT+1);
//    setVisible(true);
//    score = 0;
//    player = new Head();
//    pellet = new Pellet();
//    
//    // Create offscreen image to draw on
//    offscreen = createImage(WIDTH+1, HEIGHT+1);
//    bufferGraphics = offscreen.getGraphics();
//    
//    // set current time in milliseconds
//    currentTime = System.currentTimeMillis();
//    
//    Timer time = new Timer(gameSpeed, this);
//    time.start();
//    while(gameOver==false){} // executes actions performed while timed
//    time.stop();
//    currentTime = System.currentTimeMillis() - currentTime;
//    
//    repaint();  // repaint to show final score
//  }
//  
//  public void paint (Graphics g)
//  {  
//    // Draw grid
//    bufferGraphics.setColor(Color.black);
//    if (DRAW_GRID)
//    {
//      for (int y=0;y<=HEIGHT/UNIT;y++)
//        for (int x=0;x<=WIDTH/UNIT;x++)
//        {
//          // Draw vertical line
//          bufferGraphics.drawLine(x*UNIT,0, x*UNIT, HEIGHT);
//          // Draw horizontal line
//          bufferGraphics.drawLine(0,y*UNIT, WIDTH, y*UNIT);
//        }
//    }
//    else
//      bufferGraphics.drawRect(0, 0, WIDTH, HEIGHT);
//    
//    // Ending screen if gameover
//    if (collisionCheck())
//      bufferGraphics.drawString("Game over, score: " + score, WIDTH/3, HEIGHT/3);
//    else
//    {
//      // Tail Clears Old Squares
//      bufferGraphics.setColor(Color.white);
//        bufferGraphics.fillRect(clearX+1, clearY+1, UNIT-1, UNIT-1);
//         
//      // Draw player
//      bufferGraphics.setColor(Color.red);  
//      bufferGraphics.fillRect(player.getX()+1, player.getY()+1, UNIT-1, UNIT-1);
//      
//      // Draw pellet
//      // Check if pellet eaten TODO pellet spawns in nonsnakespace
//      if (isEaten())
//      {
//        pellet = new Pellet();
//        // check if same as player Head
//        if (pellet.getX()==player.getX()&&pellet.getY()==player.getY())
//          pellet = new Pellet();
//        else // checks snakeSpace for taken spaces
//        {
//          for (int i=0;i<snakeSpace.size();i++)
//            if (pellet.getX()==snakeSpace.get(i).getX() &&
//                  pellet.getY()==snakeSpace.get(i).getY())
//              pellet = new Pellet();
//        }
//      }
//      bufferGraphics.setColor(Color.blue);
//      bufferGraphics.fillRect(pellet.getX()+1, pellet.getY()+1, UNIT-1, UNIT-1);
//    }
//    
//    // offscreen image drawn
//    g.drawImage(offscreen,0,0,this);
//    // proceeding
//    Toolkit.getDefaultToolkit().sync();
//  }
//  
//  public void update(Graphics g)
//  {
//    paint(g);
//  }
//  
//  public void actionPerformed(ActionEvent arg0)
//  {
//    clearX = player.getChild().getX();  // store last place tail was in
//    clearY = player.getChild().getY();  // store last place tail was in
//    
//    // Populate arrayList
//    if (player.getSize()!=0)
//    {
//      snakeSpace.add(0, new Space(player.getX(), player.getY())); // Adds previous head space to front of list
//      snakeSpace.remove(snakeSpace.size()-1);
//    }
//    player.move();
//    player.getChild().move();
//    if (collisionCheck())
//      gameOver = true;
//    
//    repaint();
//  }
//  
//  public boolean keyDown(Event e, int key)
//  {
//    player.changeDir(key);
//    return true;
//  }
//  
//  /**
//   * collisionCheck
//   * @return Returns true if Player has crashed into edge or a Tail Piece
//   */
//  public boolean collisionCheck()
//  { 
//    // TODO Do not let snake disappear on collision
//    // check wall collision
//    if (player.getX()>=WIDTH || player.getX()<0 ||
//          player.getY()<0 || player.getY()>=HEIGHT)
//      return true;
//    
//    // Checks for Head matching any space in SnakeSpace to check for collision
//    for (int i=0;i<snakeSpace.size();i++)
//    {
//      if (player.getX()==snakeSpace.get(i).getX() &&
//            player.getY()==snakeSpace.get(i).getY())
//        return true; 
//    }
//
//    return false;
//  }
//  
//  /**
//   * isEaten()
//   * Checks if the pellet is in the same position as head, and returns
//   * true if so and increments score and adds a Tail Piece to player
//   * @return   Returns true if head occupies the same coordinates as pellet
//   */
//  public boolean isEaten()
//  {
//    if (player.getX()==pellet.getX() && player.getY()==pellet.getY())
//    {
//      player.addSize();
//      score = score + UNIT;
//      player.getChild().expand();
//      snakeSpace.add(new Space(player.getChild().getX(), player.getChild().getY()));
//      return true;
//    }
//    else return false;
//  }
//  
//}