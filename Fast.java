import java.util.Comparator;
import java.util.Collections;
import java.util.ArrayList;

public class Fast {

	private static void sort(Comparable[] a, Point compPoint, int lo, int hi) {
		if (hi <= lo)
			return;
		int lt = lo;
		int gt = hi;
		int i = lo;
		int count = 0;
		Comparator comp = compPoint.SLOPE_ORDER;
		Comparable v = a[lo];
		ArrayList<Point> line = new ArrayList<Point>();
		line.add(compPoint);
		while (i <= gt) {
			int cmp = comp.compare(a[i], v);
			if (cmp < 0)
				exch(a, lt++, i++);
			else if (cmp > 0)
				exch(a, i, gt--);
			else {
				count++;
				line.add((Point) a[i]);
				i++;
			}
		}
		if (count >= 3) {
			Collections.sort(line, new Comparator<Point>() {
				public int compare(Point v, Point w) {
					return v.compareTo(w);
				}
			});
			for (int j = 0; j < line.size(); j++) {
				if (j == line.size() - 1)
					StdOut.println(line.get(j).toString());
				else
					StdOut.print(line.get(j).toString()
						+ " -> ");
			}

			line.get(0).drawTo(line.get(line.size() - 1));
		}

		sort(a, compPoint, lo, lt - 1);
		sort(a, compPoint, gt + 1, hi);
	}



	private static void exch(Comparable[] a, int v, int w) {
		Comparable tmp = a[v];
		a[v] = a[w];
		a[w] = tmp;
	}

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

		StdRandom.shuffle(points);

		for (int i = 0; i < N - 3; i++) {
			sort(points, points[i], i + 1, N - 1);
		}

		StdDraw.show(0);
	}
}
