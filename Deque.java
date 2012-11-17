import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {

    private int n;
    private Node first;
    private Node last;

    private class Node {
        private Item item;
        private Node next;
        private Node prev;
    }

    public Deque() {
        first = null;
        last = null;
        n = 0;
    }

    public boolean isEmpty() {
        return first == null;
    }

    public int size() {
        return n;
    }

    public void addFirst(Item item) {
        if (null == item) {
            throw new NullPointerException();
        }
        Node node = new Node();
        node.item = item;
        node.next = null;
        if (isEmpty()) {
            first = node;
            last = node;
            node.prev = null;
        } else {
            first.next = node;
            node.prev = first;
            first = node;
        }
        n++;
    }

    public void addLast(Item item) {
        if (null == item) {
            throw new NullPointerException();
        }
        Node node = new Node();
        node.item = item;
        if (isEmpty()) {
            first = node;
            last = node;
            node.prev = null;
            node.next = null;
        } else {
            node.next = last;
            last.prev = node;
            last = node;
        }
        n++;
    }

    public Item removeFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        Item tmp = first.item;
        if (first == last) {
            first = null;
            last = null;
        } else {
            first = first.prev;
            first.next = null;
        }
        n--;
        return tmp;
    }

    public Item removeLast() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        Item tmp = last.item;
        if (last == first) {
            last = null;
            first = null;
        } else {
            last = last.next;
            last.prev = null;
        }
        n--;
        return tmp;
    }

    public Iterator<Item> iterator() {
        return new DequeIterator();
    }

    private class DequeIterator implements Iterator<Item> {
        private Node current = first;
        public boolean hasNext() {
            return current != null;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            Item item = current.item;
            current = current.prev;
            return item;
        }
    }
/*
    public static void main(String[] args) {
       
        Deque<String> d = new Deque<String>();
        d.addFirst("A");
        assert d.size() == 1;
        String tmp = d.removeLast();
        assert d.size() == 0;
        assert tmp.equals("A");
        d.addFirst("B");
        assert d.removeFirst().equals("B");
        assert d.size() == 0;
        d.addFirst("A");
        d.addFirst("B");
        d.addFirst("C");
        d.addLast("X");
        for (String s : d) {
            StdOut.print(" " + s);
        }
        StdOut.println();

        Iterator iter = d.iterator();
        while (iter.hasNext()) {
            tmp = (String) iter.next();
            StdOut.print(" " + tmp);
        }
        StdOut.println();

        assert d.size() == 4;
        assert d.removeLast().equals("X");
        assert d.removeLast().equals("A");
        assert d.removeFirst().equals("C");
        assert d.removeLast().equals("B");
        assert d.isEmpty();
        
        mIterator();
    }

    public static void mIterator() {
        Deque<String> d = new Deque<String>();
        d.addFirst("A");
        d.addFirst("F");
        d.addFirst("B");
        d.addFirst("C");
        d.addLast("X");

        Iterator<String> it1 = d.iterator();
        Iterator<String> it2 = d.iterator();

        while (it1.hasNext()) {
            StdOut.println("it1: " + it1.next());
        }

        while (it2.hasNext()) {
            StdOut.println("it2: " + it2.next());
        }
    }
    */
}


