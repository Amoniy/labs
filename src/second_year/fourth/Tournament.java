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

public class Tournament {

    private static int vertexCount;
    private static ArrayList<Edge> currentRoute;

    private static class Edge {
        int from;
        int to;
        int flow;
        int capacity;
        Edge link;
        Edge reversedEdge;
        Edge mirroredEdge;
        boolean isReversed;

        Edge(int from, int to, int capacity) {
            this.from = from;
            this.to = to;
            this.capacity = capacity;
            flow = 0;
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
        if (vertex == vertexCount - 1) {
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
        InputReader reader = new InputReader(new FileInputStream("txt.in"));
        OutputWriter writer = new OutputWriter(new FileOutputStream("txt.out"));
        int realVertexCount = reader.readInt();
        vertexCount = realVertexCount * 2 + 2;
        int edgeCount = 0;

        Vector<Edge>[] edges = new Vector[vertexCount];
        for (int i = 0; i < vertexCount; i++) {
            edges[i] = new Vector<>();
        }

        Vector<Edge> initialEdges = new Vector<>();

        for (int i = 1; i < realVertexCount + 1; i++) {
            edgeCount++;
            int from = 0;
            int to = i;
            int capacity = 3 * realVertexCount;
            Edge initialEdge = new Edge(from, to, capacity);
            Edge edge = new Edge(from, to, capacity, initialEdge, false);
            Edge reversedEdge = new Edge(to, from, 0, initialEdge, true);
            initialEdges.add(initialEdge);
            edges[from].add(edge);
            edges[to].add(reversedEdge);
            initialEdge.link = edge;
            edge.reversedEdge = reversedEdge;
            reversedEdge.reversedEdge = edge;
        }

        char[][] answer = new char[realVertexCount][realVertexCount];
        int[] neededPoints = new int[realVertexCount];

        for (int i = 0; i < realVertexCount; i++) {
            String string = reader.readString();
            for (int j = i; j < realVertexCount; j++) {
                char c = string.charAt(j);
                if (c == '#') {
                    answer[i][j] = '#';
                    continue;
                }
                if (c == 'l') {
                    answer[i][j] = 'l';
                    answer[j][i] = 'w';
                    neededPoints[i] -= 1;
                    neededPoints[j] -= 2;
                    continue;
                }
                if (c == 'L') {
                    answer[i][j] = 'L';
                    answer[j][i] = 'W';
                    neededPoints[j] -= 3;
                    continue;
                }
                if (c == 'w') {
                    answer[i][j] = 'w';
                    answer[j][i] = 'l';
                    neededPoints[i] -= 2;
                    neededPoints[j] -= 1;
                    continue;
                }
                if (c == 'W') {
                    answer[i][j] = 'W';
                    answer[j][i] = 'L';
                    neededPoints[i] -= 3;
                    continue;
                }

                edgeCount++;
                edgeCount++;

                int from = i + 1;
                int to = j + 1;
                int capacity = 3;
                Edge initialEdge = new Edge(from, to + realVertexCount, capacity);
                Edge edge = new Edge(from, to + realVertexCount, capacity, initialEdge, false);
                Edge reversedEdge = new Edge(to + realVertexCount, from, capacity, initialEdge, true);

                Edge mirroredInitialEdge = new Edge(to, from + realVertexCount, capacity);
                Edge mirroredEdge = new Edge(to, from + realVertexCount, capacity, mirroredInitialEdge, false);
                Edge mirroredReversedEdge = new Edge(from + realVertexCount, to, capacity, mirroredInitialEdge, true);

                initialEdges.add(initialEdge);
                initialEdges.add(mirroredInitialEdge);

                edges[from].add(edge);
                edges[to].add(mirroredEdge);
                edges[to + realVertexCount].add(reversedEdge);
                edges[from + realVertexCount].add(mirroredReversedEdge);

                edge.reversedEdge = reversedEdge;
                reversedEdge.reversedEdge = edge;
                mirroredEdge.reversedEdge = mirroredReversedEdge;
                mirroredReversedEdge.reversedEdge = mirroredEdge;

                edge.mirroredEdge = mirroredEdge;
                mirroredEdge.mirroredEdge = edge;
                reversedEdge.mirroredEdge = mirroredReversedEdge;
                mirroredReversedEdge.mirroredEdge = reversedEdge;

                initialEdge.link = edge;
                mirroredInitialEdge.link = mirroredEdge;
            }
        }

        for (int i = 0; i < realVertexCount; i++) {
            neededPoints[i] += reader.readInt();
        }

        for (int i = 1; i < realVertexCount + 1; i++) {
            edgeCount++;
            int from = i + realVertexCount;
            int to = 2 * realVertexCount + 1;
            int capacity = neededPoints[i - 1]; // сколько баллов нужно итой команде такая и пропускная способность
            Edge initialEdge = new Edge(from, to, capacity);
            Edge edge = new Edge(from, to, capacity, initialEdge, false);
            Edge reversedEdge = new Edge(to, from, 0, initialEdge, true);
            initialEdges.add(initialEdge);
            edges[from].add(edge);
            edges[to].add(reversedEdge);
            initialEdge.link = edge;
            edge.reversedEdge = reversedEdge;
            reversedEdge.reversedEdge = edge;
        }

        while (true) {
            int[] distance = new int[vertexCount];
            distance[0] = 0;
            for (int i = 1; i < vertexCount; i++) {
                distance[i] = Integer.MAX_VALUE;
            }
            boolean[] wasVisited = new boolean[vertexCount];
            wasVisited[0] = true;
            Queue<Integer> queue = new LinkedList<>();
            queue.add(0);
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

            if (distance[vertexCount - 1] == Integer.MAX_VALUE) { // пути не существует
                break;
            }

            Vector<Edge>[] layeredNetwork = new Vector[vertexCount];
            for (int i = 0; i < vertexCount; i++) {
                layeredNetwork[i] = new Vector<>();
            }
            for (int i = 0; i < edgeCount; i++) {
                if (distance[initialEdges.get(i).link.from] == distance[initialEdges.get(i).link.to] - 1) {
                    layeredNetwork[initialEdges.get(i).link.from].add(initialEdges.get(i).link);
                } else if (distance[initialEdges.get(i).link.reversedEdge.from] == distance[initialEdges.get(i).link.reversedEdge.to] - 1) {
                    layeredNetwork[initialEdges.get(i).link.reversedEdge.from].add(initialEdges.get(i).link.reversedEdge);
                }
            }
            currentRoute = new ArrayList<>();
            while (dfs(0, layeredNetwork)) {

                int minCapacity = Integer.MAX_VALUE;
                for (int i = 0; i < currentRoute.size(); i++) {
                    if (minCapacity > currentRoute.get(i).capacity) {
                        minCapacity = currentRoute.get(i).capacity;
                    }
                }
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
                    if (currentEdge.mirroredEdge != null) { // ребро не начальное и не конечное
                        Edge mirroredEdge = currentEdge.mirroredEdge;
                        mirroredEdge.flow -= minCapacity;
                        mirroredEdge.capacity += minCapacity;
                        mirroredEdge.reversedEdge.flow += minCapacity;
                        mirroredEdge.reversedEdge.capacity -= minCapacity;
                        //?
                        if (!mirroredEdge.isReversed) {
                            mirroredEdge.link.flow -= minCapacity;
                        } else {
                            mirroredEdge.link.flow += minCapacity;
                        }
                        //?
                    }
                }
                currentRoute = new ArrayList<>();

            }
        }

        for (int i = 0; i < edgeCount; i++) {
            Edge edge = initialEdges.get(i);
            if (edge.from == 0 || edge.to == vertexCount - 1) {
                continue;
            }
            int from = edge.from - 1;
            int to = edge.to - realVertexCount - 1;
            if (edge.flow == 0) {
                answer[from][to] = 'L';
            } else if (edge.flow == 1) {
                answer[from][to] = 'l';
            } else if (edge.flow == 2) {
                answer[from][to] = 'w';
            } else if (edge.flow == 3) {
                answer[from][to] = 'W';
            }
        }
        // проход по ребрам и заполнение ответа

        for (int i = 0; i < realVertexCount; i++) {
            for (int j = 0; j < realVertexCount; j++) {
                writer.printLine(answer[i][j]);
            }
            writer.printLineln();
        }

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
