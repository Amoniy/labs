package lab3;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;

public class RMQ {
    static int infinity = 1000000001;

    static void set(int v, int x, int[] tree) {
        tree[v] = x;
        if (v == 0) {
            return;
        }
        if (v % 2 == 1) {//leftson
            set((v - 1) / 2, minimum(tree[v], tree[v + 1]), tree);
        } else {
            set((v - 1) / 2, minimum(tree[v], tree[v - 1]), tree);
        }
    }

    static int min(int v, int l, int r, int NEEDEDLEFT, int NEEDEDRIGHT, int[] tree) {
        int x = infinity;
        if (l == r && l >= NEEDEDLEFT && r <= NEEDEDRIGHT) {
            return tree[v];
        }

        if (NEEDEDLEFT <= l && NEEDEDRIGHT >= r) {
            return tree[v];
        }

        if (NEEDEDLEFT > r || NEEDEDRIGHT < l) {
            return infinity;
        }

        if (NEEDEDLEFT > l || NEEDEDRIGHT < r) {
            int m = (l + r) / 2;
            return minimum(min(v * 2 + 1, l, m, NEEDEDLEFT, NEEDEDRIGHT, tree), min(v * 2 + 2, m + 1, r, NEEDEDLEFT, NEEDEDRIGHT, tree));
        }
        return x;
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
        String sourceFileName = "rmq.in";
        String destinationFileName = "rmq.out";
        java.io.FileInputStream fileInputStream = new java.io.FileInputStream(sourceFileName);
        java.io.FileOutputStream fileOutputStream = new java.io.FileOutputStream(destinationFileName);
        BufferedReader br = new BufferedReader(new FileReader(sourceFileName));
        FileWriter writer = new FileWriter("rmq.out", true);
        String split = "[ ]+";

        int n = Integer.parseInt(br.readLine());
        String[] temp = br.readLine().split(split);
        int x = 1;
        for (int i = 1; i < n * 2; i *= 2) {
            x = i;
        }
        int[] array = new int[x];
        for (int i = 0; i < n; i++) {
            array[i] = Integer.parseInt(temp[i]);
        }
        for (int i = n; i < x; i++) {
            array[i] = infinity;
        }
        int[] ret = new int[2 * x - 1];
        int[] tree = build(array, 0, 0, x - 1, ret);
        String str = br.readLine();

        try {
            while (true) {

                if (str.equals(null)) {
                    break;
                }
                String[] s = str.split(split);
                if (s[0].equals("min")) {
                    int l = Integer.parseInt(s[1]);
                    int r = Integer.parseInt(s[2]);
                    writer.write(min(0, x - 1, 2 * x - 2, l + x - 2, r + x - 2, tree) + "\n");
                } else {
                    int l = Integer.parseInt(s[1]);
                    int r = Integer.parseInt(s[2]);
                    set(l + x - 2, r, tree);
                }
                str = br.readLine();
            }
        } catch (Exception e) {

        }
        writer.flush();
        fileInputStream.close();
        fileOutputStream.close();
    }
}
