package labs.dmlab4;

import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.InputMismatchException;
import java.util.Vector;

public class B {
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
        char symbol;

        public Edge(int to, char symbol) {
            this.symbol = symbol;
            this.to = to;
        }
    }

    //C:\java\projects\src\labs\lab5\test
    public static void main(String[] args) throws Exception {
        String sourceFileName = "problem2.in";
        String destinationFileName = "problem2.out";
        java.io.FileInputStream fileInputStream = new java.io.FileInputStream(sourceFileName);
        java.io.FileOutputStream fileOutputStream = new java.io.FileOutputStream(destinationFileName);
        OutputWriter writer = new OutputWriter(fileOutputStream);
        InputReader reader = new InputReader(fileInputStream);
        String word = reader.readString();
        int n = reader.readInt();
        int m = reader.readInt();
        int k = reader.readInt();
        Vector<Edge>[] map = new Vector[n + 1];
        boolean[] acceptable = new boolean[n + 1];
        for (int i = 1; i < n + 1; i++) {
            map[i] = new Vector<>();
            acceptable[i] = false;
        }
        for (int i = 1; i < k + 1; i++) {
            acceptable[reader.readInt()] = true;
        }
        for (int i = 0; i < m; i++) {
            int left = reader.readInt();
            int right = reader.readInt();
            char symbol = reader.readString().charAt(0);
            map[left].add(new Edge(right, symbol));
        }
        boolean answer = false;
        Vector<Integer>[] sets = new Vector[word.length() + 1];
        sets[0] = new Vector<>();
        sets[0].add(1);
        int[] container;
        for (int i = 1; i <= word.length(); i++) {
            sets[i] = new Vector<>();
            container = new int[n + 1];
            char symbol = (word.charAt(i - 1));
            int cap = sets[i - 1].size();
            for (int j = 0; j < cap; j++) {
                int anotherCap = map[sets[i - 1].get(j)].size();
                for (int q = 0; q < anotherCap; q++) {
                    if (map[sets[i - 1].get(j)].get(q).symbol == symbol) {
                        int nextPosition = map[sets[i - 1].get(j)].get(q).to;
                        if (container[nextPosition] == 0) {
                            sets[i].add(nextPosition);
                            container[nextPosition] = 1;
                        }
                    }
                }
            }
        }
        for (int i = 0; i < sets[word.length()].size(); i++) {
            if (acceptable[sets[word.length()].get(i)]) {
                writer.print("Accepts");
                answer = true;
                break;
            }
        }
        if (!answer) {
            writer.print("Rejects");
        }
        writer.flush();
        fileInputStream.close();
        fileOutputStream.close();
    }
}
