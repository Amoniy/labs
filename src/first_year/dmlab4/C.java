package first_year.dmlab4;

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

public class C {
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

    static int currentPosition = 0;
    static boolean hasLoops = false;

    static boolean dfs(int v, int[] used, boolean[] isNeededVertice, Vector<Edge>[] map, int[] answer) {
        used[v] = 0;//visited
        if (!isNeededVertice[v]) {
            used[v] = 1;
            //    answer[currentPosition] = -1;
            //  currentPosition++;
            return false;
        }
        for (int i = 0; i < map[v].size(); i++) {
            int nextVertice = map[v].get(i).to;
            if (!isNeededVertice[nextVertice] && used[nextVertice] == -1) {
                used[nextVertice] = 1;
                //dfs(nextVertice, used, isNeededVertice, map, answer);
            } else {
                if (used[nextVertice] == -1) {//not visited
                    if (dfs(nextVertice, used, isNeededVertice, map, answer)) {
                        hasLoops = true;
                        return true;//has loops
                    }
                } else if (used[nextVertice] == 0) {
                    hasLoops = true;
                    return true;
                }
            }
        }
        used[v] = 1;//finished
        answer[currentPosition] = v;
        currentPosition++;
        return false;
    }

    static boolean dfs(int v, int[] used, Vector<Edge>[] map, int[] answer) {
        used[v] = 0;//visited
        for (int i = 0; i < map[v].size(); i++) {
            int nextVertice = map[v].get(i).to;
            if (used[nextVertice] == -1) {//not visited
                if (dfs(nextVertice, used, map, answer)) {
                    hasLoops = true;
                    return true;//has loops
                }
            } else if (used[nextVertice] == 0) {
                hasLoops = true;
                return true;
            }
        }
        used[v] = 1;//finished
        answer[currentPosition] = v;
        currentPosition++;
        return false;
    }

    public static void main(String[] args) throws Exception {
        String sourceFileName = "problem3.in";
        String destinationFileName = "problem3.out";
        FileInputStream fileInputStream = new FileInputStream(sourceFileName);
        FileOutputStream fileOutputStream = new FileOutputStream(destinationFileName);
        OutputWriter writer = new OutputWriter(fileOutputStream);
        InputReader reader = new InputReader(fileInputStream);
        int n = reader.readInt();
        int m = reader.readInt();
        int k = reader.readInt();
        Vector<Edge>[] map = new Vector[n + 1];
        Vector<Edge>[] backMap = new Vector[n + 1];
        int[] used = new int[n + 1];
        boolean[] acceptable = new boolean[n + 1];
        int[] length = new int[n + 1];
        for (int i = 0; i < n + 1; i++) {
            map[i] = new Vector<>();
            backMap[i] = new Vector<>();
            acceptable[i] = false;
            used[i] = -1;
            length[i] = -1;
        }
        for (int i = 1; i < k + 1; i++) {
            acceptable[reader.readInt()] = true;
        }
        for (int i = 0; i < m; i++) {
            int left = reader.readInt();
            int right = reader.readInt();
            char symbol = reader.readString().charAt(0);
            map[left].add(new Edge(right, symbol));
            backMap[right].add(new Edge(left, symbol));
        }
        ArrayList<Integer> queue = new ArrayList<>();
        for (int i = 1; i < n + 1; i++) {
            if (acceptable[i]) {
                length[i] = 1;
                queue.add(i);
            }
        }
        while (queue.size() != 0) {
            int start = queue.get(0);
            queue.remove(0);
            for (int i = 0; i < backMap[start].size(); i++) {
                int connectedVertice = backMap[start].get(i).to;
                if (length[connectedVertice] == -1) {
                    queue.add(queue.size(), connectedVertice);
                    length[connectedVertice] = 1;
                }
            }
        }
        if (length[1] == 1) {
            queue.add(1);
            length[1] = 2;
        }
        while (queue.size() != 0) {
            int start = queue.get(0);
            queue.remove(0);
            for (int i = 0; i < map[start].size(); i++) {
                int connectedVertice = map[start].get(i).to;
                if (length[connectedVertice] == 1) {
                    queue.add(queue.size(), connectedVertice);
                    length[connectedVertice] = 2;
                }
            }
        }
        boolean[] isNeededVertice = new boolean[n + 1];
        int countOfNeededVertices = 0;
        for (int i = 0; i < n + 1; i++) {
            if (length[i] == 2) {
                isNeededVertice[i] = true;
                countOfNeededVertices++;
            } else {
                used[i] = 1;
                isNeededVertice[i] = false;
            }
        }

        int[] answer = new int[countOfNeededVertices];
        for (int i = 1; i < n + 1; i++) {
            if (used[i] == -1) {
                dfs(i, used, /*isNeededVertice,*/ map, answer);
            }
            if (hasLoops) {
                writer.print("-1");
                break;
            }
        }
        int[] topSort = new int[countOfNeededVertices];
        if (!hasLoops) {
            for (int i = countOfNeededVertices - 1; i >= 0; i--) {
                topSort[countOfNeededVertices - i - 1] = answer[i];
            }
            /*for (int i = 0; i < countOfNeededVertices; i++) {
                writer.print(topSort[i] + " ");
            }
            writer.print("\n");*/
            long[] numberOfWays = new long[n + 1];
            for (int i = 0; i < n + 1; i++) {
                numberOfWays[i] = 0;
            }
            numberOfWays[1] = 1;
            int mod = 1000000007;
            for (int i = 1; i < countOfNeededVertices; i++) {
                for (int j = 0; j < backMap[topSort[i]].size(); j++) {
                    int inGoingVertice = backMap[topSort[i]].get(j).to;
                    numberOfWays[topSort[i]] += numberOfWays[inGoingVertice];
                    numberOfWays[topSort[i]] = numberOfWays[topSort[i]] % mod;
                }
            }
            long totalWords = 0;
            for (int i = 1; i < n + 1; i++) {
                if (acceptable[i]) {
                    totalWords += numberOfWays[i];
                    totalWords = totalWords % mod;
                }
            }
            writer.print(totalWords + "");
        }
        writer.flush();
        fileInputStream.close();
        fileOutputStream.close();
    }
}
