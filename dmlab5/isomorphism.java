package labs.dmlab5;

import java.io.*;
import java.util.InputMismatchException;

public class isomorphism {
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

    public static boolean dfs(int u, int v, boolean[] visited, int[][] firstMap, int[][] secondMap, boolean[] firstAcceptable, boolean[] secondAcceptable) {
        boolean isIsomorfic = true;
        visited[u] = true;
        if (firstAcceptable[u] ^ secondAcceptable[v]) {
            return false;
        }
        for (int i = 0; i < 26; i++) {
            int firstChild = firstMap[u][i];
            int secondChild = secondMap[v][i];
            if (!visited[firstChild]) {
                isIsomorfic = dfs(firstChild, secondChild, visited, firstMap, secondMap, firstAcceptable, secondAcceptable);
            }
            if (!isIsomorfic) {
                return false;
            }
        }
        return isIsomorfic;
    }

    //C:\java\projects\src\labs\lab5\test
    public static void main(String[] args) throws Exception {
        String sourceFileName = "isomorphism.in";
        String destinationFileName = "isomorphism.out";
        java.io.FileInputStream fileInputStream = new java.io.FileInputStream(sourceFileName);
        java.io.FileOutputStream fileOutputStream = new java.io.FileOutputStream(destinationFileName);
        OutputWriter writer = new OutputWriter(fileOutputStream);
        InputReader reader = new InputReader(fileInputStream);
        int n = reader.readInt();
        int m = reader.readInt();
        int k = reader.readInt();
        int[][] firstMap = new int[n + 1][26];
        boolean[] firstAcceptable = new boolean[n + 1];
        for (int i = 1; i < n + 1; i++) {
            firstAcceptable[i] = false;
        }
        for (int i = 1; i < k + 1; i++) {
            firstAcceptable[reader.readInt()] = true;
        }
        for (int i = 1; i <= n; i++) {
            for (int j = 0; j < 26; j++) {
                firstMap[i][j] = 0;
            }
        }
        for (int i = 0; i < m; i++) {
            int left = reader.readInt();
            int right = reader.readInt();
            int symbol = ((int) reader.read()) % 97;
            firstMap[left][symbol] = right;
        }
        n = reader.readInt();
        int M = reader.readInt();
        int K = reader.readInt();
        int[][] secondMap = new int[n + 1][26];
        boolean[] secondAcceptable = new boolean[n + 1];
        for (int i = 1; i < n + 1; i++) {
            secondAcceptable[i] = false;
        }
        for (int i = 1; i < K + 1; i++) {
            secondAcceptable[reader.readInt()] = true;
        }
        for (int i = 1; i <= n; i++) {
            for (int j = 0; j < 26; j++) {
                secondMap[i][j] = 0;
            }
        }
        for (int i = 0; i < M; i++) {
            int left = reader.readInt();
            int right = reader.readInt();
            int symbol = ((int) reader.read()) % 97;
            secondMap[left][symbol] = right;
        }
        boolean isIsomorfic = true;
        if (firstMap.length != secondMap.length) {
            isIsomorfic = false;
        }
        if (K != k) {
            isIsomorfic = false;
        }
        if (M != M) {
            isIsomorfic = false;
        }
        boolean[] visited = new boolean[n + 1];
        for (int i = 0; i < n + 1; i++) {
            visited[i] = false;
        }
        if (isIsomorfic) {
            visited[0] = true;
            isIsomorfic = dfs(1, 1, visited, firstMap, secondMap, firstAcceptable, secondAcceptable);
        }
        if (isIsomorfic) {
            writer.print("YES");
        } else {
            writer.print("NO");
        }
        writer.flush();
        fileInputStream.close();
        fileOutputStream.close();
    }
}
