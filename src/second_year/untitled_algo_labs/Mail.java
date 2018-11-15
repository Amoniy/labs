package second_year.untitled_algo_labs;

import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.HashSet;
import java.util.InputMismatchException;
import java.util.LinkedList;
import java.util.Queue;

public class Mail {

    static int[] ret;

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
    static int[][] tree;
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

    static int count(int vertex, int ancestor, int currentMax) {
        if (vertex == ancestor) {
            return maximum(currentMax, tree[treeIndexes[roots[vertex]]][indexInTree[vertex] + xOfTree[roots[vertex]] - 1]);
        }
        if (roots[vertex] == roots[ancestor]) {
            return maximum(currentMax, max(0, xOfTree[roots[vertex]] - 1, 2 * xOfTree[roots[vertex]] - 2,
                    indexInTree[ancestor] + xOfTree[roots[vertex]] - 1,
                    indexInTree[vertex] + xOfTree[roots[vertex]] - 1,
                    treeIndexes[roots[vertex]]));
        }
        return maximum(currentMax, count(parents[roots[vertex]], ancestor,
                max(0, xOfTree[roots[vertex]] - 1, 2 * xOfTree[roots[vertex]] - 2,
                        indexInTree[roots[vertex]] + xOfTree[roots[vertex]] - 1,
                        indexInTree[vertex] + xOfTree[roots[vertex]] - 1,
                        treeIndexes[roots[vertex]])));
    }

    static void set(int vertex, int value) {
        tree[treeIndexes[roots[vertex]]][indexInTree[vertex] + xOfTree[roots[vertex]] - 1] = value;
        goup(indexInTree[vertex] + xOfTree[roots[vertex]] - 1, treeIndexes[roots[vertex]]);
    }

    static int max(int v, int l, int r, int NEEDEDLEFT, int NEEDEDRIGHT, int treeIndex) {
        int x = 0;

        if (NEEDEDLEFT > r || NEEDEDRIGHT < l) {//does not cross
            return x;
        }

        if (NEEDEDLEFT <= l && NEEDEDRIGHT >= r) {//borders are equal or current in needed
            return tree[treeIndex][v];
        }
        if (l <= NEEDEDLEFT && r >= NEEDEDRIGHT) {//needed in current
            int m = (l + r) / 2;
            return maximum(max(v * 2 + 1, l, m, NEEDEDLEFT, NEEDEDRIGHT, treeIndex),
                    max(v * 2 + 2, m + 1, r, NEEDEDLEFT, NEEDEDRIGHT, treeIndex));
        }

        int m = (l + r) / 2;
        return maximum(max(v * 2 + 1, l, m, NEEDEDLEFT, NEEDEDRIGHT, treeIndex),
                max(v * 2 + 2, m + 1, r, NEEDEDLEFT, NEEDEDRIGHT, treeIndex));
    }

    static void goup(int v, int treeIndex) {
        if (v == 0) {
            return;
        }
        if (v % 2 == 1) { // leftson
            if (tree[treeIndex][(v - 1) / 2] != maximum(tree[treeIndex][v], tree[treeIndex][v + 1])) {
                tree[treeIndex][(v - 1) / 2] = maximum(tree[treeIndex][v], tree[treeIndex][v + 1]);
                goup((v - 1) / 2, treeIndex);
            }
        } else {
            if (tree[treeIndex][(v - 1) / 2] != maximum(tree[treeIndex][v], tree[treeIndex][v - 1])) {
                tree[treeIndex][(v - 1) / 2] = maximum(tree[treeIndex][v], tree[treeIndex][v - 1]);
                goup((v - 1) / 2, treeIndex);
            }
        }
    }

    static int maximum(int f, int s) {
        if (f > s) {
            return f;
        }
        return s;
    }

    static void build(int[] a, int v, int l, int r) {
        if (l == r)
            ret[v] = a[l];
        else {
            int m = (l + r) / 2;
            build(a, v * 2 + 1, l, m);
            build(a, v * 2 + 2, m + 1, r);
            ret[v] = maximum(ret[v * 2 + 1], ret[v * 2 + 2]);
        }
    }

    public static void main(String[] args) throws IOException {
        String sourceFileName = "mail.in";
        String destinationFileName = "mail.out";
        FileInputStream fileInputStream = new FileInputStream(sourceFileName);
        FileOutputStream fileOutputStream = new FileOutputStream(destinationFileName);
        InputReader in = new InputReader(fileInputStream);
        OutputWriter out = new OutputWriter(fileOutputStream);
        int n = in.readInt();
        Queue<Integer> towers = new LinkedList<>();
        for (int i = 0; i < n; i++) {
            towers.add(in.readInt());
        }
        paths = new HashSet[n + 1];
        used = new boolean[n + 1];
        roots = new int[n + 1];
        weights = new int[n + 1];
        treeSizes = new int[n + 1];
        treeIndexes = new int[n + 1];
        indexInTree = new int[n + 1];
        parents = new int[n + 1];
        xOfTree = new int[n + 1];

        for (int i = 1; i < n + 1; i++) {
            paths[i] = new HashSet<>();
        }

        // fill paths
        for (int i = 0; i < n - 1; i++) {
            int son = in.readInt();
            int parent = in.readInt();
            paths[son].add(parent);
            paths[parent].add(son);
        }

        int m = in.readInt();

        // prepare lca tree
        weights[1] = 1;
        dfs(1);

        roots[1] = 1;
        buildPathsDfs(1);

        // build trees for paths
        tree = new int[countOfTrees + 1][];
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
                tree[treeIndexes[roots[i]]] = new int[2 * v - 1];
                xOfTree[roots[i]] = v;
            }
        }

        for (int i = 1; i < n + 1; i++) {
            tree[treeIndexes[roots[i]]][indexInTree[i] + xOfTree[roots[i]] - 1] = towers.poll();
        }
        boolean[] wasBuilt = new boolean[n + 1];
        for (int i = 1; i < n + 1; i++) {
            if (!wasBuilt[roots[i]]) {
                ret = new int[xOfTree[roots[i]] * 2 - 1];
                build(tree[treeIndexes[roots[i]]], 0, xOfTree[roots[i]] - 1, 2 * xOfTree[roots[i]] - 2);
                tree[treeIndexes[roots[i]]] = ret;
                wasBuilt[roots[i]] = true;
            }
        }

        for (int i = 0; i < m; i++) {
            String query = in.readString();
            int left = in.readInt();
            int right = in.readInt();
            if (query.equals("!")) {
                // set
                set(left, right);
            } else {
                // count
                int ancestor = lca(left, right);
                out.printLineln(String.valueOf(maximum(count(left, ancestor, 0), count(right, ancestor, 0))));
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

    public static class element {
        int number;
        int[] inputs;
        int[] output;

        public element(int number, int[] inputs, int[] output) {
            this.number = number;
            this.inputs = inputs;
            this.output = output;
        }
    }

    public static int binIterative(int x, int[] array) {
        int l = -1;
        int r = array.length;
        while (r > l + 1) {
            int m = (l + r) / 2;
            if (array[m] >= x) {
                r = m;
            } else {
                l = m;
            }
        }
        return r;
    }
}
