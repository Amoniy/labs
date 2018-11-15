package second_year.untitled_algo_labs;

import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.Arrays;
import java.util.Comparator;
import java.util.InputMismatchException;

public class Destroy {

    public static class MyComparator implements Comparator<long[]> {
        public int compare(long[] a, long[] b) {
            return Long.compare(a[0], b[0]);
        }
    }

    public static class MyIndexComparator implements Comparator<long[]> {
        public int compare(long[] a, long[] b) {
            return Long.compare(a[1], b[1]);
        }
    }

    static void mergeSortIterative(long[][] a) {
        for (int i = 1; i < a[0].length; i *= 2) {
            for (int j = 0; j < a[0].length - i; j += 2 * i) {
                merge(a, j, j + i, min(j + 2 * i, a[0].length));
            }
        }
    }

    static int min(int a, int b) {
        return (a <= b) ? a : b;
    }

    static void merge(long[][] a, int left, int mid, int right) {
        int it1 = 0;
        int it2 = 0;
        long[][] result = new long[4][right - left];
        while (left + it1 < mid && mid + it2 < right) {
            if (a[2][left + it1] > a[2][mid + it2]) {
                result[0][it1 + it2] = a[0][left + it1];
                result[1][it1 + it2] = a[1][left + it1];
                result[2][it1 + it2] = a[2][left + it1];
                result[3][it1 + it2] = a[3][left + it1];
                it1 += 1;
            } else {
                result[0][it1 + it2] = a[0][mid + it2];
                result[1][it1 + it2] = a[1][mid + it2];
                result[2][it1 + it2] = a[2][mid + it2];
                result[3][it1 + it2] = a[3][mid + it2];
                it2 += 1;
            }
        }
        while (left + it1 < mid) {
            result[0][it1 + it2] = a[0][left + it1];
            result[1][it1 + it2] = a[1][left + it1];
            result[2][it1 + it2] = a[2][left + it1];
            result[3][it1 + it2] = a[3][left + it1];
            it1 += 1;
        }
        while (mid + it2 < right) {
            result[0][it1 + it2] = a[0][mid + it2];
            result[1][it1 + it2] = a[1][mid + it2];
            result[2][it1 + it2] = a[2][mid + it2];
            result[3][it1 + it2] = a[3][mid + it2];
            it2 += 1;
        }
        for (int i = 0; i < it1 + it2; i++) {
            a[0][left + i] = result[0][i];
            a[1][left + i] = result[1][i];
            a[2][left + i] = result[2][i];
            a[3][left + i] = result[3][i];
        }
    }

    static void makeSet(int[] parent, int[] size, int v) {
        parent[v] = v;
        size[v] = 1;
    }

    static int findSet(int[] parent, int[] size, int v) {
        if (v == parent[v]) {
            return v;
        }
        return parent[v] = findSet(parent, size, parent[v]);
    }

    static void unionSets(int[] parent, int[] size, int a, int b) {
        a = findSet(parent, size, a);
        b = findSet(parent, size, b);
        if (a != b) {
            if (size[a] < size[b]) {
                parent[a] = b;
                size[b] += size[a];
            } else {
                parent[b] = a;
                size[a] += size[b];
            }
        }
    }

    public static void main(String[] args) throws IOException {
        String sourceFileName = "txt.in";
        String destinationFileName = "txt.out";
        FileInputStream fileInputStream = new FileInputStream(sourceFileName);
        FileOutputStream fileOutputStream = new FileOutputStream(destinationFileName);
        InputReader in = new InputReader(fileInputStream);
        OutputWriter out = new OutputWriter(fileOutputStream);

        int n = in.readInt();
        int m = in.readInt();
        long s = Long.parseLong(in.readString());
        int countOfComponents = n;
        int[] size = new int[n + 1];
        int[] parent = new int[n + 1];
        for (int i = 1; i < n + 1; i++) {
            makeSet(parent, size, i);
        }
        long[][] map = new long[4][m];
        for (int i = 0; i < m; i++) {
            map[0][i] = in.readInt(); //a
            map[1][i] = in.readInt(); //b
            map[2][i] = Long.parseLong(in.readString()); //w
            map[3][i] = i + 1;
        }

        mergeSortIterative(map);
        boolean[] used = new boolean[m];
        for (int i = 0; i < m; i++) {
            if (countOfComponents == 1) {
                break;
            }
            if (findSet(parent, size, (int) map[0][i]) != findSet(parent, size, (int) map[1][i])) {
                countOfComponents--;
                unionSets(parent, size, (int) map[0][i], (int) map[1][i]);
                used[i] = true;
            }
        }

        long[][] edges = new long[m - n + 1][2];
        int currentIndex = 0;
        for (int i = 0; i < m; i++) {
            if (!used[i]) {
                edges[currentIndex][0] = map[2][i]; //weight
                edges[currentIndex][1] = map[3][i]; //index
                currentIndex++;
            }
        }
        Arrays.sort(edges, new MyComparator());

        long currentSum = 0;
        int finalIndex = 0;
        for (int i = 0; i < m - n + 1; i++) {
            if (currentSum + edges[i][0] > s) {
                break;
            } else {
                currentSum += edges[i][0];
                finalIndex++;
            }
        }

        out.printLineln(finalIndex);
        Arrays.sort(edges, 0, finalIndex, new MyIndexComparator());
        for (int i = 0; i < finalIndex; i++) {
            out.printLine(edges[i][1] + " ");
        }

        out.flush();
    }

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
}
