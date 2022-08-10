import java.util.Random;

// RobotPlayer class
public class RobotPlayer extends Player{
  // fields
  private Random rand;

  // constructor 
  public RobotPlayer(String name, char token, int max){
    super(name, token, max);
    this.rand = new Random();
  }

  // // method for getting a move
  public int getUserInput(){
    int move;
    //generate random number, add one so zero can't be returned
    move = rand.nextInt(max)+1;
    System.out.println(name + " placed a counter in column "+ move +":");        return move;
  }
}