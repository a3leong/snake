/**
 * 
 *  @author Aaron Leong
 *  @version
 *  
 *  @param xPos   x position of pellet
 *  @param yPos   y position of pellet
 *  Class Pellet
 *  Drops pellets in random spots on the board. If a Piece occupies the space,
 *  the pellet will not be dropped there (Checked by SnakeMain).
 *  If the Head Piece touches the pellet,
 *  the Tail calls its expand() method.
 */
public class Pellet extends Piece {
  // Ctor creates pellet in random spot using spawn
  public Pellet()
  {
    spawn();
  }
  
  /**
   * spawn()
   * Used for instantiation of a Pellet. Gives the instance a random 
   * xPos and yPos position within the bounds of the playfield
   */
  private void spawn()
  {
    // Creates value between 0 and WIDTH
    this.xPos = (int) (Math.random()*(WIDTH/UNIT))*UNIT;
    // Creates value between 0 and HEIGHT
    this.yPos = (int) (Math.random()*(HEIGHT/UNIT))*UNIT; 
  }
  
  // Getters
  /**
   * getX()
   * Getter method for horizontal position of Pellet instance
   * @return  xPos
   */
  public int getX(){ return xPos; }
  /**
   * int getY()
   * Getter method for vertical position of Pellet instance
   * @return  yPos
   */
  public int getY(){ return yPos; }
}

