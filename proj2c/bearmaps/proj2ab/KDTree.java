package bearmaps.proj2ab;
import java.util.List;
import java.util.NoSuchElementException;

public class KDTree implements PointSet {

    private Node root;

    public KDTree(List<Point> points) {
        root = new Node(points.get(0));
        for(int i = 1; i < points.size(); i++) {
            root.insert(root, points.get(i), 0);
        }
    }

    @Override
    public Point nearest(double x, double y) {
        Point myPoint = new Point(x, y);
        Node best = root.findNearest(root, myPoint, root, 0);
        return new Point(best.x, best.y);
    }

    private class Node {
        private double x;
        private double y;
        private Node left;
        private Node right;

        public Node(Point p) {
            this.x = p.getX();
            this.y = p.getY();
            this.left = null;
            this.right = null;
        }

        public Node insert(Node root, Point p, int depth) {
            if(root == null) return new Node(p);
            if(depth % 2 == 0) {
                if(Double.compare(p.getX(), root.x) >= 0) {
                    root.right = insert(root.right, p, depth + 1);
                } else {
                    root.left = insert(root.left, p, depth + 1);
                }
            } else {
                if(Double.compare(p.getY(), root.y) >= 0) {
                    root.right = insert(root.right, p, depth + 1);
                } else {
                    root.left = insert(root.left, p, depth + 1);
                }
            }
            return root;
        }

        public Node findNearest(Node root, Point p, Node best, int depth) {
            Node good;
            Node bad;
            if(root == null) return best;
            if(Point.distance(p, new Point(root.x, root.y)) < Point.distance(p, new Point(best.x, best.y))) {
                best = root;
            }
            if(depth % 2 == 0) {
                if(Double.compare(p.getX(), root.x) >= 0) {
                    good = root.right;
                    bad = root.left;
                } else {
                    good = root.left;
                    bad = root.right;
                }
            } else {
                if(Double.compare(p.getY(), root.y) >= 0) {
                    good = root.right;
                    bad = root.left;
                } else {
                    good = root.left;
                    bad = root.right;
                }
            }
            best = findNearest(good, p, best, depth + 1);
            if(depth % 2 == 0) {
                if(Double.compare(Point.distance(p, new Point(best.x, p.getY())), Point.distance(p, new Point(best.x, best.y))) < 0) {
                    best = findNearest(bad, p, best, depth + 1);
                }
            } else {
                if(Double.compare(Point.distance(p, new Point(p.getX(), best.y)), Point.distance(p, new Point(best.x, best.y))) < 0) {
                    best = findNearest(bad, p, best, depth + 1);
                }
            }
            return best;
        }
    }
}
