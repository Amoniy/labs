package first_year.lab6;

import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.InputMismatchException;
import java.util.Vector;

public class PathMgep {
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

    public static void main(String[] args) throws Exception {
        String sourceFileName = "PathMgep.in";
        String destinationFileName = "PathMgep.out";
        FileInputStream fileInputStream = new FileInputStream(sourceFileName);
        FileOutputStream fileOutputStream = new FileOutputStream(destinationFileName);
        OutputWriter writer = new OutputWriter(fileOutputStream);
        InputReader reader = new InputReader(fileInputStream);
        int n = reader.readInt();
        int s = reader.readInt();
        int f = reader.readInt();
        Vector<Edge>[] map = new Vector[n + 1];
        int costt;
        for (int i = 1; i < n + 1; i++) {
            map[i] = new Vector<>();
        }
        for (int i = 0; i < n; i++) {
            int left = i + 1;
            for (int j = 0; j < n; j++) {
                costt = reader.readInt();
                if (costt > 0 && i != j) {
                    map[left].add(new Edge(j + 1, costt));
                }
            }

        }
        long[] length = new long[n + 1];
        boolean[] used = new boolean[n + 1];
        for (int i = 0; i < n + 1; i++) {
            length[i] = Long.MAX_VALUE;
            used[i] = false;
        }
        length[s] = 0;
        int start;
        for (int i = 0; i < n; i++) {
            start = -1;
            for (int j = 1; j < n + 1; j++) {
                if (!used[j]) {
                    if (start == -1 || length[start] > length[j]) {
                        start = j;
                    }
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
                } else {
                    length[right] = Math.min(length[right], length[start] + cost);
                }
            }
        }
        if (length[f] == Long.MAX_VALUE) {
            writer.print("-1");
        } else {
            writer.print(length[f] + "");
        }
        writer.flush();
        fileInputStream.close();
        fileOutputStream.close();
    }
}
