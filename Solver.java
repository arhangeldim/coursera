import java.util.Comparator;

public class Solver {
	private Board initial;
	private MinPQ<Board> pq;
	private MinPQ<Board> tpq;
	private Queue<Board> backtrace;
	private Queue<Board> tbacktrace;
	private ManhattanCmp mcmp;
	private int movesCount;
	private int tMovesCount;
	private boolean isSolvable;

	public Solver(Board initial) {
		this.initial = initial;
		mcmp = new ManhattanCmp();
		pq = new MinPQ(8, mcmp);
		backtrace = new Queue<Board>();
		backtrace.enqueue(initial);
		movesCount = 0;
		tpq = new MinPQ(8, mcmp);
		tbacktrace = new Queue<Board>();
		tbacktrace.enqueue(initial.twin());
		tMovesCount = 0;
		isSolvable = true;
		doSolve();
	}

	private void doSolve() {
		Board tmp = initial;
		Board twin = initial.twin();
		while (!tmp.isGoal()) {
			for (Board b : tmp.neighbors())
				pq.insert(b);
			tmp = pq.delMin();
			movesCount++;
			backtrace.enqueue(tmp);

			for (Board b : twin.neighbors())
				tpq.insert(b);
			twin = tpq.delMin();
			tMovesCount++;
			tbacktrace.enqueue(twin);
		}
	}

	private class ManhattanCmp implements Comparator<Board> {
		public int compare(Board v, Board w) {
			if (v.manhattan() < w.manhattan())
				return -1;
			else if (v.manhattan() > w.manhattan())
				return 1;
			else
				return 0;
		}
	}


	public boolean isSolvable() {
		return isSolvable;
	}

	public int moves() {
		return movesCount;
	}

	public Iterable<Board> solution() {
		return backtrace;
	}

	public static void main(String[] args) {

		In in = new In(args[0]);
		int N = in.readInt();
		int[][] blocks = new int[N][N];
		for (int i = 0; i < N; i++)
			for (int j = 0; j < N; j++)
				blocks[i][j] = in.readInt();
		Board initial = new Board(blocks);


		Solver solver = new Solver(initial);

		if (!solver.isSolvable())
			StdOut.println("No solution available");
		else {
			StdOut.println("Minimum number of moves = " + solver.moves());
			for (Board board : solver.solution())
				StdOut.println(board);
		}
	}
}


