package second_year.fourth;

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
import java.util.LinkedList;
import java.util.Queue;
import java.util.Vector;

public class Snails {

    private static int vertexCount;
    private static ArrayList<Edge> currentRoute;
    private static int finish;
    private static int totalFlow = 0;

    private static class Edge {
        int from;
        int to;
        int flow;
        int capacity;
        Edge link;
        Edge reversedEdge;
        int index;
        boolean isReversed;

        Edge(int from, int to, int capacity, int index) {
            this.from = from;
            this.to = to;
            this.capacity = capacity;
            flow = 0;
            this.index = index;
        }

        Edge(int from, int to, int capacity, Edge link, boolean isReversed) {
            this.from = from;
            this.to = to;
            this.capacity = capacity;
            flow = 0;
            this.link = link;
            this.isReversed = isReversed;
        }
    }

    private static boolean dfs(int vertex, Vector<Edge>[] edges) {
        if (vertex == finish) {
            return true;
        }
        boolean flag = false;
        for (int i = 0; i < edges[vertex].size(); i++) {
            if (edges[vertex].get(i).capacity > 0) {
                int son = edges[vertex].get(i).to;

                if (dfs(son, edges)) {

                    currentRoute.add(edges[vertex].get(i));
                    return true;
                } else { // дальше нет пути до стока
                    edges[vertex].remove(i);
                    i--;
                }

            } else { // по ребру уже нельзя пройти
                edges[vertex].remove(i);
                i--;
            }
        }
        return flag;
    }

