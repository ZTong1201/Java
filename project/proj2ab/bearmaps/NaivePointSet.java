package bearmaps;
import java.util.List;

public class NaivePointSet implements PointSet {

    private List<Point> listOfPoints;

    public NaivePointSet(List<Point> points) {
        listOfPoints = points;
    }

    @Override
    public Point nearest(double x, double y) {
        Point myPoint = new Point(x, y);
        Point bestPoint = listOfPoints.get(0);
        double bestDist = Point.distance(myPoint, bestPoint);
        for(int i = 1; i < listOfPoints.size(); i++) {
            double currentDist = Point.distance(myPoint, listOfPoints.get(i));
            if(currentDist <= bestDist) {
                bestDist = currentDist;
                bestPoint = listOfPoints.get(i);
            }
        }
        return bestPoint;
    }


    public static void main(String[] args) {
        Point p1 = new Point(1.1, 2.2); // constructs a Point with x = 1.1, y = 2.2
        Point p2 = new Point(3.3, 4.4);
        Point p3 = new Point(-2.9, 4.2);

        NaivePointSet nn = new NaivePointSet(List.of(p1, p2, p3));
        Point ret = nn.nearest(3.0, 4.0); // returns p2
        ret.getX(); // evaluates to 3.3
        ret.getY(); // evaluates to 4.4
        System.out.println(ret.toString());
    }

}
