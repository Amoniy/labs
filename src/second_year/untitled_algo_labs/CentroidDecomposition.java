package second_year.untitled_algo_labs;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.InputMismatchException;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Queue;

public class CentroidDecomposition {

    static int totalSize;
    static int currentUsed = 0;
    static LinkedHashSet<Integer>[] paths;
    static int[] weights;
    static int[] used;
    static int[] parents;
    static int currentSize;

    static void countWeights(int vertex) {
        weights[vertex] = 1;
        used[vertex] = currentUsed;
        for (int nextVertex : paths[vertex]) {
            if (used[nextVertex] != currentUsed) {
                countWeights(nextVertex);
                weights[vertex] += weights[nextVertex];
            }
        }
        currentSize = weights[vertex];
    }

    static int dfs(int vertex, int sizeOfTree, boolean isFirst, int previous) {
        if (isFirst) {
            currentUsed++;
            countWeights(vertex);
            sizeOfTree = currentSize;
        }
        if (sizeOfTree == 1) {
            return vertex;
        }
        int maxWeight = 1;
        int maxVertex = vertex;
        for (int i : paths[vertex]) {
            if (previous == i) { // не перехожу обратно
                continue;
            }
            if (maxWeight < weights[i]) {
                maxVertex = i;
                maxWeight = weights[i];
            }
        }
        int centroid = vertex;
        if (maxWeight * 2 > sizeOfTree) {
            centroid = dfs(maxVertex, sizeOfTree, /*array,*/ false, vertex);
        }
        return centroid;
    }

    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        OutputWriter out = new OutputWriter(System.out);
        totalSize = in.readInt();
        parents = new int[totalSize + 1];
        paths = new LinkedHashSet[totalSize + 1];
        weights = new int[totalSize + 1];
        used = new int[totalSize + 1];
        for (int i = 1; i < totalSize + 1; i++) {
            paths[i] = new LinkedHashSet<>();
        }
        for (int i = 0; i < totalSize - 1; i++) {
            int left = in.readInt();
            int right = in.readInt();
            paths[left].add(right);
            paths[right].add(left);
        }

        Queue<Integer[]> toProcess = new LinkedList<>();
        toProcess.add(new Integer[]{1, totalSize, 0});
        while (toProcess.size() > 0) {
            Integer[] vertexToProcess = toProcess.poll();
            int centroid = dfs(vertexToProcess[0], vertexToProcess[1], true, -1);
            parents[centroid] = vertexToProcess[2];
            for (int i : paths[centroid]) {
                paths[i].remove(centroid);
                toProcess.add(new Integer[]{i, currentSize, centroid});
            }
        }
        for (int i = 1; i < totalSize + 1; i++) {
            out.print(parents[i]);
            out.print(" ");
        }
        out.flush();
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
