// Player class
public abstract class Player{
  // fields
  protected String name;
  protected char token;
  protected int max; // number of columns on board

  // constructor 
  public Player(String name, char token, int max){
    this.name = name;
    this.token = token; 
    this.max = max;
  }

  // method for getting a move
  public abstract int getUserInput();
}