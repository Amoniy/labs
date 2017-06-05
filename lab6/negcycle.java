package labs.lab6;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.InputMismatchException;
import java.util.Vector;

public class negcycle {
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
        int from;
        long cost;

        public Edge(int from, int to, long cost) {
            this.cost = cost;
            this.to = to;
            this.from = from;
        }
    }

    //C:\java\projects\src\labs\lab6\test
    public static void main(String[] args) throws Exception {
        String sourceFileName = "negcycle.in";
        String destinationFileName = "negcycle.out";
        java.io.FileInputStream fileInputStream = new java.io.FileInputStream(sourceFileName);
        java.io.FileOutputStream fileOutputStream = new java.io.FileOutputStream(destinationFileName);
        OutputWriter writer = new OutputWriter(fileOutputStream);
        InputReader reader = new InputReader(fileInputStream);
        int n = reader.readInt();
        long limiter = 1000000000;
        Vector<Edge> map = new Vector<>();
        int[] used = new int[n + 1];
        int[] parent = new int[n + 1];
        for (int i = 0; i < n + 1; i++) {
            used[i] = -1;
            parent[i] = -1;//i
        }
        for (int i = 1; i < n + 1; i++) {
            for (int j = 1; j < n + 1; j++) {
                long cost = Long.parseLong(reader.readString());
                if (cost != limiter) {
                    int from = i;
                    int to = j;
                    map.add(new Edge(from, to, cost));
                }
            }
        }
        limiter /= 10;
        long[] length = new long[n + 1];
        for (int i = 0; i < n + 1; i++) {
            length[i] = limiter;
        }
        length[1] = 0;
        Edge edge;
        int start = -1;
        for (int i = 0; i < n; i++) {
            start = -1;
            for (int j = 0; j < map.size(); j++) {
                edge = map.get(j);
                //if (length[edge.from] < limiter) {
                    if (length[edge.to] > length[edge.from] + edge.cost) {
                        length[edge.to] = Math.max(length[edge.from] + edge.cost, -limiter);
                        parent[edge.to] = edge.from;
                        start = edge.to;
                    }
                //}
            }
        }
        if (start != -1) {
            for (int i = 0; i < n; i++) {
                start = parent[start];
            }
            ArrayList<Integer> cycle = new ArrayList<>();
            for (int currentVertice = start; ; currentVertice = parent[currentVertice]) {
                cycle.add(currentVertice);
                if (currentVertice == start && cycle.size() > 1) {
                    break;
                }
            }
            Collections.reverse(cycle);
            writer.print("YES\n" + (cycle.size()) + "\n");
            for (int i = 0; i < cycle.size(); i++) {
                writer.print(cycle.get(i) + " ");
            }
        } else {
            writer.print("NO");
        }
        writer.flush();
        fileInputStream.close();
        fileOutputStream.close();
    }
}
