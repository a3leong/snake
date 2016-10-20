/**
 * 
 * @author Aaron Leong
 * 
 * Class Space
 * A class used to keep track of all the spaces taken up by the snake 
 * (excluding the head position).
 */
public class Space extends Piece {
  // Constructor creates a Space instance with x and y coordinate position
  Space(int x, int y)
  {
    this.xPos = x;
    this.yPos = y;
  }
  
  /**
   * getX()
   * Getter method for horizontal position of Space instance
   * @return  xPos
   */
  public int getX(){ return xPos; }
  /**
   * getY()
   * Getter method for vertical position of Space instance
   * @return  yPos
   */
  public int getY(){ return yPos; }
}
