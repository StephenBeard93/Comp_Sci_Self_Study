import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;
import java.util.ArrayList;
import java.util.Iterator;

public class KdTree {
   public Node root;
   private int n;
   private KdTreeIterable itr = new KdTreeIterable();
   
   // construct an empty set of points
   public KdTree() {
	    
   }
   
   // private node class 
   private class Node{
	 public Point2D p;
	 public Node left = null;
	 public Node right = null;
	 
	 public Node(Point2D p) {
		 this.p = p;
	 }
   }
   
   private class KdTreeIterable implements Iterable<Point2D>{
	   ArrayList<Point2D> list = new ArrayList<Point2D>();
	   
	   public KdTreeIterable(Node root){
		   buildList(root);
	   }
	   
	   public KdTreeIterable(){
		   }
	   
	   // return iterable
	   public Iterator<Point2D> iterator(){
		   return list.iterator();
	   }
	   
	   
	   // recursively check every tree in the node
	   public void buildList(Node root){
		   if(root.left != null) {
			   buildList(root.left);
		   }
		   if(root.right != null) {
			   buildList(root.right);
		   }
		   list.add(root.p);
		   return;
	   }
	   
	   public boolean hasNext() {
	       return list.iterator().hasNext();
	   }
	   
	   public Point2D next() {
		   return list.iterator().next();
	   }
   }

   
   // is the set empty?	
   public boolean isEmpty() {
	   if(root == null) {
		   return true;
	   }
	   else {
		   return false;
	   }
   }
   
   // number of points in the set
   public int size() {
	    return n;
   }
   
   // add the point to the set (if it is not already in the set)
   public void insert(Point2D p) {
	   if(contains(p) == true) {
		   return;
	   }
	   if(n == 0) {
		   Node first = new Node(p);
		   root = first;
		   n++;
		   return;
	   }
	   n++; // increment size of set 
	   Node last = root;
	   Node next = root;
	   boolean isX = true;
	   boolean wasLastMoveLeft = true;
	   // rotate axis check
	   while(next != null) {
		   last = next;
		   if(isX){
			   if(p.x() < next.p.x()) {
				   next = next.left;
				   isX = false;
				   wasLastMoveLeft = true;
			   }
			   else {
				   next = next.right;
				   isX = false;
				   wasLastMoveLeft = false;
			   }
		   }
		   else {
			   if(p.y() < next.p.y()) {
				   next = next.left;
				   isX = true;
				   wasLastMoveLeft = true;
			   }
			   else {
				   next = next.right;
				   isX = true;
				   wasLastMoveLeft = false;
			   }
		   }
	   }
	   // insert point p 
	   if(wasLastMoveLeft) {
		   last.left = new Node(p);
	   }
	   else {
		   last.right = new Node(p);
	   }
   }
   
   // does the set contain point p?
   public boolean contains(Point2D p) {
	   if(n == 0) {
		   return false;
	   } 
	   Node next = root;
	   boolean isX = true;
	   // rotate axis check
	   while(next != null) {
		   // check whether points are equal
		   if(next.p.x() == p.x()&& next.p.y() == p.y()) {
			   return true;
		   }
		   if(isX){
			   if(p.x() < next.p.x()) {
				   next = next.left;
				   isX = false;
			   }
			   else {
				   next = next.right;
				   isX = false;
			   }
		   }
		   else {
			   if(p.y() < next.p.y()) {
				   next = next.left;
				   isX = true;
			   }
			   else {
				   next = next.right;
				   isX = true;
			   }
		   }
	   }
	   // return false if search algorithim reaches null node without finding a match
	   return false;
   }
   
   // draw all points to standard draw
   public void draw() {
	   StdDraw.setPenRadius(0.01); 
	   StdDraw.setPenColor(StdDraw.BLACK); 
	   StdDraw.setScale(0,100);
	   // get iterable object 
	   KdTreeIterable itr = new KdTreeIterable(root);
	   for(Point2D b : itr){
		   b.draw();
	   }
	 //while(itr.hasNext()) {
		//   itr.next().draw();
	  //}
   }
   
   // all points that are inside the rectangle (or on the boundary) 
   public Iterable<Point2D> range(RectHV rect, Node root, boolean isX){
	   if(root == null) {
		   return itr;
	   }
	   if(rect.contains(root.p)) {
		   itr.list.add(root.p);
	   }
	   if(isX) {
		   if(rect.xmin() < root.p.x()) {
			   isX = false;
			   range(rect, root.left, isX);
		   }
		   isX = false;
		   range(rect, root.right, isX);
	   }
	   else{
		  if(rect.ymin() < root.p.y()) {
			  isX = true;
			  range(rect, root.left, isX);
		  }
		  isX = true;
		  range(rect, root.right, isX);
	   }
	   return itr;
   }
	   
	   
   // a nearest neighbor in the set to point p; null if the set is empty
   public Point2D nearest(Point2D p) {
	    
   }

   // unit testing of the methods (optional)
   public static void main(String[] args) {
	   KdTree myTree = new KdTree();
	   myTree.insert(new Point2D(1,1));
	   myTree.insert(new Point2D(2,2));
	   myTree.insert(new Point2D(2,3));
	   myTree.insert(new Point2D(1,2));
	   myTree.insert(new Point2D(4,4));
	   myTree.draw();
	   RectHV myRect = new RectHV(1,1,2,2); 
	   Iterable<Point2D> myIterable = myTree.range(myRect, myTree.root,true);
	   for(Point2D p : myIterable) {
		   System.out.println(p);
	   }
   }
}