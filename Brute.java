import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Brute {

	public static void main(String[] args) {
		StdDraw.setXscale(0, 32768);
		StdDraw.setYscale(0, 32768);
		StdDraw.show(0);


		String filename = args[0];
		In in = new In(filename);
		int N = in.readInt();
		Point[] points = new Point[N];
		for (int i = 0; i < N; i++) {
			int x = in.readInt();
			int y = in.readInt();
			Point p = new Point(x, y);
			points[i] = p;
			p.draw();
		}

		for (int i = 0; i < N - 3; i++) {
			for (int j = i + 1; j < N - 2; j++) {
				double slope1 = points[i].slopeTo(points[j]);
				for (int k = j + 1; k < N - 1; k++) {
					double slope2 = points[j].slopeTo(points[k]);
					if (slope1 != slope2)
						continue;
					for (int l = k + 1; l < N; l++) {
						double slope3 =
							points[k].slopeTo(points[l]);
						if (slope3 != slope1)
							continue;
						drawLine(points, i, j, k, l);
					}
				}
			}
		}
		StdDraw.show(0);
	}




	private static void drawLine(
		Point[] points, int i, int j, int k, int l) {
		ArrayList<Point> sp = new ArrayList<Point>();
		sp.add(points[i]);
		sp.add(points[j]);
		sp.add(points[k]);
		sp.add(points[l]);
		Collections.sort(sp, new Comparator<Point>() {
			public int compare(Point v, Point w) {
				return v.compareTo(w);
			}
		});

		for (int c = 0; c < sp.size(); c++) {
			if (c == sp.size() - 1)
				StdOut.println(sp.get(c).toString());
			else
				StdOut.print(sp.get(c).toString() + " -> ");
		}
		sp.get(0).drawTo(sp.get(sp.size() - 1));
	}

}
