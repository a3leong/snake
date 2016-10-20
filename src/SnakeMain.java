import java.applet.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.Timer;

/**
 * 
 * @author Aaron Leong
 * @version 0.9 June 28 2013
 * @see javax.swing.Timer
 * @param serailVersionUID  Default serialVersion UID
 * @param DRAW_GRID         Determines whether grid is drawn for game or not
 * @param PLAY_SOUNDS       Determines whether sounds are played or not
 * @param UNIT              Size of one Unit on the gameboard, can be scaled
 * @param WIDTH             Width of the gameboard
 * @param HEIGHT            Height of the gameboard
 * @param gameSpeed         Current speed of the game, increases with score
 * @param score             Players current score (amt of pellets eaten)
 * @param snakeSpace        Current occupied spaces by snake (not Head)
 * @param player            Head piece user controls with arrow keys
 * @param clearX            X coordinate of space to turn from snake to blank
 * @param clearY            Y coordinate of space to turn from snake to blank
 * @param gameOver          Determines whether game has ended or not
 * @param gameStart         Determines whether to advance past cover screen
 * @param move              Movement sound effect played on each iteration
 * @param soundCounter      Limits occurance of movement sound
 * @param eat               Eating sound effect played upon eating pellet
 * @param lose              Sound played upon collision
 * @param loseSoundTriggeredMakes sure lose sound only triggered once per game
 * 
 * SnakeMain
 * Runs snake game applet. Score increases by UNIT every time a pellet is eaten
 * game ends when player hits a wall or any part of the snake with the
 * controlled Head piece. To change the scale of the game change UNIT of both
 * SnakeMain.java and Piece.java. To change dimensions of board change
 * HEIGHT and WIDTH of both SnakeMain.java and Piece.java as well.
 * Upon game over, game can be replayed.  
 */
public class SnakeMain extends Applet implements ActionListener
{
  private static final long serialVersionUID = 1L;  // Default serialVersionUID
  final boolean DRAW_GRID = true;
  final boolean PLAY_SOUNDS = true;
  final int UNIT = 10; // Amt of units each square takes up
  final int WIDTH = UNIT*50, HEIGHT = UNIT*30;
  
  
  // Font for game over
  Font newFont = new Font("arial", Font.BOLD,UNIT*2);
  int gameSpeed = 70;  // speed of the game, TODO change later to increment on score
  int score;
  ArrayList<Space> snakeSpace = new ArrayList<Space>();  // Array list of space types
  Head player;
  Pellet pellet;
  int clearX;    // x Coord to clear
  int clearY;    // y Coord to clear
  boolean gameStart;
  boolean gameOver;
  boolean loseSoundTriggered;

  
  
  // Double buffer
  Image offscreen;
  Graphics bufferGraphics;
  
  
  // TODO add sounds
  int soundCounter;
  AudioClip move;
  AudioClip eat;
  AudioClip lose;
  
  
  public void init()
  {  
    System.gc();   // Unneeded? TODO
    
    // Load Audio
    loseSoundTriggered = false;
    soundCounter = 1;
    move = getAudioClip(getDocumentBase(),"move.wav");
    eat = getAudioClip(getDocumentBase(),"eat.wav");
    lose = getAudioClip(getDocumentBase(),"lose.wav");
    
    setBackground(Color.white);
    setSize(WIDTH+1,HEIGHT+1+(UNIT*3));
    setVisible(true);
    score = 0;
    player = new Head();
    pellet = new Pellet();
    gameOver = false;
    gameStart = false;
    
    // Create offscreen image to draw on
    offscreen = createImage(WIDTH+1, HEIGHT+1+(UNIT*3));
    bufferGraphics = offscreen.getGraphics();
    bufferGraphics.setFont(newFont);
  }
  
  public void start()
  {
    
    while(gameStart!=true){}  // Does not start game until input
    // set current time in milliseconds
    
    Timer time = new Timer(gameSpeed, this);
    
    time.start();
  }
  
  public void reset()
  {  
    System.gc();   // Unneeded? TODO
    loseSoundTriggered=false;
    setSize(WIDTH+1,HEIGHT+1+(UNIT*3));
    snakeSpace = new ArrayList<Space>();
    setVisible(true);
    score = 0;
    player = new Head();
    pellet = new Pellet();
    
    // Create offscreen image to draw on
    offscreen = createImage(WIDTH+1, HEIGHT+1+(UNIT*3));
    bufferGraphics = offscreen.getGraphics();
    bufferGraphics.setFont(newFont);
    gameOver = false;
  }
  
