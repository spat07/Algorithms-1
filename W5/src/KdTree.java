import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.RectHV;

public class KdTree {
   private class Node {
       private Point2D pt;
       private Node lb;
       private Node rt;
       private RectHV rect;
   }
   private Node root;
   private int size;
   
   public KdTree()                               // construct an empty set of points 
   {
      //System.out.println("construct 2d Tree\n");
      root = null;
      size = 0;
   };
   public boolean isEmpty()                      // is the set empty? 
   {
       return ((size() != 0) ? false:true);
   }
   public int size()                         // number of points in the set 
   {
       return (size);
   }
   
   private Node insertHelper(Node n, Point2D p, boolean isHoriz, double xmin, double ymin, double xmax, double ymax)
   {
       if (n == null)
       {
    	   
           n = new Node();
           n.pt = p;
           n.lb = null;
           n.rt = null;
           n.rect = new RectHV(xmin, ymin, xmax, ymax);
           
           size++;
           
           return n;
       }
       
       
       if (isHoriz)
       {
           if(p.x() < n.pt.x()) // pt in vert - left rect - 
           {
               n.lb =  insertHelper(n.lb, p, !isHoriz, n.rect.xmin(), n.rect.ymin(), n.pt.x(), n.rect.ymax());
           }
           else // pt in vert - right rect
           {
               n.rt =  insertHelper(n.rt, p, !isHoriz, n.pt.x(), n.rect.ymin(), n.rect.xmax(), n.rect.ymax());
           }
       }
       else
       {
           if(p.y() < n.pt.y()) // pt in horiz bottom
           {
               n.lb = insertHelper(n.lb, p, !isHoriz, n.rect.xmin(), n.rect.ymin(), n.rect.xmax(), n.pt.y());
           }
           else
           {
               n.rt = insertHelper(n.rt, p, !isHoriz, n.rect.xmin(), n.pt.y(), n.rect.xmax(), n.rect.ymax());
           }
       }
	return n;
   }
   public void insert(Point2D p)              // add the point to the set (if it is not already in the set)
   {
	   root = insertHelper(root, p, true, 0.0, 0.0, 1.0, 1.0);
   }

   private boolean containsHelper(Node n, Point2D p, boolean isHoriz)
   {
       if (n == null)
       {
           return false;
       }
       
       if(n.pt.equals(p))
       {
           return true;
       }
           
       if (isHoriz)
       {
           if(p.x() < n.pt.x())
               return containsHelper(n.lb, p, !isHoriz);
           else if (p.x() >= n.pt.x())
               return containsHelper(n.rt, p, !isHoriz);
       }
       else
       {
           if(p.y() < n.pt.y())
               return containsHelper(n.lb, p, !isHoriz);
           else if (p.y() >= n.pt.y())
               return containsHelper(n.rt, p, !isHoriz);
       }
       
       //should never come here?
       return false;
   }

   public boolean contains(Point2D p)            // does the set contain point p? 
   {
       return (containsHelper(root, p, true));
   }
   public void draw()                         // draw all points to standard draw 
   {
       //level order traversal and draw
       Queue<Node> dtree = new Queue<Node>();
       Queue<Boolean> ori = new Queue<Boolean>();
       
       if(root != null)
       {
	       dtree.enqueue(root);
	       ori.enqueue(false);
       }
       
       while(!dtree.isEmpty())
       {
           
           Node n = dtree.dequeue();
           Boolean isHoriz = ori.dequeue();
           
           //draw line
           if(!isHoriz)
           {
               StdDraw.setPenColor(StdDraw.RED);
               StdDraw.line(n.pt.x(), n.rect.ymin(), n.pt.x(), n.rect.ymax());
           }
           else
           {
               StdDraw.setPenColor(StdDraw.BLUE);
               StdDraw.line(n.rect.xmin(), n.pt.y(), n.rect.xmax(), n.pt.y());
           }
           
           //enqueue children
           if (n.lb != null) 
           {
	           dtree.enqueue(n.lb);
	           ori.enqueue(!isHoriz);
           }
           if(n.rt != null)
           {
	           dtree.enqueue(n.rt);
	           ori.enqueue(!isHoriz);
           }
       }
       
   }
   public Iterable<Point2D> range(RectHV qRect)             // all points that are inside the rectangle (or on the boundary)
   {
       Stack<Point2D> res = new Stack<Point2D>();
       Queue<Node> stree = new Queue<Node>();
       
       if(root != null)
       {
    	   stree.enqueue(root);
       }
       
       while(!stree.isEmpty())
       {
           Node n = stree.dequeue();
           if(qRect.intersects(n.rect))
           {
               // check if n is in query rect
               if(qRect.contains(n.pt))
                   res.push(n.pt);
               // add subtree for search
               if(n.lb != null)
               {
            	   stree.enqueue(n.lb);
               }
               
               if(n.rt != null)
               {
            	   stree.enqueue(n.rt);
               }
           }
       }
       return (res);
   }
   
