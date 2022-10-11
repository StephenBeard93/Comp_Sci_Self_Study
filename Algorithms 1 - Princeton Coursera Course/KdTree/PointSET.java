import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import java.util.ArrayList;

public class PointSET {
   private SET<Point2D> set; 
	
   // construct an empty set of points 
   public PointSET() {
       this.set = new SET<Point2D>();   
   }
   
   // is the set empty? 
   public boolean isEmpty() {
	   return set.isEmpty();
   }
   
   // number of points in the set
   public int size() {
	    return set.size();
   }
   
   // add the point to the set (if it is not already in the set)
   public void insert(Point2D p) {
	   set.add(p);
   }
   
   // does the set contain point p?
   public boolean contains(Point2D p) {
	   return set.contains(p);
   }
   
   // draw all points to standard draw 
   public void draw() {
	   StdDraw.setPenRadius(0.01); 
	   StdDraw.setPenColor(StdDraw.BLACK); 
	   StdDraw.setScale(0,100);
	   for(Point2D p : set) {
		   p.draw();
	   }
	   
   }
   
   
   // all points that are inside the rectangle (or on the boundary) 
   public Iterable<Point2D> range(RectHV rect){
	   ArrayList<Point2D> contains = new ArrayList<Point2D>();
	   for(Point2D p : set) {
		   if(rect.contains(p)) {
			   contains.add(p);
		   }
	   }
	   return contains;
   }
   
   // a nearest neighbor in the set to point p; null if the set is empty 
   public Point2D nearest(Point2D p) {
	   if(set.isEmpty()) {
		   return null;
	   }
	   Point2D min = new Point2D(0.0, 0.0);
	   double distMin = Double.POSITIVE_INFINITY;
	   for(Point2D a : set) {
		   if(p.distanceTo(a) < distMin) {
			   min = a;
			   distMin = p.distanceTo(a);
		   }
	   }
	   return min;
   }

   
   // unit testing of the methods (optional)
   public static void main(String[] args) {
	  Point2D p1 = new Point2D(1,1);
	  Point2D p2 = new Point2D(5,2);
	  PointSET mySet = new PointSET();
	  System.out.println(mySet.isEmpty());
	  mySet.insert(p1);
	  mySet.insert(p2);
	  System.out.println(mySet.size());
	  System.out.println(mySet.contains(p1));
	  RectHV myRect = new RectHV(0,0,0,0);
	  for(Point2D p : mySet.range(myRect)) {
		  System.out.println(p);
	  }
	  Point2D p3 = new Point2D(4,3);
	  System.out.println(mySet.nearest(p3));
	  mySet.draw();
   }
}