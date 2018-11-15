package second_year.untitled_algo_labs;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.InputMismatchException;
import java.util.Vector;

public class Planar {

    static class Pair {
        public int first;
        public int second;
    }

    static int N = (int) 3e5;

    static Vector<Pair> e;
    static int n;
    static int m;
    static int[] col;
    static int[] x;

    static boolean edge(Pair a, Pair b) {
        if (x[a.first] > x[b.first]) {
            Pair temp = new Pair();
            temp.first = a.first;
            temp.second = a.second;
            a = new Pair();
            a.first = b.first;
            a.second = b.second;
            b = new Pair();
            b.first = temp.first;
            b.second = temp.second;
        }
        return (x[a.first] < x[b.first]) && (x[b.first] < x[a.second]) && (x[a.second] < x[b.second]);
    }

    static void exit() {
        System.out.println("NO");
        System.exit(0);
    }

    static void dfs(int v, int color) {
        col[v] = color;
        for (int to = 0; to < m; to++) {
            if (edge(e.get(v), e.get(to))) {
                if (col[to] == 0) {
                    dfs(to, 3 - color);
                } else if (col[to] == col[v]) {
                    exit();
                }
            }
        }
    }

    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
//        OutputWriter out = new OutputWriter(System.out);
        n = in.readInt();
        m = in.readInt();
        col = new int[N];
        x = new int[N];

        int[] p = new int[n];
        e = new Vector<>(N);
        for (int i = 0; i < m; i++) {
            int first = in.readInt();
            int second = in.readInt();
            e.add(new Pair());
            e.get(i).first = first - 1;
            e.get(i).second = second - 1;
        }
        for (int i = 0; i < n; i++) {
            p[i] = in.readInt() - 1;
            x[p[i]] = i;
        }
        for (int i = 0; i < m; i++) {
            if (x[e.get(i).first] > x[e.get(i).second]) {
                int temp = e.get(i).first;
                e.get(i).first = e.get(i).second;
                e.get(i).second = temp;
            }
        }
        for (int i = 0; i < m; i++) {
            if (col[i] == 0) {
                dfs(i, 1);
            }
        }
        System.out.println("YES");
        for (int i = 0; i < n; ++i) {
            System.out.print(x[i] + " 0 ");
        }
        System.out.println();
        for (int i = 0; i < m; i++) {
            int v = e.get(i).first;
            int u = e.get(i).second;
            System.out.print(((double) (x[v] + x[u]) / 2.0) + " ");
            if (col[i] == 1) {
                System.out.println(Math.abs(x[v] - x[u]) / 2.0);
            } else {
                System.out.println(Math.abs(x[v] - x[u]) / -2.0);
            }
        }

//        out.flush();
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
