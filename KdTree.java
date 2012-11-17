import java.awt.Color;

public class KdTree {

	private static class Node {
		private Point2D p;
		private RectHV rect;
		private Node lb;
		private Node rt;
		private String name;
		private static int id = 0;

		public Node(Point2D p, RectHV rect, Node lb, Node rt) {
			this.p = p;
			this.rect = rect;
			this.lb = lb;
			this.rt = rt;
			this.name = String.valueOf(id++);
		}

	}

	private Node root;
	private int size;

	public KdTree() {
		Node root = null;
		size = 0;
	}

	public boolean isEmpty() {
		return size == 0;
	}

	public int size() {
		return size;
	}

	public void insert(Point2D p) {
		if (isEmpty()) {
			root = new Node(p, new RectHV(0, 0, 1, 1), null, null);
			size++;
		} else {
			Node node = root;
			Node parent = root;
			int level = 0;
			while (node != null) {
				if (node.p.equals(p))
					return;
				parent = node;
				if ((level++ % 2) == 0)
					node = (p.x() < node.p.x()) ? node.lb : node.rt;
				else
					node = (p.y() < node.p.y()) ? node.lb : node.rt;
			}
			size++;
			// Compare by X-coordinate
			// new node location is on right / on left from parent
			if (((level - 1) % 2) == 0)
				if (p.x() < parent.p.x())
						parent.lb = new Node(p, new RectHV(parent.rect.xmin(),
								parent.rect.ymin(),
								parent.p.x(),
								parent.rect.ymax()), null, null);
				else
						parent.rt = new Node(p, new RectHV(parent.p.x(),
								parent.rect.ymin(),
								parent.rect.xmax(),
								parent.rect.ymax()), null, null);
			// Compare by Y-coordinate
			// new node location is on top / on bottom from parent
			else
				if (p.y() < parent.p.y())
						parent.lb = new Node(p, new RectHV(parent.rect.xmin(),
								parent.rect.ymin(),
								parent.rect.xmax(),
								parent.p.y()), null, null);
				else
					parent.rt = new Node(p, new RectHV(parent.rect.xmin(),
								parent.p.y(),
								parent.rect.xmax(),
								parent.rect.ymax()), null, null);
		}

	}

	public boolean contains(Point2D p) {
		Node node = root;
		int level = 0;
		while (node != null) {
			if (node.p.equals(p))
				return true;
			else if ((level++ % 2) == 0)
				node = (p.x() < node.p.x()) ? node.lb : node.rt;
			else
				node = (p.y() < node.p.y()) ? node.lb : node.rt;
		}
		return false;
	}

	// dir = true - vertical line
	// dir = false - horizontal line
	private void internalDraw(Node node, boolean dir, double xmin, double xmax, double ymin, double ymax) {
		if (node == null)
			return;

		StdDraw.setPenRadius(.001);
		// OY vertical line, x fixed
		if (dir) {
			StdDraw.setPenColor(StdDraw.RED);
			StdDraw.line(node.p.x(), ymin, node.p.x(), ymax);
			internalDraw(node.lb, !dir, xmin, node.p.x(), ymin, ymax);
			internalDraw(node.rt, !dir, node.p.x(), xmax, ymin, ymax);
		} else {
			StdDraw.setPenColor(StdDraw.BLUE);
			StdDraw.line(xmin, node.p.y(), xmax, node.p.y());
			internalDraw(node.lb, !dir, xmin, xmax, ymin, node.p.y());
			internalDraw(node.rt, !dir, xmin, xmax, node.p.y(), ymax);
		}
		StdDraw.setPenColor(StdDraw.BLACK);
		StdDraw.setPenRadius(.01);
		node.p.draw();
		/*
		if (dir)
			StdDraw.textRight(node.p.x() + 0.05, node.p.y(), node.name);
		else
			StdDraw.textRight(node.p.x(), node.p.y() - 0.05, node.name);
			*/
	}