   private Point2D nearest_helper(Node n, Point2D res, Point2D that)
   {

	   if(n == null)
	   {
		   return res;
	   }
	   
	   //highlight selected rectangle
//	   StdDraw.setPenColor(StdDraw.YELLOW);
//	   n.rect.draw();

//   	   System.out.printf("n.pt = %s, res.pt = %s, that.pt = %s\n", n.pt.toString(), res.toString(), that.toString());
   
       if((n.lb != null && n.lb.rect.distanceTo(that) < res.distanceTo(that)) &&
       (n.rt != null && n.rt.rect.distanceTo(that) < res.distanceTo(that)))
       {
//     	   System.out.printf("n.lb.Drec = %f, res.D = %f\n", n.lb.rect.distanceTo(that), res.distanceTo(that));
//    	   System.out.printf("n.rt.Drec = %f, res.D = %f\n", n.rt.rect.distanceTo(that), res.distanceTo(that));
    	   
    	   if(n.lb.rect.contains(that))
    	   {
    		   if(n.lb.pt.distanceTo(that) < res.distanceTo(that))
    		   {
    			   res = nearest_helper(n.lb, n.lb.pt, that);
    		   }
    	   }
    	   else
    	   {
    		   if(n.rt.pt.distanceTo(that) < res.distanceTo(that))
    		   {
    			   res = nearest_helper(n.rt, n.rt.pt, that);
    		   }
    	   }
       }
       else if(n.rt != null && n.rt.rect.distanceTo(that) < res.distanceTo(that))
       {
//    	   System.out.printf("n.rt.Drec = %f, res.D = %f\n", n.rt.rect.distanceTo(that), res.distanceTo(that));
    	   if(n.rt.pt.distanceTo(that) < res.distanceTo(that))
    	   {
    		   res = nearest_helper(n.rt, n.rt.pt, that);
    	   }
       }
       else if(n.lb != null && n.lb.rect.distanceTo(that) < res.distanceTo(that))
       {
//    	   System.out.printf("n.lb.Drec = %f, res.D = %f\n", n.lb.rect.distanceTo(that), res.distanceTo(that));
    	   if(n.lb.pt.distanceTo(that) < n.rect.distanceTo(that))
    	   {
    		   res = nearest_helper(n.lb, n.lb.pt, that);
    	   }
       }
    
       return res;
   }
   public Point2D nearest(Point2D that)             // a nearest neighbor in the set to point p; null if the set is empty
   {
	   Point2D res = root.pt;
	   
	   res = nearest_helper(root, res, that);
	   return res;
   }
//   public Point2D nearest(Point2D that)             // a nearest neighbor in the set to point p; null if the set is empty
//   {
//       Point2D res = root.pt; //initialize with root pt
//       
//       Queue<Node> stree = new Queue<Node>();
//       
//       if (root != null)
//       {
//    	   stree.enqueue(root);
//       }
//       
//       while(!stree.isEmpty())
//       {
//           Node n = stree.dequeue();
//           
//           if((n.lb != null && n.lb.rect.distanceTo(that) < res.distanceTo(that)) &&
//           (n.rt != null && n.rt.rect.distanceTo(that) < res.distanceTo(that)))
//           {
//               // if both subtrees are close then choose the one on the side of pt
//               if(n.lb.rect.contains(that))
//               {
//                   stree.enqueue(n.lb);
//                   res = n.lb.pt;
//               }
//               else
//               {
//                   stree.enqueue(n.rt);
//                   res = n.rt.pt;
//               }
//           }
//           else if(n.lb != null && n.lb.rect.distanceTo(that) < res.distanceTo(that))
//           {
//               stree.enqueue(n.lb);
//               res = n.lb.pt;
//           }
//           else
//           {
//        	   if(n.rt != null)
//        	   {
//	               stree.enqueue(n.rt);
//	               res = n.rt.pt;
//        	   }
//           }
//       }
//       
//       
//       return (res);
//   }

   public static void main(String[] args)                  // unit testing of the methods (optional)
   {

       RectHV rc1 = new RectHV(0.0, 0.0, 1.0, 1.0);
       StdDraw.setPenRadius(0.01);
       rc1.draw();
       
       KdTree kdt = new KdTree();
       StdDraw.setPenRadius(0.02);
       
       String filename = args[0];
       In in = new In(filename);
       while (!in.isEmpty()) {
           double x = in.readDouble();
           double y = in.readDouble();
           Point2D p = new Point2D(x, y);
           kdt.insert(p);
           p.draw();
       }
       
       System.out.printf("%d points in the set\n", kdt.size());
       StdDraw.setPenRadius(0.01);
       kdt.draw();
       
       Point2D op1 = new Point2D(0.5, 0.7);
       StdDraw.setPenRadius(0.03);
       StdDraw.setPenColor(StdDraw.YELLOW);
       op1.draw();
       
       System.out.printf("Nearest pt is %s\n", kdt.nearest(op1).toString());
       
       RectHV rc2 = new RectHV(0.0, 0.0, 1.0, 1.0);
       StdDraw.setPenRadius(0.01);
       StdDraw.setPenColor(StdDraw.GREEN);
       rc2.draw();
       
       System.out.printf("Point in the rectHV %s\n", rc2.toString());
       for(Point2D p : kdt.range(rc2))
       {
           System.out.println(p.toString());
       }
       
   }
}