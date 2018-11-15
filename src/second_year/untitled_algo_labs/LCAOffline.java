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
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.LinkedList;
import java.util.Queue;

public class LCAOffline {

    static final int INFINITY = Integer.MAX_VALUE;
    static final int SIZE = 500000;
    static boolean[] used;
    static ArrayList<Integer>[] paths;
    static int[] indexes;
    static int[] depths;
    static int[] vertices;
    static int index = 0;

    static class InputReader {

        private InputStream stream;
        private byte[] buf = new byte[1024];
        private int curChar;
        private int numChars;
        private SpaceCharFilter filter;

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

    static int min(int v, int l, int r, int neededLeft, int neededRight, int[] tree) {
        int x = INFINITY;
        if (l == r && l >= neededLeft && r <= neededRight) {
            return tree[v];
        }

        if (neededLeft <= l && neededRight >= r) {
            return tree[v];
        }

        if (neededLeft > r || neededRight < l) {
            return INFINITY;
        }

        if (neededLeft > l || neededRight < r) {
            int m = (l + r) / 2;
            return minimum(min(v * 2 + 1, l, m, neededLeft, neededRight, tree), min(v * 2 + 2, m + 1, r, neededLeft, neededRight, tree));
        }
        return x;
    }

    static int[] build(int[] array, int v, int l, int r, int[] ret) {
        if (l == r)
            ret[v] = array[l];
        else {
            int m = (l + r) / 2;
            ret = build(array, v * 2 + 1, l, m, ret);
            ret = build(array, v * 2 + 2, m + 1, r, ret);
            ret[v] = minimum(ret[v * 2 + 1], ret[v * 2 + 2]);
        }
        return ret;
    }

    static int minimum(int f, int s) {
        if (f == INFINITY) {
            return s;
        }
        if (s == INFINITY) {
            return f;
        }
        if (depths[f] < depths[s]) {
            return f;
        }
        return s;
    }

    public static void main(String[] args) throws Exception {
        String sourceFileName = "lca.in";
        String destinationFileName = "lca.out";
        FileInputStream fileInputStream = new FileInputStream(sourceFileName);
        FileOutputStream fileOutputStream = new FileOutputStream(destinationFileName);
        FileWriter writer = new FileWriter("lca.out", true);
        InputReader in = new InputReader(fileInputStream);
        String split = "[ ]+";
        int k = in.readInt();
        paths = new ArrayList[SIZE + 1];
        used = new boolean[SIZE + 1];
        indexes = new int[SIZE + 1];
        depths = new int[2 * SIZE - 1];
        vertices = new int[2 * SIZE - 1];
        for (int i = 0; i < SIZE + 1; i++) {
            paths[i] = new ArrayList<>();
        }
        Queue<Integer[]> requests = new LinkedList<>();// from, to
        int m = 0;
        for (int i = 0; i < k; i++) {
            String temp = in.readString();
            if (temp.equals("ADD")) {
                int parent = in.readInt();
                int son = in.readInt();
                paths[parent].add(son);
            } else {
                int left = in.readInt();
                int right = in.readInt();
                requests.add(new Integer[]{left, right});
                m++;
            }
        }

        //build tree
        dfs(1, 0);
        int size = depths.length;
        int x = 1;
        for (int i = 1; i < size * 2; i *= 2) {
            x = i;
        }
        int[] array = new int[x];
        for (int i = 0; i < size; i++) {
            array[i] = i;
        }
        for (int i = size; i < x; i++) {
            array[i] = INFINITY;
        }
        int[] ret = new int[2 * x - 1];
        int[] tree = build(array, 0, 0, x - 1, ret);

        for (int i = 0; i < m; i++) {
            Integer[] tempArray = requests.poll();
            int l = tempArray[0];
            int r = tempArray[1];
            if (indexes[l] > indexes[r]) {
                int temp = l;
                l = r;
                r = temp;
            }
            l = indexes[l];
            r = indexes[r];
            writer.write(String.valueOf(vertices[(min(0, x - 1, 2 * x - 2, l + x - 1, r + x - 1, tree))]) + "\n");
        }

        writer.flush();
        fileInputStream.close();
        fileOutputStream.close();
    }
}