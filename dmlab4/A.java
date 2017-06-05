package labs.dmlab4;

import java.io.*;
import java.util.InputMismatchException;

public class A {
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
        int symbol;

        public Edge(int to, int symbol) {
            this.symbol = symbol;
            this.to = to;
        }
    }

    //C:\java\projects\src\labs\lab5\test
    public static void main(String[] args) throws Exception {
        String sourceFileName = "problem1.in";
        String destinationFileName = "problem1.out";
        java.io.FileInputStream fileInputStream = new java.io.FileInputStream(sourceFileName);
        java.io.FileOutputStream fileOutputStream = new java.io.FileOutputStream(destinationFileName);
        OutputWriter writer = new OutputWriter(fileOutputStream);
        InputReader reader = new InputReader(fileInputStream);
        String word = reader.readString();
        int n = reader.readInt();
        int m = reader.readInt();
        int k = reader.readInt();
        int[][] map = new int[n + 1][26];
        boolean[] acceptable = new boolean[n + 1];
        for (int i = 1; i < n + 1; i++) {
            acceptable[i] = false;
        }
        for (int i = 1; i < k + 1; i++) {
            acceptable[reader.readInt()] = true;
        }
        for (int i = 1; i <= n; i++) {
            for (int j = 0; j < 26; j++) {
                map[i][j] = 0;
            }
        }
        for (int i = 0; i < m; i++) {
            int left = reader.readInt();
            int right = reader.readInt();
            int symbol = ((int) reader.read()) % 97;
            map[left][symbol] = right;
        }
        int currentPosition = 1;
        boolean answer = false;
        for (int i = 0; i < word.length(); i++) {
            if (map[currentPosition][((int) word.charAt(i)) % 97] == 0) {
                writer.print("Rejects");
                answer=true;
                break;
            } else {
                currentPosition = map[currentPosition][((int) word.charAt(i) % 97)];
            }
        }
        if (!answer) {
            if (acceptable[currentPosition]) {
                writer.print("Accepts");
            } else {
                writer.print("Rejects");
            }
        }
        writer.flush();
        fileInputStream.close();
        fileOutputStream.close();
    }
}
