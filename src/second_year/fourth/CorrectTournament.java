package second_year.fourth;

import java.io.BufferedWriter;
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

public class CorrectTournament {

    private static int vertexCount;
    private static ArrayList<Edge> currentRoute;

    private static class Edge {
        int from;
        int to;
        int flow;
        int capacity;
        Edge link;
        Edge reversedEdge;
        int index;
        boolean isReversed;
        int team;
        int opposingTeam;

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
//        InputReader reader = new InputReader(new FileInputStream("txt.in"));
//        OutputWriter writer = new OutputWriter(new FileOutputStream("txt.out"));
        InputReader reader = new InputReader(System.in);
        OutputWriter writer = new OutputWriter(System.out);
        int realVertexCount = reader.readInt();
        int matches = realVertexCount * (realVertexCount - 1) / 2;
        int[] neededPoints = new int[realVertexCount];
        String[] strings = new String[realVertexCount];
        for (int i = 0; i < realVertexCount; i++) {
            strings[i] = reader.readString();
            int curMatches = strings[i].length() - strings[i].replaceAll("W", "").replaceAll("w", "").length();
            matches -= curMatches;
        }
        vertexCount = matches + realVertexCount + 2;

        int edgeCount = realVertexCount + matches + 2 * matches;
        Vector<Edge>[] edges = new Vector[vertexCount];
        for (int i = 0; i < vertexCount; i++) {
            edges[i] = new Vector<>();
        }
        Edge[] initialEdges = new Edge[edgeCount];

        char[][] answer = new char[realVertexCount][realVertexCount];
        int currentEdgeCount = 0;
        int currentMatchIndex = 0;
        for (int i = 0; i < realVertexCount; i++) {
            String string = strings[i];
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

                // .
                currentMatchIndex++;
                int from = currentMatchIndex;
                int to = i + matches + 1;
                int secondTo = j + matches + 1;
                int capacity = 3;

                // to first team

                Edge initialEdge = new Edge(from, to, capacity, i);
                initialEdges[currentEdgeCount] = initialEdge;
                currentEdgeCount++;
                Edge edge = new Edge(from, to, capacity, initialEdge, false);
                edges[from].add(edge);
                Edge reversedEdge = new Edge(to, from, 0, initialEdge, true);
                edges[to].add(reversedEdge);

                edge.reversedEdge = reversedEdge;
                reversedEdge.reversedEdge = edge;
                initialEdge.link = edge;

                initialEdge.team = i;
                initialEdge.opposingTeam = j;

                // to second team

                Edge secondInitialEdge = new Edge(from, secondTo, capacity, i);
                initialEdges[currentEdgeCount] = secondInitialEdge;
                currentEdgeCount++;
                Edge secondEdge = new Edge(from, secondTo, capacity, secondInitialEdge, false);
                edges[from].add(secondEdge);
                Edge secondReversedEdge = new Edge(secondTo, from, 0, secondInitialEdge, true);
                edges[secondTo].add(secondReversedEdge);

                secondEdge.reversedEdge = secondReversedEdge;
                secondReversedEdge.reversedEdge = secondEdge;
                secondInitialEdge.link = secondEdge;

                secondInitialEdge.team = j;
                secondInitialEdge.opposingTeam = i;
            }
        }
        for (int i = 0; i < realVertexCount; i++) {
            neededPoints[i] += reader.readInt();
        }
        // from s
        for (int i = 0; i < matches; i++) {
            int from = 0;
            int to = i + 1;
            int capacity = 3;

            Edge initialEdge = new Edge(from, to, capacity, i);
            initialEdges[currentEdgeCount] = initialEdge;
            currentEdgeCount++;
            Edge edge = new Edge(from, to, capacity, initialEdge, false);
            edges[from].add(edge);
            Edge reversedEdge = new Edge(to, from, 0, initialEdge, true);
            edges[to].add(reversedEdge);

            edge.reversedEdge = reversedEdge;
            reversedEdge.reversedEdge = edge;
            initialEdge.link = edge;
        }
        // to t
        for (int i = 0; i < realVertexCount; i++) {
            int from = i + matches + 1;
            int to = vertexCount - 1;
            int capacity = neededPoints[i];

            Edge initialEdge = new Edge(from, to, capacity, i);
            initialEdges[currentEdgeCount] = initialEdge;
            currentEdgeCount++;
            Edge edge = new Edge(from, to, capacity, initialEdge, false);
            edges[from].add(edge);
            Edge reversedEdge = new Edge(to, from, 0, initialEdge, true);
            edges[to].add(reversedEdge);

            edge.reversedEdge = reversedEdge;
            reversedEdge.reversedEdge = edge;
            initialEdge.link = edge;
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
                if (distance[initialEdges[i].link.from] == distance[initialEdges[i].link.to] - 1) {
                    layeredNetwork[initialEdges[i].link.from].add(initialEdges[i].link);
                } else if (distance[initialEdges[i].link.reversedEdge.from] == distance[initialEdges[i].link.reversedEdge.to] - 1) {
                    layeredNetwork[initialEdges[i].link.reversedEdge.from].add(initialEdges[i].link.reversedEdge);
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
                }
                currentRoute = new ArrayList<>();

            }
        }

        for (int i = 0; i < matches * 2; i++) {
            Edge edge = initialEdges[i];
            int first = edge.team;
            int second = edge.opposingTeam;
            if (edge.flow == 3) { // first won over second
                answer[first][second] = 'W';
                answer[second][first] = 'L';
            } else if (edge.flow == 2) {
                answer[first][second] = 'w';
                answer[second][first] = 'l';
            } else if (edge.flow == 1) {
                answer[first][second] = 'l';
                answer[second][first] = 'w';
            } else if (edge.flow == 0) {
                answer[first][second] = 'L';
                answer[second][first] = 'W';
            }
        }

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
