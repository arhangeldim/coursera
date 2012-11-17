public class Subset {
    public static void main(String[] args) {
        if (args.length != 1) {
            throw new IllegalArgumentException();
        }
        int k = Integer.parseInt(args[0]);
        if (k < 0) {
            throw new IllegalArgumentException();
        }
        RandomizedQueue<String> q = new RandomizedQueue<String>();
        String line = null;
        while (!StdIn.isEmpty()) {
            if (!StdIn.hasNextLine()) break;
            line = StdIn.readString();

            q.enqueue(line);
        }
        if (k > q.size()) {
            throw new IllegalArgumentException();
        }
        for (int i = 0; i < k; i++) {
            StdOut.println(q.dequeue());
        }
    }
}