  public void paint (Graphics g)
  {  
    // Draw grid
    bufferGraphics.setColor(Color.gray);
    if (DRAW_GRID)
    {
      for (int y=0;y<=HEIGHT/UNIT;y++)
        for (int x=0;x<=WIDTH/UNIT;x++)
        {
          // Draw vertical line
          bufferGraphics.drawLine(x*UNIT,0, x*UNIT, HEIGHT);
          // Draw horizontal line
          bufferGraphics.drawLine(0,y*UNIT, WIDTH, y*UNIT);
        }
    }
    else
      bufferGraphics.drawRect(0, 0, WIDTH, HEIGHT);
    
    if (gameStart)
    {
      // Ending screen if gameover
      if (collisionCheck())
      {
        if (PLAY_SOUNDS && loseSoundTriggered==false)
        {
          lose.play();
          loseSoundTriggered = true;
        }
        bufferGraphics.setColor(Color.black);
        bufferGraphics.drawString("Game Over, Press Enter to Play Again",
                                      WIDTH/4, HEIGHT+(UNIT*2));
      }
      else
      {
        // Draw Score
        bufferGraphics.setColor(Color.white);
        bufferGraphics.fillRect(0,HEIGHT+1, WIDTH+1, HEIGHT+(UNIT*3));
        bufferGraphics.setColor(Color.black);
        bufferGraphics.drawString("Score: " + score, 0, HEIGHT+(UNIT*2));
        // Tail Clears Old Squares
        bufferGraphics.setColor(Color.white);
        bufferGraphics.fillRect(clearX+1, clearY+1, UNIT-1, UNIT-1);
           
        // Draw player
        bufferGraphics.setColor(Color.red);  
        bufferGraphics.fillRect(player.getX()+1, player.getY()+1, UNIT-1, UNIT-1);
        
        // Draw pellet
        if (isEaten())
        {
          pellet = new Pellet();
          // check if same as player Head
          if (pellet.getX()==player.getX()&&pellet.getY()==player.getY())
            pellet = new Pellet();
          else // checks snakeSpace for taken spaces
          {
            for (int i=0;i<snakeSpace.size();i++)
              if (pellet.getX()==snakeSpace.get(i).getX() &&
                    pellet.getY()==snakeSpace.get(i).getY())
                pellet = new Pellet();
          }
        }
        bufferGraphics.setColor(Color.blue);
        bufferGraphics.fillRect(pellet.getX()+1, pellet.getY()+1, UNIT-1, UNIT-1);
      }
    }
    else
    {
      bufferGraphics.setColor(Color.white);
      bufferGraphics.fillRect(0,HEIGHT+1, WIDTH+1, HEIGHT+(UNIT*3));
      bufferGraphics.setColor(Color.black);
      bufferGraphics.drawString("Press Enter to Start", 0, HEIGHT+(UNIT*2));
    }
    // offscreen image drawn
    g.drawImage(offscreen,0,0,this);
    // proceeding
    Toolkit.getDefaultToolkit().sync();
  }
  
  public void update(Graphics g)
  {
    paint(g);
  }
  
  public void actionPerformed(ActionEvent arg0)
  { 
    clearX = player.getChild().getX();  // store last place tail was in
    clearY = player.getChild().getY();  // store last place tail was in
    
    // Populate arrayList
    if (player.getSize()!=0)
    {
      snakeSpace.add(0, new Space(player.getX(), player.getY())); // Adds previous head space to front of list
      snakeSpace.remove(snakeSpace.size()-1);
    }
    
    if (gameOver!=true)
    {
      if (PLAY_SOUNDS)
      {
        // Play move sound every other iteration
        if (soundCounter%2==0)
        {
          soundCounter=1;
          move.play();
        }
        else soundCounter++;
      }
      player.move();
      player.getChild().move();
    }
    
    if (collisionCheck())
      gameOver = true;
    repaint();
  }
  
  public boolean keyDown(Event e, int key)
  {
    if (gameStart)
    {
      // Resets if game over and Enter key hit
      if (gameOver==true && key==10)
        reset();
      if (gameOver!=true)
        player.changeDir(key);
    }
    else if (key==10)
      gameStart = true;
    
    return true;
  }
  
  /**
   * collisionCheck
   * @return Returns true if Player has crashed into edge or a Tail Piece
   */
  public boolean collisionCheck()
  { 
    // TODO Do not let snake disappear on collision
    // check wall collision
    if (player.getX()>=WIDTH || player.getX()<0 ||
          player.getY()<0 || player.getY()>=HEIGHT)
      return true;
    // Checks for Head matching any space in SnakeSpace to check for collision
    for (int i=0;i<snakeSpace.size();i++)
    {
      if (player.getX()==snakeSpace.get(i).getX() &&
            player.getY()==snakeSpace.get(i).getY())
        return true;
    }

    return false;
  }
  
  /**
   * isEaten()
   * Checks if the pellet is in the same position as head, and returns
   * true if so and increments score and adds a Tail Piece to player
   * @return   Returns true if head occupies the same coordinates as pellet
   */
  public boolean isEaten()
  {
    if (player.getX()==pellet.getX() && player.getY()==pellet.getY())
    {
      if (PLAY_SOUNDS)
        eat.play();   // Plays eating sfx 
      player.addSize();
      score = score + UNIT;
      player.getChild().expand();
      snakeSpace.add(new Space(player.getChild().getX(), player.getChild().getY()));
      return true;
    }
    else return false;
  }
  
}