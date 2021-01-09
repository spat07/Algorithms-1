import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.RectHV;

public class PointSET {
   private SET<Point2D> pset;
   
   public PointSET()                               // construct an empty set of points 
   {
      System.out.println("construct point\n");
      pset = new SET<Point2D>();
   };
   public boolean isEmpty()                      // is the set empty? 
   {
       return ((size() != 0) ? false:true);
   }
   public int size()                         // number of points in the set 
   {
       return (pset.size());
   }
   public void insert(Point2D p)              // add the point to the set (if it is not already in the set)
   {
       pset.add(p);
   }
   public boolean contains(Point2D p)            // does the set contain point p? 
   {
       return (pset.contains(p) ? true : false);
   }
   public void draw()                         // draw all points to standard draw 
   {
       for(Point2D p : pset)
       {
           p.draw();
       }
   }
   public Iterable<Point2D> range(RectHV rect)             // all points that are inside the rectangle (or on the boundary)
   {
       Stack<Point2D> res = new Stack<Point2D>();
       
       return (res);
   }
   public Point2D nearest(Point2D p)             // a nearest neighbor in the set to point p; null if the set is empty
   {
       return (null);
   }

   public static void main(String[] args)                  // unit testing of the methods (optional)
   {
       PointSET ps1 = new PointSET();
       
       Point2D p1 = new Point2D(0.4, 0.4);
       ps1.insert(p1);
       
       Point2D p2 = new Point2D(0.3, 0.4);
       ps1.insert(p2);
       
       Point2D p3 = new Point2D(0.4, 0.5);
       ps1.insert(p3);
       
       Point2D p4 = new Point2D(0.3, 0.5);
       ps1.insert(p4);
       
       System.out.printf("%d points in the set\n", ps1.size());
       ps1.draw();
   }
}