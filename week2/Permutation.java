import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Permutation {
    public static void main(String[] args) {
        if (args == null || args.length == 0) {
            return;
        }

        int n = Integer.parseInt(args[0]);

        if (n == 0) {
            return;
        }

        RandomizedQueue<String> rq = new RandomizedQueue<>();

        while (!StdIn.isEmpty()) {
            rq.enqueue(StdIn.readString());
        }

        for (int i = 0; i < n; i++) {
            StdOut.println(rq.dequeue());
        }
    }
}