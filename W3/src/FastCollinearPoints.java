
public class FastCollinearPoints {

    private int segmentCnt = 0;
    LineSegment ls[] = new LineSegment[1000];
    
    class Slopes
    {
        double slope;
        Point p;
    };
    
    public FastCollinearPoints(Point[] points) // finds all line segments containing 4 or more points
    {
        Slopes [] slopes;
        if(points.length == 0)
            throw new java.lang.IllegalArgumentException("number of input points can not be zero!");
        if(Duplicates(points))
            throw new java.lang.IllegalArgumentException("either NULL or duplicate points in input!");
        
        slopes = new Slopes[points.length];
        for (int i = 0; i < points.length; i++)
        {
            for (int j = i, cnt = 0; j < points.length; j++, cnt++)
            {
                slopes[cnt].slope = points[i].slopeTo(points[j]);
                slopes[cnt].p = points[j];
            }
            Sort(slopes);
            if(slopes.length >=3 &&
                    (slopes[0].slope == slopes[1].slope) &&
                    (slopes[1].slope == slopes[2].slope))
            {
                ls[segmentCnt++] = new LineSegment(points[i], slopes[2].p);
            }
        }

    }
    public int numberOfSegments() // the number of line segments
    {
        return segmentCnt;
    }
    public LineSegment[] segments() // the line segments
    {
        return ls;
    }
    
    private void Sort(Slopes [] slopes)
    {
        
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

}