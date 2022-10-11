import java.util.Comparator;

public class BruteCollinearPoints {
	
   private Point [] points; 
   private LineSegment[] segments;
	
   // finds all line segments containing 4 points
   public BruteCollinearPoints(Point[] points) throws IllegalArgumentException {
	   if(points == null) {
		   throw new IllegalArgumentException("points is null");
	   }
	   for(int i = 0; i < points.length; i++) {
		   if(points[i] == null) {
		   throw new IllegalArgumentException("points is null");   
		   }
	   }
	   this.points = points;
	   this.segments = new LineSegment[points.length];
	   bruteForce();
   }
   
   // the number of line segments
   public int numberOfSegments() {
	  int count = 0;
	  for(int i = 0; i < segments.length; i++) {
		  if(segments[i] != null) {
			  count++;
		  }
	  }
	  return count;
   }
   
   // the line segments
   public LineSegment[] segments() {
	   return segments;
   }
   
   // brute force find all line segments
   private void bruteForce() {
	   int segLength = 0;
	   for(int i = 0; i < points.length; i++) {
		   Comparator<Point> a = points[i].slopeOrder();
		   int count = 0;
		   int start = i + 1;
		   for(int j = i; j < points.length; j++) {
			   if(start == points.length) {
				   break;
			   }
			   if(a.compare(points[start], points[j])==0) {
				   count ++;
				   if(count == 4) {
					   LineSegment line = new LineSegment(points[i],points[j]);
					   segments[segLength] = line;
					   segLength ++;
					   break;   
				   }
				   
			   }
		   }
		   
	   }
   }
   
   public static void main(String[] args) {
	   Point c = new Point(1,2);
	   Point d = new Point(1,3);
	   Point e = new Point(1,4);
	   Point f = new Point(1,5);
	   Point g = new Point(1,6);
	   Point h = new Point(1,7);
	   Point[] p = new Point[6];
	   p[0]      = c;
	   p[1]      = d;
	   p[2]      = e;
	   p[3]      = f;
	   p[4]      = g;
	   p[5]      = h;
	   BruteCollinearPoints test = new BruteCollinearPoints(p);
	   System.out.println(test.numberOfSegments());
	   LineSegment[] mySegs = test.segments();
	   System.out.println(mySegs[0]);
	   System.out.println(mySegs[1]);
	   System.out.println(mySegs[2]);
	   System.out.println(mySegs[3]);
   }
}