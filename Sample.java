public class Sample {
    public static void main(String[] args) {
        String line;
        while (!StdIn.isEmpty()) {
            if (StdIn.isEmpty()) break;
            if (!StdIn.hasNextLine()) break;
            line = StdIn.readString();
            StdOut.println("line = " + line);
        }
    }
}

