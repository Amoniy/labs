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
import java.util.Vector;

public class Matching {

    static Vector<Integer>[] graph;
    static boolean[] wasVisited;
    static int[] connections;

    public static class MyComparator implements Comparator<int[]> {
        public int compare(int[] a, int[] b) {
            if (a[0] < b[0]) {
                return 1;
            } else {
                return -1;
            }
        }
    }

    public static boolean dfs(int vertex) {
        if (wasVisited[vertex]) {
            return false;
        }
        wasVisited[vertex] = true;
        for (int i = 0; i < graph[vertex].size(); i++) {
            int nextVertex = graph[vertex].get(i);
            if (connections[nextVertex] == 0 || dfs(connections[nextVertex])) {
                connections[nextVertex] = vertex;
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args) throws IOException {
        String sourceFileName = "matching.in";
        String destinationFileName = "matching.out";
        FileInputStream fileInputStream = new FileInputStream(sourceFileName);
        FileOutputStream fileOutputStream = new FileOutputStream(destinationFileName);
        InputReader in = new InputReader(fileInputStream);
        OutputWriter out = new OutputWriter(fileOutputStream);

        int n = in.readInt();
        graph = new Vector[2 * n + 1];
        connections = new int[2 * n + 1];
        for (int i = 1; i < 2 * n + 1; i++) {
            graph[i] = new Vector<>();
        }
        int[][] vertices = new int[n][2];
        for (int i = 0; i < n; i++) {
            vertices[i][0] = in.readInt(); //weight
            vertices[i][1] = i + 1; //index
        }
        Arrays.sort(vertices, new MyComparator());
        for (int i = 1; i < n + 1; i++) {
            int currentSize = in.readInt();
            for (int j = 0; j < currentSize; j++) {
                int connectedVertex = in.readInt() + n;
                graph[i].add(connectedVertex);
                graph[connectedVertex].add(i);
            }
        }

        for (int i = 0; i < n; i++) {
            wasVisited = new boolean[2 * n + 1];
            dfs(vertices[i][1]);
        }

        for (int i = n + 1; i < 2 * n + 1; i++) {
            connections[connections[i]] = i - n;
        }

        for (int i = 1; i < n + 1; i++) {
            out.printLine(connections[i] + " ");
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
