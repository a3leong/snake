/**
 * 
 * @author Aaron Leong
 * @param UNIT     Size of a unit on board, can be scaled
 * @param xSpeed   Horizontal speed of Piece
 * @param ySpeed   Vertical speed of Piece
 * @param xPos     Current horizontal position of Piece
 * @param yPos     Current vertical position of Piece
 * 
 * Class Piece
 * Abstract class used have fields shared by all game pieces used
 * (xSpeed and ySpeed are only used by Head and Tail class)
 * 
 */
public abstract class Piece {
  final protected int UNIT = 10;   // Size of piece
  final protected int WIDTH = UNIT*50;
  final protected int HEIGHT = UNIT*30;
  protected int xSpeed;    // Speed moving in X direction
  protected int ySpeed;    // Speed moving in Y direction
  protected int xPos;      // Current xPosition of Piece
  protected int yPos;      // Current yPosition of Piece
  
  abstract int getX();
  abstract int getY();
}
