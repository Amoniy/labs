package first_year.lab3;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.StringTokenizer;

public class Parking {

    static class FastScanner {
        BufferedReader br;
        StringTokenizer st;

        FastScanner(File f) {
            try {
                br = new BufferedReader(new FileReader(f));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        String next() {
            while (st == null || !st.hasMoreTokens()) {
                try {
                    st = new StringTokenizer(br.readLine());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return st.nextToken();
        }

        int nextInt() {
            return Integer.parseInt(next());
        }
    }

    static void goup(int v, int[] tree) {
        if (v == 0) {
            return;
        }
        if (v % 2 == 1) {//leftson
            if (tree[(v - 1) / 2] != minimum(tree[v], tree[v + 1])) {
                tree[(v - 1) / 2] = minimum(tree[v], tree[v + 1]);
                goup((v - 1) / 2, tree);
            }
        } else {
            if (tree[(v - 1) / 2] != minimum(tree[v], tree[v - 1])) {
                tree[(v - 1) / 2] = minimum(tree[v], tree[v - 1]);
                goup((v - 1) / 2, tree);
            }
        }
    }

    static int[] build(int[] a, int v, int l, int r, int[] ret) {
        if (l == r)
            ret[v] = a[l];
        else {
            int m = (l + r) / 2;
            ret = build(a, v * 2 + 1, l, m, ret);
            ret = build(a, v * 2 + 2, m + 1, r, ret);
            ret[v] = minimum(ret[v * 2 + 1], ret[v * 2 + 2]);
        }
        return ret;
    }

    static int minimum(int f, int s) {
        if (f <= s) {
            return f;
        }
        return s;
    }

    public static void main(String[] args) throws Exception {
        String destinationFileName = "Parking.out";
        FileOutputStream fileOutputStream = new FileOutputStream(destinationFileName);
        FileWriter writer = new FileWriter("Parking.out", true);
        FastScanner in = new FastScanner(new File("Parking.in"));
        int n = in.nextInt();
        int m = in.nextInt();
        int x = 1;
        for (int i = 1; i < n * 2; i *= 2) {
            x = i;
        }
        int[] array = new int[2 * x];
        for (int i = 0; i < n; i++) {
            array[i] = 0;
            array[i + x] = 0;
        }
        for (int i = n; i < x; i++) {
            array[i] = 1;
            array[i + x] = 1;
        }
        int[] ret = new int[4 * x - 1];
        int[] tree = build(array, 0, 0, 2 * x - 1, ret);

        for (int i = 0; i < m; i++) {
            String c = in.next();
            int q = in.nextInt();
            if (c.equals("enter")) {
                int v = q + 2 * x - 2;
                while (true) {
                    if (tree[v] == 1) {//is full
                        if (v % 2 == 1) {//left son
                            v = (v - 1) / 2;
                        } else {//right son
                            v /= 2;
                        }
                    } else {
                        if ((v * 2 + 2) > (4 * x - 1)) {//empty leaf
                            tree[v] = 1;
                            goup(v, tree);
                            writer.write((v - 2 * x + 2) % x + "\n");
                            break;
                        } else if (tree[v * 2 + 1] == 0) {//left son is empty
                            v = v * 2 + 1;
                        } else {
                            v = v * 2 + 2;//left son is not empty
                        }
                    }
                }
            } else {
                tree[q + 2 * x - 2] = 0;
                tree[q + 3 * x - 2] = 0;
                goup(q + 2 * x - 2, tree);
                goup(q + 3 * x - 2, tree);
            }
        }

        writer.flush();
        fileOutputStream.close();
    }
}

