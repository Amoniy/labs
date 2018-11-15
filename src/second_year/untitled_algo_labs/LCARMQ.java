package second_year.untitled_algo_labs;

import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.InputMismatchException;
import java.util.Vector;

public class LCARMQ {

    static boolean[] used;
    static Vector<Integer>[] paths;
    static int[] indexes;
    static int[] depths;
    static int[] vertices;
    static int[] logarithms;
    static int[] powers;
    static int index = 0;

    static class InputReader {

        private InputStream stream;
        private byte[] buf = new byte[1024];
        private int curChar;
        private int numChars;
        private InputReader.SpaceCharFilter filter;

        public InputReader(InputStream stream) {
            this.stream = stream;
        }

        public int read() {
            if (numChars == -1)
                throw new InputMismatchException();
            if (curChar >= numChars) {
                curChar = 0;
                try {
                    numChars = stream.read(buf);
                } catch (IOException e) {
                    throw new InputMismatchException();
                }
                if (numChars <= 0)
                    return -1;
            }
            return buf[curChar++];
        }

        public int readInt() {
            int c = read();
            while (isSpaceChar(c))
                c = read();
            int sgn = 1;
            if (c == '-') {
                sgn = -1;
                c = read();
            }
            int res = 0;
            do {
                if (c < '0' || c > '9')
                    throw new InputMismatchException();
                res *= 10;
                res += c - '0';
                c = read();
            } while (!isSpaceChar(c));
            return res * sgn;
        }

        public String readString() {
            int c = read();
            while (isSpaceChar(c))
                c = read();
            StringBuilder res = new StringBuilder();
            do {
                res.appendCodePoint(c);
                c = read();
            } while (!isSpaceChar(c));
            return res.toString();
        }

        public boolean isSpaceChar(int c) {
            if (filter != null)
                return filter.isSpaceChar(c);
            return c == ' ' || c == '\n' || c == '\r' || c == '\t' || c == -1;
        }

        public String next() {
            return readString();
        }

        public interface SpaceCharFilter {
            public boolean isSpaceChar(int ch);
        }
    }

    static class OutputWriter {
        private final PrintWriter writer;

        public OutputWriter(OutputStream outputStream) {
            writer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(outputStream)));
        }

        public OutputWriter(Writer writer) {
            this.writer = new PrintWriter(writer);
        }

        public void print(Object... objects) {
            for (int i = 0; i < objects.length; i++) {
                if (i != 0)
                    writer.print(' ');
                writer.print(objects[i]);
            }
        }

        public void printLine(Object... objects) {
            print(objects);
        }

        public void printLineln(Object... objects) {
            print(objects);
            writer.println();
        }

        public void close() {
            writer.close();
        }

        public void flush() {
            writer.flush();
        }

    }

    static void dfs(int vertice, int depth) {
        used[vertice] = true;
        indexes[vertice] = index;
        depths[index] = (depth);
        vertices[index] = (vertice);
        index++; // enter to vertice
        for (int i = 0; i < paths[vertice].size(); i++) {
            if (!used[paths[vertice].get(i)]) {
                dfs(paths[vertice].get(i), depth + 1);
                depths[index] = (depth);
                vertices[index] = (vertice);
                index++; // exit from son
            }
        }
    }

    static int min(int l, int r, int[][] sparseTable) {
        int len = r - l + 1;
        int log = logarithms[len];
        return minimum(sparseTable[log][l], sparseTable[log][r - powers[log] + 1]);
    }

    static int[][] build() {
        int n = depths.length;
        int logN = 0;
        logN = logarithms[n] + 1;
        int[][] sparseTable = new int[logN][n];
        for (int i = 0; i < n; i++) {
            sparseTable[0][i] = i;
        }
        for (int j = 1; j < logN; j++) {
            for (int i = 0; i + powers[j] <= n; i++) {
                sparseTable[j][i] = minimum(sparseTable[j - 1][i], sparseTable[j - 1][i + powers[j - 1]]);
            }
        }

        return sparseTable;
    }

    static int minimum(int f, int s) {
        if (depths[f] < depths[s]) {
            return f;
        }
        return s;
    }

    public static void main(String[] args) throws Exception {
        String sourceFileName = "lca_rmq.in";
        String destinationFileName = "lca_rmq.out";
        FileInputStream fileInputStream = new FileInputStream(sourceFileName);
        FileOutputStream fileOutputStream = new FileOutputStream(destinationFileName);
        FileWriter writer = new FileWriter("lca_rmq.out", true);
        InputReader in = new InputReader(fileInputStream);
        int n = in.readInt();
        int m = in.readInt();
        paths = new Vector[n];
        used = new boolean[n];
        indexes = new int[n];
        depths = new int[2 * n - 1];
        vertices = new int[2 * n - 1];
        for (int i = 0; i < n; i++) {
            paths[i] = new Vector<>();
        }
        for (int i = 1; i < n; i++) {
            int parent = in.readInt();
            paths[parent].add(i);
        }

        //build tree
        dfs(0, 0);
        logarithms = new int[depths.length + 1];

        powers = new int[28];
        powers[0] = 1;
        for (int i = 1; i < 28; i++) {
            powers[i] = powers[i - 1] * 2;
        }
        int log = 1;
        int len = 2;
        for (int i = 2; i < depths.length + 1; i++) {
            for (int j = 0; (j < len) && (i < depths.length + 1); j++) {
                logarithms[i] = log;
                i++;
            }
            log++;
            len = powers[log];
            i--;
        }
        int[][] sparseTable = build();

        long l = in.readInt();
        long r = in.readInt();

        int X = in.readInt();
        int Y = in.readInt();
        int Z = in.readInt();

        long a1 = l;
        long a2 = r;
        long sum = 0;
        for (int i = 0; i < m; i++) {
            l = indexes[(int) l];
            r = indexes[(int) r];
            if (l > r) {
                long temp = l;
                l = r;
                r = temp;
            }
            long ans = vertices[(min((int) l, (int) r, sparseTable))];
            sum += ans;
            long new_a1 = (X * a1 + Y * a2 + Z) % n;
            l = (new_a1 + ans) % n;
            a1 = new_a1;
            a2 = (X * a2 + Y * new_a1 + Z) % n;
            r = a2;
        }
        writer.write(String.valueOf(sum));

        writer.flush();
        fileInputStream.close();
        fileOutputStream.close();
    }
}
