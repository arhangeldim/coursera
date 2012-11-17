import java.util.Iterator;


public class RandomizedTest {


    static RandomizedQueue<String> q = new RandomizedQueue<String>();

    public static void main(String[] args) {
        q.enqueue("A");
        q.enqueue("B");
        q.enqueue("C");
        q.enqueue("D");
        StdOut.println("size: " + q.size());

        StdOut.println("deq: " + q.dequeue());
        StdOut.println("deq: " + q.dequeue());
        StdOut.println("deq: " + q.dequeue());
        StdOut.println("deq: " + q.dequeue());

        StdOut.println("size: " + q.size());

        iteratorTest();
        multipleIterator();
    }

    public static void multipleIterator() {
        q.enqueue("X");
        q.enqueue("Y");
        q.enqueue("Z");
        q.enqueue("V");

        Iterator<String> it = q.iterator();
        while (it.hasNext()) {
            StdOut.println("iter: " + it.next());
        }

        Iterator<String> it2 = q.iterator();
        while (it2.hasNext()) {
            StdOut.println("iter2: " + it2.next());
        }
    }


    public static void iteratorTest() {

        q.enqueue("A");
        q.enqueue("B");
        q.enqueue("C");
        q.enqueue("D");
        StdOut.println("size: " + q.size());


        for (String s : q) {
            StdOut.print(" " + s);
        }

        StdOut.println();


        for (String s : q) {
            StdOut.print(" " + s);
        }
    }
}
