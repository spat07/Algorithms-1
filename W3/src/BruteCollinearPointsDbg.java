
import edu.princeton.cs.algs4.StdRandom;

public class BruteCollinearPointsDbg {
    private int segmentCnt = 0;
    LineSegment ls[] = new LineSegment[1000];
    public BruteCollinearPointsDbg(Point[] points)    // finds all line segments containing 4 points
    {
        if(points.length == 0)
            throw new java.lang.IllegalArgumentException("number of input points can not be zero!");
        if(Duplicates(points))
            throw new java.lang.IllegalArgumentException("either NULL or duplicate points in input!");

        for(int i = 0; i < points.length;i++)
            for(int j = i + 1; j < points.length;j++)
                for(int k = j + 1; k < points.length;k++)
                    for(int l = k + 1; l < points.length;l++)
                    {
                        if((points[i].slopeOrder().compare(points[j], points[k]) == 0) &&
                                (points[j].slopeOrder().compare(points[k], points[l]) == 0))
                        {
                            if(segmentCnt >= 1000)
                                throw new java.lang.ArrayIndexOutOfBoundsException();
                            ls[segmentCnt] = new LineSegment(points[i], points[k]);
                            segmentCnt++;
                            break;
                            //i += 4; j +=4; k += 4; l +=4;
                        }
//                        else
//                        {
//                            i++; j++; k++; l++;
//                        }
                        //System.out.printf("i = %d, j = %d, k = %d, l = %d\n", i, j, k, l);
                        
                    }
    }
    public int numberOfSegments()        // the number of line segments
    {
        return segmentCnt;
    }
    public LineSegment[] segments()                // the line segments
    {
        return ls;
    }
    private Boolean Duplicates(Point[] points)
    
    {
        for (int i=0; i < points.length;i++)
        {
            if (points[i] != null)
            {
                for(int j = i; j < points.length;j++)
                    if(points[i].equals(points[j]))
                        return false;
            }
            else
            {
                return false;
            }
        }
        return true;
    }

    public static void main(String args[])
    {
        //test 1
        System.out.println("**************test1************\n");
        Point pt[] = new Point[20];
        for(int i = 0; i < 20; i++)
        {
            pt[i] = new Point(i, i);
            //pt[i].draw();
        }
        
        //pt[0].drawTo(pt[10]);
        
        BruteCollinearPointsDbg clp1 = new BruteCollinearPointsDbg(pt);
        System.out.printf("clp1:number of segments = %d", clp1.numberOfSegments());

            
        
        //test 2
        System.out.println("\n**************test2************\n");
        Point pt1[] = new Point[20];
        
        for (int i = 0; i < 20; i++)
        {
            int x = StdRandom.uniform(i+20);
            int y = StdRandom.uniform(i+20);
            
            pt1[i] = new Point(x,y);
        }
        
        BruteCollinearPointsDbg clp2 = new BruteCollinearPointsDbg(pt1);
        System.out.printf("clp2:number of line segments = %d\n", clp2.numberOfSegments());

        
        LineSegment ll1[] = clp2.segments();
        for(int l = 0; l < clp2.numberOfSegments();l++)
        {
//            ll1[l].draw();
//            try {
//                Thread.sleep(100);
//            } catch (InterruptedException e) {
//                // TODO Auto-generated catch block
//                e.printStackTrace();
//            }
            System.out.print(ll1[l].toString()+"\n");
        }           
        
    }
};