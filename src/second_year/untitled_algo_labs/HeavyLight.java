package second_year.untitled_algo_labs;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.HashSet;
import java.util.InputMismatchException;

public class HeavyLight {

    // for lca
    static boolean[] used;
    static HashSet<Integer>[] paths;
    static int[] parents;

    // for paths
    static int maximumTreeSize = 0;
    static int[] xOfTree;
    static int[] treeSizes; // искать только по индексу корня
    static int[] roots;
    static int[] weights;
    static int[][] adds;
    static int countOfTrees = 0;
    static int[] treeIndexes; // treeIndexes[root] = index of tree in trees[]. begins from 1
    static int[] indexInTree; // по чистому номеру вершины

    static void buildPathsDfs(int currentVertex) {
        int currentRoot = roots[currentVertex];
        if (treeIndexes[currentRoot] == 0) {
            countOfTrees++;
            treeIndexes[currentRoot] = countOfTrees;
        }
        indexInTree[currentVertex] = treeSizes[currentRoot];

        treeSizes[currentRoot]++;
        if (maximumTreeSize < treeSizes[currentRoot]) {
            maximumTreeSize = treeSizes[currentRoot];
        }
        for (int nextVertex : paths[currentVertex]) {
            if (weights[nextVertex] < weights[currentVertex]) { // проверка на родителя
                if (weights[nextVertex] * 2 >= weights[currentVertex]) { // большой сын
                    roots[nextVertex] = currentRoot;
                    buildPathsDfs(nextVertex);
                } else {
                    roots[nextVertex] = nextVertex;
                    buildPathsDfs(nextVertex);
                }
            }
        }
    }

    static int sum(int v, int l, int r, int NEEDEDLEFT, int NEEDEDRIGHT, int treeIndex, int add) {
        if (NEEDEDLEFT > r || NEEDEDRIGHT < l) { // does not cross
            return 0;
        }

        if (NEEDEDLEFT <= l && NEEDEDRIGHT >= r) { // borders are equal or current in needed
            return add + adds[treeIndex][v];
        }
        // needed in current or crosses
        int m = (l + r) / 2;
        return sum(v * 2 + 1, l, m, NEEDEDLEFT, NEEDEDRIGHT, treeIndex,
                add + adds[treeIndex][v]) +
                sum(v * 2 + 2, m + 1, r, NEEDEDLEFT, NEEDEDRIGHT, treeIndex, add + adds[treeIndex][v]);
    }

    static void add(int v, int q, int l, int r, int NEEDEDLEFT, int NEEDEDRIGHT, int treeIndex) {
        if (NEEDEDLEFT > r || NEEDEDRIGHT < l) { // does not cross
            return;
        }
        if (NEEDEDLEFT <= l && NEEDEDRIGHT >= r) { // borders are equal or current in needed
            adds[treeIndex][v] += q;
            return;
        } else { // needed in current or crosses
            int m = (l + r) / 2;
            add(v * 2 + 1, q, l, m, NEEDEDLEFT, NEEDEDRIGHT, treeIndex);
            add(v * 2 + 2, q, m + 1, r, NEEDEDLEFT, NEEDEDRIGHT, treeIndex);
        }
    }

    static void dfs(int vertex) {
        used[vertex] = true;
        for (int nextVertex : paths[vertex]) {
            if (!used[nextVertex]) {
                weights[nextVertex]++;
                parents[nextVertex] = vertex;
                dfs(nextVertex);
                weights[vertex] += weights[nextVertex];
            }
        }
    }

    static int lca(int left, int right) {
        if (left == right) {
            return left;
        }
        if (roots[left] == roots[right]) {
            if (weights[left] > weights[right]) {
                return left;
            } else {
                return right;
            }
        }
        if (weights[roots[left]] < weights[roots[right]]) {
            return lca(parents[roots[left]], right);
        }
        return lca(left, parents[roots[right]]);
    }

    static int count(int vertex, int ancestor, int currentCount) {
        if (vertex == ancestor) {
            return currentCount;
        }
        if (roots[vertex] == roots[ancestor]) {
            return currentCount + sum(0, xOfTree[roots[vertex]] - 1, 2 * xOfTree[roots[vertex]] - 2,
                    indexInTree[ancestor] + xOfTree[roots[vertex]],
                    indexInTree[vertex] + xOfTree[roots[vertex]] - 1,
                    treeIndexes[roots[vertex]], 0);
        }
        return count(parents[roots[vertex]], ancestor, currentCount +
                sum(0, xOfTree[roots[vertex]] - 1, 2 * xOfTree[roots[vertex]] - 2,
                        indexInTree[roots[vertex]] + xOfTree[roots[vertex]] - 1,
                        indexInTree[vertex] + xOfTree[roots[vertex]] - 1,
                        treeIndexes[roots[vertex]], 0));
    }

    static void add(int vertex, int ancestor) {
        if (vertex == ancestor) {
            return;
        }
        if (roots[vertex] == roots[ancestor]) {
            add(0, 1, xOfTree[roots[vertex]] - 1, 2 * xOfTree[roots[vertex]] - 2,
                    indexInTree[ancestor] + xOfTree[roots[vertex]],
                    indexInTree[vertex] + xOfTree[roots[vertex]] - 1, treeIndexes[roots[vertex]]);
            return;
        }
        add(0, 1, xOfTree[roots[vertex]] - 1, 2 * xOfTree[roots[vertex]] - 2,
                indexInTree[roots[vertex]] + xOfTree[roots[vertex]] - 1,
                indexInTree[vertex] + xOfTree[roots[vertex]] - 1, treeIndexes[roots[vertex]]);
        add(parents[roots[vertex]], ancestor);
    }

    public static void main(String[] args) throws IOException {
        InputReader in = new InputReader(System.in);
        OutputWriter out = new OutputWriter(System.out);
        int n = in.readInt();
        int m = in.readInt();
        paths = new HashSet[n + 1];
        used = new boolean[n + 1];
        roots = new int[n + 1];
        weights = new int[n + 1];
        treeSizes = new int[n + 1];
        treeIndexes = new int[n + 1];
        indexInTree = new int[n + 1];
        parents = new int[n + 1];
        xOfTree = new int[n + 1];

        for (int i = 0; i < n + 1; i++) {
            paths[i] = new HashSet<>();
        }

        // fill paths
        for (int i = 0; i < n - 1; i++) {
            int son = in.readInt();
            int parent = in.readInt();
            paths[son].add(parent);
            paths[parent].add(son);
        }

        // prepare lca tree
        weights[1] = 1;
        dfs(1);

        roots[1] = 1;
        buildPathsDfs(1);

        // build trees for paths
        adds = new int[countOfTrees + 1][];
        boolean[] wasResized = new boolean[n + 1];
        for (int i = 1; i < n + 1; i++) {
            if (!wasResized[roots[i]]) {
                int v = treeSizes[roots[i]];
                wasResized[roots[i]] = true;
                v--;
                v |= v >> 1;
                v |= v >> 2;
                v |= v >> 4;
                v |= v >> 8;
                v |= v >> 16;
                v++;
                adds[treeIndexes[roots[i]]] = new int[2 * v - 1];
                xOfTree[roots[i]] = v;
            }
        }

        for (int i = 0; i < m; i++) {
            String query = in.readString();
            int left = in.readInt();
            int right = in.readInt();
            if (query.equals("P")) {
                // add
                int ancestor = lca(left, right);
                add(left, ancestor);
                add(right, ancestor);
            } else {
                // count
                if (parents[left] == right) {
                    out.printLineln(count(left, right, 0));
                } else {
                    out.printLineln(count(right, left, 0));
                }
            }
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
