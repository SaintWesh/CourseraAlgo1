import java.util.ArrayList;
import java.util.Arrays;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.In;

public class FastCollinearPoints {
    private final Point[] points;
    private LineSegment[] ls;
    // finds all line segments containing 4 or more points
    public FastCollinearPoints(Point[] ps) {
    	 //check
        if (ps == null) throw new java.lang.NullPointerException();
        int len = ps.length;
        for (int i = 0; i < len; i++) {
            if (ps[i] == null) throw new java.lang.NullPointerException();
        }
        // find duplicates
        points = Arrays.copyOf(ps, len);
        Arrays.sort(points);
        Point last = points[0];
        for (int i = 1; i < len; i++) {
            if (points[i].compareTo(last) == 0) 
                throw new java.lang.IllegalArgumentException();
            last = points[i];
        }
    }

    private void findSegments() {
        int len = points.length;
        ArrayList<LineSegment> als = new ArrayList<LineSegment>();
        Point[] copy = Arrays.copyOf(points, len);
        // find all line segments containing 4 or more points
        for (int i = 0; i < len - 3; i++) {
            Point p = points[i];
            Arrays.sort(copy, p.slopeOrder());

            for (int j = 1; j < len - 2;) {
                double firstSlope = p.slopeTo(copy[j]);
                int jj = j;
                boolean flag = true;
                Point endPoint = copy[j];
                while (jj < len && p.slopeTo(copy[jj]) == firstSlope) {
                    if (copy[jj].compareTo(p) < 0) flag = false;
                	if (copy[jj].compareTo(endPoint) > 0) endPoint = copy[jj];
                	// StdOut.println(flag);
                    jj++;
                }
                // p is the lower end of the segment
                if (jj - j >= 3 && flag) {
                    als.add(new LineSegment(p, endPoint));
                }
                // change j
                j = jj;
            }
        }
        ls = new LineSegment[als.size()];
        als.toArray(ls);
    }
    // the number of line segments
    public int numberOfSegments() {
        if (ls == null) findSegments();
        return ls.length;
    }
    // the line segments
    public LineSegment[] segments() {
        if (ls == null) findSegments();
        return Arrays.copyOf(ls, ls.length);
    }

    public static void main(String[] args) {

    // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
    
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.show(0);
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
    }
}