public class Merge {

    public static void merge(Comparable[] a, Comparable[] aux, int lo, int mid, int hi) {
        assert isSorted(a, lo, mid);
        assert isSorted(a, mid + 1, hi);

        for (int i = lo; i <= hi; i++)
            aux[i] = a[i];

        int l = lo;
        int m = mid + 1;
        for (int i = lo; i <= hi; i++) {
            if (l > mid)
                a[i] = aux[m++];
            else if (m > hi)
                a[i] = aux[l++];
            else if (less(aux[l], aux[m]))
                a[i] = aux[l++];
            else
                a[i] = aux[m++];
        }

        isSorted(a ,lo ,hi);

    }

    public static void sort(Comparable[] a, Comparable[] aux, int lo, int hi) {
        if (hi <= lo)
            return;
        int mid = lo + (hi - lo) / 2;
        sort(a, aux, lo, mid);
        sort(a, aux, mid + 1, hi);
        merge(a, aux, lo , mid , hi);
    }

    public static void sort(Comparable[] a) {
        Comparable[] aux = new Comparable[a.length];
        sort(a, aux, 0, a.length - 1);
    }

    private static boolean less(Comparable a , Comparable b) {
        return (a.compareTo(b) < 0);
    }

    private static boolean isSorted(Comparable[] a) {
        return isSorted(a, 0, a.length - 1);
    }

    private static boolean isSorted(Comparable[] a, int lo, int hi) {
        for (int i = lo + 1; i <= hi; i++) {
            if (less(a[i - 1], a[i]))
                return false;
        }
        return true;
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
