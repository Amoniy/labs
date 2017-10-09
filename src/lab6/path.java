package lab6;

import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Vector;

public class path {
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
        String sourceFileName = "path.in";
        String destinationFileName = "path.out";
        FileInputStream fileInputStream = new FileInputStream(sourceFileName);
        FileOutputStream fileOutputStream = new FileOutputStream(destinationFileName);
        OutputWriter writer = new OutputWriter(fileOutputStream);
        InputReader reader = new InputReader(fileInputStream);
        int n = reader.readInt();
        int m = reader.readInt();
        int s = reader.readInt();
        long limiter = 9000000000000000000L;
        Vector<Edge> map = new Vector<>();
        Vector<Integer>[] connectivityMap = new Vector[n + 1];
        for (int i = 0; i < n + 1; i++) {
            connectivityMap[i] = new Vector<>();
        }
        for (int i = 0; i < m; i++) {
            int from = reader.readInt();
            int to = reader.readInt();
            connectivityMap[from].add(to);
            map.add(new Edge(from, to, Long.parseLong(reader.readString())));
        }
        long[] length = new long[n + 1];
        for (int i = 0; i < n + 1; i++) {
            length[i] = limiter;
        }
        length[s] = 0;
        Edge edge;
        for (int i = 1; i < n; i++) {
            for (int j = 0; j < map.size(); j++) {
                edge = map.get(j);
                if (length[edge.from] != limiter) {
                    if (length[edge.to] != limiter) {
                        if (length[edge.to] > length[edge.from] + edge.cost) {
                            length[edge.to] = Math.max(length[edge.from] + edge.cost, -limiter);
                        }
                    } else {
                        length[edge.to] = length[edge.from] + edge.cost;
                    }
                }
            }
        }
        boolean[] isInCycle = new boolean[n + 1];
        for (int i = 0; i < n + 1; i++) {
            isInCycle[i] = false;
        }
        int start;
        for (int j = 0; j < map.size(); ++j) {
            if (length[map.get(j).from] != limiter) {
                if (length[map.get(j).to] > length[map.get(j).from] + map.get(j).cost) {
                    start = map.get(j).to;
                    isInCycle[start] = true;
                }
            }
        }
        ArrayList<Integer> queue = new ArrayList<>();
        for (int i = 1; i < n + 1; i++) {
            if (isInCycle[i]) {
                queue.add(i);
            }
        }
        while (queue.size() > 0) {
            int currentVertice = queue.get(0);
            queue.remove(0);
            for (int i = 0; i < connectivityMap[currentVertice].size(); i++) {
                int nextVertice = connectivityMap[currentVertice].get(i);
                if (!isInCycle[nextVertice]) {
                    isInCycle[nextVertice] = true;
                    queue.add(nextVertice);
                }
            }
        }
        for (int i = 1; i < n + 1; i++) {
            if (isInCycle[i]) {
                writer.print("-\n");
            } else if (length[i] == limiter) {
                writer.print("*\n");
            } else {
                writer.print(length[i] + "\n");
            }
        }
        writer.flush();
        fileInputStream.close();
        fileOutputStream.close();
    }
}
