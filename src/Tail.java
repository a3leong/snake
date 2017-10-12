import java.util.ArrayList;

/**
 * 
 * @author Aaron Leong
 * @param parent     Head piece the Tail instance is following
 * @param directions ArrayList of directions the Tail is supposed to follow
 *                   used as a FIFO
 *
 * Class Tail
 * Tail is created by Head and is given instructions to follow the Head with
 * an ever increasing distance and clear the Player marked spaces behind it.
 */
public class Tail extends Piece {
  Head parent;
  ArrayList<Integer> directions = new ArrayList<Integer>();
  
  public Tail(Head pt)
  {
    this.parent = pt;   // Sets passed Head instance as the parent
    directions.add(0);  // Set directions to no direction change 
    // Set variables the same as parent
    this.xSpeed = pt.xSpeed;
    this.ySpeed = pt.ySpeed;
    this.xPos = pt.xPos;
    this.yPos = pt.yPos;
  }

  // Setters
  /**
   * changeDir(int keypress)
   * Remove the last user input added and replaces it with a keypress passed
   * by the parent class Head.
   * @param keypress Valid user input checked by Head and passed to child
   */
  public void changeDir(int keypress)
  {
      directions.remove(directions.size()-1);
      directions.add(keypress);
  }

  /**
   * move()
   * Moves the Tail piece, follows instructions stored in variable directions
   * @param execute  User input stored in directions placed at the front of the
   *                 queue to be implemented when called
   */
  public void move()
  { 
      int execute = directions.remove(0); // remove direction to be executed
      directions.add(0); // append straight at end of queue

      // If up key pressed and not moving down or up
      if (execute == 1004)
        {  this.ySpeed = -UNIT; this.xSpeed = 0; }
      // If down key pressed and head not moving up or down
      else if (execute == 1005)
        {  this.ySpeed = UNIT; this.xSpeed = 0; }  
      // If left key pressed and not moving right or left
      else if (execute == 1006)
        {  this.ySpeed = 0; this.xSpeed = -UNIT; }  
      // If right key pressed and not moving left or right
      else if (execute == 1007)
        {  this.ySpeed = 0; this.xSpeed = UNIT; }
      this.xPos = this.xPos+xSpeed;
      this.yPos = this.yPos+ySpeed;
  }
  
  /**
   * expand()
   * Called when Head class "eats" a pellet. Increases the direction of list
   * size and moves the Tail piece back 1 unit in the appropriate direction
   */
  public void expand()
  {
    directions.add(0, 0); // Increase direction list size

    // If moving up or down
    if (xSpeed!=0)
    {
      if(xSpeed<0)
        this.xPos = this.xPos + UNIT;
      else this.xPos = this.xPos - UNIT;
    } 
    else if (ySpeed!=0)
    {
      if(ySpeed<0)
        this.yPos = this.yPos + UNIT;
      else this.yPos = this.yPos - UNIT;
    } 
  }
  
  public void stop()
  {
    this.xSpeed = 0;
    this.ySpeed = 0;
  }
  
  // Getters
  /**
   * getX()
   * Getter method for horizontal position of Tail instance
   * @return  xPos
   */
  public int getX(){ return xPos; }
  /**
   * int getY()
   * Getter method for vertical position of Tail instance
   * @return  yPos
   */
  public int getY(){ return yPos; }
}
