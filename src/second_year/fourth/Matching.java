package second_year.fourth;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.InputMismatchException;
import java.util.Vector;

public class Matching {

    private static int leftSize;
    private static int rightSize;

    private static boolean dfs(int vertex, Vector<Integer>[] edges, boolean[] wasVisited) {
        if (vertex == leftSize + rightSize + 1) {
            return true;
        }
        for (int i = 0; i < edges[vertex].size(); i++) {
            int son = edges[vertex].get(i);
            if (wasVisited[son]) {
                continue;
            }
            wasVisited[son] = true;
            if (dfs(son, edges, wasVisited)) {
                if (vertex == 0) { // убираем из s
                    edges[vertex].remove(i);
                } else if (son == leftSize + rightSize + 1) { // убираем до t
                    edges[vertex].remove(i);
                } else {
                    edges[vertex].remove(i);
                    edges[son].add(vertex);
                }
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args) throws IOException {
        InputReader reader = new InputReader(System.in);
        OutputWriter writer = new OutputWriter(System.out);
//        InputReader reader = new InputReader(new FileInputStream("txt.in"));
//        OutputWriter writer = new OutputWriter(new FileOutputStream("txt.out"));
        leftSize = reader.readInt();
        rightSize = reader.readInt();
        Vector<Integer>[] edges = new Vector[leftSize + rightSize + 2];
        for (int i = 0; i < leftSize + rightSize + 2; i++) {
            edges[i] = new Vector<>();
        }
        for (int i = 1; i <= leftSize; i++) {
            edges[0].add(i);
        }
        for (int i = leftSize + 1; i < leftSize + rightSize + 1; i++) {
            edges[i].add(leftSize + rightSize + 1);
        }
        for (int i = 1; i <= leftSize; i++) {
            while (true) {
                int to = reader.readInt();
                if (to == 0) {
                    break;
                }
                edges[i].add(to + leftSize);
            }
        }
        int count = 0;
        boolean[] wasVisited = new boolean[leftSize + rightSize + 2];
        while (dfs(0, edges, wasVisited)) {
            count++;
            wasVisited = new boolean[leftSize + rightSize + 2];
        }

        writer.printLineln(count);
        for (int i = leftSize + 1; i < leftSize + rightSize + 1; i++) {
            if (/*edges[i].size() == 1 &&*/ edges[i].get(0) != (rightSize + leftSize + 1)) {
                writer.printLineln(edges[i].get(0) + " " + (i - leftSize));
            }
        }

        writer.flush();
        writer.close();

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