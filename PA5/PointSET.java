import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.StdDraw;
import java.util.ArrayList;

public class PointSET {

    private SET<Point2D> points;
    // construct an empty set of points 
    public PointSET() {
        points = new SET<Point2D>();
    }                             
    // is the set empty? 
    public boolean isEmpty() {
        return points.isEmpty();
    }
    // number of points in the set 
    public int size() {
        return points.size();
    }
    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {
        if (p == null) throw new java.lang.NullPointerException();
        points.add(p);
    }
    // does the set contain point p? 
    public boolean contains(Point2D p) {
        if (p == null) throw new java.lang.NullPointerException();
        return points.contains(p);
    }
    // draw all points to standard draw 
    public void draw() {
        for (Point2D point : points) {
            StdDraw.point(point.x(), point.y());
        }
    }                       
    // all points that are inside the rectangle 
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) throw new java.lang.NullPointerException();
        ArrayList<Point2D> ret = new ArrayList<Point2D>();
        for (Point2D point : points) {
            if (rect.contains(point)) {
                ret.add(point);
            }
        }
        ret.trimToSize();
        return ret;
    }
    // a nearest neighbor in the set to point p; null if the set is empty 
    public Point2D nearest(Point2D p) {
        if (p == null) throw new java.lang.NullPointerException();
        Point2D nearestPoint = null;
        double nearestDistance = Double.POSITIVE_INFINITY;
        for (Point2D point : points) {
            double dis = point.distanceTo(p);
            if (dis < nearestDistance) {
                nearestPoint = point;
                nearestDistance = dis;
            }
        }
        return nearestPoint;
    }        
    // unit testing of the methods (optional) 
    public static void main(String[] args) {

    }     

}


