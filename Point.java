import java.util.Comparator;

public class Point implements Comparable<Point> {

	public final Comparator<Point> SLOPE_ORDER;
	private final int x;
	private final int y;

	public Point(int x, int y) {
		this.x = x;
		this.y = y;
		SLOPE_ORDER = new SlopeComparator();
	}

	private class SlopeComparator implements Comparator {
		public int compare(Object v, Object w) {
			Point pv, pw;
			pv = (Point) v;
			pw = (Point) w;
			double slopeV = Point.this.slopeTo(pv);
			double slopeW = Point.this.slopeTo(pw);
			if (slopeV < slopeW)
				return -1;
			else if (slopeV > slopeW)
				return 1;
			else
				return 0;
		}
	}

	public void draw() {
		StdDraw.point(x, y);
	}

	public void drawTo(Point that) {
		StdDraw.line(this.x, this.y, that.x, that.y);
	}

	public int compareTo(Point that) {
		if ((this.y < that.y) || ((this.y == that.y) && (this.x < that.x)))
			return -1;
		else if ((this.y == that.y) && (this.x == that.x))
			return 0;
		else
			return +1;
	}

	public double slopeTo(Point that) {
		if (compareTo(that) == 0)
			return Double.NEGATIVE_INFINITY;
		else if (this.y == that.y)
			return +0;
		else if (this.x == that.x)
			return Double.POSITIVE_INFINITY;
		else
			return new Double(that.y - this.y)
			/ new Double(that.x - this.x);
	}

	public String toString() {
		return "(" + x + ", " + y + ")";
	}

	public static void main(String[] args) {
		Point p1 = new Point(0, 0);
		Point p2 = new Point(1, 1);
		Point p3 = new Point(1, 2);
		StdOut.println("p2, p3 / p1 = " + p1.SLOPE_ORDER.compare(p2, p3));
		StdOut.println("p3, p1 / p2 = " + p2.SLOPE_ORDER.compare(p3, p1));

	}
}
