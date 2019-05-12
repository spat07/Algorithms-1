public class BruteCollinearPoints {
    private int segmentCnt = 0;
    LineSegment ls[] = new LineSegment[1000];
    public BruteCollinearPoints(Point[] points)    // finds all line segments containing 4 points
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
                        }
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
        
    }
};