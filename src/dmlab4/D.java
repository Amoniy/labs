package dmlab4;

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

public class D {
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
        String sourceFileName = "problem4.in";
        String destinationFileName = "problem4.out";
        FileInputStream fileInputStream = new FileInputStream(sourceFileName);
        FileOutputStream fileOutputStream = new FileOutputStream(destinationFileName);
        OutputWriter writer = new OutputWriter(fileOutputStream);
        InputReader reader = new InputReader(fileInputStream);
        int n = reader.readInt();
        int m = reader.readInt();
        int k = reader.readInt();
        int l = reader.readInt();
        Vector<Edge>[] map = new Vector[n + 1];
        //Vector<Edge>[] backMap = new Vector[n + 1];
        boolean[] acceptable = new boolean[n + 1];
        int[][] count = new int[n + 1][l + 1];
        ArrayList<Integer>[] queue = new ArrayList[l + 1];
        for (int i = 0; i < l + 1; i++) {
            queue[i] = new ArrayList<>();
        }
        for (int i = 0; i < n + 1; i++) {
            map[i] = new Vector<>();
            //backMap[i] = new Vector<>();
            acceptable[i] = false;
            for (int j = 0; j < l + 1; j++) {
                count[i][j] = 0;
            }
        }
        for (int i = 1; i < k + 1; i++) {
            acceptable[reader.readInt()] = true;
        }
        for (int i = 0; i < m; i++) {
            int left = reader.readInt();
            int right = reader.readInt();
            char symbol = reader.readString().charAt(0);
            map[left].add(new Edge(right, symbol));
            //backMap[right].add(new Edge(left, symbol));
        }
        int totalCount = 0;
        int mod = 1000000007;
        queue[0].add(1);
        count[1][0] = 1;
        for (int i = 0; i < l; i++) {
            while (queue[i].size() > 0) {
                //int currentVertice = queue[i].iterator().next();
                int currentVertice = queue[i].get(0);
                queue[i].remove(0);
                //queue[i].remove(currentVertice);
                for (int j = 0; j < map[currentVertice].size(); j++) {
                    int nextVertice = map[currentVertice].get(j).to;
                    if (!queue[i + 1].contains(nextVertice)) {
                        queue[i + 1].add(nextVertice);
                    }
                    count[nextVertice][i + 1] += count[currentVertice][i];
                    count[nextVertice][i + 1] = count[nextVertice][i + 1] % mod;
                }
            }
        }
        for (int i = 1; i < n + 1; i++) {
            if (acceptable[i]) {
                totalCount += count[i][l];
                totalCount = totalCount % mod;
            }
        }
        writer.print(totalCount + "");

        writer.flush();
        fileInputStream.close();
        fileOutputStream.close();
    }
}
