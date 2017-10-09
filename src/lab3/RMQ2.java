package lab3;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;

public class RMQ2 {
    static long infinity = 9223372036854775807L;
    static String split = "[ ]+";

    static void goup(int v, long[] tree) {
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

    static void set(int v, long q, int l, int r, int NEEDEDLEFT, int NEEDEDRIGHT, boolean[] soperations, boolean[] aoperations, long[] sets, long[] adds, long[] tree) {
        if (NEEDEDLEFT > r || NEEDEDRIGHT < l) {//does not cross
            return;
        }

        if (NEEDEDLEFT <= l && NEEDEDRIGHT >= r) {//borders are equal or qurrent in needed
            sets[v] = q;
            soperations[v] = true;
            aoperations[v] = false;
            tree[v] = q;
            goup(v, tree);
            return;
        }

        //if (l<=NEEDEDLEFT && r>=NEEDEDRIGHT) {//needed in current
        else {//needed in current or crosses
            if (soperations[v]) {
                soperations[2 * v + 1] = true;
                soperations[2 * v + 2] = true;
                aoperations[2 * v + 1] = false;
                aoperations[2 * v + 2] = false;
                sets[2 * v + 1] = sets[v];
                sets[2 * v + 2] = sets[v];
                tree[2 * v + 1] = sets[v];
                tree[2 * v + 2] = sets[v];
                soperations[v] = false;
            } else if (aoperations[v]) {
                if (soperations[2 * v + 1]) {
                    sets[2 * v + 1] += adds[v];
                    tree[2 * v + 1] += adds[v];
                } else if (aoperations[2 * v + 1]) {
                    adds[2 * v + 1] += adds[v];
                    tree[2 * v + 1] += adds[v];
                } else {
                    aoperations[2 * v + 1] = true;
                    adds[2 * v + 1] = adds[v];
                    tree[2 * v + 1] += adds[v];
                }

                if (soperations[2 * v + 2]) {
                    sets[2 * v + 2] += adds[v];
                    tree[2 * v + 2] += adds[v];
                } else if (aoperations[2 * v + 2]) {
                    adds[2 * v + 2] += adds[v];
                    tree[2 * v + 2] += adds[v];
                } else {
                    aoperations[2 * v + 2] = true;
                    adds[2 * v + 2] = adds[v];
                    tree[2 * v + 2] += adds[v];
                }
                goup(2 * v + 1, tree);
                goup(2 * v + 2, tree);
                aoperations[v] = false;
            }

            int m = (l + r) / 2;
            set(v * 2 + 1, q, l, m, NEEDEDLEFT, NEEDEDRIGHT, soperations, aoperations, sets, adds, tree);
            set(v * 2 + 2, q, m + 1, r, NEEDEDLEFT, NEEDEDRIGHT, soperations, aoperations, sets, adds, tree);
        }
    }

    static void add(int v, long q, int l, int r, int NEEDEDLEFT, int NEEDEDRIGHT, boolean[] soperations, boolean[] aoperations, long[] sets, long[] adds, long[] tree) {
        if (NEEDEDLEFT > r || NEEDEDRIGHT < l) {//does not cross
            return;
        }

        if (NEEDEDLEFT <= l && NEEDEDRIGHT >= r) {//borders are equal or qurrent in needed
            if (soperations[v]) {
                sets[v] += q;
                tree[v] += q;
                goup(v, tree);
                return;
            } else if (aoperations[v]) {
                adds[v] += q;
                tree[v] += q;
                goup(v, tree);
                return;
            } else {
                aoperations[v] = true;
                adds[v] = q;
                tree[v] += q;
                goup(v, tree);
                return;
            }
        }
        //if (l<=NEEDEDLEFT && r>=NEEDEDRIGHT) {//needed in current
        else {//needed in current or crosses
            if (soperations[v]) {
                soperations[2 * v + 1] = true;
                soperations[2 * v + 2] = true;
                aoperations[2 * v + 1] = false;
                aoperations[2 * v + 2] = false;
                sets[2 * v + 1] = sets[v];
                sets[2 * v + 2] = sets[v];
                tree[2 * v + 1] = sets[v];
                tree[2 * v + 2] = sets[v];
                soperations[v] = false;
            } else if (aoperations[v]) {
                if (soperations[2 * v + 1]) {
                    sets[2 * v + 1] += adds[v];
                    tree[2 * v + 1] += adds[v];
                } else if (aoperations[2 * v + 1]) {
                    adds[2 * v + 1] += adds[v];
                    tree[2 * v + 1] += adds[v];
                } else {
                    aoperations[2 * v + 1] = true;
                    adds[2 * v + 1] = adds[v];
                    tree[2 * v + 1] += adds[v];
                }

                if (soperations[2 * v + 2]) {
                    sets[2 * v + 2] += adds[v];
                    tree[2 * v + 2] += adds[v];
                } else if (aoperations[2 * v + 2]) {
                    adds[2 * v + 2] += adds[v];
                    tree[2 * v + 2] += adds[v];
                } else {
                    aoperations[2 * v + 2] = true;
                    adds[2 * v + 2] = adds[v];
                    tree[2 * v + 2] += adds[v];
                }
                goup(2 * v + 1, tree);
                goup(2 * v + 2, tree);
                aoperations[v] = false;
            }

            int m = (l + r) / 2;
            add(v * 2 + 1, q, l, m, NEEDEDLEFT, NEEDEDRIGHT, soperations, aoperations, sets, adds, tree);
            add(v * 2 + 2, q, m + 1, r, NEEDEDLEFT, NEEDEDRIGHT, soperations, aoperations, sets, adds, tree);
        }
    }

    static long min(int v, int l, int r, int NEEDEDLEFT, int NEEDEDRIGHT, boolean[] soperations, boolean[] aoperations, long[] sets, long[] adds, long[] tree, long counter) {
        long x = infinity;

        if (NEEDEDLEFT > r || NEEDEDRIGHT < l) {//does not cross
            return x;
        }

        if (NEEDEDLEFT <= l && NEEDEDRIGHT >= r) {//borders are equal or qurrent in needed
            if (soperations[v]) {
                return sets[v] + counter;
            } else {
                return tree[v] + counter;
            }
        }
        if (l <= NEEDEDLEFT && r >= NEEDEDRIGHT) {//needed in current
            if (soperations[v]) {
                return sets[v] + counter;
            } else if (aoperations[v]) {
                counter += adds[v];
            }
            int m = (l + r) / 2;
            return minimum(min(v * 2 + 1, l, m, NEEDEDLEFT, NEEDEDRIGHT, soperations, aoperations, sets, adds, tree, counter), min(v * 2 + 2, m + 1, r, NEEDEDLEFT, NEEDEDRIGHT, soperations, aoperations, sets, adds, tree, counter));
        }

        if (soperations[v]) {//crosses
            return sets[v] + counter;
        } else if (aoperations[v]) {
            counter += adds[v];
        }
        int m = (l + r) / 2;
        return minimum(min(v * 2 + 1, l, m, NEEDEDLEFT, NEEDEDRIGHT, soperations, aoperations, sets, adds, tree, counter), min(v * 2 + 2, m + 1, r, NEEDEDLEFT, NEEDEDRIGHT, soperations, aoperations, sets, adds, tree, counter));
    }

    static long[] build(long[] a, int v, int l, int r, long[] ret) {
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

    static long minimum(long f, long s) {
        if (f <= s) {
            return f;
        }
        return s;
    }

    public static void main(String[] args) throws Exception {
        String sourceFileName = "rmq2.in";
        String destinationFileName = "rmq2.out";
        java.io.FileInputStream fileInputStream = new java.io.FileInputStream(sourceFileName);
        java.io.FileOutputStream fileOutputStream = new java.io.FileOutputStream(destinationFileName);
        BufferedReader br = new BufferedReader(new FileReader(sourceFileName));
        FileWriter writer = new FileWriter("rmq2.out", true);

        int n = Integer.parseInt(br.readLine());
        String[] temp = br.readLine().split(split);
        int x = 1;
        for (int i = 1; i < n * 2; i *= 2) {
            x = i;
        }
        long[] array = new long[x];
        for (int i = 0; i < n; i++) {
            array[i] = Long.parseLong(temp[i]);
        }
        for (int i = n; i < x; i++) {
            array[i] = infinity;
        }
        long[] ret = new long[2 * x - 1];
        long[] tree = build(array, 0, 0, x - 1, ret);
        String str = br.readLine();

        boolean[] aoperations = new boolean[2 * x - 1];
        boolean[] soperations = new boolean[2 * x - 1];
        long[] adds = new long[2 * x - 1];
        long[] sets = new long[2 * x - 1];
        for (int i = 0; i < 2 * x - 1; i++) {
            aoperations[i] = false;
            soperations[i] = false;
        }

        try {
            while (true) {
                String[] s = str.split(split);
                if (s[0].equals("min")) {
                    int l = Integer.parseInt(s[1]);
                    int r = Integer.parseInt(s[2]);
                    long o = 0;
                    writer.write(min(0, x - 1, 2 * x - 2, l + x - 2, r + x - 2, soperations, aoperations, sets, adds, tree, o) + "\n");
                } else if (s[0].equals("add")) {
                    int l = Integer.parseInt(s[1]);
                    int r = Integer.parseInt(s[2]);
                    long q = Long.parseLong(s[3]);
                    add(0, q, x - 1, 2 * x - 2, l + x - 2, r + x - 2, soperations, aoperations, sets, adds, tree);
                } else {
                    int l = Integer.parseInt(s[1]);
                    int r = Integer.parseInt(s[2]);
                    long q = Long.parseLong(s[3]);
                    set(0, q, x - 1, 2 * x - 2, l + x - 2, r + x - 2, soperations, aoperations, sets, adds, tree);
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
