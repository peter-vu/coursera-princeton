/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class Permutation {


    public static void main(String[] args) {
        int k = Integer.parseInt(args[0]);
        if (k == 0) return;
        RandomizedQueue<String> q = new RandomizedQueue<>();
        int i = 1;
        for (i = 1; i <= k; i++) {
            q.enqueue(StdIn.readString());
            // StdOut.println("queue size: " + q.size());
        }
        // StdOut.println("i: " + i);
        while (!StdIn.isEmpty()) {
            int j = StdRandom.uniform(1, i + 1);
            // StdOut.println("k: " + k + ", j: " + j);
            if (j <= k) {
                q.dequeue();
                q.enqueue(StdIn.readString());
                // StdOut.println("New element: " + q.sample());
            }
            else {
                // StdOut.println("I skip: " + StdIn.readString());
                StdIn.readString();
            }
            i++;

        }
        // StdOut.println("queue size: " + q.size());
        while (k > 0) {
            StdOut.println(q.dequeue());
            k--;
        }

    }
}
