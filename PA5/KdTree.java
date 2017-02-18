import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;
import java.util.ArrayList;

public class KdTree {
    private Node root;
    private int size;
    // construct an empty set of points 
    public KdTree() {

    }                             
    
    private static class Node {
        private final Point2D p; // the point
        private final RectHV rect; // the axix-aligned rectangle corresponding to this node
        private Node lb; // the left/bottom subtree
        private Node rt; // the right/top subtree

        public Node(Point2D p, RectHV rect, Node lb, Node rt) {
            this.p = p;
            this.rect = rect;
            this.lb = lb;
            this.rt = rt;
        }

        public Point2D getPoint() {
            return p;
        }

        public RectHV getRect() {
            return rect;
        }

        public Node getLb() {
            return lb;
        }

        public Node getRt() {
            return rt;
        }

        public void setLb(Node lb) {
            this.lb = lb;
        }
        
        public void setRt(Node rt) {
            this.rt = rt;
        }

    }
    // is the set empty? 
    public boolean isEmpty() {
        return root == null;
    }
    // number of points in the set 
    public int size() {
        return size;
    }                   
    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {
        if (p == null) throw new java.lang.NullPointerException();
        if (root == null) {
            root = new Node(p, new RectHV(0.0, 0.0, 1.0, 1.0), null, null);
            ++size;
            return;
        }
        Node current = root, last = null;
        boolean isVertical = true;
        double cmp = 0.0;
        while (current != null) {
            // already inserted
            if (current.getPoint().equals(p)) return;
            // keep the current Node in last
            last = current;
            // change the current Node
            if (isVertical) {
                cmp = p.x() - current.getPoint().x();
                if (cmp > 0) current = current.getRt();
                else current = current.getLb();
            } else {
                cmp = p.y() - current.getPoint().y();
                if (cmp > 0) current = current.getRt();
                else current = current.getLb();
            }
            isVertical = !isVertical;
        }
        // insert
        isVertical = !isVertical;
        RectHV lastRect = last.getRect();
        RectHV currentRect = null;
        double lastX = last.getPoint().x();
        double lastY = last.getPoint().y();

        if (cmp > 0.0) {
            if (isVertical) {
                currentRect = new RectHV(lastX, lastRect.ymin(), lastRect.xmax(), lastRect.ymax());
            } else {
                currentRect = new RectHV(lastRect.xmin(), lastY, lastRect.xmax(), lastRect.ymax());
            }
            last.setRt(new Node(p, currentRect, null, null));
        } else {
            if (isVertical) {
                currentRect = new RectHV(lastRect.xmin(), lastRect.ymin(), lastX, lastRect.ymax());
            } else {
                currentRect = new RectHV(lastRect.xmin(), lastRect.ymin(), lastRect.xmax(), lastY);
            }
            last.setLb(new Node(p, currentRect, null, null));
        }
        ++size;
    }
    // does the set contain point p? 
    public boolean contains(Point2D p) {
        if (p == null) throw new java.lang.NullPointerException();
        Node current = root;
        boolean isVertiacal = true;
        while (current != null) {
            Point2D currentPoint = current.getPoint();
            if (p.equals(currentPoint)) return true;
            if (isVertiacal) {
                if (p.x() > currentPoint.x()) current = current.getRt();
                else current = current.getLb();
            } else {
                if (p.y() > currentPoint.y()) current = current.getRt();
                else current = current.getLb();
            }
            isVertiacal = !isVertiacal;
        }
        return false;
    }
    
    private void draw(Node x, boolean isVertiacal) {
        if (x == null) return;
        // draw the point in the node x
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius();
        StdDraw.point(x.getPoint().x(), x.getPoint().y());
        // draw the line
        if (isVertiacal) {
            StdDraw.setPenColor(StdDraw.RED);
            StdDraw.setPenRadius();
            StdDraw.line(x.getPoint().x(), x.getRect().ymin(), x.getPoint().x(), x.getRect().ymax());
        } else {
            StdDraw.setPenColor(StdDraw.BLUE);
            StdDraw.setPenRadius();
            StdDraw.line(x.getRect().xmin(), x.getPoint().y(), x.getRect().xmax(), x.getPoint().y());
        }
        draw(x.getLb(), !isVertiacal);
        draw(x.getRt(), !isVertiacal);
    }
    // draw all points to standard draw 
    public void draw() {
        StdDraw.setScale(0, 1);
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius();
        root.getRect().draw();
        draw(root, true);
    }

    private void range(Node x, RectHV rect, ArrayList<Point2D> points) {
        if (x == null) return;
        if (!rect.intersects(x.getRect())) return;
        if (rect.contains(x.getPoint())) points.add(x.getPoint());
        range(x.getLb(), rect, points);
        range(x.getRt(), rect, points);
    }
    // all points that are inside the rectangle 
    public Iterable<Point2D> range(RectHV rect) {
        ArrayList<Point2D> points = new ArrayList<Point2D>();
        range(root, rect, points);
        points.trimToSize();
        return points;
    }

    private Point2D nearest(Node x, Point2D p, Point2D nearestPoint, double nearestDis, boolean isVertiacal) {
        if (x == null) return nearestPoint;
        double disRect = x.getRect().distanceSquaredTo(p);
        if (disRect > nearestDis) return nearestPoint;
        double dis = p.distanceSquaredTo(x.getPoint());
        if (dis < nearestDis) {
            nearestDis = dis;
            nearestPoint = x.getPoint();
        }
        Point2D currentPoint = x.getPoint();
        Node first = null, next = null;
        if ((isVertiacal) && p.x() < currentPoint.x() || (!isVertiacal && p.y() < currentPoint.y())) {
                first = x.getLb();
                next = x.getRt();
        } else {
            first = x.getRt();
            next = x.getLb();
        }
        nearestPoint = nearest(first, p, nearestPoint, nearestDis, !isVertiacal);
        return nearest(next, p, nearestPoint, p.distanceSquaredTo(nearestPoint), !isVertiacal);
    }
    // a nearest neighbor in the set to point p; null if the set is empty 
    public Point2D nearest(Point2D p) {
        if (p == null) throw new java.lang.NullPointerException();
        return nearest(root, p, null, Double.POSITIVE_INFINITY, true);
    }

    // public Point2D nearest(Point2D p) {

    // }
    // unit testing of the methods (optional) 
    public static void main(String[] args) {

    }                 
}