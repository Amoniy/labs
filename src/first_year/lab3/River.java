package first_year.lab3;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import java.util.StringTokenizer;

public class River {
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

        long nextLong() {
            return Long.parseLong(next());
        }
    }

    public static long currenttax = 0;
    public static Random random = new Random(1);

    public static class node {
        long value;
        int priority;
        node leftson;
        node rightson;
        int weight = 1;

        public node(long value) {
            this.value = value;
            this.priority = random.nextInt();
            this.leftson = null;
            this.rightson = null;
        }
    }

    public static node[] split(node root, int k) {
        if (root == null) {
            node[] ret = {null, null};
            return ret;
        }
        int l = 0;
        if (root.leftson != null) {
            l = root.leftson.weight;
        }
        if (l < k) {
            node[] ret = split(root.rightson, k - l - 1);
            root.rightson = ret[0];
            ret[0] = root;
            update(root);
            return ret;
        } else {
            node[] ret = split(root.leftson, k);
            root.leftson = ret[1];
            ret[1] = root;
            update(root);
            return ret;
        }
    }

    public static void update(node t) {
        if (t.leftson != null && t.rightson != null) {
            t.weight = 1 + t.leftson.weight + t.rightson.weight;
        } else if (t.leftson != null) {
            t.weight = 1 + t.leftson.weight;
        } else if (t.rightson != null) {
            t.weight = 1 + t.rightson.weight;
        } else if (t != null) {
            t.weight = 1;
        }
    }

    public static node merge(node first, node second) {
        if (first == null) {
            return second;
        }
        if (second == null) {
            return first;
        }
        if (first.priority < second.priority) {
            first.rightson = merge(first.rightson, second);
            update(first);
            return first;
        } else {
            second.leftson = merge(first, second.leftson);
            update(second);
            return second;
        }
    }

    public static node add(node root, long x, int ind) {
        currenttax += x * x;
        if (root == null) {
            return new node(x);
        } else {
            node[] temp = split(root, ind);
            return merge(merge(temp[0], new node(x)), temp[1]);
        }

    }

    public static node remove(node root, int x) {
        node[] temp = split(root, x);
        node[] secondtemp = split(temp[1], 1);
        root = merge(temp[0], secondtemp[1]);
        return root;
    }

    public static node bankrupt(node root, int x) {
        node[] temp = split(root, x);
        node[] secondtemp = split(temp[1], 1);
        long f1 = 0;
        long f2 = 0;
        long df1 = 0;
        long df2 = 0;
        node tmp1 = temp[0];
        node tmp2 = secondtemp[1];
        if (secondtemp[1] == null) {//right is empy=>add all to the left
            while (tmp1.rightson != null) {
                tmp1 = tmp1.rightson;
            }
            f1 = tmp1.value;
            tmp1.value += secondtemp[0].value;
            df1 = tmp1.value;
        } else if (temp[0] == null) {//left is empty
            while (tmp2.leftson != null) {
                tmp2 = tmp2.leftson;
            }
            f2 = tmp2.value;
            tmp2.value += secondtemp[0].value;
            df2 = tmp2.value;
        } else {
            while (tmp1.rightson != null) {
                tmp1 = tmp1.rightson;
            }
            f1 = tmp1.value;
            tmp1.value += secondtemp[0].value / 2;
            df1 = tmp1.value;

            while (tmp2.leftson != null) {
                tmp2 = tmp2.leftson;
            }
            f2 = tmp2.value;
            tmp2.value += secondtemp[0].value / 2 + secondtemp[0].value % 2;
            df2 = tmp2.value;
        }
        root = merge(temp[0], secondtemp[1]);

        currenttax = (currenttax - f1 * f1 - f2 * f2 + df1 * df1 + df2 * df2 - secondtemp[0].value * secondtemp[0].value);
        return root;
    }

    public static node divide(node root, int x) {
        node[] temp = split(root, x);
        node[] secondtemp = split(temp[1], 1);
        long f = secondtemp[0].value * secondtemp[0].value;
        long one = secondtemp[0].value / 2;
        long two = (secondtemp[0].value / 2 + secondtemp[0].value % 2);
        node temproot = null;
        temproot = add(temproot, one, 0);
        temproot = add(temproot, two, 1);
        root = merge(merge(temp[0], temproot), secondtemp[1]);
        currenttax = (currenttax - f);
        return root;
    }

    public static void main(String[] args) throws Exception {
        String destinationFileName = "River.out";
        FileOutputStream fileOutputStream = new FileOutputStream(destinationFileName);
        FileWriter writer = new FileWriter("River.out", true);
        FastScanner in = new FastScanner(new File("River.in"));
        int n = in.nextInt();
        int WTF = in.nextInt();
        node root = null;
        for (int i = 0; i < n; i++) {
            root = add(root, in.nextLong(), i);
        }
        writer.write("" + currenttax + "\n");
        int k = in.nextInt();
        for (int i = 0; i < k; i++) {
            int operation = in.nextInt();
            int ind = in.nextInt();
            if (operation == 1) {
                root = bankrupt(root, ind - 1);
                writer.write("" + currenttax + "\n");
            } else {
                root = divide(root, ind - 1);
                writer.write("" + currenttax + "\n");
            }
        }
        writer.flush();
        fileOutputStream.close();
    }
}
