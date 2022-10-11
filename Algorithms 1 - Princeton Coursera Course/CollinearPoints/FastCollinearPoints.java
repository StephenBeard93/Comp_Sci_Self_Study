import java.util.Comparator;

public class FastCollinearPoints {
   /* Create an aux array of points
    * iterate through the points array - creating a comparator for each point
    * use a recursive merge call until you reach an array of just one item
    * 'sort' using the comparator method compare on the current comparator
    */
	
   private Point [] points; 
   private LineSegment[] segments;
   private Point[] aux;
	
   // finds all line segments containing 4 or more points
   public FastCollinearPoints(Point[] points) {
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
	   this.aux = new Point[points.length];
	   mergeSort(0,points.length -1);
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
   
   private void merge(Point[] a, Point[] aux, Point b, int lo, int mid, int hi){
	   //create comparator
	   Comparator<Point> c = b.slopeOrder();
	   // copy points to aux array
	   for (int k = lo; k <= hi; k++) {
		   aux[k] = a[k];
	   }
	   // merge
	   int i = lo, j = mid+1;   
	   for(int k = lo; k <= hi; k++) {
		   if(i > mid) {
			   a[k] = aux[j++];
		   }
		   else if(j > hi) {
			   a[k] = aux[i++];
		   }
		   else if(c.compare(aux[i],aux[j]) == 1) {
			   a[k] = aux[i++];
		   }
		   else {
			   a[k] = aux[j++];
		   }
	   }
   }
   
   private void sort(Point[] a, Point[] aux, int lo, int hi, Point b) {
	   if(hi <= lo) {
		   return;
	   }
	   int mid = lo + (hi - lo)/2;
	   sort(a, aux ,lo ,mid, b);
	   sort(a, aux, mid + 1, hi, b);
	   merge(a, aux, b, lo, mid, hi);
	   
   }
   
   private void mergeSort(int lo, int hi) {
	   lo = 0;
	   hi = points.length -1;
	   int segLength = 0;
	   // copy points onto another array as need to feed in multiple times
	   Point[] copy = new Point[points.length];
	   for(int i = 0; i < points.length; i++) {
		   copy[i] = points[i];
	   }
	   for(int i = 0; i < points.length; i++) {
		   sort(copy, aux, lo, hi, copy[i]);
		   // look for and save 4 or more points which have the same slope
		   Comparator<Point> c = points[i].slopeOrder();
		   int count = 0;
		   for(int j = i; j < copy.length - 1; j++) { 
			   int next = j + 1;
			   //System.out.println(count);
			   if(c.compare(copy[i],copy[next])== 0) {
				   count++;
				   if(j == copy.length -2) {
					   if(count >= 3) {
						   LineSegment line = new LineSegment(points[i],copy[j]);
						   segments[segLength] = line;
						   segLength ++;
						   count = 0;
				   }
			   }   
			   else {
				   if(count >= 3) {
					   LineSegment line = new LineSegment(points[i],copy[j]);
					   segments[segLength] = line;
					   segLength ++;
					   count = 0;
				   }
				   
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
	   FastCollinearPoints test = new FastCollinearPoints(p);
	   System.out.println(test.numberOfSegments());
	   LineSegment[] mySegs = test.segments();
	   System.out.println(mySegs[0]);
	   System.out.println(mySegs[1]);
	   System.out.println(mySegs[2]);
	   System.out.println(mySegs[3]);
   }
}