	public void draw() {
		if (isEmpty())
			return;
		Node node = root;
		boolean dir = true;
		StdDraw.setPenColor(StdDraw.BLACK);
		StdDraw.setPenRadius(.001);
		StdDraw.line(0, 0, 0, 1);
		StdDraw.line(0, 0, 1, 0);
		StdDraw.line(1, 1, 0, 1);
		StdDraw.line(1, 1, 1, 0);
		internalDraw(root, dir, 0, 1, 0, 1);
	}


	private void checkNodeRange(Node node, RectHV rect, SET<Point2D> set) {
		if (node == null)
			return;
		if (node.rect.intersects(rect)) {
			if (rect.contains(node.p))
				set.add(node.p);
			checkNodeRange(node.lb, rect, set);
			checkNodeRange(node.rt, rect, set);
		}
	}

	public Iterable<Point2D> range(RectHV rect) {
		SET<Point2D> set = new SET<Point2D>();
		checkNodeRange(root, rect, set);
		return set;
	}

	private Node nearestRec(Point2D p, Node node, double dist) {
		if (node.rect.distanceTo(p) > dist)
			return node;
		Node min = node;
		if (node.rect.distanceTo(p) < dist && dist != 0) {
			dist = node.p.distanceTo(p);
			min = node;
		}
		if (node.lb != null && node.lb.rect.distanceTo(p) < dist) {
			min = nearestRec(p, node.lb, dist);
			dist = node.p.distanceTo(p);
		}
		if (node.rt != null && node.rt.rect.distanceTo(p) < dist)
			min = nearestRec(p, node.rt, dist);
		return min;
	}

	public Point2D nearest(Point2D p) {
		if (isEmpty())
			return null;
		return root.p;
		//return nearestRec(p, root, root.p.distanceTo(p)).p;
	}

	private Node getNode(Point2D p) {
		Node node = root;
		int level = 0;
		while (node != null) {
			if (node.p.equals(p))
				return node;
			if ((level++ % 2) == 0)
				if (p.x() < node.p.x())
					node = node.lb;
				else
					node = node.rt;
			else
				if (p.y() < node.p.y())
					node = node.lb;
				else
					node = node.rt;
		}
		return null;
	}

	private void fillRect(RectHV rect, Color color) {
			StdDraw.setPenColor(color);
			StdDraw.filledRectangle((rect.xmin() + rect.xmax()) / 2,
					(rect.ymin() + rect.ymax()) / 2,
					(rect.xmax() - rect.xmin()) / 2,
					(rect.ymax() - rect.ymin()) / 2);
	}



	public static void main(String[] args) {
		KdTree kd = new KdTree();
			Point2D p1 = new Point2D(0.5, 0.6);
			Point2D p2 = new Point2D(0.2, 0.8);
		if (args.length != 0) {
			In in = new In(args[0]);
			double x, y;
			while (!in.isEmpty()) {
				x = in.readDouble();
				if (!in.isEmpty()) {
					y = in.readDouble();
					kd.insert(new Point2D(x, y));
				}
			}
		} else {
			p1 = new Point2D(0.5, 0.6);
			p2 = new Point2D(0.2, 0.8);
			kd.insert(p1);
			for (int i = 0; i < 10; i++)
				kd.insert(new Point2D(StdRandom.uniform(), StdRandom.uniform()));

			kd.insert(p2);
			Node n = kd.getNode(p2);
		}

/*
		if (n != null)
			kd.fillRect(n.rect, StdDraw.YELLOW);

		StdOut.println("Contains " + p1.toString() + ": " + kd.contains(p1) );


		RectHV r = new RectHV(0.2,0.2, 0.6,0.6);
		for (Point2D it : kd.range(r))
			StdOut.println("IN: " + it.toString());

		kd.fillRect(r, StdDraw.GRAY);
*/

		StdOut.println("Nearest " + p1.toString() + " : " + kd.nearest(p1).toString());
		kd.draw();

	}
}
