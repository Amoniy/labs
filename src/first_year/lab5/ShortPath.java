package first_year.lab5;

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

public class ShortPath {
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

    static int currentPosition = 0;
    static boolean hasLoops = false;

    static boolean dfs(int v, int[] used, Vector<Edge>[] map, int[] answer) {
        used[v] = 0;//visited
        for (int i = 0; i < map[v].size(); i++) {
            int nextVertice = map[v].get(i).to;
            if (used[nextVertice] == -1) {//not visited
                if (dfs(nextVertice, used, map, answer)) {
                    return true;//has loops
                }
            } else if (used[nextVertice] == 0) {
                hasLoops = true;
                return true;
            }
        }
        used[v] = 1;//finished
        answer[answer.length - 1 - currentPosition] = v;
        currentPosition++;
        return false;
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
        String sourceFileName = "ShortPath.in";
        String destinationFileName = "ShortPath.out";
        FileInputStream fileInputStream = new FileInputStream(sourceFileName);
        FileOutputStream fileOutputStream = new FileOutputStream(destinationFileName);
        OutputWriter writer = new OutputWriter(fileOutputStream);
        InputReader reader = new InputReader(fileInputStream);
        int n = reader.readInt();
        int m = reader.readInt();
        int s = reader.readInt();
        int t = reader.readInt();
        Vector<Edge>[] map = new Vector[n + 1];
        int[] used = new int[n + 1];
        for (int i = 1; i < n + 1; i++) {
            map[i] = new Vector<>();
            used[i] = -1;
        }
        for (int i = 0; i < m; i++) {
            int left = reader.readInt();
            int right = reader.readInt();
            int cost = reader.readInt();
            map[left].add(new Edge(right, cost));
        }
        int[] answer = new int[n];
        for (int i = 1; i < n + 1; i++) {
            if (used[i] == -1) {
                dfs(i, used, map, answer);
            }
        }
        int[] costs = new int[n + 1];
        for (int i = 0; i < n + 1; i++) {
            costs[i] = Integer.MAX_VALUE;
        }
        int startIndex = 0;
        for (int i = 0; i < answer.length; i++) {
            if (answer[i] == s) {
                startIndex = i;
                break;
            }
            if (answer[i] == t) {
                startIndex = -1;
                writer.print("Unreachable");
                break;
            }
        }
        if (startIndex != -1) {
            boolean[] visitedInTopSort = new boolean[n + 1];
            for (int i = s; i < answer.length; i++) {
                visitedInTopSort[i] = false;
            }
            visitedInTopSort[s] = true;
            costs[s] = 0;
            for (int i = startIndex; answer[i] != t; i++) {
                if (visitedInTopSort[answer[i]]) {
                    for (int j = 0; j < map[answer[i]].size(); j++) {
                        costs[map[answer[i]].get(j).to] = Math.min(costs[map[answer[i]].get(j).to], costs[answer[i]] + map[answer[i]].get(j).cost);
                        visitedInTopSort[map[answer[i]].get(j).to] = true;
                    }
                }
            }
            if (visitedInTopSort[t]) {
                writer.print(costs[t] + "");
            } else {
                writer.print("Unreachable");
            }
        }
        writer.flush();
        fileInputStream.close();
        fileOutputStream.close();
    }
}

