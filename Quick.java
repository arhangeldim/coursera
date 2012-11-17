
public class Quick {

    public static void sort(Comparable[] a) {
        StdRandom.shuffle(a);
        sort(a, 0, a.length - 1);
        assert isSorted(a);
    }

    public static void sort(Comparable[] a, int lo, int hi) {
        if (lo >= hi)
            return;
        int j = partition(a, lo, hi);
        sort(a, lo, j - 1);
        sort(a, j + 1, hi);
    }

    public static int partition(Comparable[] a, int lo, int hi) {
        int i = lo;
        int j = hi + 1;
        Comparable v = a[lo];
        while (true) {
            while (less(a[++i], v))
                if (i == hi) break;

            while(less(v, a[--j]))
                if (j == lo) break;

            if (i >= j) break;

            exch(a, i, j);
        }

        exch(a, lo, j);
        return j;

    }

    public static boolean less(Comparable a, Comparable b) {
        return (a.compareTo(b) < 0);
    }

    public static boolean isSorted(Comparable[] a) {
        return isSorted(a, 0, a.length - 1);
    }

    public static boolean isSorted(Comparable[] a, int lo, int hi) {
        for (int i = lo + 1; i <= hi; i++)
            if (less(a[i - 1], a[i]))
                return false;
        return true;
    }

    public static void exch(Object[] a, int i, int j) {
        Object tmp = a[i];
        a[i] = a[j];
        a[j] = tmp;
    }

    public static void show(Comparable[] a) {
        StdOut.println("\nshow():");
        for (int i = 0; i < a.length; i++)
            StdOut.println("a[" + i + "] = " + a[i]);
    }

    public static void main(String[] args) {
        String[] str = {"A", "M", "D", "G", "E", "X"};
        show(str);
        sort(str);
        show(str);
    }
}
