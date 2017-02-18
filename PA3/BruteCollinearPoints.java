import java.util.Arrays;
import java.util.ArrayList;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.In;

public class BruteCollinearPoints {
    private final Point[] points;
    private LineSegment[] ls;
    // finds all line segments containing 4 points
    public BruteCollinearPoints(Point[] ps) {
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
    // a lazy approach
    private void findSegments() {
        int len = points.length;
        ArrayList<LineSegment> als = new ArrayList<LineSegment>();

        for (int i = 0; i < len; i++) {
            for (int j = i + 1; j < len; j++) {
                for (int k = j + 1; k < len; k++) {
                    for (int m = k + 1; m < len; m++) {
                        Point p = points[i];
                        if (p.slopeTo(points[j]) == p.slopeTo(points[k]) 
                            && p.slopeTo(points[j]) == p.slopeTo(points[m])) {
                            als.add(new LineSegment(p, points[m]));
                        }
                    }
                }
            }
        }
        // change state
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
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
    }
}