    public static void main(String[] args) throws IOException {
        InputReader reader = new InputReader(new FileInputStream("snails.in"));
        OutputWriter writer = new OutputWriter(new FileOutputStream("snails.out"));
        vertexCount = reader.readInt();
        int edgeCount = reader.readInt();
        int start = reader.readInt() - 1;
        finish = reader.readInt() - 1;
        if (edgeCount == 0) {
            writer.printLineln("NO");
            writer.flush();
            writer.close();
            return;
        }

        Vector<Edge>[] edges = new Vector[vertexCount];
        for (int i = 0; i < vertexCount; i++) {
            edges[i] = new Vector<>();
        }

        Edge[] initialEdges = new Edge[edgeCount];

        for (int i = 0; i < edgeCount; i++) {
            int from = reader.readInt() - 1;
            int to = reader.readInt() - 1;
            int capacity = 1;
            if (from == to) {
                capacity = 0;
            }
            Edge initialEdge = new Edge(from, to, capacity, i);
            initialEdges[i] = initialEdge;
            Edge edge = new Edge(from, to, capacity, initialEdge, false);
            edges[from].add(edge); // совпадает
            Edge reversedEdge = new Edge(to, from, 0, initialEdge, true);
            edges[to].add(reversedEdge); // противоположное to!=initial.to => противоположное направление => вывод с минусом

            edge.reversedEdge = reversedEdge;
            reversedEdge.reversedEdge = edge;
            initialEdge.link = edge;
        }

        while (true) {
            int[] distance = new int[vertexCount];
            for (int i = 0; i < vertexCount; i++) {
                distance[i] = Integer.MAX_VALUE;
            }
            distance[start] = 0;
            boolean[] wasVisited = new boolean[vertexCount];
            wasVisited[start] = true;
            Queue<Integer> queue = new LinkedList<>();
            queue.add(start);
            while (!queue.isEmpty()) {
                int currentVertex = queue.poll();
                for (int i = 0; i < edges[currentVertex].size(); i++) {
                    if (edges[currentVertex].get(i).capacity > 0) {
                        int to = edges[currentVertex].get(i).to;
                        if (!wasVisited[to]) {
                            queue.add(to);
                            distance[to] = distance[currentVertex] + 1;
                            wasVisited[to] = true;
                        }
                    }
                }
            } // нашли расстояния до вершин от старта

            if (distance[finish] == Integer.MAX_VALUE) { // пути не существует
                break;
            }

            Vector<Edge>[] layeredNetwork = new Vector[vertexCount];
            for (int i = 0; i < vertexCount; i++) {
                layeredNetwork[i] = new Vector<>();
            }
            for (int i = 0; i < edgeCount; i++) {
                if (distance[initialEdges[i].link.from] == distance[initialEdges[i].link.to] - 1) {
                    layeredNetwork[initialEdges[i].link.from].add(initialEdges[i].link);
                } else if (distance[initialEdges[i].link.reversedEdge.from] == distance[initialEdges[i].link.reversedEdge.to] - 1) {
                    layeredNetwork[initialEdges[i].link.reversedEdge.from].add(initialEdges[i].link.reversedEdge);
                }
            }
            currentRoute = new ArrayList<>();
            while (dfs(start, layeredNetwork)) {

                int minCapacity = Integer.MAX_VALUE;
                for (int i = 0; i < currentRoute.size(); i++) {
                    if (minCapacity > currentRoute.get(i).capacity) {
                        minCapacity = currentRoute.get(i).capacity;
                    }
                } // теоретически for можно убрать

                for (int i = 0; i < currentRoute.size(); i++) {
                    Edge currentEdge = currentRoute.get(i);
                    currentEdge.flow += minCapacity;
                    currentEdge.capacity -= minCapacity;
                    currentEdge.reversedEdge.flow -= minCapacity;
                    currentEdge.reversedEdge.capacity += minCapacity;
                    if (!currentEdge.isReversed) {
                        currentEdge.link.flow += minCapacity;
                    } else {
                        currentEdge.link.flow -= minCapacity;
                    }
                }
                totalFlow++;
                currentRoute = new ArrayList<>();
                if (totalFlow == 2) {
                    break;
                }
            }
            if (totalFlow == 2) {
                break;
            }
        }

        if (totalFlow < 2) {
            writer.printLineln("NO");
            writer.flush();
            writer.close();
            return;
        }

        writer.printLineln("YES");
        boolean[] wasVisited = new boolean[edgeCount];
        Queue<Integer> queue = new LinkedList<>();
        queue.add(start);
        writer.printLine(start + 1);
        while (!queue.isEmpty()) {
            int currentVertex = queue.poll();
            if (currentVertex == finish) {
                queue.clear();
                break;
            }
            for (int i = 0; i < edges[currentVertex].size(); i++) {
                if (edges[currentVertex].get(i).flow > 0) {
                    int to = edges[currentVertex].get(i).to;
                    int edgeIndex;
                    if (edges[currentVertex].get(i).isReversed) {
                        edgeIndex = edges[currentVertex].get(i).reversedEdge.link.index;
                    } else {
                        edgeIndex = edges[currentVertex].get(i).link.index;
                    }
                    if (!wasVisited[edgeIndex]) {
                        wasVisited[edgeIndex] = true;
                        writer.printLine(" " + (to + 1));
                        queue.add(to);
                        break;
                    }
                }
            }
        }
        writer.printLineln();

        writer.printLine(start + 1);
        queue.add(start);
        while (!queue.isEmpty()) {
            int currentVertex = queue.poll();
            if (currentVertex == finish) {
                break;
            }
            for (int i = 0; i < edges[currentVertex].size(); i++) {
                if (edges[currentVertex].get(i).flow > 0) {
                    int to = edges[currentVertex].get(i).to;
                    int edgeIndex;
                    if (edges[currentVertex].get(i).isReversed) {
                        edgeIndex = edges[currentVertex].get(i).reversedEdge.link.index;
                    } else {
                        edgeIndex = edges[currentVertex].get(i).link.index;
                    }
                    if (!wasVisited[edgeIndex]) {
                        wasVisited[edgeIndex] = true;
                        writer.printLine(" " + (to + 1));
                        queue.add(to);
                        break;
                    }
                }
            }
        }
        writer.printLineln();

        writer.flush();
        writer.close();
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
