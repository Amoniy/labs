package lab3;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.StringTokenizer;

public class crypto {

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


    static int[] sum(int v, int l, int r, int NEEDEDLEFT, int NEEDEDRIGHT, int[][] tree, int mod) {
        int[] empty = {1, 0, 0, 1};
        if (l == r && l >= NEEDEDLEFT && r <= NEEDEDRIGHT) {
            return tree[v];
        }

        if (NEEDEDLEFT <= l && NEEDEDRIGHT >= r) {
            return tree[v];
        }

        if (NEEDEDLEFT > r || NEEDEDRIGHT < l) {
            return empty;
        }

        if (NEEDEDLEFT > l || NEEDEDRIGHT < r) {
            int m = (l + r) / 2;
            return timing(sum(v * 2 + 1, l, m, NEEDEDLEFT, NEEDEDRIGHT, tree, mod), sum(v * 2 + 2, m + 1, r, NEEDEDLEFT, NEEDEDRIGHT, tree, mod), mod);
        }
        return empty;
    }

    static int[] timing(int[] l, int[] r, int mod) {
        int[] x = {(l[0] * r[0] + l[1] * r[2]) % mod, (l[0] * r[1] + l[1] * r[3]) % mod, (l[2] * r[0] + l[3] * r[2]) % mod, (l[2] * r[1] + l[3] * r[3]) % mod};
        return x;
    }

    static int[][] build(int[][] a, int v, int l, int r, int[][] ret, int mod) {
        if (l == r)
            ret[v] = a[l];
        else {
            int m = (l + r) / 2;
            ret = build(a, v * 2 + 1, l, m, ret, mod);
            ret = build(a, v * 2 + 2, m + 1, r, ret, mod);
            ret[v] = timing(ret[v * 2 + 1], ret[v * 2 + 2], mod);
        }
        return ret;
    }

    static String visualize(int[] matrix) {
        String str = "" + matrix[0] + " " + matrix[1] + "\n" + matrix[2] + " " + matrix[3] + "\n";
        return str;
    }

    //C:\java\projects\src\labs\lab3\test
    public static void main(String[] args) throws Exception {
        //String sourceFileName = "C:\\java\\projects\\src\\labs\\lab3\\test.in";
        String destinationFileName = "crypto.out";
        //java.io.FileInputStream fileInputStream = new java.io.FileInputStream(sourceFileName);
        FileOutputStream fileOutputStream = new FileOutputStream(destinationFileName);
        //BufferedReader br = new BufferedReader(new FileReader(sourceFileName));
        FileWriter writer = new FileWriter("crypto.out", true);
        String split = "[ ]+";
        //String[] firsttemp=br.readLine().split(split);
        FastScanner in = new FastScanner(new File("crypto.in"));
        int R = in.nextInt();
        int n = in.nextInt();

        int m = in.nextInt();
        int[] empty = {1, 0, 0, 1};

        int x = 1;
        for (int i = 1; i < n * 2; i *= 2) {
            x = i;
        }
        int[][] array = new int[x][4];

        for (int i = 0; i < n; i++) {
            //String[] tempone=br.readLine().split(split);
            //String[] temptwo=br.readLine().split(split);
            //String[] tempzero=br.readLine().split(split);
            //for(int j=0;j<2;j++){
            //    array[i][j]=Integer.parseInt(tempone[j]);
            //    array[i][2+j]=Integer.parseInt(temptwo[j]);
            //}
            array[i][0] = in.nextInt();
            array[i][1] = in.nextInt();
            array[i][2] = in.nextInt();
            array[i][3] = in.nextInt();
        }

        for (int i = n; i < x; i++) {
            array[i] = empty;
        }
        int[][] ret = new int[2 * x - 1][4];
        int[][] tree = build(array, 0, 0, x - 1, ret, R);

        for (int i = 0; i < m; i++) {
            int l = in.nextInt();
            int r = in.nextInt();
            writer.write(visualize(sum(0, x - 1, 2 * x - 2, l + x - 2, r + x - 2, tree, R)) + "\n");
        }

        writer.flush();
        //fileInputStream.close();
        fileOutputStream.close();
    }
}
