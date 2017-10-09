package lab5;

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

public class fire {
    static int currentComponent = 1;
    static int currentPosition = 1;

    static void dfs(int v, int[] used, Vector<Integer>[] map, int[] answer) {
        used[v] = 1;//visited
        for (int i = 0; i < map[v].size(); i++) {
            int nextVertice = map[v].get(i);
            if (used[nextVertice] == -1) {//not visited
                dfs(nextVertice, used, map, answer);
            }
        }
        answer[v - 1] = currentPosition;
        currentPosition++;
    }

    static void backDfs(int v, int[] used, Vector<Integer>[] backMap, int[] components) {
        used[v] = 1;//visited
        for (int i = 0; i < backMap[v].size(); i++) {
            int nextVertice = backMap[v].get(i);
            if (used[nextVertice] == -1) {//not visited
                backDfs(nextVertice, used, backMap, components);
            }
        }
        components[v - 1] = currentComponent;
        currentPosition++;
    }

    static void mergeSortIterative(int[][] a) {
        for (int i = 1; i < a[0].length; i *= 2) {
            for (int j = 0; j < a[0].length - i; j += 2 * i) {
                merge(a, j, j + i, min(j + 2 * i, a[0].length));
            }
        }
    }

    static int min(int a, int b) {
        return (a <= b) ? a : b;
    }

    static void merge(int[][] a, int left, int mid, int right) {
        int it1 = 0;
        int it2 = 0;
        int[][] result = new int[3][right - left];
        while (left + it1 < mid && mid + it2 < right) {
            if (a[1][left + it1] > a[1][mid + it2]) {
                result[0][it1 + it2] = a[0][left + it1];
                result[1][it1 + it2] = a[1][left + it1];
                it1 += 1;
            } else {
                result[0][it1 + it2] = a[0][mid + it2];
                result[1][it1 + it2] = a[1][mid + it2];
                it2 += 1;
            }
        }
        while (left + it1 < mid) {
            result[0][it1 + it2] = a[0][left + it1];
            result[1][it1 + it2] = a[1][left + it1];
            it1 += 1;
        }
        while (mid + it2 < right) {
            result[0][it1 + it2] = a[0][mid + it2];
            result[1][it1 + it2] = a[1][mid + it2];
            it2 += 1;
        }
        for (int i = 0; i < it1 + it2; i++) {
            a[0][left + i] = result[0][i];
            a[1][left + i] = result[1][i];
        }
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

    //C:\java\projects\src\labs\lab5\test
    public static void main(String[] args) throws Exception {
        String sourceFileName = "fire.in";
        String destinationFileName = "fire.out";
        FileInputStream fileInputStream = new FileInputStream(sourceFileName);
        FileOutputStream fileOutputStream = new FileOutputStream(destinationFileName);
        OutputWriter out = new OutputWriter(fileOutputStream);
        InputReader in = new InputReader(fileInputStream);
        int n = in.readInt();
        int m = in.readInt();
        Vector<Integer>[] map = new Vector[n + 1];
        Vector<Integer>[] backMap = new Vector[n + 1];
        //ArrayList<Integer>[] map = new ArrayList[n + 1];
        //ArrayList<Integer>[] backMap = new ArrayList[n + 1];
        int[] used = new int[n + 1];
        for (int i = 1; i < n + 1; i++) {
            //map[i] = new ArrayList<>();
            //backMap[i] = new ArrayList<>();
            map[i] = new Vector<>();
            backMap[i] = new Vector<>();
            used[i] = -1;
        }
        for (int i = 0; i < m; i++) {

            int left = in.readInt();
            int right = in.readInt();
            map[left].add(right);
            backMap[right].add(left);
        }

        int[] answer = new int[n];
        for (int i = 1; i < n + 1; i++) {
            if (used[i] == -1) {
                dfs(i, used, map, answer);
            }
        }
        int[][] mapAccordingToTimes = new int[2][n];
        for (int i = 0; i < n; i++) {
            mapAccordingToTimes[0][i] = i + 1;
            mapAccordingToTimes[1][i] = answer[i];
        }
        mergeSortIterative(mapAccordingToTimes);

        for (int i = 1; i < n + 1; i++) {
            used[i] = -1;
        }
        int[] components = new int[n];
        for (int i = 1; i < n + 1; i++) {
            if (used[mapAccordingToTimes[0][i - 1]] == -1) {
                backDfs(mapAccordingToTimes[0][i - 1], used, backMap, components);
                currentComponent++;
            }
        }
        boolean[][] mapOfCondensation = new boolean[currentComponent][currentComponent];
        int countOfCondensedEdges = 0;

        for (int i = 1; i < currentComponent; i++) {
            for (int j = 1; j < currentComponent; j++) {
                mapOfCondensation[i][j] = false;
            }
        }
        for (int i = 1; i < currentComponent; i++) {
            mapOfCondensation[i][i] = true;
        }
        for (int i = 1; i < n + 1; i++) {
            for (int j = 0; j < map[i].size(); j++) {
                if (!mapOfCondensation[components[i - 1]][components[map[i].get(j) - 1]]) {
                    countOfCondensedEdges++;
                    mapOfCondensation[components[i - 1]][components[map[i].get(j) - 1]] = true;
                }
            }
        }
        int count = 0;
        for (int i = 1; i < currentComponent; i++) {
            int miniCount = 0;
            for (int j = 1; j < currentComponent; j++) {
                if (mapOfCondensation[i][j]) {
                    miniCount++;
                    if (miniCount == 2) {
                        break;
                    }
                }

            }
            if (miniCount == 1) {
                count++;
            }
        }
        out.print(count + "");
        out.flush();
        fileInputStream.close();
        fileOutputStream.close();
    }
}
