
public class Board {

	private int [][] blocks;
	private int n;

	public Board(int[][] blocks) {
		this.blocks = blocks;
		this.n = blocks.length;
	}

	private int getValue(int x, int y) {
		return x * n + y + 1;
	}

	public int dimension() {
		return n;
	}

	public int hamming() {
		int hamming = 0;
		for (int i = 0; i < n; i++)
			for (int j = 0; j < n; j++) {
				if (blocks[i][j] == 0)
					continue;
				if (blocks[i][j] != getValue(i, j))
					hamming++;
			}
		return hamming;
	}

	public int manhattan() {
		int manhattan = 0;
		for (int i = 0; i < n; i++)
			for (int j = 0; j < n; j++) {
				if (blocks[i][j] == 0)
					continue;
				int row = (blocks[i][j] - 1) / n;
				int col = (blocks[i][j] - 1) % n;
				manhattan += (Math.abs(i - row) + Math.abs(j - col));
			}
		return manhattan;
	}

	public boolean isGoal() {
		return hamming() == 0;
	}

	public Board twin() {
		int[][] t = copyBoard();
		for (int i = 0; i < n; i++) {
			boolean ok = true;
			for (int j = 0; j < n; j++) {
				if (t[i][j] == 0) {
					ok = false;
					break;
				}
			}
			if (ok) {
				int tmp = t[i][0];
				t[i][0] = t[i][1];
				t[i][1] = tmp;
				break;
			}
		}
		return new Board(t);

	}

	public boolean equals(Object v) {
		boolean res;
		if (v == this) return true;
		if (v == null) return false;
		if (!(v instanceof Board))
			return false;
		Board that = (Board) v;
		if (that.dimension() != dimension())
			return false;
		for (int i = 0; i < dimension(); i++)
			for (int j = 0; j < dimension(); j++)
				if (that.blocks[i][j] != blocks[i][j])
					return false;
		return true;
	}

	private int[][] copyBoard() {
		int[][] b = new int[n][n];
		for (int i = 0; i < n; i++)
			for (int j = 0; j < n; j++)
				b[i][j] = blocks[i][j];
		return b;
	}

	public  Iterable<Board> neighbors() {
		int blankX, blankY;
		Queue<Board> boards = new Queue<Board>();
		int tmp;
		int i, j;
		for (i = 0; i < n; i++)
			for (j = 0; j < n; j++)
				if (blocks[i][j] == 0) {
					if (i != 0) {
						int[][] nb = copyBoard();
						nb[i][j] = nb[i - 1][j];
						nb[i - 1][j] = 0;
						boards.enqueue(new Board(nb));
					}
					if (i != n - 1) {
						int[][] nb = copyBoard();
						nb[i][j] = nb[i + 1][j];
						nb[i + 1][j] = 0;
						boards.enqueue(new Board(nb));
					}
					if (j != 0) {
						int[][] nb = copyBoard();
						nb[i][j] = nb[i][j - 1];
						nb[i][j - 1] = 0;
						boards.enqueue(new Board(nb));
					}
					if (j != n - 1) {
						int[][] nb = copyBoard();
						nb[i][j] = nb[i][j + 1];
						nb[i][j + 1] = 0;
						boards.enqueue(new Board(nb));
					}
					return boards;
				}
		return null;

	}


	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(n + "\n");
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				sb.append(String.format("%2d", blocks[i][j]));
			}
			sb.append("\n");
		}
		return sb.toString();

	}


}



