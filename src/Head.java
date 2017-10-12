/**
 * 
 * @author   Aaron Leong
 * @version  1.0 Jun 20 13
 * @param XSTART    Start x-location of head piece
 * @param YSTART    Starting y-location of head piece
 * @param size      The size of the snake (excluding the head piece)
 * @param XSTART    The starting horizontal position of the snake
 * @param YSTART    The starting vertical position of the snake
 * @param child     The Tail the Head piece sends instructions to
 * @param keypress  Stores last valid keypress entered by user.
 * 
 * Class Head
 * Holds the data for the Head piece including Xposition and Yposition as well
 * as data for the Tail piece which it sends directions to store. Has the 
 * ability to be reset.
 */
public class Head extends Piece {
  final private int XSTART = UNIT*2;
  final private int YSTART = UNIT*3;
  private Tail child;
  private int keypress;
  private int size;

  // Constructor
  public Head()
  {
    this.reset();            // Sets starting position
  }

  // Mutators
  /**
   * reset()
   * Resets position and direction (+x dir.) of the snake if you reset the game
   * and resets the size and assigns it a new Tail.
   */
  public void reset()
  {
    this.xPos = XSTART;
    this.yPos = YSTART;
    this.xSpeed = UNIT;
    this.ySpeed = 0;
    child = new Tail(this);
    size = 0;
  }

  /**
   * changeDir(int key)
   * Checks that the user input is valid (will not cause snake to crash itself
   * or move in the direction it is already moving) then stores the user input
   * in keypress and sends key to child.changeDir method
   * @param key  User input key (up, down, left, or right arrow key)
   */
  public void changeDir(int key)
  {
    // check for improper presses
    // If up key pressed and not moving down or up
    if (key == 1004 && (this.ySpeed == -UNIT||this.ySpeed == UNIT))
      {}
    // If down key pressed and head not moving up or down
    else if (key == 1005 && (this.ySpeed == -UNIT||this.ySpeed == UNIT))
      {}  
    // If left key pressed and not moving right or left
    else if (key == 1006 && (this.xSpeed == -UNIT||this.xSpeed == UNIT))
      {}  
    // If right key pressed and not moving left or right
    else if (key == 1007 && (this.xSpeed == -UNIT||this.xSpeed == UNIT))
      {}
    else
    {
      keypress = key;
      child.changeDir(key); // Store direction in tail
    }
  }
  
  /**
   * move()
   * Reads keypress to change the direction of the snake if need be.
   * Changes the xPos and yPos to change the position of the snake according
   * to the snake's direction
   */
  public void move()
  {
    // Up, down, left, right --> 1004,5,6,7 respectively
    // If up key pressed and not moving down or up
    if (keypress == 1004)
      {  this.ySpeed = -UNIT; this.xSpeed = 0; }
    // If down key pressed and head not moving up or down
    else if (keypress == 1005)
      {  this.ySpeed = UNIT; this.xSpeed = 0; }  
    // If left key pressed and not moving right or left
    else if (keypress == 1006)
      {  this.ySpeed = 0; this.xSpeed = -UNIT; }  
    // If right key pressed and not moving left or right
    else if (keypress == 1007)
      {  this.ySpeed = 0; this.xSpeed = UNIT; }
    
    this.xPos = this.xPos+this.xSpeed;
    this.yPos = this.yPos+this.ySpeed;
  }
  
  /**
   * void addSize()
   * Increments size
   */
  public void addSize()
  {
    size++;
  }
  
  public void stop()
  {
    this.xSpeed = 0;
    this.ySpeed = 0;
  }
  
  // Getters  
  /**
   * getSize()
   * Getter method to return the size of the snake
   * @return The size of the snake (not including the head)
   */
  public int getSize(){return size;}
  /**
   * Tail getChild()
   * Getter method for Head's Tail instance
   * @return  The tail piece of a snake instance
   */
  public Tail getChild(){return child;}
  /**
   * int getX()
   * Getter method for horizontal position of Head
   * @return  xPos
   */
  public int getX(){return xPos;}
  /**
   * int getY()
   * Getter method for vertical position of Head
   * @return  yPos
   */
  public int getY() {return yPos;}
}
