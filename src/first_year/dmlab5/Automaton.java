package first_year.dmlab5;

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

public class Automaton {
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

    public static void main(String[] args) throws Exception {
        String sourceFileName = "Test.in";
        String destinationFileName = "Test.out";
        FileInputStream fileInputStream = new FileInputStream(sourceFileName);
        FileOutputStream fileOutputStream = new FileOutputStream(destinationFileName);
        OutputWriter writer = new OutputWriter(fileOutputStream);
        InputReader reader = new InputReader(fileInputStream);
        int n = 27;
        Vector<Edge>[] map = new Vector[n + 1];
        boolean[] acceptable = new boolean[n + 1];
        for (int i = 0; i < n + 1; i++) {
            map[i] = new Vector<>();
            acceptable[i] = false;
        }
        int m = reader.readInt();
        int S = reader.readString().charAt(0) % 64;
        acceptable[27] = true;
        for (int i = 0; i < m; i++) {
            int left = reader.readString().charAt(0) % 64;
            String second = reader.readString();
            second = reader.readString();
            char symbol = second.charAt(0);
            int right;
            if (second.length() == 1) {
                right = 27;
            } else {
                right = second.charAt(1) % 64;
            }
            map[left].add(new Edge(right, symbol));
        }
        int numberOfWords = reader.readInt();
        for (int I = 0; I < numberOfWords; I++) {
            String word = reader.readString();
            boolean answer = false;
            Vector<Integer>[] sets = new Vector[word.length() + 1];
            sets[0] = new Vector<>();
            sets[0].add(S);
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
                    writer.print("yes\n");
                    answer = true;
                    break;
                }
            }
            if (!answer) {
                writer.print("no\n");
            }
        }
        writer.flush();
        fileInputStream.close();
        fileOutputStream.close();
    }
}
