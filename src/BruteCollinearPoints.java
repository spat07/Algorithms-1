
public class BruteCollinearPoints {
    private int segmentCnt = 0;
    LineSegment ls[] = new LineSegment[100];
    public BruteCollinearPoints(Point[] points)    // finds all line segments containing 4 points
    {
        if(points.length == 0)
            throw new java.lang.IllegalArgumentException("number of input points can not be zzero!");

        for(int i = 0; i < points.length; i++)
            for(int j = i + 1; j < points.length; j++)
                for(int k = j + 1; k < points.length; k++)
                    for(int l = k + 1; l < points.length; l++)
                    {
                        if((points[i].slopeOrder().compare(points[j], points[k]) == 0) &&
                                (points[j].slopeOrder().compare(points[k], points[l]) == 0))
                        {
                            if(segmentCnt >= 100)
                                throw new java.lang.ArrayIndexOutOfBoundsException();
                            ls[segmentCnt] = new LineSegment(points[i], points[k]);
                            segmentCnt++;
                            i += 4; j +=4; k += 4; l +=4;
                        }
                        System.out.printf("i = %d, j = %d, k = %d, l = %d\n", i, j, k, l);
                        
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
    
    public static void main(String args[])
    {
        Point pt[] = new Point[20];
        for(int i = 1; i < 21; i++)
        {
            pt[i-1] = new Point(i, i);
            //pt[i].draw();
        }
        
        //pt[0].drawTo(pt[10]);
        
        BruteCollinearPoints clp1 = new BruteCollinearPoints(pt);
        System.out.printf("number of segments = %d", clp1.numberOfSegments());
        
    }
};