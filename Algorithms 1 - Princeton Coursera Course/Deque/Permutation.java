import edu.princeton.cs.algs4.StdIn;

public class Permutation {
   public static void main(String[] args) {
	   int n = Integer.parseInt(args[0]);
	   System.out.println(n);
	   RandomizedQueue<String> q = new RandomizedQueue<String>();
	   
	   while (!StdIn.isEmpty()) {
		   try {
			   String value = StdIn.readString();
			   q.enqueue(value);
		   }
		   catch(Exception e){
			   System.out.println("exception thrown");
//			   break;
		   }
	   }
	   
	   for(int i =0; i < n; i++) {
		  System.out.println(q.dequeue());
	   }
	   
   }
}