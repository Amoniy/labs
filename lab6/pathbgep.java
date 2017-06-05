package labs.lab6;

import java.io.*;
import java.util.Comparator;
import java.util.InputMismatchException;
import java.util.PriorityQueue;
import java.util.Vector;

public class pathbgep {
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

    public static class Edge {
        int to;
        int cost;

        public Edge(int to, int cost) {
            this.cost = cost;
            this.to = to;
        }
    }

    public static class Vertice {
        int index;
        long cost;

        public Vertice(int index, long cost) {
            this.cost = cost;
            this.index = index;
        }
    }

    private static class MyComp implements Comparator<Vertice> {
        public int compare(Vertice e1, Vertice e2) {
            if (e1.cost > e2.cost) {
                return 1;
            } else {
                return -1;
            }
        }
    }

    //C:\java\projects\src\labs\lab6\test
    public static void main(String[] args) throws Exception {
        String sourceFileName = "pathbgep.in";
        String destinationFileName = "pathbgep.out";
        java.io.FileInputStream fileInputStream = new java.io.FileInputStream(sourceFileName);
        java.io.FileOutputStream fileOutputStream = new java.io.FileOutputStream(destinationFileName);
        OutputWriter writer = new OutputWriter(fileOutputStream);
        InputReader reader = new InputReader(fileInputStream);
        int n = reader.readInt();
        int m = reader.readInt();
        Vector<Edge>[] map = new Vector[n + 1];
        for (int i = 1; i < n + 1; i++) {
            map[i] = new Vector<>();
        }
        for (int i = 0; i < m; i++) {
            int left = reader.readInt();
            int right = reader.readInt();
            int cost = reader.readInt();
            map[left].add(new Edge(right, cost));
            map[right].add(new Edge(left, cost));
        }
        long[] length = new long[n + 1];
        boolean[] used = new boolean[n + 1];
        for (int i = 0; i < n + 1; i++) {
            length[i] = Long.MAX_VALUE;
            used[i] = false;
        }
        length[1] = 0;
        PriorityQueue<Vertice> heap = new PriorityQueue<Vertice>(new MyComp());
        for (int i = 1; i < n + 1; i++) {
            heap.add(new Vertice(i, length[i]));
        }
        int start;
        for (int i = 0; i < n; i++) {
            /*start = -1;
            for (int j = 1; j < n + 1; j++) {
                if (!used[j]) {
                    if (start == -1 || length[start] > length[j]) {
                        start = j;
                    }
                }
            }*/
            while (true) {
                Vertice edge = heap.poll();
                start = edge.index;
                if (length[edge.index] == edge.cost) {
                    break;
                }
            }
            if (length[start] == Long.MAX_VALUE) {
                break;
            }
            used[start] = true;
            for (int j = 0; j < map[start].size(); j++) {
                int right = map[start].get(j).to;
                int cost = map[start].get(j).cost;
                if (length[right] == Long.MAX_VALUE) {
                    length[right] = length[start] + cost;
                    heap.add(new Vertice(right, length[right]));
                } else {
                    if (length[right] > length[start] + cost) {
                        length[right] = length[start] + cost;
                        heap.add(new Vertice(right, length[right]));
                    }
                }
            }
        }
        for (int i = 1; i < n + 1; i++) {
            if (length[i] == Long.MAX_VALUE) {
                writer.print("-1 ");
            } else {
                writer.print(length[i] + " ");
            }
        }
        writer.print("\n");
        writer.flush();
        fileInputStream.close();
        fileOutputStream.close();
    }
}
