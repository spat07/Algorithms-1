import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdDraw;
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
       for(Point2D p : pset)
       {
           if(rect.contains(p))
               res.push(p);
       }
       return (res);
   }
   public Point2D nearest(Point2D that)             // a nearest neighbor in the set to point p; null if the set is empty
   {
       double dist = 1.0;
       Point2D res = null;
       for(Point2D p : pset)
       {
           if(p.distanceTo(that) < dist)
           {
               dist = p.distanceTo(that);
               res = p;
           }
       }
       return (res);
   }

   public static void main(String[] args)                  // unit testing of the methods (optional)
   {

       RectHV rc1 = new RectHV(0.0, 0.0, 1.0, 1.0);
       StdDraw.setPenRadius(0.01);
       rc1.draw();
       
       PointSET ps1 = new PointSET();
       StdDraw.setPenRadius(0.02);
       
       Point2D p1 = new Point2D(0.25, 0.5);
       ps1.insert(p1);
       
       Point2D p2 = new Point2D(0.25, 1.0);
       ps1.insert(p2);
       
       Point2D p3 = new Point2D(0.5, 0.75);
       ps1.insert(p3);
       
       Point2D p4 = new Point2D(0.5, 0.25);
       ps1.insert(p4);
       
       System.out.printf("%d points in the set\n", ps1.size());
       ps1.draw();
       
       Point2D op1 = new Point2D(0.75, 0.75);
       StdDraw.setPenColor(StdDraw.RED);
       op1.draw();
       
       System.out.printf("Nearest pt is %s\n", ps1.nearest(op1).toString());
       
       RectHV rc2 = new RectHV(0.25, 0.25, 0.75, 0.5);
       StdDraw.setPenRadius(0.01);
       StdDraw.setPenColor(StdDraw.BLUE);
       rc2.draw();
       
       System.out.printf("Point in the rectHV %s\n", rc2.toString());
       for(Point2D p : ps1.range(rc2))
       {
           System.out.println(p.toString());
       }
       
   }
}